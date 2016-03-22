package com.epam.spring.core.controller;

import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.AuditoriumService;
import com.epam.spring.core.service.EventService;
import com.epam.spring.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
@Controller
@RequestMapping("/view")
public class InfoController {
    @Autowired
    AuditoriumService auditoriumService;
    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;

    @RequestMapping("/auditoriums")
    ModelAndView getAuditoriumsInfo() {
        ModelAndView mv = new ModelAndView("auditoriums");
        List<Auditorium> auditoriums = auditoriumService.getAuditoriums();
        mv.addObject("auditoriums", auditoriums);
        return mv;
    }

    @RequestMapping("/events")
    ModelAndView getEventsInfo() {
        ModelAndView mv = new ModelAndView("events");
        List<Event> events = eventService.getAll();
        mv.addObject("events", events);
        return mv;
    }

    @RequestMapping("/users/{name}")
    ModelAndView getUsersByName(@PathVariable String name) {
        ModelAndView mv = new ModelAndView("users");
        List<User> users = userService.getUsersByName(name);
        mv.addObject("users", users);
        return mv;
    }

    @RequestMapping("/auditoriums/{name}")
    ModelAndView getAuditoriumByName(@PathVariable String name) {
        ModelAndView mv = new ModelAndView("auditoriums");
        Auditorium auditorium = auditoriumService.getByName(name);
        List<Auditorium> auditoriums = new ArrayList<>();
        if(auditorium != null) {
            auditoriums.add(auditorium);
        }
        mv.addObject("auditoriums", auditoriums);
        return mv;
    }

    @RequestMapping("/events/{name}")
    ModelAndView getEventByName(@PathVariable String name) {
        ModelAndView mv = new ModelAndView("events");
        Event event = eventService.getByName(name);
        List<Event> events = new ArrayList<>();
        if(event != null) {
            events.add(event);
        }
        mv.addObject("events", events);
        return mv;
    }

    public void setAuditoriumService(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
