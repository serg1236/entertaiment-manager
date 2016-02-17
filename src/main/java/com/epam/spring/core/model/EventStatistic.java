package com.epam.spring.core.model;

/**
 * Created by Sergiy_Dakhniy
 */
public class EventStatistic {
    private int id;
    private Event Event;
    private int priceRequired = 0;
    private int accessedByName = 0;
    private int ticketsBooked = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return Event;
    }

    public void setEvent(Event event) {
        Event = event;
    }

    public int getPriceRequired() {
        return priceRequired;
    }

    public void setPriceRequired(int priceRequired) {
        this.priceRequired = priceRequired;
    }

    public int getAccessedByName() {
        return accessedByName;
    }

    public void setAccessedByName(int accessedByName) {
        this.accessedByName = accessedByName;
    }

    public int getTicketsBooked() {
        return ticketsBooked;
    }

    public void setTicketsBooked(int ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }
}
