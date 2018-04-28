package com.example;

import org.jscience.physics.model.RelativisticModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signup", new Signup());
        return "signup";
    }


    @PostMapping("/signup")
    public String signupSubmit(Map<String, Object> model, @ModelAttribute Signup signup) {

        RelativisticModel.select();

        if( signup.addtoDb())
            model.put("science", signup.username + signup.getPassword());
        else {
            model.put("science", "User name is already taken Or some connection issues due to which you are unable to sign in");
            return "hello";
        }
        return "login";
    }
}
