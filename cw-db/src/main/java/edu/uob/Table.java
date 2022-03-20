package edu.uob;

import edu.uob.DBExceptions.AttributeDuplicationException;

import java.util.ArrayList;
import java.util.HashMap;
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

    public Integer getNewId(){

        return ++maxId;
    }

    public void setAttributes(String attribute) throws AttributeDuplicationException {
        if(attributes.contains(attribute))throw new AttributeDuplicationException();
        attributes.add(attribute);
    }

    public ArrayList<String> getAttributes(){
        return attributes;
    }

    public ArrayList<Map<String,String>> getTableData(){
        return tableData;
    }

    public void setNullToAttribute(String attributes){
        for (Map<String,String> entity: tableData) {
            entity.put(attributes,TokenType.NULL);
        }
    }

    public void dropAttribute(String attribute){
        if(!attributes.contains(attribute))return;
        for (Map<String,String> entity: tableData) {
            entity.remove(attribute);
        }
        attributes.remove(attribute);
    }

    public void insertEntity(ArrayList<String> valueList){
        Map<String, String> entity = new HashMap<>();
        int postion = 0;
        for (String attribute: attributes) {
            if(attribute.equals("id")){
                entity.put(attribute,String.valueOf(getNewId()));
            }else{
                entity.put(attribute,valueList.get(postion++));
            }
        }
        getTableData().add(entity);
    }

    public void addTabledata(Map<String,String> entity){
        tableData.add(entity);
    }
}
