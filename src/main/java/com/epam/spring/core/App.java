package com.epam.spring.core;

import com.epam.spring.core.dao.AuditoriumDao;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.repository.Repository;
import com.epam.spring.core.service.AuditoriumService;
import com.epam.spring.core.service.BookingService;
import com.epam.spring.core.service.impl.BookingServiceImpl;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class App {

    public static void main(String args[]) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        AuditoriumDao dao = (AuditoriumDao) context.getBean("auditoriumDao");
        List<Auditorium> auditoriums = dao.read();
        context.close();
    }
}
