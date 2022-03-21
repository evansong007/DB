package edu.uob.DBExceptions;

public class InvalidConditionException extends QueryException{
    public InvalidConditionException(){super();}

    @Override
    public String toString() {
        return "The Condition of Query is Invalid";
    }
}
