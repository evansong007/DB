package edu.uob.DBExceptions;

public class TableAlreadyExistsException extends QueryException{
    public TableAlreadyExistsException(){
        super();
    }

    @Override
    public String toString() {
        return "Table Already Exists";
    }
}
