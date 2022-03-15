package edu.uob.DBCommand;


import edu.uob.DBExceptions.InvalidDataBaseNameException;

import java.io.File;

public class UseCMD extends DBcmd {

    @Override
    public void executeCommand()throws InvalidDataBaseNameException {
        rootBase.setCurrentDatabasePath(baseName);
        File dataBase = new File(rootBase.getCurrentDatabasePath());
        if(!dataBase.isDirectory())throw new InvalidDataBaseNameException();
    }
}
