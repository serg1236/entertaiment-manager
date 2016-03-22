package com.epam.spring.core.dao.db.impl;

import com.epam.spring.core.dao.OccasionDao;
import com.epam.spring.core.dao.TicketDao;
import com.epam.spring.core.model.Occasion;
import com.epam.spring.core.model.Ticket;
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
public class DbTicketDao implements TicketDao {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Ticket> ticketRowMapper;

    public List<Ticket> read() {
        return jdbcTemplate.query("SELECT * FROM TICKET", ticketRowMapper);
    }

    public void create(Ticket entry) {
        int occasionId = entry.getOccasion().getId();
        jdbcTemplate.update("INSERT INTO TICKET(OCCASION_ID, SEAT) VALUES(?,?)", new Object[]{occasionId, entry.getSeat()});
    }

    public void delete(Ticket entry) {
        jdbcTemplate.update("DELETE FROM TICKET WHERE ID=?", new Object[]{entry.getId()});
    }

    public void update(Ticket entry) {
        int occasionId = entry.getOccasion().getId();
        jdbcTemplate.update("UPDATE TICKET SET OCCASION_ID=?, SEAT=?, WHERE ID=?",
                new Object[]{occasionId, entry.getSeat(), entry.getId()});
    }

    public Ticket getById(int id) {
        Ticket ticket = null;
        try {
            ticket = jdbcTemplate.queryForObject("SELECT * FROM TICKET WHERE ID=?",
                    new Object[]{id}, ticketRowMapper);
        }finally {
            return ticket;
        }
    }

    public List<Ticket> getByOccasion(Occasion occasion) {
        return jdbcTemplate.query("SELECT * FROM TICKET WHERE OCCASION_ID=?", new Object[]{occasion.getId()}, ticketRowMapper);
    }


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTicketRowMapper(RowMapper<Ticket> ticketRowMapper) {
        this.ticketRowMapper = ticketRowMapper;
    }

}
