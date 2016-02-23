package com.epam.spring.core.dao.db.impl;

import com.epam.spring.core.dao.AuditoriumDao;
import com.epam.spring.core.dao.db.util.InsertStatementFactory;
import com.epam.spring.core.model.Auditorium;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class DbAuditoriumDao implements AuditoriumDao{

    private JdbcTemplate jdbcTemplate;
    private static final String NAME = "NAME";
    private static final String NUMBER_OF_SEATS = "NUMBER_OF_SEATS";
    private static final String ID = "ID";
    private static final String SEAT_NUMBER = "SEAT_NUMBER";
    private static final String TABLE_NAME = "AUDITORIUM";
    private List<Auditorium> initialAuditoriums;

    public List<Auditorium> read() {
        return jdbcTemplate.query("SELECT * FROM AUDITORIUM", getAuditoriumRowMapper());
    }

    public void create(Auditorium entry) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(NAME, entry.getName());
        params.put(NUMBER_OF_SEATS, entry.getNumberOfSeats());
        int id = new InsertStatementFactory(TABLE_NAME,  ID).executeQuery(jdbcTemplate, params);
        for(Integer vipSeat: entry.getVipSeats()) {
            jdbcTemplate.update("INSERT INTO VIP_SEAT(AUDITORIUM_ID, SEAT_NUMBER) VALUES(?,?)", id, vipSeat);
        }
    }

    public void delete(Auditorium entry) {
        throw new RuntimeException("Auditorium delete is not supported");
    }

    public void update(Auditorium entry) {
        throw new RuntimeException("Auditorium update is not supported");
    }

    public Auditorium getById(int id) {
        Auditorium auditorium = null;
        try {
            auditorium =
                    jdbcTemplate.queryForObject("SELECT * FROM AUDITORIUM WHERE ID=?", new Object[]{id},
                            getAuditoriumRowMapper());
        }finally {
            return auditorium;
        }
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Auditorium> getAuditoriumRowMapper() {
        return new RowMapper<Auditorium>() {
            public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
                Auditorium auditorium = new Auditorium();
                auditorium.setId(resultSet.getInt(ID));
                auditorium.setName(resultSet.getString(NAME));
                auditorium.setNumberOfSeats(resultSet.getInt(NUMBER_OF_SEATS));
                auditorium.setVipSeats(jdbcTemplate.query("SELECT * FROM VIP_SEAT WHERE AUDITORIUM_ID = ?", new Object[]{auditorium.getId()},
                        getSeatRowMapper()));
                return auditorium;
            }
        };
    }

    private RowMapper<Integer> getSeatRowMapper() {
        return new RowMapper<Integer>() {
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt(SEAT_NUMBER);
            }
        };
    }

    public void initAuditoriums() {
        for(Auditorium auditorium: initialAuditoriums) {
            create(auditorium);
        }
    }

    public void setInitialAuditoriums(List<Auditorium> initialAuditoriums) {
        this.initialAuditoriums = initialAuditoriums;
    }
}
