package com.epam.spring.core.dao.db.mapper;

import com.epam.spring.core.dao.OccasionDao;
import com.epam.spring.core.model.Occasion;
import com.epam.spring.core.model.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Sergiy_Dakhniy
 */
public class TicketMapper implements RowMapper<Ticket> {

    private OccasionDao occasionDao;
    private static final String OCCASION_ID = "OCCASION_ID";
    private static final String SEAT = "SEAT";
    private static final String ID = "ID";

    public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
        Occasion occasion = occasionDao.getById(resultSet.getInt(OCCASION_ID));
        int seat = resultSet.getInt(SEAT);
        Ticket ticket = new Ticket(occasion, seat);
        ticket.setId(resultSet.getInt(ID));
        return ticket;
    }

    public void setOccasionDao(OccasionDao occasionDao) {
        this.occasionDao = occasionDao;
    }
}
