package edu.uob;

import DBCommands.*;
import DBExceptions.*;


public class Parser {
    public DBcmd processingCentre;
    private Tokenizer query;
    private static TokenType tokenType;


    public DBcmd parseQuery(String command) throws QueryException {
        if (command.isEmpty()) throw new QueryIsEmptyException();
        query = new Tokenizer(command);
        String token = query.getFirstToken().toUpperCase();
        switch (token) {
            case "USE": {
                processingCentre = new UseCMD();
                parseUseQuery();
                break;
            }
            case "CREATE": {
                processingCentre = new CreateCMD();
                parseCreatQuery();
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

    private void parseUseQuery() throws QueryException {
        String dataBase = query.getNextToken();
        if (!tokenType.isPlainText(dataBase)) throw new InvalidPlainTextException();
        processingCentre.setDataBase(dataBase);
        if (!query.getNextToken().equals(";")) throw new TooManyParametersException();

    }

    private void parseCreatQuery() throws QueryException {

    }
}
