package edu.uob.DBCommand;


import java.io.File;

public class DropCMD extends DBcmd {
    @Override
    public void executeCommand() {
        if (commandType.equals("DATABASE")) {
            File dataBase = getBasePath(baseName);
            if(dataBase.exists()&&dataBase.isDirectory())dataBase.delete();
        } else if (commandType.equals("TABLE")) {
            File table = getTablePath(tableNames.get(0));
            if(table.exists())table.delete();
        }
        this.queryResult = "query successful";
    }
}

