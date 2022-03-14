package edu.uob;

import java.io.File;

public class DBPath {
    private final String rootPath;
    private String currentDatabasePath;

    public DBPath(File databaseDirectory) {
        String databasePath = databaseDirectory.toString();
        this.rootPath = databasePath.substring(0, databasePath.length() - 1);
    }

    public String getDatabasePath() {
        return rootPath;
    }

    public void setCurrentDatabasePath(String baseName) {
        this.currentDatabasePath = rootPath + baseName;
    }

    public String getCurrentDatabasePath() {
        return currentDatabasePath;
    }
}
