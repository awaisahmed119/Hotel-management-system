package com.example;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.*;

import static java.util.UUID.randomUUID;

public class Room {
    static BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIUKUAGF4RUZMJPNA", "SsicoPjOsBf6GQilvY5t0jBW7dV66dlC/lTZ1Tvv");
    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_WEST_2).withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();
    static DynamoDB dynamoDB = new DynamoDB(client);



   public String id;
    String category;
    String specs;
    String price;
    String features;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Room(String id, String category, String specs,String features, String price,String status) {
        this.id = id;
        this.category = category;
        this.specs = specs;
        this.price = price;
        this.features=features;
        this.status=status;
    }

    public Room() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public List<Room> getrooms(String usertype){

        Table table = dynamoDB.getTable("room");


        ItemCollection<ScanOutcome> items = table.scan();
        Iterator<Item> iterator = items.iterator();

        List<Room> output = new ArrayList<Room>();
        Item item=null;
        while (iterator.hasNext()) {
            item=iterator.next();
            String status=item.get("roomstatus").toString();
            if((usertype.equals("user") && status.equals("available")) || usertype.equals("admin") ) {


                String id = item.get("id").toString();
                String category = item.get("category").toString();
                String specs = item.get("specs").toString();
                String features = item.get("features").toString();
                String price = item.get("price").toString();

                Room r = new Room(id, category, specs, features, price, status);
                output.add(r);

            }
        }


         return output;

    }






    public boolean addroom(){

        Table table = dynamoDB.getTable("room");
        Item item = new Item().withPrimaryKey("id", id).withString("category", category).withString("specs", specs)
                .withString("features", "features").withString("price",price).withString("roomstatus","available");
        try {
            table.putItem(item);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean deleteroom(String id){
        Table table = dynamoDB.getTable("room");
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("id", id));

        try {
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;

    }

    public static Room loadroom(String id ){
        Table table = dynamoDB.getTable("room");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("id = :v_id")
                .withValueMap(new ValueMap()
                        .withString(":v_id", id));

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item = null;
        Room r=null;

        while (iterator.hasNext()) {
            item = iterator.next();
            String category=item.get("category").toString();
            String specs=item.get("specs").toString();
            String features=item.get("features").toString();
            String price=item.get("price").toString();
            String status=item.get("roomstatus").toString();
             r=new Room(id,category,specs,features,price,status);
        }
        return r;
    }

    public  boolean updateroom(){

        Table table = dynamoDB.getTable("room");

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("id", id)
                .withUpdateExpression("set category = :c, specs=:s, features=:f, price=:p, roomstatus=:st")
                .withValueMap(new ValueMap().withString(":c", category).withString(":s", specs).withString(":f", features)
                        .withString(":p", price).withString(":st",status))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {

            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);


        }
        catch (Exception e) {
           return false;

            }
        return true;
    }

    public static boolean bookroom(String id){
        Table table = dynamoDB.getTable("room");

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("id", id)
                .withUpdateExpression("set roomstatus =:s")
                .withValueMap(new ValueMap().withString(":s", "booked"))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {

            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);


        }
        catch (Exception e) {
            return false;

        }
        return true;

    }


}
