package edu.uob;

import java.util.ArrayList;
import java.util.Map;

public class Table {
    private final String tableName;
    private Integer maxId;
    private ArrayList<String> attributes;
    private ArrayList<Map<String,String>> tableData;

    public Table(String tableName){
        this.tableName = tableName;
        this.maxId = 0;
        this.attributes = new ArrayList<>();
        this.tableData = new ArrayList<>();
    }

    public String getTableName(){
        return tableName;
    }

    public void setMaxId(Integer id){maxId=id;}

    public Integer getMaxId(){
        return maxId;
    }

    public void updateMaxId(String id){
        Integer maxId = getMaxId();
        Integer currentId = Integer.parseInt(id);
        maxId = maxId > currentId ? maxId : currentId;
        setMaxId(maxId);
    }

    public void setAttributes(String attribute) {
        attributes.add(attribute);
    }

    public ArrayList<String> getAttributes(){
        return attributes;
    }

    public ArrayList<Map<String,String>> getTableData(){
        return tableData;
    }
}
