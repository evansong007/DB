package edu.uob;

import java.io.*;
import java.util.*;


public class DBFileIO {
    private static final String fileType = ".tab";
    private Table table;
    private String filepath;

    public DBFileIO(Table table, DBPath path) {
        this.table = table;
        filepath = path.getCurrentDatabasePath() + table.getTableName() + fileType;
    }


    public void tableReader() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String row;
        ArrayList<String> tableContent = new ArrayList<>();
        while ((row = bufferedReader.readLine()) != null) {
            if (!row.equals("\n")) {
                tableContent.add(row);
            }
        }
        readAttributes(tableContent);
        bufferedReader.close();
    }

    public void readAttributes(ArrayList<String> tableContent) {
        String[] attributes = tableContent.get(0).split("\\t");
        for (String attribute : attributes) {
            table.setAttributes(attribute);
        }
        tableContent.remove(0);
        readData(tableContent);
    }

    public void readData(ArrayList<String> tableContent) {
        ArrayList<String> attributes=table.getAttributes();
        for (String rowData:tableContent) {
            String[] row = rowData.split("\\t");
            Map<String, String> entity = new HashMap<>();
            for (int columnNumber = 0; columnNumber < row.length; columnNumber++) {
                if (attributes.get(columnNumber).equals("id")) {
                    table.updateMaxId(row[columnNumber]);
                }
                entity.put(attributes.get(columnNumber), row[columnNumber]);
            }
            table.getTableData().add(entity);
        }
    }

    public void tableWriter() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath));
        writeAttributes(bufferedWriter);
        bufferedWriter.close();
    }

    public void writeAttributes(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.newLine();
        ArrayList<String> attributes = table.getAttributes();
        for(String attribute:attributes){
            bufferedWriter.write(attribute+"\t");
        }
        bufferedWriter.flush();
        writeData(bufferedWriter, attributes);
    }

    public void writeData(BufferedWriter bufferedWriter, ArrayList<String> attributes) throws IOException {
        ArrayList<Map<String, String>> tableData = table.getTableData();
        for (Map<String, String> entity:tableData) {
            bufferedWriter.newLine();
            for (String attribute : attributes) {
                bufferedWriter.write(entity.get(attribute) + "\t");
            }
            bufferedWriter.flush();
        }
    }
}
