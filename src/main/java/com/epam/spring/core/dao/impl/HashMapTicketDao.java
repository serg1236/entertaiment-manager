package com.epam.spring.core.dao.impl;

import com.epam.spring.core.dao.TicketDao;
import com.epam.spring.core.model.Ticket;
import com.epam.spring.core.repository.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class HashMapTicketDao implements TicketDao {

    private Repository repository;
    private static int ticketCount = 0;

    public List<Ticket> read() {
        return new ArrayList<Ticket>(repository.getTickets().values());
    }

    public void create(Ticket entry) {
        entry.setId(ticketCount++);
        repository.getTickets().put(entry.getId(), entry);
    }

    public void delete(Ticket entry) {
        repository.getTickets().remove(entry.getId());
    }

    public void update(Ticket entry) {
        repository.getTickets().put(entry.getId(), entry);
    }

    public Ticket getById(int id) {
        return repository.getTickets().get(id);
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
