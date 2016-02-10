package com.epam.spring.core.model;

public class Ticket {
    private static int ticketCount = 0;
    private int id = ticketCount++;
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
}
