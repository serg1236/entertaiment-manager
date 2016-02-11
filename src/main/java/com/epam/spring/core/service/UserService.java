package com.epam.spring.core.service;

import java.util.List;

import com.epam.spring.core.model.Ticket;
import com.epam.spring.core.model.User;

public interface UserService {

    void register(User user);
    void remove(User user);
    User getById(int id);
    User getUserByEmail(String email);
    List<User> getUsersByName(String name);
    List<Ticket> getBookedTickets(User user);


}
