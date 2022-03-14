package DBCommands;

import java.util.List;

public abstract class DBcmd {
    List<String> colNames;
    List<String> tableNames;
    String DBname;
    String commandType;

    public abstract void executeCommand();
}
