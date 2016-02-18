package com.epam.spring.core.aspect.test;

import com.epam.spring.core.model.*;
import com.epam.spring.core.service.AuditoriumService;
import com.epam.spring.core.service.DiscountService;
import com.epam.spring.core.service.DiscountStatisticService;
import com.epam.spring.core.service.EventService;
import com.epam.spring.core.strategy.impl.BirthdayStrategy;
import com.epam.spring.core.strategy.impl.TenthTicketStrategy;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Sergiy_Dakhniy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class DiscountAspectTest implements ApplicationContextAware{

    @Autowired
    private DiscountService discountService;
    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private EventService eventService;
    @Autowired
    private DiscountStatisticService statisticService;
    private ApplicationContext context;
    private static User user;
    private static User user2;
    private static Calendar today;
    private static Calendar tomorrow;

    @BeforeClass
    public static void init() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        today = Calendar.getInstance();
        today.set(Calendar.YEAR, 2016);
        today.set(Calendar.MONTH, Calendar.JANUARY);
        today.set(Calendar.DAY_OF_MONTH, 1);

        tomorrow = Calendar.getInstance();
        tomorrow.set(Calendar.YEAR, 2016);
        tomorrow.set(Calendar.MONTH, Calendar.JANUARY);
        tomorrow.set(Calendar.DAY_OF_MONTH, 2);

        user = new User("Jon Snow", "wall@westeros.com", cal.getTime());
        user2 = new User("Ned Stark", "north@westeros.com", cal.getTime());
    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Test
    public void checkTotalUsages() {

        Event event = context.getBean(Event.class);
        Event event2 = context.getBean(Event.class);
        eventService.create(event);
        eventService.create(event2);
        Auditorium auditorium = auditoriumService.getAuditoriums().get(0);
        Auditorium auditorium2 = auditoriumService.getAuditoriums().get(1);
        eventService.assignAuditorium(event, auditorium, today.getTime());
        eventService.assignAuditorium(event2, auditorium2, today.getTime());
        double discount = discountService.getDiscount(user, event, today.getTime());
        double discount2 = discountService.getDiscount(user, event2, today.getTime());

        assertEquals(2, statisticService.getStatistic(BirthdayStrategy.class).getTotalUsage());
    }

    @Test
    public void checkUsagesForUser() {
        List<Ticket> tickets = new ArrayList<Ticket>();
        for(int i = 0; i < 9; i++) {
            Occasion occasion = new Occasion(today.getTime(), new Auditorium(), context.getBean(Event.class));
            tickets.add(new Ticket(occasion, i));
        }
        user2.setPurchasedTickets(tickets);
        double discount = discountService.getDiscount(user2, context.getBean(Event.class), tomorrow.getTime());

        Event event = context.getBean(Event.class);
        eventService.create(event);
        Auditorium auditorium = auditoriumService.getAuditoriums().get(0);
        eventService.assignAuditorium(event, auditorium, today.getTime());
        double discount2 = discountService.getDiscount(user, event, today.getTime());
        assertEquals(1, statisticService.getUsagesByUser(BirthdayStrategy.class, user));
        assertEquals(1, statisticService.getUsagesByUser(TenthTicketStrategy.class, user2));
    }

}
