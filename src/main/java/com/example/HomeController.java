package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping(value ="/home", method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        if(session==null)
            return "redirect:" + "/login";

       if(session.getAttribute("role").equals("user")) {
           Room r = new Room();
           List<Room> rooms = r.getrooms("user");
           model.addAttribute("rooms", rooms);
          String rating= Review.overallrating();
           model.addAttribute("rating",rating);
           return "uhome";
       }

       else if(session.getAttribute("role").equals("admin")){
           Room r = new Room();
           List<Room> rooms = r.getrooms("admin");
           model.addAttribute("rooms", rooms);
           return "home";
       }
        return "redirect:" + "/login";
    }





}
