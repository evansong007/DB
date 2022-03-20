package edu.uob;

import edu.uob.DBCommand.*;
import edu.uob.DBExceptions.*;

import java.io.IOException;
import java.nio.file.Paths;


public class Parser {
    public DBcmd processingCentre;
    private Tokenizer query;
    TokenType tokenType = new TokenType();


    public DBcmd parseQuery(String command) throws QueryException {
        if (command.isEmpty()) throw new QueryIsEmptyException();
        query = new Tokenizer(command);
        String token = query.getFirstToken().toUpperCase();
        switch (token) {
            case "USE": {
                processingCentre = new UseCMD();
                parseUse();
                break;
            }
            case "CREATE": {
                processingCentre = new CreateCMD();
                parseCreat();
                break;
            }
            case "DROP": {
                processingCentre = new DropCMD();
                parseDrop();
                break;
            }
            case "ALTER": {
                processingCentre = new AlterCMD();
                parseAlter();
                break;
            }
            case "INSERT": {
                processingCentre = new InsertCMD();
                parseInsert();
                break;
            }
            case "SELECT": {
                processingCentre = new SelectCMD();
                parseSelect();
                break;
            }
            case "UPDATE": {
                processingCentre = new UpdateCMD();
                parseUpdate();
                break;
            }
            case "DELETE": {
                processingCentre = new DeleteCMD();
                parseDelete();
                break;
            }
            case "JOIN": {
                processingCentre = new JoinCMD();
                parseJoin();
                break;
            }
            default: {
                throw new QueryException();
            }
        }
        if (query.hasMoreToken()) throw new QueryException();
        return processingCentre;
    }

    private void parseUse() throws QueryException {
        isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
        processingCentre.setDataBase(query.getcurrentToken());
    }

    private void parseCreat() throws QueryException {
        String commandType = query.getNextToken();
        if (commandType.matches(TokenType.DATABASE)) {
            processingCentre.setCommandType("DATABASE");
            parseUse();

        } else if (commandType.matches(TokenType.TABLE)) {
            processingCentre.setCommandType("TABLE");
            isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
            processingCentre.addTableName(query.getcurrentToken());
            if (query.hasMoreToken()) {
                isTokenValid(TokenType.LEFTBRACKET, query.getNextToken());
                isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
                processingCentre.addAttributeList(query.getcurrentToken());
                parseAttributeList();
                isTokenValid(TokenType.RIGHTBRACKET, query.getcurrentToken());
            }
        }
    }

    public void parseAttributeList() throws QueryException {
        while (query.getNextToken().matches(TokenType.APOSTROPHE)) {
            isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
            processingCentre.addAttributeList(query.getcurrentToken());
        }
    }

    public void parseDrop() throws QueryException {
        String commandType = query.getNextToken();
        if (commandType.matches(TokenType.DATABASE)) {
            processingCentre.setCommandType("DATABASE");
            parseUse();
            return;
        } else if (commandType.matches(TokenType.TABLE)) {
            processingCentre.setCommandType("TABLE");
            isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
            processingCentre.addTableName(query.getcurrentToken());
            return;
        }

    }

    public void parseAlter() throws QueryException {
        isTokenValid(TokenType.TABLE, query.getNextToken());
        isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
        processingCentre.addTableName(query.getcurrentToken());
        isTokenValid(TokenType.AlTERATIONTYPE, query.getNextToken());
        processingCentre.setAlterationType(query.getcurrentToken());
        isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
        processingCentre.addValueList(query.getcurrentToken());
    }

    public void parseInsert() throws QueryException {
        isTokenValid(TokenType.INTO, query.getNextToken());
        isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
        processingCentre.addTableName(query.getcurrentToken());
        isTokenValid(TokenType.VALUES, query.getNextToken());
        isTokenValid(TokenType.LEFTBRACKET, query.getNextToken());
        isValue(query.getNextToken());
        processingCentre.addValueList(query.getcurrentToken());
        parseValueList();
        isTokenValid(TokenType.RIGHTBRACKET, query.getcurrentToken());
    }

