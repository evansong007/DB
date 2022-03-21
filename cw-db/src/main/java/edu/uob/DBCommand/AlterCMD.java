package edu.uob.DBCommand;

import edu.uob.DBExceptions.AttributeDuplicationException;
import edu.uob.DBFileIO;
import edu.uob.Table;


import java.io.File;
import java.io.IOException;

public class AlterCMD extends DBcmd {
    @Override
    public void executeCommand() throws IOException, AttributeDuplicationException {
        File tablePath = getTablePath(tableNames.get(0));
        Table table = new Table(tableNames.get(0));
        DBFileIO fileIO = new DBFileIO(table,tablePath);
        fileIO.tableReader();
        if(alterationType.matches("(?i)(ADD)")){
            for (String attribute:attributeList) {
                table.setAttributes(attribute);
                table.setNullToAttribute(attribute);
            }
        }else if(alterationType.matches("(?i)(DROP)")){
            for (String attribute:attributeList) {
                table.dropAttribute(attribute);
            }
        }
        fileIO.tableWriter();
        this.queryResult = "query successful";
    }

}
