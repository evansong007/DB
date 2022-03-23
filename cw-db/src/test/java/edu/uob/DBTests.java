package edu.uob;

import edu.uob.DBExceptions.QueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

// PLEASE READ:
// The tests in this file will fail by default for a template skeleton, your job is to pass them
// and maybe write some more, read up on how to write tests at
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
final class DBTests {

    private DBServer server;
    private Parser parser;
    private TokenType tokenType;

    // we make a new server for every @Test (i.e. this method runs before every @Test test case)
    @BeforeEach
    void setup(@TempDir File dbDir) {
        // Notice the @TempDir annotation, this instructs JUnit to create a new temp directory somewhere
        // and proceeds to *delete* that directory when the test finishes.
        // You can read the specifics of this at
        // https://junit.org/junit5/docs/5.4.2/api/org/junit/jupiter/api/io/TempDir.html

        // If you want to inspect the content of the directory during/after a test run for debugging,
        // simply replace `dbDir` here with your own File instance that points to somewhere you know.
        // IMPORTANT: If you do this, make sure you rerun the tests using `dbDir` again to make sure it
        // still works and keep it that way for the submission.
        //"C:\\Users\\songe\\Documents\\resources\\."

        server = new DBServer(dbDir);
        parser = new Parser();
        tokenType = new TokenType();
    }

    // Here's a basic test for spawning a new server and sending an invalid command,
    // the spec dictates that the server respond with something that starts with `[ERROR]`
    @Test
    void testInvalidCommandIsAnError() {
        assertTrue(server.handleCommand("CREATE DATABASE markbook;").startsWith("[OK]"));
        assertTrue(server.handleCommand("USE markbook;").startsWith("[OK]"));
        assertTrue(server.handleCommand("CREATE TABLE marks (name, mark, pass);").startsWith("[OK]"));
        assertTrue(server.handleCommand("INSERT INTO marks VALUES ('Steve', +65, TRUE);").startsWith("[OK]"));
        assertTrue(server.handleCommand("INSERT INTO marks VALUES ('Dave', 55, TRUE);").startsWith("[OK]"));
        assertTrue(server.handleCommand("INSERT INTO marks VALUES ('Bob', 35, FALSE);").startsWith("[OK]"));
        assertTrue(server.handleCommand("INSERT INTO marks VALUES ('Clive', 20, FALSE);").startsWith("[OK]"));
        assertTrue(server.handleCommand("SELECT * FROM marks;").startsWith("[OK]"));
        assertTrue(server.handleCommand("SELECT * FROM marks WHERE (pass == FALSE) AND (mark > 35);").startsWith("[OK]"));
        assertTrue(server.handleCommand("SELECT * FROM marks WHERE name LIKE 've';").startsWith("[OK]"));
        assertTrue(server.handleCommand("SELECT id FROM marks WHERE pass == FALSE;").startsWith("[OK]"));
        assertTrue(server.handleCommand("SELECT name FROM marks WHERE mark>60;").startsWith("[OK]"));
        assertTrue(server.handleCommand("DELETE FROM marks WHERE mark<40;").startsWith("[OK]"));
        assertTrue(server.handleCommand("SELECT * FROM marks").startsWith("[ERROR]"));
        assertTrue(server.handleCommand("SELECT * FROM crew;").startsWith("[ERROR]"));
        assertTrue(server.handleCommand("SELECT * FROM marks pass == TRUE;").startsWith("[ERROR]"));
    }

    // Add more unit tests or integration tests here.
    // Unit tests would test individual methods or classes whereas integration tests are geared
    // towards a specific usecase (i.e. creating a table and inserting rows and asserting whether the
    // rows are actually inserted)
    @Test
    void testInvalidTokenTypeError() {
        //test TokenType <PlainText>
        assertTrue("Name9".matches(TokenType.PLAINTEXT));
        assertFalse("na me9".matches(TokenType.PLAINTEXT));
        assertFalse("na&me9".matches(TokenType.PLAINTEXT));
        //test TokenType <StringLiteral>
        assertTrue("\'dasd ^&*%\'".matches(TokenType.STRINGLITERAL));
        assertFalse("\'dasd9^&*%\'".matches(TokenType.STRINGLITERAL));
        assertFalse("\'dasd\"&*%\'".matches(TokenType.STRINGLITERAL));
        assertFalse("\'dasd,&*%\'".matches(TokenType.STRINGLITERAL));
        assertFalse("\'dasd\'&*%\'".matches(TokenType.STRINGLITERAL));
        //test TokenType <Operator>
        assertTrue("==".matches(TokenType.OPERATOR));
        assertTrue("like".matches(TokenType.OPERATOR));
        assertFalse("= ".matches(TokenType.OPERATOR));
        assertFalse("<!".matches(TokenType.OPERATOR));
        //test TokenType <DATABASE|TABLE>
        assertTrue("DataBase".matches(TokenType.DATABASE));
        assertTrue("Table".matches(TokenType.TABLE));
        //test TokenType <FLOATLITERAL>
        assertTrue("0.12300".matches(TokenType.FLOATLITERAL));
        assertTrue("+02345.12300".matches(TokenType.FLOATLITERAL));
        assertTrue("-02345.12300".matches(TokenType.FLOATLITERAL));
        assertFalse("-02a45.123 00".matches(TokenType.FLOATLITERAL));
        //test TokenType <INTEGERLITERAL>
        assertTrue("123".matches(TokenType.INTEGERLITERAL));
        assertTrue("+003".matches(TokenType.INTEGERLITERAL));
        assertTrue("-090".matches(TokenType.INTEGERLITERAL));
    }

    @Test
    void testParser() throws QueryException {
        assertDoesNotThrow(() ->
                parser.parseQuery("Select * From table    where     age     >= 40 ;"));
        assertDoesNotThrow(() ->
                parser.parseQuery("Select * From table where age>=40;"));
        assertDoesNotThrow(() ->
                parser.parseQuery("Select * From table    where    ( age     <40)and (number < 50);"));
        assertDoesNotThrow(() ->
                parser.parseQuery("Select attribute1, attribute2  ,attribute3,attribute4 From table;"));
        assertDoesNotThrow(() ->
                parser.parseQuery("INSERT INTO table VALUES('name','home address', -18,+32.99);"));
        assertDoesNotThrow(() ->
                parser.parseQuery("UPDATE table SET name= 'wang',age=+20 WHERE id==20;"));

        assertThrows(QueryException.class,() ->
                parser.parseQuery("UPDTE table SET name= 'wang',age=+20 WHERE id==20;"));
    }

    
}
