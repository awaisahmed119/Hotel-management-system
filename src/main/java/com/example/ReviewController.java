package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReviewController {


    @GetMapping(value ="/review")
    public String reviews(Model model, HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        if(session==null)
            return "redirect:" + "/login";

        model.addAttribute("reviewobj", new Review());
        List<Review> reviews=Review.loadreviews();
        model.addAttribute("reviews", reviews);

        if(session.getAttribute("role").toString().equals("user"))
         return "reviews";
        else if(session.getAttribute("role").toString().equals("admin"))
            return "reviewAdmin";

        return "temp"; //will not be used.
    }





    @RequestMapping(value ="/submitreview", method = RequestMethod.POST)
    public String reviewsubmit(Model model, HttpServletRequest request,@ModelAttribute Review review) {
        HttpSession session=request.getSession(false);
        if(session==null)
            return "redirect:" + "/login";

        review.setUser(session.getAttribute("username").toString());
        if(review.savetoDB()){
            return "redirect:" + "/review";
        }

        else {
            model.addAttribute("science","error in submitting a review");
            return "hello";
        }

        }


      @RequestMapping(value ="/deletereview", method = RequestMethod.POST)
    public String delreview(Model model, HttpServletRequest request, @RequestParam("delbutton") String uid) {
        HttpSession session=request.getSession(false);
        if(session==null)
            return "redirect:" + "/login";

        if(Review.delreview(uid)){
            return "redirect:" + "/review";
        }

        else{
            model.addAttribute("science","error in deleting room");
            return "hello";
        }

    }

    @RequestMapping(value ="/rate", method = RequestMethod.POST)
    public String rate(Model model, HttpServletRequest request, @RequestParam("rating") String rating) {
        HttpSession session=request.getSession(false);
        if(session==null)
            return "redirect:" + "/login";

        String username=session.getAttribute("username").toString();
        if(Review.submitrating(username,rating)){
            return "redirect:" + "/review";
        }

        else{
            model.addAttribute("science","error in submittin rating. you can submit rating only once and input should be real number from 0-5");
            return "hello";
        }

    }


}
