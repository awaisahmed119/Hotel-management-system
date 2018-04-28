package com.example;



import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.*;

public class Login {
    //making aws client and dynamo db object and setting credentials
   static BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIUKUAGF4RUZMJPNA", "SsicoPjOsBf6GQilvY5t0jBW7dV66dlC/lTZ1Tvv");
   static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_WEST_2).withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();
    static DynamoDB dynamoDB = new DynamoDB(client);



    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Login() {

    }

    public String authenticate(){
        String type="";
        Table table = dynamoDB.getTable("user");


         Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
         expressionAttributeValues.put(":user", username);
        expressionAttributeValues.put(":pass", password);
        ItemCollection<ScanOutcome> items = table.scan("username = :user and password = :pass" , // FilterExpression
                 // ProjectionExpression
                null,expressionAttributeValues);

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {

            type=iterator.next().get("type").toString();
            //System.out.println(iterator.next().toJSONPretty());
        }

        return type ;
    }
}
