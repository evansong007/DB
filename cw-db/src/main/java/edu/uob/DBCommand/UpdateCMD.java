package edu.uob.DBCommand;

import edu.uob.DBExceptions.QueryException;
import edu.uob.DBFileIO;


import java.io.IOException;
import java.util.Map;

public class UpdateCMD extends DBcmd {
    @Override
    public void executeCommand() throws QueryException, IOException {
        DBFileIO fileIO = getFileIO(tableNames.get(0));
        fileIO.tableReader();
        for (Map<String, String> entity : fileIO.getTable().getTableData()) {
            if (checkConditionList(entity)) {
                int n = 0;
                for (String attribute: attributeList) {
                    entity.replace(attribute,valueList.get(n++));
                }
            }
        }
        fileIO.tableWriter();
        this.queryResult = "query successful";
    }
}
