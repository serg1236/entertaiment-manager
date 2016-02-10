package com.epam.spring.core.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sergiy_Dakhniy
 */
public class Occasion {
    private static int occasionCount = 0;

    private Event event;
    private Auditorium auditorium;
    private Date date;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Occasion(Date date, Auditorium auditorium, Event event) {
        this.date = date;
        this.auditorium = auditorium;
        this.event = event;
    }
}
