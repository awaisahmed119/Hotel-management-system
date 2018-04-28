package com.example;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;

public class Picture {
    static BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIUKUAGF4RUZMJPNA", "SsicoPjOsBf6GQilvY5t0jBW7dV66dlC/lTZ1Tvv");
    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_WEST_2).withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();
    static DynamoDB dynamoDB = new DynamoDB(client);


    String url;
    String rid;
    String username;

    public Picture(String url, String rid) {
        this.url = url;
        this.rid = rid;
    }

    public Picture(String username, String url, String temp) {
        this.username = username;
        this.url = url;
    }

    public Picture() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public static ArrayList<Picture> loadpics(String rid) {
        Table table = dynamoDB.getTable("roompics");

        Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
        expressionAttributeValues.put(":rid", rid);


        ItemCollection<ScanOutcome> items = table.scan("rid = :rid", // FilterExpression
                "link", // ProjectionExpression
                null, expressionAttributeValues);
        Iterator<Item> iterator = items.iterator();
        Item item = null;

        ArrayList<Picture> list = new ArrayList<Picture>();
        while (iterator.hasNext()) {
            item = iterator.next();
            String url = item.get("link").toString();
            Picture p = new Picture(url, rid);
            list.add(p);
        }
        return list;
    }


    public static boolean uploadUrl(String rid, String url) {

        Table table = dynamoDB.getTable("roompics");
        // String id = UUID.randomUUID().toString();
        Item item = new Item().withPrimaryKey("url", url).withString("rid", rid).withString("link", url);
        try {
            table.putItem(item);
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    public static boolean uploadtoS3andStoreInDB(Map<String, Object> model, String rid, String name, MultipartFile file) {

        AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAIUKUAGF4RUZMJPNA", "SsicoPjOsBf6GQilvY5t0jBW7dV66dlC/lTZ1Tvv"));
        //    String bucketname="awais11";
        //    s3client.createBucket(bucketname);
        String picname = rid + name;

        byte[] contentBytes = null;
        InputStream is = null;
        try {
            is = file.getInputStream();
            contentBytes = IOUtils.toByteArray(is);

            is = file.getInputStream();

        } catch (IOException e) {
            System.out.print("error in uploading ");
            return false;
        }

        Long contentLength = Long.valueOf(contentBytes.length);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);


        try {
            s3client.putObject(new PutObjectRequest("awais11", picname, is, metadata).withCannedAcl(CannedAccessControlList.PublicRead));

            com.amazonaws.services.s3.model.S3Object s3Object = s3client.getObject(new GetObjectRequest("awais11", picname));
            String url = s3Object.getObjectContent().getHttpRequest().getURI().toString();

            model.put("picUrl", url);

            Picture.uploadUrl(rid, url);
        } catch (AmazonServiceException ase) {
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return false;
        } catch (AmazonClientException ace) {
            System.out.println("Error Message: " + ace.getMessage());
            return false;
        }
        return true;
    }

    public static boolean delpics(String url) {
        AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAIUKUAGF4RUZMJPNA", "SsicoPjOsBf6GQilvY5t0jBW7dV66dlC/lTZ1Tvv"));

        Table table = dynamoDB.getTable("roompics");

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("url", url));

        try {
            table.deleteItem(deleteItemSpec);
            System.out.println("Delete image link succeeded");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        try {
            URI uri = new URI(url);
            String[] segments = uri.getPath().split("/");
            String url2 = segments[segments.length - 1];
            s3client.deleteObject(new DeleteObjectRequest("awais11", url2));
        } catch (Exception e) {
        }

        return true;
    }


//functions related to user profile pictures

    public static boolean userPicUploadUrl(String username, String url) {

        Table table = dynamoDB.getTable("profilepics");
        // String id = UUID.randomUUID().toString();
        Item item = new Item().withPrimaryKey("username", username).withString("url", url);
        try {
            table.putItem(item);
        } catch (Exception e) {
            return false;
        }
        return true;

    }


    public static boolean userPicUploadtoS3andStoreInDB(Map<String, Object> model, String username, MultipartFile file) {

        AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAIUKUAGF4RUZMJPNA", "SsicoPjOsBf6GQilvY5t0jBW7dV66dlC/lTZ1Tvv"));
        //    String bucketname="awais11";
        //    s3client.createBucket(bucketname);
        String picname = username;

        byte[] contentBytes = null;
        InputStream is = null;
        try {
            is = file.getInputStream();
            contentBytes = IOUtils.toByteArray(is);

            is = file.getInputStream();

        } catch (IOException e) {
            System.out.print("error in uploading ");
            return false;
        }

        Long contentLength = Long.valueOf(contentBytes.length);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);


        try {
            s3client.putObject(new PutObjectRequest("userpics11", picname, is, metadata).withCannedAcl(CannedAccessControlList.PublicRead));

            com.amazonaws.services.s3.model.S3Object s3Object = s3client.getObject(new GetObjectRequest("userpics11", picname));
            String url = s3Object.getObjectContent().getHttpRequest().getURI().toString();

            model.put("picUrl", url);

            Picture.userPicUploadUrl(username, url);
        } catch (AmazonServiceException ase) {
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return false;
        } catch (AmazonClientException ace) {
            System.out.println("Error Message: " + ace.getMessage());
            return false;
        }
        return true;
    }

    public static String loadprofilePicUrl(String username) {
        String url = "";
        Table table = dynamoDB.getTable("profilepics");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("username = :v_usr")
                .withValueMap(new ValueMap()
                        .withString(":v_usr", username));

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item = null;
        while (iterator.hasNext()) {
            item = iterator.next();
            url = item.get("url").toString();

        }

        return url;
    }


    public static boolean delUserPic(String username) {
        AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAIUKUAGF4RUZMJPNA", "SsicoPjOsBf6GQilvY5t0jBW7dV66dlC/lTZ1Tvv"));

        Table table = dynamoDB.getTable("profilepics");

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("username", username));

        try {
            table.deleteItem(deleteItemSpec);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        try {

            s3client.deleteObject(new DeleteObjectRequest("userpics11", username));
        } catch (Exception e) {
        }

        return true;
    }

}