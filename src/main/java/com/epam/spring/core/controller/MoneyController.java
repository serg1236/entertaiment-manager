package com.epam.spring.core.controller;

import com.epam.spring.core.model.User;
import com.epam.spring.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Sergiy_Dakhniy
 */
@Controller
@RequestMapping("transactions")
public class MoneyController {

    @Autowired
    UserService userService;

    @RequestMapping("fill-account")
    public ModelAndView fillAccount(@RequestParam double sum) {

        ModelAndView mv = new ModelAndView("index");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        if(sum <= 0) {
            mv.addObject("message", "Invalid sum!");
        } else {
            userService.fillAccount(userDetail.getUsername(), sum);
            mv.addObject("message", "Your account updated successfully!");
        }
        return mv;
    }
}
