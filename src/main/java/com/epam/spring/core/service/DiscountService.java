package com.epam.spring.core.service;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.User;

import java.util.Date;

/**
 * Created by Sergiy_Dakhniy
 */
public interface DiscountService {
    double getDiscount(User user, Event event, Date date);
}
