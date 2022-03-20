package edu.uob.DBCommand;

import edu.uob.*;
import edu.uob.DBExceptions.AttributeNotExistsException;
import edu.uob.DBExceptions.DataBaseAlreadyExists;
import edu.uob.DBExceptions.QueryException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;


public abstract class DBcmd {
    DBPath rootBase;
    String baseName;
    String commandType;
    String alterationType;
    final String fileType = ".tab";
    ArrayList<String> tableNames = new ArrayList<>();
    ArrayList<String> attributeList=new ArrayList<>();
    ArrayList<String> valueList =new ArrayList<>();
    ArrayList<Object> conditionList = new ArrayList<>();
    String queryResult;

    public abstract void executeCommand() throws QueryException, IOException;

    public void setDataBase(String dataBase){
        this.baseName=dataBase;
    }

    public void setRootBase(DBPath rootPath){
        this.rootBase=rootPath;
    }

    public void setCommandType(String commandtype){this.commandType=commandtype;}

    public void addAttributeList(String attribute){
        attributeList.add(attribute);
    }

    public void setAlterationType(String type){this.alterationType = type;}

    public void addValueList(String value){
        if(value.matches(TokenType.STRINGLITERAL)){
            String targetString = value.replace("'","");
            valueList.add(targetString);
        }else {
            valueList.add(value);
        }

    }

    public void addConditionList(Object condition){this.conditionList.add(condition);}

    public void addTableName(String tableName){this.tableNames.add(tableName);}

    public File getTablePath(String tableName){
        return new File(rootBase.getCurrentDatabasePath()+File.separator+tableName+fileType);
    }

    public File getBasePath(String baseName){
        return new File(rootBase.getRootPath()+baseName);
    }

    public String getQueryResult(){
        return queryResult;
    }

    public DBFileIO getFileIO(String tableName){
        File tablePath = getTablePath(tableName);
        Table table = new Table(tableName);
        return new DBFileIO(table,tablePath);
    }

    public void isAttributeExist(Table table,String attribute) throws AttributeNotExistsException {
        if(!table.getAttributes().contains(attribute)) throw new AttributeNotExistsException();
    }


}
