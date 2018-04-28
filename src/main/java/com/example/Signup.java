package com.example;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Signup {

    static BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIUKUAGF4RUZMJPNA", "SsicoPjOsBf6GQilvY5t0jBW7dV66dlC/lTZ1Tvv");
    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_WEST_2).withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();
    static DynamoDB dynamoDB = new DynamoDB(client);



    String username;
    String password;
    String email;

    public Signup(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public Signup(String username, String email) {
        this.username = username;

        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Signup() {
    }
    public boolean addtoDb(){

        Table table = dynamoDB.getTable("user");
        Item item = new Item().withPrimaryKey("username", username).withString("password", password).withString("email", email)
                .withString("type", "user");
       try {
           table.putItem(item);
       }
       catch (Exception e){
           return false;
       }
       return true;
    }

    public static List<Signup> loadusers(){

        Table table = dynamoDB.getTable("user");


        ItemCollection<ScanOutcome> items = table.scan();
        Iterator<Item> iterator = items.iterator();

        List<Signup> output = new ArrayList<Signup>();
        Item item=null;
        while (iterator.hasNext()) {
            item=iterator.next();
            String type = item.get("type").toString();
        if(type.equals("user")) {
            String username = item.get("username").toString();
            String email = item.get("email").toString();


            Signup u = new Signup(username, email);
            output.add(u);
        }
        }


        return output;
    }

    public static boolean deluser(String uid){
        Table table = dynamoDB.getTable("user");
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("username", uid));

        try {
            table.deleteItem(deleteItemSpec);

        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }
}
