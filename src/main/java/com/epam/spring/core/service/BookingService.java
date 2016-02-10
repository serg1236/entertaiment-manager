package com.epam.spring.core.service;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Ticket;
import com.epam.spring.core.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public interface BookingService {
    double getTicketPrice(Event event, Date date, List<Integer> seats, User user);
    void bookTicket(User user, Ticket ticket);
    List<Ticket> getTicketsForEvent(Event event, Date date);
}