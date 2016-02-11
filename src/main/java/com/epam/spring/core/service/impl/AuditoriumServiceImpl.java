package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.AuditoriumDao;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.service.AuditoriumService;

import java.util.List;
import java.util.Set;

/**
 * Created by Sergiy_Dakhniy
 */
public class AuditoriumServiceImpl implements AuditoriumService {

    private AuditoriumDao dao;

    public List<Auditorium> getAuditoriums() {
        return dao.read();
    }

    public int getSeatsNumber(String auditoriumName) {
        Auditorium auditorium = getByName(auditoriumName);
        return auditorium!=null? auditorium.getNumberOfSeats(): 0;
    }

    public List<Integer> getVipSeats(String auditoriumName) {
        Auditorium auditorium = getByName(auditoriumName);
        return auditorium!=null? auditorium.getVipSeats(): null;
    }

    private Auditorium getByName(String name) {
        List<Auditorium> auditoriums = dao.read();
        for (Auditorium auditorium: auditoriums) {
            if(auditorium.getName().equals(name)) {
                return auditorium;
            }
        }
        return null;
    }

	public void setDao(AuditoriumDao dao) {
		this.dao = dao;
	}
    
    
}
