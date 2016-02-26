package com.epam.spring.core.service.test;

import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.EventRating;
import com.epam.spring.core.service.AuditoriumService;
import com.epam.spring.core.service.EventService;
import org.junit.Before;
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
public class EventServiceTest implements ApplicationContextAware{

    private ApplicationContext context;
    private static List<Event> events = new ArrayList<Event>();
    @Autowired
    private EventService eventService;
    @Autowired
    private AuditoriumService auditoriumService;

    @BeforeClass
    public static void initEvents() {
        Event event1 = new Event();
        event1.setName("Okean Elzy concert");
        event1.setRating(EventRating.HIGH);

        Event event2 = new Event();
        event2.setName("EPAM Winter party");
        event2.setRating(EventRating.HIGH);
        events.add(event1);
        events.add(event2);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Test
    public void getPrototypeEventBeans() {
        assertNotSame(context.getBean(Event.class), context.getBean(Event.class));
    }

    @Test
    public void createEvents() {
        for(Event event: events) {
            eventService.create(event);
        }
        assertEquals(2, eventService.getAll().size());
    }

    @Test(expected = RuntimeException.class)
    public void getByNameAndAssignSameAuditoriums() {
        for(Event event: events) {
            eventService.create(event);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.MAY);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        List<Auditorium> auditoriums = auditoriumService.getAuditoriums();

        Event event1 = eventService.getByName("EPAM Winter party");
        Event event2 = eventService.getByName("Okean Elzy concert");
        Event event3 = eventService.getByName("Some other name");
        assertNotNull(event1);
        assertNotNull(event2);
        assertEquals(event2.getName(), "Okean Elzy concert");
        assertNull(event3);
        eventService.assignAuditorium(event1, auditoriums.get(0), cal.getTime());
        eventService.assignAuditorium(event2, auditoriums.get(0), cal.getTime());
    }

    @Test
    public void remove() {
        for(Event event: events) {
            eventService.create(event);
        }
        eventService.remove(eventService.getByName("EPAM Winter party"));
        assertEquals(1, eventService.getAll().size());
    }
}
