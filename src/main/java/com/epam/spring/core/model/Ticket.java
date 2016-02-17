package com.epam.spring.core.model;

public class Ticket {
    private int id;
    private Occasion occasion;
    private int seat;

    public Occasion getOccasion() {
        return occasion;
    }

    public void setOccasion(Occasion occasion) {
        this.occasion = occasion;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public Ticket(Occasion occasion, int seat) {
        this.occasion = occasion;
        this.seat = seat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
