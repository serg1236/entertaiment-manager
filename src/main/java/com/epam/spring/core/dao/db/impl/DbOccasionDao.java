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

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private TicketDao ticketDao;

    private static final String EVENT_ID = "EVENT_ID";
    private static final String AUDITORIUM_ID = "AUDITORIUM_ID";
    private static final String DATE = "DATE";

    public List<Occasion> read() {
        return new ArrayList<Occasion>(repository.getOccasions().values());
    }

    public void create(Occasion entry) {
        int eventId = entry.getEvent().getId();
        int auditoriumId = entry.getAuditorium().getId();
    }

    public void delete(Occasion entry) {
        repository.getOccasions().remove(entry.getId());
    }

    public void update(Occasion entry) {
        repository.getOccasions().put(entry.getId(), entry);
    }

    public Occasion getById(int id) {
        return repository.getOccasions().get(id);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RowMapper<Occasion> getOccasionRowMapper(){
        return new RowMapper<Occasion>() {
            public Occasion mapRow(ResultSet resultSet, int i) throws SQLException {
                Event event = eventDao.getById(resultSet.getInt(EVENT_ID));
                Auditorium auditorium = auditoriumDao.getById(resultSet.getInt(AUDITORIUM_ID));
                Date date = resultSet.getDate(DATE);
                return new Occasion(date, auditorium, event);
            }
        };
    }
}
