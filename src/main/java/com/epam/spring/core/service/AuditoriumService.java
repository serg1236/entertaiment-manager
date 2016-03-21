package com.epam.spring.core.service;

import com.epam.spring.core.model.Auditorium;

import java.util.List;
import java.util.Set;

/**
 * Created by Sergiy_Dakhniy
 */
public interface AuditoriumService {

    List<Auditorium> getAuditoriums();
    Auditorium getByName(String auditoriumName);
    int getSeatsNumber(String auditoriumName);
    List<Integer> getVipSeats(String auditoriumName);
}
