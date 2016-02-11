package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.*;
import com.epam.spring.core.model.*;
import com.epam.spring.core.service.BookingService;
import com.epam.spring.core.service.DiscountService;

import java.util.Date;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class BookingServiceImpl implements BookingService {

    private AuditoriumDao auditoriumDao;
    private OccasionDao occasionDao;
    private UserDao userDao;
    private EventDao eventDao;
    private TicketDao ticketDao;
    private DiscountService discountService;

    public double getTicketPrice(Event event, Date date, int seatNumber, User user) {
        double ticketPrice = -1;
        Occasion occasion = findOccasion(event, date);
        if(checkForAvailableSeat(occasion, seatNumber)) {
            double discount = discountService.getDiscount(user, event, date);
            ticketPrice = event.getBasePrice() * event.getRating().getPriceMultiplier();
            if(occasion.getAuditorium().getVipSeats().contains(seatNumber)) {
                ticketPrice *= 2;
            }
            ticketPrice = event.getBasePrice() * (1 - discount);
        }
        return ticketPrice;
    }

    private boolean checkForAvailableSeat(Occasion occasion, int seatNumber) {
        if(occasion == null) {
            return false;
        } else {
            for(Ticket ticket: ticketDao.read()) {
                if(ticket.getOccasion().getId() == occasion.getId() && ticket.getSeat() == seatNumber) {
                    return false;
                }
            }
        }
        return true;
    }

    private Occasion findOccasion (Event event, Date date) {
        List<Occasion> occasions = occasionDao.read();
        Occasion foundOccasion = null;
        for (Occasion occasion: occasions) {
            Event occasionEvent = occasion.getEvent();
            Date occasionDate = occasion.getDate();
            if(occasionEvent.getId() == event.getId()) {
                if(occasionDate.compareTo(date) == 0) {
                    foundOccasion = occasion;
                }
            }
        }
        return foundOccasion;
    }

    private boolean isUserRegistered(User user) {
        return userDao.getById(user.getId()) != null;
    }

    public void bookTicket(User user, Ticket ticket) {
        if(!checkForAvailableSeat(ticket.getOccasion(), ticket.getSeat())){
            throw new RuntimeException("Seat is occupied already");
        }
        ticketDao.create(ticket);
        if(isUserRegistered(user)) {
            user.getPurchasedTickets().add(ticket);
            userDao.update(user);
        }
        ticket.getOccasion().getPurchasedTickets().add(ticket);
    }

    public List<Ticket> getTicketsForEvent(Event event, Date date) {
        Occasion occasion = findOccasion(event, date);
        return occasion!=null? occasion.getPurchasedTickets(): null;
    }
}
