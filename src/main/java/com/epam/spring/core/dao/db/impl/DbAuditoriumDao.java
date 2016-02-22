package com.epam.spring.core.dao.db.impl;

import com.epam.spring.core.dao.AuditoriumDao;
import com.epam.spring.core.dao.db.util.InsertStatementFactory;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class DbAuditoriumDao implements AuditoriumDao{

    private JdbcTemplate jdbcTemplate;

    public List<Auditorium> read() {
        return null;
    }

    public void create(Auditorium entry) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("NAME", entry.getName());
        params.put("NUMBER_OF_SEATS", entry.getNumberOfSeats());
        int id = new InsertStatementFactory("AUDITORIUM", "ID").executeQuery(jdbcTemplate, params);
        for(Integer vipSeat: entry.getVipSeats()) {
            jdbcTemplate.update("INSERT INTO VIP_SEAT(AUDITORIUM_ID, SEAT_NUMBER) VALUES(?,?)", id, vipSeat);
        }
    }

    public void delete(Auditorium entry) {
        throw new RuntimeException("Auditorium removal is not supported");
    }

    public void update(Auditorium entry) {

    }

    public Auditorium getById(int id) {
        return null;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
