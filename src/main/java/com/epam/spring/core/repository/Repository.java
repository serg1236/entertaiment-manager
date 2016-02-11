package com.epam.spring.core.repository;

import com.epam.spring.core.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class Repository {

    private  Map<Integer, User> users = new HashMap<Integer, User>();
    private  Map<Integer, Ticket> tickets = new HashMap<Integer, Ticket>();
    private  Map<Integer, Event> events = new HashMap<Integer, Event>();
    private  Map<Integer, Occasion> occasions = new HashMap<Integer, Occasion>();
    private  Map<Integer, Auditorium> auditoriums;

    public void setAuditoriums(Map<Integer, Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    public  Map<Integer, User> getUsers() {
        return users;
    }

    public  Map<Integer, Ticket> getTickets() {
        return tickets;
    }

    public  Map<Integer, Event> getEvents() {
        return events;
    }

    public  Map<Integer, Occasion> getOccasions() {
        return occasions;
    }

    public  Map<Integer, Auditorium> getAuditoriums() {
        return auditoriums;
    }
}
