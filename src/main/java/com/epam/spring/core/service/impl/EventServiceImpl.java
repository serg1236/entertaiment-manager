package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.EventDao;
import com.epam.spring.core.dao.OccasionDao;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Occasion;
import com.epam.spring.core.service.EventService;

import java.util.Date;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class EventServiceImpl implements EventService {

    private EventDao eventDao;
    private OccasionDao occasionDao;

    public void create(Event event) {
        eventDao.create(event);
    }

    public void remove(Event event) {
        eventDao.delete(event);
    }

    public Event getByName(String name) {
        List<Event> events = getAll();
        Event foundEvent = null;
        for(Event event: events) {
            foundEvent = event;
            break;
        }
        return foundEvent;
    }

    public List<Event> getAll() {
        return eventDao.read();
    }

    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {
        List<Occasion> occasions = occasionDao.read();
        for(Occasion occasion: occasions) {
            //check if (there is no same event in the same time) || (other event in the same time in same auditorium)
            if((occasion.getDate().compareTo(date) == 0) &&
                    ((occasion.getAuditorium().getId() == auditorium.getId())||
                            occasion.getEvent().getId() == event.getId())) {
                throw new RuntimeException("Cannot add event. Check your inputs");
            }
        }
        Occasion occasion = new Occasion(date, auditorium, event);
        occasionDao.create(occasion);
    }
}
