package com.epam.spring.core.dao;

import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public interface Dao<T> {
    List<T> read();
    void create (T entry);
    void delete (T entry);
    void update (T entry);
    T getById(int id);
}
