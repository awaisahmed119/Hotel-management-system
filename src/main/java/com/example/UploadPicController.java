package com.example;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
@Controller
public class UploadPicController {



    @PostMapping("/submitpic")
    public String upldpic(Map<String, Object> model, @RequestParam("rid") String rid, @RequestParam("name") String name, @RequestParam("file") MultipartFile file,HttpServletRequest request) {

        if(request.getSession(false)==null)
            return"redirect:" + "/login";

        Picture.uploadtoS3andStoreInDB(model,rid,name,file);

        return "addroompic";
    }

    @GetMapping("/upldpic")
    public String signupSubmit(Map<String, Object> model,HttpServletRequest request) {
        if(request.getSession(false)==null)
            return"redirect:" + "/login";

        return "addroompic";
    }


    @PostMapping("/submitprofilepic")
    public String profilepic(Map<String, Object> model, @RequestParam("file") MultipartFile file,HttpServletRequest request) {

        if(request.getSession(false)==null)
            return"redirect:" + "/login";


        String username=request.getSession(false).getAttribute("username").toString();
        Picture.userPicUploadtoS3andStoreInDB(model,username,file);

        return"redirect:" + "/profile";
    }

    @PostMapping("/removeprofilepic")
    public String removeprofilepic(HttpServletRequest request) {

        if(request.getSession(false)==null)
            return"redirect:" + "/login";


        String username=request.getSession(false).getAttribute("username").toString();

        Picture.delUserPic(username);

        return"redirect:" + "/profile";
    }

}
