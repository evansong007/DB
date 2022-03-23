package edu.uob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TokenType {
    public static final String PLAINTEXT = "[0-9a-zA-Z]+";
    public static final String STRINGLITERAL="('([^\\d',\"]*?)')";
    public static final String OPERATOR = "(?i)(>=|<=|==|!=|<|>|LIKE)";
    public static final String SINGLEQUAL = "(?<=[^<>!=])=(?=[ \\w+'-])";
    public static final String DATABASE = "(?i)(DATABASE)";
    public static final String TABLE = "(?i)(TABLE)";
    public static final String AlTERATIONTYPE = "(?i)(ADD|DROP)";
    public static final String INTO = "(?i)(INTO)";
    public static final String VALUES = "(?i)(VALUES)";
    public static final String LEFTBRACKET = "\\(";
    public static final String RIGHTBRACKET = "\\)";
    public static final String BOOLEANLITERAL = "(?i)(TRUE|FALSE)";
    public static final String FLOATLITERAL = "[+-]?((\\d+\\.?\\d*)|(\\.\\d+))";
    public static final String INTEGERLITERAL = "[+-]??(\\d)+";
    public static final String NULL = "(?i)(NULL)";
    public static final String APOSTROPHE = "\\,";
    public static final String FROM = "(?i)(FROM)";
    public static final String WHERE = "(?i)(WHERE)";
    public static final String LOGICALOPREATOR = "(?i)(AND|OR)";
    public static final String SET = "(?i)(SET)";
    public static final String EQUALS = "\\=";
    public static final String AND = "(?i)(AND)";
    public static final String ON = "(?i)(ON)";

}
