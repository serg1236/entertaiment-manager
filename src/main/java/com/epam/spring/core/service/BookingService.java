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
    double getTicketPrice(Event event, Date date, int seat, User user);
    void bookTicket(User user, Event event, Date date, int seat);
    List<Ticket> getTicketsForEvent(Event event, Date date);
}
