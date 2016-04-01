package com.epam.spring.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.Contended;

/**
 * Created by Sergiy_Dakhniy
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("message", "You logged out successfully!");
        return mv;
    }

    @RequestMapping("/error")
    public ModelAndView error() {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("error", "Cannot find you in our DB. Please check your credentials");
        return mv;
    }

}
