package com.epam.spring.core.dao.db.impl;

import com.epam.spring.core.dao.EventStatisticDao;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.EventStatistic;
import com.epam.spring.core.repository.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class DbEventStatisticDao implements EventStatisticDao {

    private Repository repository;
    private static int eventStatisticsCount = 0;

    public List<EventStatistic> read() {
        return new ArrayList<EventStatistic>(repository.getEventStatistics().values());
    }

    public void create(EventStatistic entry) {
        entry.setId(eventStatisticsCount++);
        repository.getEventStatistics().put(entry.getId(), entry);
    }

    public void delete(EventStatistic entry) {
        repository.getEventStatistics().remove(entry.getId());
    }

    public void update(EventStatistic entry) {
        repository.getEventStatistics().put(entry.getId(), entry);
    }

    public EventStatistic getById(int id) {
        return repository.getEventStatistics().get(id);
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public EventStatistic getByEvent(Event event) {
        List<EventStatistic> allStatistics = read();
        EventStatistic foundStatistic = null;
        for(EventStatistic eventStatistic : allStatistics) {
            if(eventStatistic.getEvent().getId() == event.getId()) {
                foundStatistic = eventStatistic;
                break;
            }
        }
        return foundStatistic;
    }
}
