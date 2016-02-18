package com.epam.spring.core.service;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.EventStatistic;

import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */


public interface EventStatisticService {
    EventStatistic createOrGetStatistic(Event event);
    EventStatistic getStatistic(Event event);
    List<EventStatistic> getAll();
    void updateStatistic(EventStatistic statistic);
}
