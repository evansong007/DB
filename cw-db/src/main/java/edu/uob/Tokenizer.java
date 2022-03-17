package edu.uob;


import edu.uob.DBExceptions.QueryException;
import edu.uob.DBExceptions.QueryIsNotFinishedException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.uob.TokenType.*;


public class Tokenizer {
    private String command;
    private final ArrayList<String> tokens = new ArrayList<>();
    private int currentTokenIndex = 0;
    private final ArrayList<String> stringLiterals = new ArrayList<>();

    public Tokenizer(String command) throws QueryException {
        this.command = command;
        optimiseCommands();
        setTokens();
        getStringLiterals();
    }

    private void setTokens() throws QueryException {
        StringTokenizer tokenizer = new StringTokenizer(command, " ");
        while (tokenizer.hasMoreElements()) {
            String currentToken = tokenizer.nextToken();
            tokens.add(currentToken);
        }
        if (!getLastToken().equals(";")) throw new QueryIsNotFinishedException();
        tokens.remove(getLastToken());
    }

    public String getFirstToken() {
        return tokens.get(0);
    }

    public String getNextToken() {
        currentTokenIndex++;
        return tokens.get(currentTokenIndex);
    }

    public String getcurrentToken() {
        return tokens.get(currentTokenIndex);
    }

    public String getLastToken() {
        return tokens.get(tokens.size() - 1);
    }

    public Boolean hasMoreToken() {
        return currentTokenIndex < tokens.size() - 1;
    }

    private void optimiseCommands() {
        setStringLiterals();
        command = command.replaceAll("('([^']*?)')", " value ");
        command = command.replaceAll(OPERATOR," $0 ");
        command = command.replaceAll(SINGLEQUAL, " $0 ");
        command = command.replaceAll("[,;()']"," $0 ");

    }

    private void setStringLiterals() {
        Pattern p = Pattern.compile(STRINGLITERAL);
        Matcher m = p.matcher(command);

        while (m.find()) {
            stringLiterals.add(m.group());
        }
    }

    private void getStringLiterals(){
        for (int value = 0, stringLiteral = 0; value < tokens.size(); value++)
        {
            if (tokens.get(value).equals("value"))
            {
                String temp = stringLiterals.get(stringLiteral++);
                tokens.set(value,temp);
            }
        }
    }


}
