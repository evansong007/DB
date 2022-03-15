package DBCommands;

import DBExceptions.QueryException;
import edu.uob.DBPath;

import java.util.List;

public abstract class DBcmd {
    DBPath rootBase;
    List<String> colNames;
    List<String> tableNames;
    String baseName;
    String commandType;

    public abstract void executeCommand() throws QueryException;

    public void setDataBase(String dataBase){
        this.baseName=dataBase;
    }
    public void setRootBase(DBPath rootPath){
        this.rootBase=rootPath;
    }
}
