package edu.uob.DBCommand;

import edu.uob.DBExceptions.QueryException;
import edu.uob.DBFileIO;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class DeleteCMD extends DBcmd {
    @Override
    public void executeCommand() throws QueryException, IOException {
        DBFileIO fileIO = getFileIO(tableNames.get(0));
        fileIO.tableReader();
        Iterator<Map<String, String>> dataOfTable = fileIO.getTable().getTableData().iterator();
        while (dataOfTable.hasNext()){
            if(checkConditionList(dataOfTable.next())) dataOfTable.remove();
        }
        fileIO.tableWriter();
        this.queryResult = "query successful";
    }

}
