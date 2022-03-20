package edu.uob.DBCommand;

import edu.uob.DBExceptions.DataBaseAlreadyExists;
import edu.uob.DBExceptions.QueryException;
import edu.uob.DBExceptions.TableAlreadyExistsException;
import edu.uob.DBFileIO;
import edu.uob.DBPath;
import edu.uob.Table;

import java.io.File;
import java.io.IOException;

public class CreateCMD extends DBcmd {
    @Override
    public void executeCommand() throws IOException, QueryException {
        if(commandType.equals("DATABASE")){
            File dataBase = getBasePath(baseName);
            if(dataBase.exists()&&dataBase.isDirectory())throw new DataBaseAlreadyExists();
            if(!dataBase.mkdir())throw new IOException();

        }else if(commandType.equals("TABLE")){
            File tablepath = getTablePath(tableNames.get(0));
            if(tablepath.exists())throw new TableAlreadyExistsException();
            if(!tablepath.createNewFile())throw new IOException();
            if(!attributeList.isEmpty()){
                Table table = new Table(tableNames.get(0));
                table.setAttributes("id");
                for (String attribute: attributeList) {
                    table.setAttributes(attribute);
                }
                DBFileIO fileIO = new DBFileIO(table,tablepath);
                fileIO.tableWriter();
            }
        }
    }
}
