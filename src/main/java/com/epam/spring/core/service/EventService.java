package com.epam.spring.core.service;

import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;

import java.util.Date;
import java.util.List;

public interface EventService {
    void create(Event event);
    void remove(Event event);
    Event getByName(String name);
    List<Event> getAll();
    void assignAuditorium(Event event, Auditorium auditorium, Date date);

}
