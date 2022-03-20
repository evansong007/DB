package edu.uob.DBExceptions;

public class AttributeNotExistsException extends QueryException {
    public AttributeNotExistsException(){super();}

    @Override
    public String toString() {
        return "Attribute Not Exists ";
    }
}
