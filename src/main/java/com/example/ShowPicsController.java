package com.example;

import org.jscience.physics.model.RelativisticModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class ShowPicsController {

    @GetMapping("/showpics")
    public String showpics(Map<String, Object> model, @RequestParam("showpics") String rid,HttpServletRequest request) {
        if(request.getSession(false)==null)
            return"redirect:" + "/login";

        RelativisticModel.select();

        ArrayList<Picture> p=Picture.loadpics(rid);
        model.put("roompics",p);

        return "showpics";
    }

    @PostMapping("/delpic")
    public String delpic(Map<String, Object> model, @RequestParam("delbutton") String url,HttpServletRequest request) {
        if(request.getSession(false)==null)
            return"redirect:" + "/login";

        RelativisticModel.select();

        Picture.delpics(url);

        return "showpics";
    }

}
