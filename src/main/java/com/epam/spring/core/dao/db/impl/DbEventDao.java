package com.epam.spring.core.dao.db.impl;

import com.epam.spring.core.dao.EventDao;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.repository.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class DbEventDao implements EventDao {
    private Repository repository;
    private static int eventCount = 0;

    public List<Event> read() {
        return new ArrayList<Event>(repository.getEvents().values());
    }

    public void create(Event entry) {
        entry.setId(eventCount++);
        repository.getEvents().put(entry.getId(), entry);
    }

    public void delete(Event entry) {
        repository.getEvents().remove(entry.getId());
    }

    public void update(Event entry) {
        repository.getEvents().put(entry.getId(), entry);
    }

    public Event getById(int id) {
        return repository.getEvents().get(id);
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
