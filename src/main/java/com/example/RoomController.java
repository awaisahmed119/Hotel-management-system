package com.example;

import org.jscience.physics.model.RelativisticModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
public class RoomController {
    @GetMapping("/addroom")
    public String addroomForm(Model model, HttpServletRequest request) {
        if(request.getSession(false)==null)
             return"redirect:" + "/login";

        model.addAttribute("room", new Room());

        return "addroom";

    }


    @PostMapping("/submitroom")
    public String addroomSubmit(Map<String, Object> model, @ModelAttribute Room r,HttpServletRequest request) {
        if(request.getSession(false)==null)
            return"redirect:" + "/login";

        RelativisticModel.select();
            if(r.addroom()) {
                model.put("science", "Room added Successfully in Database");
            }
            else
                 model.put("science", "Can't add the room in Database");
            return "hello";
    }

    @PostMapping("/delroom")
    public String delroom(Map<String, Object> model,@RequestParam("delbutton") String id,HttpServletRequest request) {
        if(request.getSession(false)==null)
            return"redirect:" + "/login";

        RelativisticModel.select();

        if(Room.deleteroom(id)) {
            model.put("science", "Room Deleted Successfully form Database");
        }
        else
            model.put("science", "Can't delete the room from Database");
        return "hello";
    }


    @PostMapping("/updtroom")
    public String updtroom(Map<String, Object> model,@RequestParam("updtbutton") String id,HttpServletRequest request) {
        if(request.getSession(false)==null)
            return"redirect:" + "/login";

        RelativisticModel.select();
        Room r=null;
        r=Room.loadroom(id);
        if(r!=null) {
            model.put("room", r);
        }

        return "editroom";
    }


    @PostMapping("/submitupdt")
    public String submitupdt(Map<String, Object> model,@ModelAttribute Room r,HttpServletRequest request) {
        if(request.getSession(false)==null)
            return"redirect:" + "/login";

        RelativisticModel.select();

        if(r.updateroom()) {
            model.put("science", "Updated");
        }
        else
            model.put("science", "Couldn't Update data in database");

        return "hello";
    }

    @PostMapping("/bookroom")
    public String bookroom(Map<String, Object> model,@RequestParam("bookbutton") String id,HttpServletRequest request) {
        if(request.getSession(false)==null)
            return"redirect:" + "/login";

        RelativisticModel.select();

        if(Room.bookroom(id)) {
            model.put("science", "Room Booked Successfully");
        }
        else
            model.put("science", "There is error in room booking");

        return "hello";
    }
}
