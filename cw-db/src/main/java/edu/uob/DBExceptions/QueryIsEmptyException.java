package edu.uob.DBExceptions;

public class QueryIsEmptyException extends QueryException {
    public QueryIsEmptyException() {
        super();
    }

    public String toString() {
        return "Invalid query: Command is empty, please try again. ";
    }
}
