package com.epam.spring.core.aspect.test;

import com.epam.spring.core.aspect.CounterAspect;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.EventRating;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.AuditoriumService;
import com.epam.spring.core.service.BookingService;
import com.epam.spring.core.service.EventService;
import com.epam.spring.core.service.UserService;
import org.junit.Before;
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
public class CounterAspectTest implements ApplicationContextAware{

    private ApplicationContext context;

    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private CounterAspect aspect;

    private Calendar today;
    private Calendar tomorrow;
    private Calendar birthDay1;
    private Calendar birthDay2;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Before
    public void init() {
        Event event1 = context.getBean(Event.class);
        event1.setName("Okean Elzy concert");
        event1.setRating(EventRating.HIGH);
        event1.setBasePrice(100);
        eventService.create(event1);
        Auditorium auditorium1 = auditoriumService.getAuditoriums().get(0);

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


        eventService.assignAuditorium(event1, auditorium1, today.getTime());
        eventService.assignAuditorium(event3, auditorium2, tomorrow.getTime());

        userService.register(new User("Jon Snow", "wall@westeros.com", birthDay1.getTime()));

    }

    @Test
    public void priceStatisticCheck() {
        Event okeanConcert = eventService.getByName("Okean Elzy concert");
        Event beerPong = eventService.getByName("Beer pong champ");
        User jon = userService.getUserByEmail("wall@westeros.com");
        bookingService.getTicketPrice(okeanConcert, today.getTime(), 5, jon);
        bookingService.getTicketPrice(okeanConcert, today.getTime(), 5, jon);
        bookingService.getTicketPrice(okeanConcert, today.getTime(), 5, jon);
        bookingService.getTicketPrice(beerPong, tomorrow.getTime(), 5, jon);
        assertEquals(3, aspect.getStatisticByEvent(okeanConcert).getPriceRequired());
        assertEquals(1, aspect.getStatisticByEvent(beerPong).getPriceRequired());
    }

    @Test
    public void bookTicketCheck() {
        Event okeanConcert = eventService.getByName("Okean Elzy concert");
        Event beerPong = eventService.getByName("Beer pong champ");
        User jon = userService.getUserByEmail("wall@westeros.com");
        bookingService.bookTicket(jon, okeanConcert, today.getTime(), 5);
        bookingService.bookTicket(jon, okeanConcert, today.getTime(), 7);
        bookingService.bookTicket(jon, beerPong, tomorrow.getTime(), 5);
        assertEquals(2, aspect.getStatisticByEvent(okeanConcert).getTicketsBooked());
        assertEquals(1, aspect.getStatisticByEvent(beerPong).getTicketsBooked());
        assertEquals(0, aspect.getStatisticByEvent(beerPong).getPriceRequired());
    }

    @Test
    public void nameAccessCheck() {
        Event okeanConcert = eventService.getByName("Okean Elzy concert");
        Event okeanConcert2 = eventService.getByName("Okean Elzy concert");
        Event beerPong = eventService.getByName("Beer pong champ");
        assertEquals(2, aspect.getStatisticByEvent(okeanConcert).getAccessedByName());
        assertEquals(1, aspect.getStatisticByEvent(beerPong).getAccessedByName());
    }
}
