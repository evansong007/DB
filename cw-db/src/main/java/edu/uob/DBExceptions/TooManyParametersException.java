package edu.uob.DBExceptions;

public class TooManyParametersException extends QueryException{
    public TooManyParametersException() {
        super();
    }

    public String toString() {
        return " Too Many Parameters ";
    }
}
