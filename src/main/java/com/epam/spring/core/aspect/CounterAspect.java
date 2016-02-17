package com.epam.spring.core.aspect;

import com.epam.spring.core.dao.EventStatisticDao;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.EventStatistic;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.EventService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
@Aspect
public class CounterAspect {

    private EventStatisticDao dao;

    @Autowired
    EventService eventService;

    @Pointcut("execution(* com.epam.spring.core.*.BookingService.getTicketPrice(..))")
    public void getTicketPriceMethod(){};

    @Pointcut("execution(* com.epam.spring.core.*.BookingService.bookTicket(..))")
    public void bookTicketMethod() {};

    @Pointcut("execution(* com.epam.spring.core.*.EventService.getByName(..))")
    public void getByNameMethod() {};

    @After("getTicketPriceMethod() && args(event, date, seat, user)")
    public void countPriceQueries(Event event, Date date, int seat, User user) {
        EventStatistic statistic = getStatistic(event);
        statistic.setPriceRequired(statistic.getPriceRequired() + 1);
        dao.update(statistic);
    }

    @AfterReturning("bookTicketMethod() && args(user, event, date, seat)")
    public void countBookedTickets (User user, Event event, Date date, int seat) {
        EventStatistic statistic = getStatistic(event);
        statistic.setTicketsBooked(statistic.getTicketsBooked() + 1);
        dao.update(statistic);
    }

    @After("getByNameMethod() && args(eventName) && !target(CounterAspect) ")
    public void countNameRequest(String eventName) {
        Event event = eventService.getByName(eventName);
        EventStatistic statistic = getStatistic(event);
        statistic.setAccessedByName(statistic.getAccessedByName() + 1);
        System.out.println("FIRED!!!!");
        dao.update(statistic);
    }

    private EventStatistic getStatistic (Event event) {
        EventStatistic statistic = dao.getByEvent(event);
        if (statistic == null) {
            statistic = new EventStatistic();
            statistic.setEvent(event);
            dao.create(statistic);
        }
        //calling again to get with set id
        return dao.getByEvent(event);
    }

    public void setDao(EventStatisticDao dao) {
        this.dao = dao;
    }

    public List<EventStatistic> getStatistics() {
        return dao.read();
    }

    public EventStatistic getStatisticByEvent(Event event) {
        return dao.getByEvent(event);
    }
}
