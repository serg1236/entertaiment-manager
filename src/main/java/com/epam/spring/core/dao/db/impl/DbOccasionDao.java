package com.epam.spring.core.dao.db.impl;

import com.epam.spring.core.dao.OccasionDao;
import com.epam.spring.core.model.Occasion;
import com.epam.spring.core.repository.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class DbOccasionDao implements OccasionDao {

    private Repository repository;
    private static int occasionCount = 0;

    public List<Occasion> read() {
        return new ArrayList<Occasion>(repository.getOccasions().values());
    }

    public void create(Occasion entry) {
        entry.setId(occasionCount++);
        repository.getOccasions().put(entry.getId(), entry);
    }

    public void delete(Occasion entry) {
        repository.getOccasions().remove(entry.getId());
    }

    public void update(Occasion entry) {
        repository.getOccasions().put(entry.getId(), entry);
    }

    public Occasion getById(int id) {
        return repository.getOccasions().get(id);
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
