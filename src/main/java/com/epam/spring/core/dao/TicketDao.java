package com.epam.spring.core.dao;

import com.epam.spring.core.model.Occasion;
import com.epam.spring.core.model.Ticket;

import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public interface TicketDao extends Dao<Ticket> {
    List<Ticket> getByOccasion(Occasion occasion);
}
