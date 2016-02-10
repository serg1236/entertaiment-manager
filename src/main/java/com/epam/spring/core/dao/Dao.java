package com.epam.spring.core.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public interface Dao<T> {
    Map<Integer ,T> read(Class<T> type);
    void create (T entry);
    void delete (T entry);
    void update (T entry);
}
