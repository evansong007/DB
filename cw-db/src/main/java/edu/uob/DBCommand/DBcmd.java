package edu.uob.DBCommand;

import edu.uob.DBExceptions.QueryException;
import edu.uob.DBPath;

import java.util.ArrayList;


public abstract class DBcmd {
    DBPath rootBase;
    String baseName;
    String creatType;
    ArrayList<String> attributeList=new ArrayList<>();

    public abstract void executeCommand() throws QueryException;

    public void setDataBase(String dataBase){
        this.baseName=dataBase;
    }

    public void setRootBase(DBPath rootPath){
        this.rootBase=rootPath;
    }

    public void setCreatType(String commandtype){this.creatType=commandtype;}

    public void addAttributeList(String attribute){
        attributeList.add(attribute);
    }

}
