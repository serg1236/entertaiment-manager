package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.EventStatisticDao;
import com.epam.spring.core.dao.impl.HashMapEventStatisticDao;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.EventStatistic;
import com.epam.spring.core.service.EventStatisticService;

import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class EventStatisticServiceImpl implements EventStatisticService {

    private EventStatisticDao dao;

    public EventStatistic createOrGetStatistic(Event event) {
        EventStatistic statistic = dao.getByEvent(event);
        if (statistic == null) {
            statistic = new EventStatistic();
            statistic.setEvent(event);
            dao.create(statistic);
        }
        //calling again to get with set id
        return dao.getByEvent(event);
    }

    public EventStatistic getStatistic(Event event) {
        return dao.getByEvent(event);
    }

    public List<EventStatistic> getAll() {
        return dao.read();
    }

    public void updateStatistic(EventStatistic statistic) {
        dao.update(statistic);
    }

    public void setDao(EventStatisticDao dao) {
        this.dao = dao;
    }
}
