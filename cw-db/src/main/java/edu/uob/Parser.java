package edu.uob;

import DBCommands.*;
import DBExceptions.*;


public class Parser {
    DBcmd processingCentre;
    Tokenizer query;

    public DBcmd parseQuery(String command) throws QueryException {
        if (command.isEmpty()) throw new QueryIsEmptyException();
        query = new Tokenizer(command);
        String token = query.getFirstToken().toUpperCase();
        switch (token) {
            case "USE": {
                processingCentre = new UseCMD();
                break;
            }
            case "CREATE": {
                processingCentre = new CreateCMD();
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

    private void parseUseQuery()throws QueryException{


    }
}
