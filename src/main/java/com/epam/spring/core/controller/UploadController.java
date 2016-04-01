package com.epam.spring.core.controller;

import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.AuditoriumService;
import com.epam.spring.core.service.EventService;
import com.epam.spring.core.service.UserService;
import com.epam.spring.core.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
@Controller
@RequestMapping("/upload")
public class UploadController {


    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private AuditoriumService auditoriumService;

    @RequestMapping
    public String renderUpload() {
        return "upload";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView uploadUsers(@RequestParam(value = "file", required = false) MultipartFile multipart ) throws IOException {
        User[] users = JsonUtils.getEntitiesArray(JsonUtils.parseFile(multipart), User[].class);
        for(User user: users){
            userService.register(user);
        }
        ModelAndView mv = new ModelAndView("redirect:/");
        String message = String.format("%d users uploaded successfully!", users.length);
        mv.addObject("message", message);
        return mv;
    }

    @RequestMapping(value = "/events", method = RequestMethod.POST)
    public ModelAndView uploadEvents(@RequestParam("file") MultipartFile multipart ) throws IOException {
        JsonNode node = JsonUtils.parseFile(multipart);
        JsonNode eventsNode = node.findValue("events");
        Event[] events = JsonUtils.getEntitiesArray(eventsNode, Event[].class);
        for(Event event: events){
            eventService.create(event);
        }
        JsonNode occasionsNode = node.findValue("occasions");
        for(JsonNode occasion: occasionsNode) {
            registerOccasion(occasion);
        }
        ModelAndView mv = new ModelAndView("redirect:/");
        String message = String.format("%d events uploaded successfully!", events.length);
        mv.addObject("message", message);
        return mv;
    }

    private void registerOccasion(JsonNode node) {
        String eventName = node.findValue("event").textValue();
        Event event = eventService.getByName(eventName);
        String auditoriumName = node.findValue("auditorium").textValue();
        Auditorium auditorium = auditoriumService.getByName(auditoriumName);
        Date date = null;
        try {
            date = new SimpleDateFormat("hh:mm dd-MM-yyyy").parse(node.findValue("date").textValue());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        eventService.assignAuditorium(event, auditorium, date);
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public void setAuditoriumService(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }
}
