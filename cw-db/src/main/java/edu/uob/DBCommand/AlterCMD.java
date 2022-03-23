package edu.uob.DBCommand;

import edu.uob.DBExceptions.AttributeDuplicationException;
import edu.uob.DBFileIO;
import java.io.IOException;

public class AlterCMD extends DBcmd {
    @Override
    public void executeCommand() throws IOException, AttributeDuplicationException {
        DBFileIO fileIO = getFileIO(tableNames.get(0));
        fileIO.tableReader();
        if(alterationType.matches("(?i)(ADD)")){
            for (String attribute:attributeList) {
                fileIO.getTable().setAttributes(attribute);
                fileIO.getTable().setNullToAttribute(attribute);
            }
        }else if(alterationType.matches("(?i)(DROP)")){
            for (String attribute:attributeList) {
                fileIO.getTable().dropAttribute(attribute);
            }
        }
        fileIO.tableWriter();
        this.queryResult = "query successful";
    }

}
