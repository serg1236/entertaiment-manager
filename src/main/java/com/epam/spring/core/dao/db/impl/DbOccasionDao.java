package com.epam.spring.core.dao.db.impl;

import com.epam.spring.core.dao.AuditoriumDao;
import com.epam.spring.core.dao.EventDao;
import com.epam.spring.core.dao.OccasionDao;
import com.epam.spring.core.dao.TicketDao;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Occasion;
import com.epam.spring.core.model.Ticket;
import com.epam.spring.core.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.validation.ObjectError;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class DbOccasionDao implements OccasionDao {

    private JdbcTemplate jdbcTemplate;
    private EventDao eventDao;
    private AuditoriumDao auditoriumDao;
    private RowMapper<Ticket> ticketRowMapper;

    private static final String EVENT_ID = "EVENT_ID";
    private static final String AUDITORIUM_ID = "AUDITORIUM_ID";
    private static final String DATE = "OCCASION_DATE";
    private static final String ID = "ID";

    public List<Occasion> read() {
        return jdbcTemplate.query("SELECT * FROM OCCASION", getOccasionRowMapper());
    }

    public void create(Occasion entry) {
        int eventId = entry.getEvent().getId();
        int auditoriumId = entry.getAuditorium().getId();
        Timestamp timestamp = new Timestamp( entry.getDate().getTime());
        jdbcTemplate.update("INSERT INTO OCCASION(EVENT_ID, AUDITORIUM_ID, OCCASION_DATE)VALUES(?,?,?)", new Object[]{eventId, auditoriumId, timestamp});
    }

    public void delete(Occasion entry) {
        jdbcTemplate.update("DELETE FROM OCCASION WHERE ID=?", new Object[]{entry.getId()});
    }

    public void update(Occasion entry) {
        int eventId = entry.getEvent().getId();
        int auditoriumId = entry.getAuditorium().getId();
        Timestamp timestamp = new Timestamp( entry.getDate().getTime());
        jdbcTemplate.update("UPDATE OCCASION SET EVENT_ID=?, AUDITORIUM_ID=?, OCCASION_DATE=? WHERE ID=?",
                new Object[]{eventId, auditoriumId, timestamp, entry.getId()});
    }

    public Occasion getById(int id) {
        Occasion occasion = null;
        try {
            occasion = jdbcTemplate.queryForObject("SELECT * FROM OCCASION WHERE ID=?", new Object[]{id}, getOccasionRowMapper());
        } finally {
            return occasion;
        }
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Occasion> getOccasionRowMapper(){
        return new RowMapper<Occasion>() {
            public Occasion mapRow(ResultSet resultSet, int i) throws SQLException {
                Event event = eventDao.getById(resultSet.getInt(EVENT_ID));
                Auditorium auditorium = auditoriumDao.getById(resultSet.getInt(AUDITORIUM_ID));
                Date date = resultSet.getTimestamp(DATE);
                Occasion occasion = new Occasion(date, auditorium, event);
                occasion.setId(resultSet.getInt(ID));
                occasion.setPurchasedTickets(getTicketsForOccasion(occasion));
                return occasion;
            }
        };
    }

    private List<Ticket> getTicketsForOccasion(Occasion occasion) {
        return jdbcTemplate.query("SELECT * FROM TICKET WHERE OCCASION_ID=?", new Object[]{occasion.getId()},
                ticketRowMapper);
    }

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public void setAuditoriumDao(AuditoriumDao auditoriumDao) {
        this.auditoriumDao = auditoriumDao;
    }

    public void setTicketRowMapper(RowMapper<Ticket> ticketRowMapper) {
        this.ticketRowMapper = ticketRowMapper;
    }
}
