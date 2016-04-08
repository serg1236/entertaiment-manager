package com.epam.spring.core.controller;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.BookingService;
import com.epam.spring.core.service.EventService;
import com.epam.spring.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.Date;

/**
 * Created by Sergiy_Dakhniy
 */
@Controller
@RequestMapping("/book")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;

    @RequestMapping
    ModelAndView renderPage() {
        ModelAndView mv = new ModelAndView("booking");
        mv.addObject("balance", getCurrentUser().getMoney());
        return mv;
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    ModelAndView bookTicket(@RequestParam("event") String eventName,
                            @DateTimeFormat(pattern = "HH:mm dd-MM-yyyy") @RequestParam("date") Date date,
                            @RequestParam("seat") Integer seat) {
        User user = getCurrentUser();
        Event event = eventService.getByName(eventName);
        bookingService.bookTicket(user, event, date, seat);
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("message", "Ticket successfully booked!");
        return mv;
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        return userService.getUserByEmail(userDetail.getUsername());
    }

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
