package com.example;


import org.jscience.physics.model.RelativisticModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginForm(Model model,HttpServletRequest request) {
        model.addAttribute("login", new Login());
       if(request.getSession(false)==null)
            return "login";
        else
            return"redirect:" + "/home";
    }


    @PostMapping("/login")
    public String loginSubmit(Map<String, Object> model,@ModelAttribute Login login,HttpServletRequest request) {
        RelativisticModel.select();
        /*
        Room r=new Room();
        ArrayList<String> rooms=r.getrooms();


        model.put("science", login.username + login.getPassword());
        model.put("rooms",rooms );
        */
        if (login.authenticate().equals("user")){
           HttpSession session=request.getSession();
           session.setAttribute("role","user");
           session.setAttribute("username",login.getUsername());
            return"redirect:" + "/home";
    }
          else if(login.authenticate().equals("admin")) {
            HttpSession session=request.getSession();
            session.setAttribute("role","admin");
            session.setAttribute("username",login.getUsername());
            return"redirect:" + "/home";
        }
        model.put("science", "Username or Password is Incorrect");
            return "hello";
    }


    @GetMapping("/logout")
    public String logout(Model model,HttpServletRequest request) {
        HttpSession session=request.getSession(false);
            if(session!=null)
                session.invalidate();
            return"redirect:" + "/login";
    }

}
