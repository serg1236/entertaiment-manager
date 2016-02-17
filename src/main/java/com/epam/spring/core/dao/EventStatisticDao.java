package com.epam.spring.core.dao;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.EventStatistic;

/**
 * Created by Sergiy_Dakhniy
 */
public interface EventStatisticDao extends Dao<EventStatistic> {
    EventStatistic getByEvent (Event event);
}
