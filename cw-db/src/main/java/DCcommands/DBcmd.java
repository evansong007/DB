package DCcommands;


import edu.uob.DBServer;

import java.util.List;

public abstract class DBcmd  {
    List<Condition> conditions;
    List<String> colNames;
    List<String> tableNames;
    String DBname;
    String commandType;

    public abstract String query(DBServer server){

    }
}
