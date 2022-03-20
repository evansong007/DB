package edu.uob.DBCommand;

import edu.uob.DBExceptions.*;
import edu.uob.DBFileIO;
import edu.uob.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JoinCMD extends DBcmd {
    Table table1;
    Table table2;
    String attribute1;
    String attribute2;
    Table joinTable = new Table("joinTable");

    @Override
    public void executeCommand() throws QueryException, IOException {
        DBFileIO fileIO1 = getFileIO(tableNames.get(0));
        fileIO1.tableReader();
        this.table1 = fileIO1.getTable();
        this.attribute1 = attributeList.get(0);
        DBFileIO fileIO2 = getFileIO(tableNames.get(1));
        fileIO2.tableReader();
        this.table2 = fileIO2.getTable();
        this.attribute2 = attributeList.get(1);
        getJoinAttributes();
        joinData();
        returnResult();

    }

    public void getJoinAttributes() throws AttributeNotExistsException, AttributeDuplicationException {
        isAttributeExist(table1, attribute1);
        isAttributeExist(table2, attribute2);

        ArrayList<String> attributes1 = table1.getAttributes();
        ArrayList<String> attributes2 = table2.getAttributes();
        joinTable.setAttributes("id");
        int stopPosition = attributes1.indexOf(attribute1);
        int startPosition = attributes2.indexOf(attribute2) + 1;
        for (int n = 0; n < stopPosition; n++) {
            if(!attributes1.get(n).equals("id")){
                joinTable.setAttributes(attributes1.get(n));
            }
        }
        for (int n = startPosition; n < attributes2.size(); n++) {
            joinTable.setAttributes(attributes2.get(n));
        }
    }

    public void joinData(){
        for (Map<String,String> entity1: table1.getTableData()) {
            for(Map<String,String> entity2: table2.getTableData()){
                if(entity1.get(attribute1).equals(entity2.get(attribute2))){
                    setData(entity1,entity2);
                }
            }
        }
    }

    public void setData(Map<String,String> entity1,Map<String,String> entity2){
        Map<String, String> entity = new HashMap<>();
        for (String attribute: joinTable.getAttributes()) {
            if(attribute.equals("id")){
                entity.put(attribute,String.valueOf(joinTable.getNewId()));
            }else {
                if(entity1.containsKey(attribute)){
                    entity.put(attribute,entity1.get(attribute));
                }else {
                    entity.put(attribute,entity2.get(attribute));
                }
            }
        }
        joinTable.addTabledata(entity);
    }

    public void returnResult() {
        StringBuilder attributes = new StringBuilder();
        attributes.append("\n");
        for (String attribute : joinTable.getAttributes()) {
            attributes.append(attribute);
            attributes.append("\t");
        }

        StringBuilder data = new StringBuilder();
        for (Map<String, String> entity : joinTable.getTableData()) {
            StringBuilder row = new StringBuilder();
            for (String attribute : joinTable.getAttributes()) {
                row.append(entity.get(attribute));
                row.append("\t");
            }
            data.append(row);
            data.append("\n");
        }

        queryResult =  attributes + "\n" +data;
    }
}
