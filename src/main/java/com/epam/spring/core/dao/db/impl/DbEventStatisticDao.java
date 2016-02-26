package com.epam.spring.core.dao.db.impl;

import com.epam.spring.core.dao.EventDao;
import com.epam.spring.core.dao.EventStatisticDao;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.EventStatistic;
import com.epam.spring.core.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class DbEventStatisticDao implements EventStatisticDao {

    private EventDao eventDao;
    private JdbcTemplate jdbcTemplate;
    private static final String ID = "ID";
    private static final String EVENT_ID = "EVENT_ID";
    private static final String PRICE_REQUIRED = "PRICE_REQUIRED";
    private static final String ACCESSED_BY_NAME = "ACCESSED_BY_NAME";
    private static final String TICKETS_BOOKED = "TICKETS_BOOKED";


    public List<EventStatistic> read() {
        return jdbcTemplate.query("SELECT * FROM EVENT_STATISTIC", getEventStatisticRowMapper());
    }

    public void create(EventStatistic entry) {
        int eventId = entry.getEvent().getId();
        jdbcTemplate.update("INSERT INTO EVENT_STATISTIC(EVENT_ID, PRICE_REQUIRED, ACCESSED_BY_NAME, TICKETS_BOOKED) VALUES(?,?,?,?)",
                eventId, entry.getPriceRequired(), entry.getAccessedByName(), entry.getTicketsBooked());
    }

    public void delete(EventStatistic entry) {
        jdbcTemplate.update("DELETE FROM EVENT_STATISTIC WHERE ID=?", entry.getId());
    }

    public void update(EventStatistic entry) {
        int eventId = entry.getEvent().getId();
        jdbcTemplate.update("UPDATE EVENT_STATISTIC SET EVENT_ID=?, PRICE_REQUIRED=?, ACCESSED_BY_NAME=?, TICKETS_BOOKED=? WHERE ID=?",
                eventId, entry.getPriceRequired(), entry.getAccessedByName(), entry.getTicketsBooked(), entry.getId());
    }

    public EventStatistic getById(int id) {
        EventStatistic eventStatistic = null;
        try {
            eventStatistic = jdbcTemplate.queryForObject("SELECT * FROM EVENT_STATISTIC WHERE ID=?", new Object[]{id},
                    getEventStatisticRowMapper());
        }finally {
            return eventStatistic;
        }
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

    private RowMapper<EventStatistic> getEventStatisticRowMapper() {
        return new RowMapper<EventStatistic>() {
            public EventStatistic mapRow(ResultSet resultSet, int i) throws SQLException {
                EventStatistic eventStatistic = new EventStatistic();
                eventStatistic.setId(resultSet.getInt(ID));
                eventStatistic.setAccessedByName(resultSet.getInt(ACCESSED_BY_NAME));
                eventStatistic.setPriceRequired(resultSet.getInt(PRICE_REQUIRED));
                eventStatistic.setTicketsBooked(resultSet.getInt(TICKETS_BOOKED));
                eventStatistic.setEvent(eventDao.getById(resultSet.getInt(EVENT_ID)));
                return eventStatistic;
            }
        };
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }
}
