package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.*;
import com.epam.spring.core.exception.BookingException;
import com.epam.spring.core.model.*;
import com.epam.spring.core.service.BookingService;
import com.epam.spring.core.service.DiscountService;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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
        double ticketPrice = 0;
        Occasion occasion = findOccasion(event, date);
        if(checkForAvailableSeat(occasion, seatNumber)) {
            double discount = discountService.getDiscount(user, event, date);
            ticketPrice = event.getBasePrice() * event.getRating().getPriceMultiplier();
            if(occasion.getAuditorium().getVipSeats().contains(seatNumber)) {
                ticketPrice *= 2;
            }
            ticketPrice *= (1 - discount);
        } else {
            throw new RuntimeException("Occasion is not found or seat is occupied");
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
                    break;
                }
            }
        }
        return foundOccasion;
    }

    private boolean isUserRegistered(User user) {
        return userDao.getById(user.getId()) != null;
    }

    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {BookingException.class, SQLException.class, DataAccessException.class})
    public void bookTicket(User user, Event event, Date date, int seat) {
        Occasion occasion = findOccasion(event, date);
        if(occasion == null) {
            throw new BookingException("No event for this date found");
        }
        Ticket ticket = new Ticket(occasion, seat);

        if(!checkForAvailableSeat(ticket.getOccasion(), ticket.getSeat())){
            throw new BookingException("Seat is occupied already");
        }
        double price  = getTicketPrice(event, date, seat, user);
        if(user.getMoney() < price) {
            throw new BookingException("Not enough money. Please fill your account!");
        }
        ticketDao.create(ticket);
        if(isUserRegistered(user)) {
            user.getPurchasedTickets().add(ticket);
            userDao.update(user);
        }
        user.setMoney(user.getMoney() - price);
        userDao.update(user);
        ticket.getOccasion().getPurchasedTickets().add(ticket);
    }

    public List<Ticket> getTicketsForEvent(Event event, Date date) {
        Occasion occasion = findOccasion(event, date);
        return ticketDao.getByOccasion(occasion);
    }

	public void setAuditoriumDao(AuditoriumDao auditoriumDao) {
		this.auditoriumDao = auditoriumDao;
	}

	public void setOccasionDao(OccasionDao occasionDao) {
		this.occasionDao = occasionDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setEventDao(EventDao eventDao) {
		this.eventDao = eventDao;
	}

	public void setTicketDao(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}
    
    
    
    
}
