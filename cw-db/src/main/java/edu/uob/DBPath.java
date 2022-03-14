package edu.uob;

import java.io.File;

public class DBPath {
    private final String databasePath;
    private String currentDatabasePath;

    public DBPath(File databaseDirectory) {
        String databasePath = databaseDirectory.toString();
        this.databasePath = databasePath.substring(0, databasePath.length() - 1);
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public void setCurrentDatabasePath(String baseName) {
        this.currentDatabasePath = databasePath + baseName;
    }

    public String getCurrentDatabasePath() {
        return currentDatabasePath;
    }
}
