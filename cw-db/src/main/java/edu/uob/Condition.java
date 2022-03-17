package edu.uob;

public class Condition {
    private  String attributeName =null;
    private  String operator=null;
    private  String value=null;



    public void setAttributeName(String attribute){
        this.attributeName =attribute;
    }

    public String getAttributeName(){
        return attributeName;
    }

    public void setOperator(String operator){
        this.operator = operator;
    }

    public String getOperator(){
        return operator;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
