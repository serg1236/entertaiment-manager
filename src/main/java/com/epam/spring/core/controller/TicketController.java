package com.epam.spring.core.controller;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Ticket;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.BookingService;
import com.epam.spring.core.service.EventService;
import com.epam.spring.core.service.UserService;
import com.sun.javafx.sg.prism.NGShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @RequestMapping
    String renderPage() {
        return "tickets";
    }

    @RequestMapping(value = "/byEvent", headers = "Accept=application/pdf")
    public ModelAndView getByEvent(@RequestParam("name")String name,
                                   @RequestParam("date") @DateTimeFormat(pattern = "HH:mm dd-MM-yyyy") Date date) {
        Event event = eventService.getByName(name);
        List<Ticket> tickets = bookingService.getTicketsForEvent(event, date);
        ModelAndView mv = new ModelAndView("TicketPdfView");
        mv.addObject("message", String.format("Tickets for event %s", event.getName()));
        mv.addObject("tickets", tickets);
        return mv;
    }

    @RequestMapping(value = "/byUser", headers = "Accept=application/pdf")
    public ModelAndView getByUser(@RequestParam("email")String email) {
        User user = userService.getUserByEmail(email);
        List<Ticket> tickets = userService.getBookedTickets(user);
        ModelAndView mv = new ModelAndView("TicketPdfView");
        mv.addObject("message", String.format("Tickets for user %s", user.getName()));
        mv.addObject("tickets", tickets);
        return mv;
    }

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
