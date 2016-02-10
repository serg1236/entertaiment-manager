package com.epam.spring.core.repository;

import com.epam.spring.core.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class Repository {

/*    private static Map<Integer, User> users = new HashMap<Integer, User>();
    private static Map<Integer, Ticket> tickets = new HashMap<Integer, Ticket>();
    private static Map<Integer, Event> events = new HashMap<Integer, Event>();
    private static Map<Integer, Occasion> occasions = new HashMap<Integer, Occasion>();
    private static Map<Integer, Auditorium> auditoriums = new HashMap<Integer, Auditorium>();*/

    private static Map <Class<?>, Map<Integer, ?> > repository = new HashMap<Class<?>, Map<Integer, ?>>();

    static {
        repository.put(User.class, new HashMap<Integer, User>());
        repository.put(Event.class, new HashMap<Integer, Event>());
        repository.put(Ticket.class, new HashMap<Integer, Ticket>());
        repository.put(Occasion.class, new HashMap<Integer, Occasion>());
        repository.put(Auditorium.class, new HashMap<Integer, Auditorium>());
    }



   /* public static Map<Integer, User> getUsers() {
        return users;
    }*/

   /* public static Map<Integer, Ticket> getTickets() {
        return tickets;
    }

    public static Map<Integer, Event> getEvents() {
        return events;
    }

    public static Map<Integer, Occasion> getOccasions() {
        return occasions;
    }

    public static Map<Integer, Auditorium> getAuditoriums() {
        return auditoriums;
    }*/

    public static <T> Map<Integer, T> getItems(Class<T> type){
        return (Map<Integer, T>) repository.get(type);
    }
}
