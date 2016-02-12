package com.epam.spring.core.service.test;

import com.epam.spring.core.service.AuditoriumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

/**
 * Created by Sergiy_Dakhniy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class AuditoriumServiceTest {

    @Autowired
    AuditoriumService auditoriumService;
    private final String PLAZA_NAME = "Plaza";

    @Test
    public void getAll() {
        assertEquals(4, auditoriumService.getAuditoriums().size());
    }

    @Test
    public void propertiesCheck() {
        assertEquals(20, auditoriumService.getSeatsNumber(PLAZA_NAME));
        assertEquals(8, auditoriumService.getVipSeats(PLAZA_NAME).size());
        assertTrue(auditoriumService.getVipSeats(PLAZA_NAME).contains(3));
        assertTrue(auditoriumService.getVipSeats(PLAZA_NAME).contains(10));
    }
}
