package com.epam.spring.core.strategy.impl;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Occasion;
import com.epam.spring.core.model.User;
import com.epam.spring.core.strategy.UserStrategy;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Sergiy_Dakhniy
 */
public class BirthdayStrategy implements UserStrategy{

    public double getDiscount(User user, Event event, Date date) {
        double discount = 0;
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(user.getBirthDate());
        Calendar occasionCalendar = Calendar.getInstance();
        occasionCalendar.setTime(date);
        if(birthCalendar.get(Calendar.MONTH) == occasionCalendar.get(Calendar.MONTH)
                &&birthCalendar.get(Calendar.DATE) == occasionCalendar.get(Calendar.DATE)) {
            discount = 0.05;
        }
        return discount;
    }
}
