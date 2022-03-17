package edu.uob.DBCommand;

import edu.uob.Condition;
import edu.uob.DBExceptions.QueryException;
import edu.uob.DBPath;

import java.util.ArrayList;
import java.util.Stack;


public abstract class DBcmd {
    DBPath rootBase;
    String baseName;
    String commandType;
    String alterationType;
    String attributeName;
    ArrayList<String> tableNames = new ArrayList<>();
    ArrayList<String> attributeList=new ArrayList<>();
    ArrayList<String> valueList =new ArrayList<>();
    ArrayList<Object> conditionList = new ArrayList<>();

    public abstract void executeCommand() throws QueryException;

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

    public void setAttributeName(String name){this.attributeName=name;}

    public void addValueList(String value){valueList.add(value);}

    public void addConditionList(Object condition){this.conditionList.add(condition);}

    public void addTableName(String tableName){this.tableNames.add(tableName);}
}
