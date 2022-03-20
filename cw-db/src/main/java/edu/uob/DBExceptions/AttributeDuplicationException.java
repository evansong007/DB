package edu.uob.DBExceptions;

public class AttributeDuplicationException extends QueryException{
    public AttributeDuplicationException(){super();}

    @Override
    public String toString() {
        return "Attribute duplication, not executable";
    }
}
