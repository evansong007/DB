package edu.uob.DBCommand;

import edu.uob.*;
import edu.uob.DBExceptions.AttributeNotExistsException;
import edu.uob.DBExceptions.InvalidConditionException;
import edu.uob.DBExceptions.QueryException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;


public abstract class DBcmd {
    DBPath rootBase;
    String baseName;
    String commandType;
    String alterationType;
    final String fileType = ".tab";
    ArrayList<String> tableNames = new ArrayList<>();
    ArrayList<String> attributeList = new ArrayList<>();
    ArrayList<String> valueList = new ArrayList<>();
    ArrayList<Object> conditionList = new ArrayList<>();
    String queryResult;

    public abstract void executeCommand() throws QueryException, IOException;

    public void setDataBase(String dataBase) {
        this.baseName = dataBase;
    }

    public void setRootBase(DBPath rootPath) {
        this.rootBase = rootPath;
    }

    public void setCommandType(String commandtype) {
        this.commandType = commandtype;
    }

    public void addAttributeList(String attribute) {
        attributeList.add(attribute);
    }

    public void setAlterationType(String type) {
        this.alterationType = type;
    }

    public void addValueList(String value) {
        valueList.add(value);
    }

    public void addConditionList(Object condition) {
        this.conditionList.add(condition);
    }

    public void addTableName(String tableName) {
        this.tableNames.add(tableName);
    }

    public File getTablePath(String tableName) {
        return new File(rootBase.getCurrentDatabasePath() + File.separator + tableName + fileType);
    }

    public File getBasePath(String baseName) {
        return new File(rootBase.getRootPath() + baseName);
    }

    public String getQueryResult() {
        return queryResult;
    }

    public DBFileIO getFileIO(String tableName) {
        File tablePath = getTablePath(tableName);
        Table table = new Table(tableName);
        return new DBFileIO(table, tablePath);
    }

    public void isAttributeExist(Table table, String attribute) throws AttributeNotExistsException {
        if (!table.getAttributes().contains(attribute)) throw new AttributeNotExistsException();
    }

    public void returnResult(Table table) {
        StringBuilder attributes = new StringBuilder();
        attributes.append("\n");
        for (String attribute : table.getAttributes()) {
            attributes.append(attribute);
            attributes.append("\t");
        }

        StringBuilder data = new StringBuilder();
        for (Map<String, String> entity : table.getTableData()) {
            StringBuilder row = new StringBuilder();
            for (String attribute : table.getAttributes()) {
                row.append(entity.get(attribute));
                row.append("\t");
            }
            data.append(row);
            data.append("\n");
        }

        queryResult = attributes + "\n" + data;
    }

    public Boolean checkCondition(Map<String, String> entity, Condition condition) throws QueryException {
        String targetValue = entity.get(condition.getAttributeName());
        String operator = condition.getOperator().toUpperCase();
        String valueOfCondition = condition.getValue();
        if(hasNull(targetValue,valueOfCondition)){
            return compareNull(targetValue,operator,valueOfCondition);
        }else if(areBothBoolean(targetValue,valueOfCondition)){
            return compareBoolean(targetValue,operator,valueOfCondition);
        }else if(areBothDigital(targetValue,valueOfCondition)){
            return compareFloat(targetValue,operator,valueOfCondition);
        }else if(areBothString(targetValue,valueOfCondition)){
            return compareString(targetValue,operator,valueOfCondition);
        }
        throw new InvalidConditionException();
    }

    public Boolean areBothDigital(String value1, String value2) {
        boolean check1 = value1.matches(TokenType.FLOATLITERAL) || value1.matches(TokenType.INTEGERLITERAL);
        boolean check2 = value2.matches(TokenType.FLOATLITERAL) || value2.matches(TokenType.INTEGERLITERAL);
        return check1 && check2;
    }

    public Boolean areBothString(String value1, String value2) {
        boolean check1 = value1.matches(TokenType.STRINGLITERAL);
        boolean check2 = value2.matches(TokenType.STRINGLITERAL);
        ;
        return check1 && check2;
    }

    public Boolean areBothBoolean(String value1, String value2) {
        boolean check1 = value1.matches(TokenType.BOOLEANLITERAL);
        boolean check2 = value2.matches(TokenType.BOOLEANLITERAL);
        ;
        return check1 && check2;
    }

    public Boolean hasNull(String value1, String value2) {
        boolean check1 = value1.matches(TokenType.NULL);
        boolean check2 = value2.matches(TokenType.NULL);
        ;
        return check1 || check2;
    }

    public Boolean compareNull(String string1, String operator, String string2) throws InvalidConditionException {
        String value1 = getValueOfString(string1);
        String value2 = getValueOfString(string2);
        return switch (operator) {
            case ("==") -> value1.equals(value2);
            case ("!=") -> !value1.equals(value2);
            default -> throw new InvalidConditionException();
        };
    }

    public Boolean compareBoolean(String string1, String operator, String string2) throws InvalidConditionException {
        return switch (operator) {
            case ("==") -> string1.equalsIgnoreCase(string2);
            case ("!=") -> !string1.equalsIgnoreCase(string2);
            default -> throw new InvalidConditionException();
        };
    }

    public Boolean compareString(String string1, String operator, String string2) throws InvalidConditionException {
        String value1 = getValueOfString(string1);
        String value2 = getValueOfString(string2);
        return switch (operator) {
            case ("==") -> value1.equals(value2);
            case (">") -> (value1.compareTo(value2)) > 0;
            case ("<") -> (value1.compareTo(value2)) < 0;
            case (">=") -> (value1.compareTo(value2)) >= 0;
            case ("<=") -> (value1.compareTo(value2)) <= 0;
            case ("!=") -> !value1.equals(value2);
            case ("LIKE") -> value1.contains(value2);
            default -> throw new InvalidConditionException();
        };
    }

    public String getValueOfString(String string) {
        return string.replace("'", "");
    }

    public Boolean isEqual(Float float1, Float float2) {
        BigDecimal value1 = new BigDecimal(float1);
        BigDecimal value2 = new BigDecimal(float2);
        return value1.compareTo(value2) == 0;
    }

    public Boolean compareFloat(String string1, String operator, String string2) throws InvalidConditionException {
        float value1 = Float.parseFloat(string1);
        float value2 = Float.parseFloat(string2);
        return switch (operator) {
            case ("==") -> isEqual(value1, value2);
            case (">") -> value1 > value2;
            case ("<") -> value1 < value2;
            case (">=") -> value1 >= value2;
            case ("<=") -> value1 <= value2;
            case ("!=") -> !isEqual(value1, value2);
            case ("LIKE") -> string1.contains(string2);
            default -> throw new InvalidConditionException();
        };
    }

    public Boolean checkConditionList(Map<String, String> entity) throws QueryException {
        Stack<Boolean> stack = new Stack<>();
        for (Object object : conditionList) {
            if (object instanceof Condition) {
                Boolean result = checkCondition(entity, (Condition) object);
                stack.push(result);
            } else {
                Boolean result1 = stack.pop();
                Boolean result2 = stack.pop();
                if (object.toString().matches(TokenType.AND)) {
                    stack.push(result1 && result2);
                } else {
                    stack.push(result1 || result2);
                }
            }
        }
        return stack.pop();
    }
}
