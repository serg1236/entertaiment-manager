package com.epam.spring.core.model;

import java.util.*;

/**
 * Created by Sergiy_Dakhniy
 */
public class Occasion {
    private int id;
    private Event event;
    private Auditorium auditorium;
    private Date date;
    private List<Ticket> purchasedTickets = new ArrayList<Ticket>();

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Occasion(Date date, Auditorium auditorium, Event event) {
        this.date = date;
        this.auditorium = auditorium;
        this.event = event;
    }

    public List<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }

    public void setPurchasedTickets(List<Ticket> purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }
}
