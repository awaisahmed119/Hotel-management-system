package com.example;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.util.Iterator;

public class Profile {
    static BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIUKUAGF4RUZMJPNA", "SsicoPjOsBf6GQilvY5t0jBW7dV66dlC/lTZ1Tvv");
    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_WEST_2).withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();
    static DynamoDB dynamoDB = new DynamoDB(client);


    String username;
    String address;
    String cellno;
    String cnic;

    public Profile() {
    }

    public Profile(String username,String address, String cellno, String cnic) {
        this.username=username;
        this.address = address;
        this.cellno = cellno;
        this.cnic = cnic;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCellno() {
        return cellno;
    }

    public void setCellno(String cellno) {
        this.cellno = cellno;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }
    public boolean savetoDB(){

        Table table = dynamoDB.getTable("profile");
        Item item = new Item().withPrimaryKey("username", username).withString("address", address).withString("cell", cellno).withString("cnic", cnic);
        try {
            table.putItem(item);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public static Profile loadprofile(String username){

        Table table = dynamoDB.getTable("profile");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("username = :v_usr")
                .withValueMap(new ValueMap()
                        .withString(":v_usr", username));

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item = null;
        Profile p=null;

        while (iterator.hasNext()) {
            item = iterator.next();
            String address=item.get("address").toString();
            String cellno=item.get("cell").toString();
            String cnic=item.get("cnic").toString();
            p=new Profile(username,address,cellno,cnic);
        }
        return p;

    }
}
