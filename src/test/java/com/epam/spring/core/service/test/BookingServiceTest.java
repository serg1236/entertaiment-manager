package com.epam.spring.core.service.test;

import com.epam.spring.core.model.*;
import com.epam.spring.core.service.AuditoriumService;
import com.epam.spring.core.service.BookingService;
import com.epam.spring.core.service.EventService;
import com.epam.spring.core.service.UserService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by Sergiy_Dakhniy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class BookingServiceTest implements ApplicationContextAware{

    private ApplicationContext context;
    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;

    private Calendar today;
    private Calendar tomorrow;
    private Calendar birthDay1;
    private Calendar birthDay2;
    private static boolean contextInitialized = false;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }


    @Before
    private void init() {
        Event event1 = context.getBean(Event.class);
        event1.setName("Okean Elzy concert");
        event1.setRating(EventRating.HIGH);
        event1.setBasePrice(100);
        eventService.create(event1);
        Auditorium auditorium1 = auditoriumService.getAuditoriums().get(0);

        Event event2 = context.getBean(Event.class);
        event2.setName("O.Torvald concert");
        event2.setRating(EventRating.MEDIUM);
        event2.setBasePrice(50);
        eventService.create(event2);
        Auditorium auditorium2 = auditoriumService.getAuditoriums().get(1);

        Event event3 = context.getBean(Event.class);
        event3.setName("Beer pong champ");
        event3.setRating(EventRating.LOW);
        event3.setBasePrice(10);
        eventService.create(event3);
        Auditorium auditorium3 = auditoriumService.getAuditoriums().get(3);

        today = Calendar.getInstance();
        today.set(Calendar.YEAR, 2016);
        today.set(Calendar.MONTH, Calendar.JANUARY);
        today.set(Calendar.DAY_OF_MONTH, 1);

        tomorrow = Calendar.getInstance();
        tomorrow.set(Calendar.YEAR, 2016);
        tomorrow.set(Calendar.MONTH, Calendar.JANUARY);
        tomorrow.set(Calendar.DAY_OF_MONTH, 2);

        birthDay1 = Calendar.getInstance();
        birthDay1.set(Calendar.YEAR, 1994);
        birthDay1.set(Calendar.MONTH, Calendar.JANUARY);
        birthDay1.set(Calendar.DAY_OF_MONTH, 1);

        birthDay2 = Calendar.getInstance();
        birthDay2.set(Calendar.YEAR, 1988);
        birthDay2.set(Calendar.MONTH, Calendar.MAY);
        birthDay2.set(Calendar.DAY_OF_MONTH, 20);

        eventService.assignAuditorium(event1, auditorium1, today.getTime());
        eventService.assignAuditorium(event2, auditorium2, today.getTime());
        eventService.assignAuditorium(event3, auditorium2, tomorrow.getTime());

        userService.register(new User("Jon Snow", "wall@westeros.com", birthDay1.getTime()));
        userService.register(new User("Chuck Norris", "1@epam.com", birthDay2.getTime()));

    }

    @Test
    public void ticketPriceCheck() {
        Event okeanConcert = eventService.getByName("Okean Elzy concert");
        User jon = userService.getUserByEmail("wall@westeros.com");
        double price = bookingService.getTicketPrice(okeanConcert, today.getTime(), 5, jon);
        bookingService.bookTicket(jon, okeanConcert, today.getTime(), 5);
        double priceForBooked = bookingService.getTicketPrice(okeanConcert, today.getTime(), 5, jon);
        assertEquals(228.0, price, 0.001);
        assertEquals(-1.0, priceForBooked, 0.001);
    }

    @Test
    public void userTicketCheck() {
        Event okeanConcert = eventService.getByName("Okean Elzy concert");
        Event beerPong = eventService.getByName("Beer pong champ");
        User jon = userService.getUserByEmail("wall@westeros.com");
        bookingService.bookTicket(jon, okeanConcert, today.getTime(), 5);
        bookingService.bookTicket(jon, beerPong, tomorrow.getTime(), 2);
        assertEquals(2, userService.getBookedTickets(jon).size());
    }

    @Test(expected = RuntimeException.class)
    public void noEventTodayCheck() {
        Event beerPong = eventService.getByName("Beer pong champ");
        User jon = userService.getUserByEmail("wall@westeros.com");
        bookingService.bookTicket(jon, beerPong, today.getTime(), 5);
    }

    @Test(expected = RuntimeException.class)
    public void occupiedSeatCheck() {
        User jon = userService.getUserByEmail("wall@westeros.com");
        Event okeanConcert = eventService.getByName("Okean Elzy concert");
        bookingService.bookTicket(jon, okeanConcert, today.getTime(), 5);
        bookingService.bookTicket(jon, okeanConcert, today.getTime(), 5);
    }

    @Test
    public void checkPurchasedTickets() {
        User jon = userService.getUserByEmail("wall@westeros.com");
        Event okeanConcert = eventService.getByName("Okean Elzy concert");
        Event beerPong = eventService.getByName("Beer pong champ");
        bookingService.bookTicket(jon, okeanConcert, today.getTime(), 5);
        bookingService.bookTicket(jon, okeanConcert, today.getTime(), 4);
        bookingService.bookTicket(jon, okeanConcert, today.getTime(), 3);
        assertEquals(3, bookingService.getTicketsForEvent(okeanConcert, today.getTime()).size());
        assertNull(bookingService.getTicketsForEvent(okeanConcert, tomorrow.getTime()));
        assertEquals(0, bookingService.getTicketsForEvent(beerPong, tomorrow.getTime()).size());
    }
}
