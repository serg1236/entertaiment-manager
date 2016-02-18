package com.epam.spring.core.aspect;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.EventStatistic;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.EventService;
import com.epam.spring.core.service.EventStatisticService;
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

    EventStatisticService eventStatisticService;

    @Pointcut("execution(* com.epam.spring.core.*.BookingService.getTicketPrice(..))")
    public void getTicketPriceMethod(){};

    @Pointcut("execution(* com.epam.spring.core.*.BookingService.bookTicket(..))")
    public void bookTicketMethod() {};

    @Pointcut("execution(* com.epam.spring.core.*.EventService.getByName(..))")
    public void getByNameMethod() {};

    @After("getTicketPriceMethod() && args(event, date, seat, user)")
    public void countPriceQueries(Event event, Date date, int seat, User user) {
        EventStatistic statistic = eventStatisticService.createOrGetStatistic(event);
        statistic.setPriceRequired(statistic.getPriceRequired() + 1);
        eventStatisticService.updateStatistic(statistic);
    }

    @AfterReturning("bookTicketMethod() && args(user, event, date, seat)")
    public void countBookedTickets (User user, Event event, Date date, int seat) {
        EventStatistic statistic = eventStatisticService.createOrGetStatistic(event);
        statistic.setTicketsBooked(statistic.getTicketsBooked() + 1);
        eventStatisticService.updateStatistic(statistic);
    }

    @AfterReturning(pointcut = "getByNameMethod()", returning = "event")
    public void countNameRequest(Event event) {
        EventStatistic statistic = eventStatisticService.createOrGetStatistic(event);
        statistic.setAccessedByName(statistic.getAccessedByName() + 1);
        eventStatisticService.updateStatistic(statistic);
    }

    public void setEventStatisticService(EventStatisticService eventStatisticService) {
        this.eventStatisticService = eventStatisticService;
    }
}
