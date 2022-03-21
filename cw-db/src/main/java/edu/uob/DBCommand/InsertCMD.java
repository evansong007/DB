package edu.uob.DBCommand;

import edu.uob.DBExceptions.QueryException;
import edu.uob.DBFileIO;
import edu.uob.Table;

import java.io.File;
import java.io.IOException;

public class InsertCMD extends DBcmd {
    @Override
    public void executeCommand() throws IOException, QueryException {
        Table table = new Table(tableNames.get(0));
        File tablePath =getTablePath(tableNames.get(0));
        DBFileIO fileIO = new DBFileIO(table,tablePath);
        fileIO.tableReader();
        table.insertEntity(valueList);
        fileIO.tableWriter();
        this.queryResult = "query successful";
    }
}
