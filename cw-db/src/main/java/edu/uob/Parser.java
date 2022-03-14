package edu.uob;

import DBExceptions.*;


public class Parser {


    public Parser(String command)throws QueryException {
        if(command.isEmpty()) throw new QueryIsEmptyException();
        Tokenizer query = new Tokenizer(command);
        String token = query.getFirstToken().toUpperCase();
        switch (token){
            case "USE":{
                break;
            }
            case "CREATE":{
                break;
            }
            case "DROP":{
                break;
            }
            case "ALTER":{
                break;
            }
            case "INSERT":{
                break;
            }
            case "SELECT":{
                break;
            }
            case "UPDATE":{
                break;
            }
            case "DELETE":{
                break;
            }
            case "JOIN":{
                break;
            }
            default:{
                throw new InvalidCommandTypeException();
            }
        }


    }
}
