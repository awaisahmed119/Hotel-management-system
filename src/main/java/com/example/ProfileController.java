package com.example;

import org.jscience.physics.model.RelativisticModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {



        if(request.getSession(false)==null)
            return"redirect:" + "/login";
        else{
            String username=request.getSession(false).getAttribute("username").toString();
            Profile p=Profile.loadprofile(username);
           if(p==null){
               p=new Profile();
               p.setUsername(username);
           }
           model.addAttribute("picurl",Picture.loadprofilePicUrl(username));
            model.addAttribute("profile", p);
            return "profile";
        }

    }

    @PostMapping("/submitprofile")
    public String loginSubmit(Map<String, Object> model, @ModelAttribute Profile p, HttpServletRequest request) {
        RelativisticModel.select();

        if(request.getSession(false)==null)
            return"redirect:" + "/login";

        String username=request.getSession(false).getAttribute("username").toString();
        p.setUsername(username);
        if(p.savetoDB()){
            model.put("science", "Your profile saved successfully");
        }
        else
            model.put("science", "Error in updating profile");

        return "hello";
    }
}