    public void parseValueList() throws QueryException {
        while (query.getNextToken().matches(TokenType.APOSTROPHE)) {
            isValue(query.getNextToken());
            processingCentre.addValueList(query.getcurrentToken());
        }

    }

    public void isTokenValid(String tokenType, String token) throws QueryException {
        if (!token.matches(tokenType)) throw new QueryException();
    }

    public void isValue(String token) throws QueryException {
        if (!token.matches(TokenType.STRINGLITERAL) &&
                !token.matches(TokenType.BOOLEANLITERAL) &&
                !token.matches(TokenType.FLOATLITERAL) &&
                !token.matches(TokenType.INTEGERLITERAL) &&
                !token.matches(TokenType.NULL)) {
            throw new QueryException();
        }
    }

    public void parseSelect() throws QueryException {
        if (query.getNextToken().matches(TokenType.PLAINTEXT)) {
            processingCentre.addValueList(query.getcurrentToken());
            parseAttributeList();
            isTokenValid(TokenType.FROM, query.getcurrentToken());
            parseUse();
            if (query.hasMoreToken()) {
                isTokenValid(TokenType.WHERE, query.getNextToken());
                parseCondition();
            }
        } else if (query.getcurrentToken().matches("\\*")) {
            isTokenValid(TokenType.FROM, query.getNextToken());
            parseUse();
            if (query.hasMoreToken()) {
                isTokenValid(TokenType.WHERE, query.getNextToken());
                parseCondition();
            }
        }
    }

    public void parseCondition() throws QueryException {
        if (query.getNextToken().matches(TokenType.LEFTBRACKET)) {
            parseCondition();
            isTokenValid(TokenType.RIGHTBRACKET, query.getNextToken());
            isTokenValid(TokenType.LOGICALOPREATOR, query.getNextToken());
            String logicalOperator = query.getcurrentToken();
            isTokenValid(TokenType.LEFTBRACKET, query.getNextToken());
            parseCondition();
            isTokenValid(TokenType.RIGHTBRACKET, query.getNextToken());
            processingCentre.addConditionList(logicalOperator);
            return;

        } else if (query.getcurrentToken().matches(TokenType.PLAINTEXT)) {
            Condition condition = new Condition();
            condition.setAttributeName(query.getcurrentToken());
            isTokenValid(TokenType.OPERATOR, query.getNextToken());
            condition.setOperator(query.getcurrentToken());
            isValue(query.getNextToken());
            condition.setValue(query.getcurrentToken());
            processingCentre.addConditionList(condition);
            return;
        }
    }

    public void parseUpdate()throws QueryException{
        isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
        processingCentre.addTableName(query.getcurrentToken());
        isTokenValid(TokenType.SET, query.getNextToken());
        parseNameValueList();
        isTokenValid(TokenType.WHERE, query.getcurrentToken());
        parseCondition();

    }

    public void parseNameValuePair()throws QueryException{
        isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
        processingCentre.addAttributeList(query.getcurrentToken());
        isTokenValid(TokenType.EQUALS, query.getNextToken());
        isValue(query.getNextToken());
        processingCentre.addValueList(query.getcurrentToken());
    }

    public void parseNameValueList()throws QueryException{
        parseNameValuePair();
        while (query.getNextToken().matches(TokenType.APOSTROPHE)){
            parseNameValuePair();
        }
    }

    public void parseDelete()throws QueryException{
        isTokenValid(TokenType.FROM, query.getNextToken());
        isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
        processingCentre.addTableName(query.getcurrentToken());
        isTokenValid(TokenType.WHERE, query.getNextToken());
        parseCondition();
    }

    public void parseJoin()throws QueryException{
        isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
        processingCentre.addTableName(query.getcurrentToken());
        isTokenValid(TokenType.AND, query.getNextToken());
        isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
        processingCentre.addTableName(query.getcurrentToken());
        isTokenValid(TokenType.ON, query.getNextToken());
        isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
        processingCentre.addAttributeList(query.getcurrentToken());
        isTokenValid(TokenType.AND, query.getNextToken());
        isTokenValid(TokenType.PLAINTEXT, query.getNextToken());
        processingCentre.addAttributeList(query.getcurrentToken());
    }

}
