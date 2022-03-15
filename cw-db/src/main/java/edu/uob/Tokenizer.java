package edu.uob;


import edu.uob.DBExceptions.QueryException;
import edu.uob.DBExceptions.QueryIsNotFinishedException;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Tokenizer {
    private final ArrayList<String> tokens = new ArrayList<>();
    private int currentTokenIndex = 0;

    public Tokenizer(String command) throws QueryException {
        StringTokenizer tokenizer = new StringTokenizer(command, " (,');", true);
        while (tokenizer.hasMoreElements()) {
            String currentToken = tokenizer.nextToken();
            if (!currentToken.equals(" "))
                tokens.add(currentToken);
        }
        if (!getLastToken().equals(";")) throw new QueryIsNotFinishedException();
        tokens.remove(getLastToken());
    }

    public String getFirstToken() {
        return tokens.get(0);
    }

    public String getNextToken() {
        currentTokenIndex++;
        return tokens.get(currentTokenIndex);
    }

    public String getcurrentToken(){return tokens.get(currentTokenIndex);}

    public String getLastToken() {
        return tokens.get(tokens.size() - 1);
    }

    public Boolean hasMoreToken(){
        return currentTokenIndex<tokens.size()-1;
    }
}
