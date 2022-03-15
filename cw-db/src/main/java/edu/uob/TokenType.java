package edu.uob;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenType {

    public Boolean isPlainText(String token){
        Pattern p = Pattern.compile("[0-9a-zA-Z]+");
        Matcher m = p.matcher(token);
        return m.matches();
    }


}
