package DBExceptions;

public class InvalidCommandTypeException extends QueryException {
    public InvalidCommandTypeException() {
        super();
    }

    public String toString() {
        return " Invalid Command Type ";
    }
}
