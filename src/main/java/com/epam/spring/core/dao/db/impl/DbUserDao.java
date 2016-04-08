package com.epam.spring.core.dao.db.impl;

import com.epam.spring.core.dao.UserDao;
import com.epam.spring.core.model.Ticket;
import com.epam.spring.core.model.User;
import com.epam.spring.core.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class DbUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Ticket> ticketRowMapper;
    private static final String NAME = "NAME";
    private static final String EMAIL = "EMAIL";
    private static final String BIRTH_DATE = "BIRTH_DATE";
    private static final String ROLES = "EM_ROLES";
    private static final String PASSWORD = "EM_PASSWORD";
    private static final String MONEY = "MONEY";
    private static final String ID = "ID";

    public List<User> read() {
        return jdbcTemplate.query("SELECT * FROM EM_USER", getUserRowMapper());
    }

    public void create(User entry) {
        jdbcTemplate.update("INSERT INTO EM_USER(NAME, EMAIL, BIRTH_DATE, EM_ROLES, EM_PASSWORD, MONEY) VALUES(?,?,?,?,?,?)",
                entry.getName(), entry.getEmail(), entry.getBirthDate(), entry.getRoles(), entry.getPassword(), entry.getMoney());
    }

    public void delete(User entry) {
        jdbcTemplate.update("DELETE FROM EM_USER WHERE ID=?", entry.getId());
    }

    public void update(User entry) {
        jdbcTemplate.update("UPDATE EM_USER SET NAME=?, EMAIL=?, BIRTH_DATE=?, EM_ROLES=?, EM_PASSWORD=?, MONEY=? WHERE ID=?",
                entry.getName(), entry.getEmail(), entry.getBirthDate(), entry.getRoles(), entry.getPassword(),  entry.getMoney(), entry.getId());
        if(entry.getPurchasedTickets() != null) {
            for(Ticket ticket: entry.getPurchasedTickets()) {
                int occasionId = ticket.getOccasion().getId();
                jdbcTemplate.update("UPDATE TICKET SET USER_ID=? WHERE OCCASION_ID=? AND SEAT=?",
                        entry.getId(), occasionId, ticket.getSeat());
            }
        }
    }

    public User getById(int id) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject("SELECT * FROM EM_USER WHERE ID=?", new Object[]{id}, getUserRowMapper());
        } finally {
            return user;
        }
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTicketRowMapper(RowMapper<Ticket> ticketRowMapper) {
        this.ticketRowMapper = ticketRowMapper;
    }

    private RowMapper<User> getUserRowMapper() {
        return new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                String name = resultSet.getString(NAME);
                String email = resultSet.getString(EMAIL);
                Date date = resultSet.getDate(BIRTH_DATE);
                User user = new User(name, email, date);
                user.setId(resultSet.getInt(ID));
                user.setPurchasedTickets(getTicketsForUser(user.getId()));
                user.setRoles(resultSet.getString(ROLES));
                user.setPassword(resultSet.getString(PASSWORD));
                user.setMoney(resultSet.getDouble(MONEY));
                return user;
            }
        };
    }

    private List<Ticket> getTicketsForUser(int id) {
        return jdbcTemplate.query("SELECT * FROM TICKET WHERE USER_ID=?", new Object[]{id},
                ticketRowMapper);
    }
}
