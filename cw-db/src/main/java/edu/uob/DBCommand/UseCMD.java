package edu.uob.DBCommand;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UseCMD extends DBcmd {

    @Override
    public void executeCommand()throws IOException {
        File dataBase = getBasePath(baseName);
        if(!dataBase.exists()||!dataBase.isDirectory())throw new FileNotFoundException();
        rootBase.setCurrentDatabasePath(baseName);
    }
}
