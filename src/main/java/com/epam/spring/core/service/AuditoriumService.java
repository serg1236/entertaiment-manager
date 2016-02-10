package com.epam.spring.core.service;

import com.epam.spring.core.model.Auditorium;

import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public interface AuditoriumService {

    List<Auditorium> getAuditoriums();
    int getSeatsNumber(Auditorium auditorium);
    List<Integer> getVipSeats(Auditorium auditorium);
}
