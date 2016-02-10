package com.epam.spring.core.dao.impl;

import com.epam.spring.core.dao.Dao;
import com.epam.spring.core.repository.Repository;

import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class HashMapDao<T> implements Dao<T>{

    private Repository repository;

    public Map<Integer, T> read(Class<T> type) {
        return null;
    }

    public void create(Object entry) {
        repository.getItems(entry.getClass()).put())
    }

    public void delete(Object entry) {

    }

    public void update(Object entry) {

    }

}
