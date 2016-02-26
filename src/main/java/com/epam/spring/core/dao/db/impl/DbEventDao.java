package com.epam.spring.core.dao.db.impl;

import com.epam.spring.core.dao.EventDao;
import com.epam.spring.core.dao.db.util.InsertStatementFactory;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.EventRating;
import com.epam.spring.core.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class DbEventDao implements EventDao {
    private JdbcTemplate jdbcTemplate;
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String BASE_PRICE = "BASE_PRICE";
    private static final String RATING_ID = "RATING_ID";
    private static final String RATING_NAME = "RATING_NAME";

    public List<Event> read() {
        return jdbcTemplate.query("SELECT * FROM EVENT", getEventRowMapper());
    }

    public void create(Event entry) {
        int ratingId = getRatingId(entry.getRating());
        jdbcTemplate.update("INSERT INTO EVENT(NAME, BASE_PRICE, RATING_ID) VALUES(?,?,?)",
                new Object[]{entry.getName(), entry.getBasePrice(), ratingId});
    }

    public void delete(Event entry) {
        jdbcTemplate.update("DELETE FROM EVENT WHERE ID=?", entry.getId());
    }

    public void update(Event entry) {
        int ratingId = getRatingId(entry.getRating());
        jdbcTemplate.update("UPDATE EVENT SET NAME=?, BASE_PRICE=?, RATING_ID=?) WHERE ID=?",
                new Object[]{entry.getName(), entry.getBasePrice(), ratingId, entry.getId()});
    }

    public Event getById(int id) {
        Event event = null;
        try {
            event =
                    jdbcTemplate.queryForObject("SELECT * FROM EVENT WHERE ID=?", new Object[]{id},
                            getEventRowMapper());
        }finally {
            return event;
        }
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Event> getEventRowMapper() {
        return new RowMapper<Event>() {
            public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                Event event = new Event();
                event.setId(resultSet.getInt(ID));
                event.setBasePrice(resultSet.getDouble(BASE_PRICE));
                event.setName(resultSet.getString(NAME));
                event.setRating(getRating(resultSet.getInt(RATING_ID)));
                return event;
            }
        };
    }

    private EventRating getRating(int id) {
        String ratingName = jdbcTemplate.queryForObject("SELECT RATING_NAME FROM EVENT_RATING WHERE ID =?",
                new Object[]{id}, String.class);
        return EventRating.valueOf(ratingName);
    }

    private int getRatingId(EventRating rating) {
        int ratingId = jdbcTemplate.queryForObject("SELECT ID FROM EVENT_RATING WHERE RATING_NAME =?",
                new Object[]{rating.toString()}, Integer.class);
        return ratingId;
    }

    public void initRatings() {
        for(EventRating rating: EventRating.values()) {
            jdbcTemplate.update("INSERT INTO EVENT_RATING(RATING_NAME) VALUES(?)", rating.toString());
        }
    }
}
