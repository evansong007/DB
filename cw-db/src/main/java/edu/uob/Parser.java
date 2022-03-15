package edu.uob;

import DBExceptions.*;
import edu.uob.DBCommand.*;
import edu.uob.DBExceptions.*;


public class Parser {
    public DBcmd processingCentre;
    private Tokenizer query;
    private static TokenType tokenType = new TokenType();


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
                break;
            }
            case "ALTER": {
                processingCentre = new AlterCMD();
                break;
            }
            case "INSERT": {
                processingCentre = new InsertCMD();
                break;
            }
            case "SELECT": {
                processingCentre = new SelectCMD();
                break;
            }
            case "UPDATE": {
                processingCentre = new UpdateCMD();
                break;
            }
            case "DELETE": {
                processingCentre = new DeleteCMD();
                break;
            }
            case "JOIN": {
                processingCentre = new JoinCMD();
                break;
            }
            default: {
                throw new InvalidCommandTypeException();
            }
        }
        return processingCentre;
    }

    private void parseUse() throws QueryException {
        String dataBase = query.getNextToken();
        if (!dataBase.matches(TokenType.PLAINTEXT)) throw new InvalidPlainTextException();
        processingCentre.setDataBase(dataBase);
    }

    private void parseCreat() throws QueryException {
        String commandType = query.getNextToken();
        if (commandType.equals("DATABASE")) {
            processingCentre.setCreatType(commandType);
            parseUse();
            if (query.hasMoreToken()) {
                throw new TooManyParametersException();
            }

        } else if (commandType.equals("TABLE")) {
            processingCentre.setCreatType(commandType);
            if (query.hasMoreToken()) {
                if (!query.getNextToken().equals("(")) throw new InvalidAttributeNameException();
                if (!query.getNextToken().matches(TokenType.PLAINTEXT)) throw new InvalidAttributeNameException();
                processingCentre.addAttributeList(query.getcurrentToken());
                parseAttributeList();
                if(!query.getcurrentToken().equals(")"))throw new InvalidAttributeNameException();
                if(query.hasMoreToken()) throw new TooManyParametersException();
            }
        } else {
            throw new InvalidCommandTypeException();
        }
    }

    public void parseAttributeList() throws QueryException {
        while (query.getNextToken().equals(",")) {
            if (query.getNextToken().matches(TokenType.PLAINTEXT)) {
                processingCentre.addAttributeList(query.getcurrentToken());
            } else {
                throw new InvalidAttributeNameException();
            }
        }
    }

    public void parseDrop () throws QueryException{
        if(query.getNextToken().equals("DATABASE")){

        }
    }
}
