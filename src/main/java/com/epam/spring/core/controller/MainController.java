package com.epam.spring.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Sergiy_Dakhniy
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public ModelAndView hello(@RequestParam(value = "message", required = false) String message){
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("message", message);
        return mv;
    }

    @RequestMapping("/accessDenied")
    public String accessDenied() {
        return "page403";
    }

}
