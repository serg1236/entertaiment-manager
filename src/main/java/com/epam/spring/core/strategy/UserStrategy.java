package com.epam.spring.core.strategy;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Occasion;
import com.epam.spring.core.model.User;

import java.util.Date;

/**
 * Created by Sergiy_Dakhniy
 */
public interface UserStrategy {
    double getDiscount(User user, Event event, Date date);
}
