package DBExceptions;

public class InvalidPlainTextException extends QueryException {
    public InvalidPlainTextException() {
        super();
    }

    public String toString() {
        return " Invalid PlainText ";
    }
}
