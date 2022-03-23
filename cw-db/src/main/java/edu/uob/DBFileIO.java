package edu.uob;

import edu.uob.DBExceptions.AttributeDuplicationException;

import java.io.*;
import java.util.*;


public class DBFileIO {
    private Table table;
    private File filepath;

    public DBFileIO(Table table, File path) {
        this.table = table;
        this.filepath = path;
    }


    public void tableReader() throws IOException, AttributeDuplicationException {
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

    public void readAttributes(ArrayList<String> tableContent) throws AttributeDuplicationException {
        table.setMaxId(Integer.parseInt(tableContent.get(0)));
        String[] attributes = tableContent.get(1).split("\\t");
        for (String attribute : attributes) {
            table.setAttributes(attribute);
        }
        tableContent.remove(0);
        tableContent.remove(0);
        readData(tableContent);
    }

    public void readData(ArrayList<String> tableContent) {
        ArrayList<String> attributes=table.getAttributes();
        for (String rowData:tableContent) {
            String[] row = rowData.split("\\t");
            Map<String, String> entity = new HashMap<>();
            for (int columnNumber = 0; columnNumber < row.length; columnNumber++) {
                entity.put(attributes.get(columnNumber), row[columnNumber]);
            }
            table.getTableData().add(entity);
        }
    }

    public void tableWriter() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath));
        bufferedWriter.write(String.valueOf(table.getMaxId())+"\n");
        bufferedWriter.flush();
        writeAttributes(bufferedWriter);
        bufferedWriter.close();
    }

    public void writeAttributes(BufferedWriter bufferedWriter) throws IOException {
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

    public Table getTable(){return table;}
}
