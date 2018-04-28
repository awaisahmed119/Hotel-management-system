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

public class Review {
    static BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIUKUAGF4RUZMJPNA", "SsicoPjOsBf6GQilvY5t0jBW7dV66dlC/lTZ1Tvv");
    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_WEST_2).withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();
    static DynamoDB dynamoDB = new DynamoDB(client);



    String user;
    String review;
    String description;

    public Review() {

    }

    public Review(String user, String review, String description) {
        this.user = user;
        this.review = review;
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean savetoDB(){

        Table table = dynamoDB.getTable("reviews");
        Item item = new Item().withPrimaryKey("uid", user).withString("review", review).withString("description", description);
        try {
            table.putItem(item);
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    public static List<Review> loadreviews(){

        Table table = dynamoDB.getTable("reviews");


        ItemCollection<ScanOutcome> items = table.scan();
        Iterator<Item> iterator = items.iterator();

        List<Review> output = new ArrayList<Review>();
        Item item=null;
        while (iterator.hasNext()) {
            item=iterator.next();


                String user = item.get("uid").toString();
                String review = item.get("review").toString();
                String description = item.get("description").toString();


                Review r = new Review(user,review, description);
                output.add(r);
        }


        return output;
    }
    public static boolean delreview(String uid){
        Table table = dynamoDB.getTable("reviews");
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("uid", uid));

        try {
            table.deleteItem(deleteItemSpec);

        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

    return true;
    }

    public static boolean submitrating(String username,String rating){
        Table table = dynamoDB.getTable("rating");

       try {
           Item item = new Item().withPrimaryKey("username", username).withDouble("rating",Integer.parseInt(rating));

           table.putItem(item);
        }
        catch (Exception e){
            return false;
        }

        String r= Double.toString(Review.getrating());

        Table table1 = dynamoDB.getTable("overallrating");

        try {
            Item item = new Item().withPrimaryKey("id", r).withString("rating",r);

            table1.putItem(item);
        }
        catch (Exception e){
            return false;
        }

        return true;


    }
    public static double getrating(){
        double rating=0.0;

        Table table = dynamoDB.getTable("rating");


        ItemCollection<ScanOutcome> items = table.scan();
        Iterator<Item> iterator = items.iterator();


        Item item=null;
        int count=0;
        while (iterator.hasNext()) {
            item=iterator.next();




                rating = rating + Double.parseDouble(item.get("rating").toString());
                count++;

        }




        return rating/count ;


    }

    public static String overallrating(){
        String rating="Not Available";
        Table table = dynamoDB.getTable("overallrating");


        ItemCollection<ScanOutcome> items = table.scan();
        Iterator<Item> iterator = items.iterator();


        Item item=null;

        while (iterator.hasNext()) {
            item=iterator.next();




          rating=item.get("rating").toString();


        }
        return rating;
    }

}
