package edu.uob.DBExceptions;

public class InvalidAttributeNameException extends QueryException{
    public InvalidAttributeNameException(){super();}

    public String toString() {
        return "Invalid AttributeName ";
    }
}
