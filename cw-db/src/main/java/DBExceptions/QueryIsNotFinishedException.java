package DBExceptions;

public class QueryIsNotFinishedException extends QueryException {
    public QueryIsNotFinishedException() {
        super();
    }

    public String toString() {
        return "Invalid query: Expected a symbol [ ; ] ";
    }
}
