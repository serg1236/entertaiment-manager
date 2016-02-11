package com.epam.spring.core.dao.impl;

import com.epam.spring.core.dao.AuditoriumDao;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.repository.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class HashMapAuditoriumDao implements AuditoriumDao{

    private Repository repository;

    public List<Auditorium> read() {
        return new ArrayList<Auditorium>(repository.getAuditoriums().values() );
    }

    public void create(Auditorium entry) {
        throw new RuntimeException("Auditorium creation is not supported");
    }

    public void delete(Auditorium entry) {
        throw new RuntimeException("Auditorium removal is not supported");
    }

    public void update(Auditorium entry) {
        repository.getAuditoriums().put(entry.getId(), entry);
    }

    public Auditorium getById(int id) {
        return repository.getAuditoriums().get(id);
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
