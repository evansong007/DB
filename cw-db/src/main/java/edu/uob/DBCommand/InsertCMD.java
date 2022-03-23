package edu.uob.DBCommand;

import edu.uob.DBExceptions.QueryException;
import edu.uob.DBFileIO;

import java.io.IOException;

public class InsertCMD extends DBcmd {
    @Override
    public void executeCommand() throws IOException, QueryException {
        DBFileIO fileIO = getFileIO(tableNames.get(0));
        fileIO.tableReader();
        fileIO.getTable().insertEntity(valueList);
        fileIO.tableWriter();
        this.queryResult = "query successful";
    }
}
