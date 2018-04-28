package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @GetMapping(value ="/users")
    public String users(Model model, HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        if(session==null)
            return "redirect:" + "/login";

        List<Signup> users=Signup.loadusers();
        model.addAttribute("users", users);

        if(session.getAttribute("role").toString().equals("user"))
            return "redirect:" + "/login";
        else if(session.getAttribute("role").toString().equals("admin"))
            return "userdetails";

        return "temp"; //will not be used.
    }

    @RequestMapping(value ="/deleteuser", method = RequestMethod.POST)
    public String delreview(Model model, HttpServletRequest request, @RequestParam("delbutton") String uid) {
        HttpSession session=request.getSession(false);
        if(session==null)
            return "redirect:" + "/login";

        if(Signup.deluser(uid)){
            return "redirect:" + "/users";
        }

        else{
            model.addAttribute("science","error in deleting user");
            return "hello";
        }

    }
}
