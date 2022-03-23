package edu.uob.DBCommand;


import edu.uob.DBExceptions.AttributeDuplicationException;
import edu.uob.DBExceptions.QueryException;
import edu.uob.DBFileIO;
import edu.uob.Table;

import java.io.IOException;
import java.util.Map;

public class SelectCMD extends DBcmd {
    @Override
    public void executeCommand() throws QueryException, IOException {
        DBFileIO fileIO = getFileIO(tableNames.get(0));
        fileIO.tableReader();
        Table targetTable = new Table("targetTable");
        if(attributeList.isEmpty()) {
            if(conditionList.isEmpty()) {
                returnResult(fileIO.getTable());
                return;
            }
            targetTable.copyAttributes(fileIO.getTable().getAttributes());
            for (Map<String, String> entity : fileIO.getTable().getTableData()) {
                if(checkConditionList(entity))targetTable.addTabledata(entity);
                returnResult(targetTable);
            }
        }else {
            targetTable.copyAttributes(attributeList);
            if(conditionList.isEmpty()) {
                targetTable.copyTableData(fileIO.getTable().getTableData());
            }else {
                for (Map<String, String> entity : fileIO.getTable().getTableData()) {
                    if(checkConditionList(entity))targetTable.addTabledata(entity);
                }
            }
            returnResult(targetTable);
        }



    }
}
