package com.epam.spring.core.aspect.test;

import com.epam.spring.core.aspect.LuckyWinnerAspect;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.EventRating;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.*;
import com.epam.spring.core.strategy.impl.BirthdayStrategy;
import com.epam.spring.core.strategy.impl.TenthTicketStrategy;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sergiy_Dakhniy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class LuckyWinnerAspectTest implements ApplicationContextAware{

    @Autowired
    private BookingService bookingService;
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

    @Value("${luck.generator}")
    private String generatorUsed;

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

        user = new User("Jon Snow", "wall@westeros.com", cal.getTime());
    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Test
    public void checkWhenLucky() {
        Event event = context.getBean(Event.class);
        event.setBasePrice(500);
        event.setRating(EventRating.MEDIUM);
        eventService.create(event);
        Auditorium auditorium = auditoriumService.getAuditoriums().get(0);
        eventService.assignAuditorium(event, auditorium, today.getTime());
        double price = bookingService.getTicketPrice(event, today.getTime(), 5, user);
        System.out.println(generatorUsed);
        if(generatorUsed.equals("trueGenerator")) {
            assertEquals(0, price, 0.001);
        }else if (generatorUsed.equals("falseGenerator")){
            assertEquals(1045, price, 0.001);
        }
    }
}
