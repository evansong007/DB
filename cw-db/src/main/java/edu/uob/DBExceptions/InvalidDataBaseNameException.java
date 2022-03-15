package edu.uob.DBExceptions;

public class InvalidDataBaseNameException extends QueryException{
    public InvalidDataBaseNameException(){super();}
    public String toString() {
        return " Invalid DataBase Name ";
    }
}
