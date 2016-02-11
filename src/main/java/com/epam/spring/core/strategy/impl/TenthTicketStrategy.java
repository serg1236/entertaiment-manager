package com.epam.spring.core.strategy.impl;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Occasion;
import com.epam.spring.core.model.User;
import com.epam.spring.core.strategy.UserStrategy;

import java.util.Date;

/**
 * Created by Sergiy_Dakhniy
 */
public class TenthTicketStrategy implements UserStrategy{

    public double getDiscount(User user, Event event, Date date) {
        double discount = 0;
        int ticketsCount = user.getPurchasedTickets().size();
        if(ticketsCount%10 == 9) {
            discount = 0.5;
        }
        return discount;
    }
}
