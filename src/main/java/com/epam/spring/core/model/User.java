package com.epam.spring.core.model;

import java.util.*;

public class User {
    private static int usersCount = 0;
    private int id = usersCount++;
    private String name;
    private String email;
    private Date birthDate;
    private Set<Ticket> purchasedTickets = new HashSet<Ticket>();

    public static int getUsersCount() {
        return usersCount;
    }

    public static void setUsersCount(int usersCount) {
        User.usersCount = usersCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }

    public void setPurchasedTickets(Set<Ticket> purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String name, String email, Date birthDate) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }
}
