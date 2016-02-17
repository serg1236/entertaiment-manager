package com.epam.spring.core.dao.impl;

import com.epam.spring.core.dao.UserDao;
import com.epam.spring.core.model.User;
import com.epam.spring.core.repository.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class HashMapUserDao implements UserDao {

    private Repository repository;
    private static int userCount = 0;

    public List<User> read() {
        return new ArrayList<User>(repository.getUsers().values());
    }

    public void create(User entry) {
        entry.setId(userCount++);
        repository.getUsers().put(entry.getId(), entry);
    }

    public void delete(User entry) {
        repository.getUsers().remove(entry.getId());
    }

    public void update(User entry) {
        repository.getUsers().put(entry.getId(), entry);
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public User getById(int id) {
        return repository.getUsers().get(id);
    }
}
