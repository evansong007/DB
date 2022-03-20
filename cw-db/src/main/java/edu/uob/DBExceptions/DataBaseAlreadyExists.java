package edu.uob.DBExceptions;

public class DataBaseAlreadyExists extends QueryException{
    public DataBaseAlreadyExists(){
        super();
    }
    public String toString() {
        return " DataBase Already Exists ";
    }
}
