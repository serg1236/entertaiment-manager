package com.epam.spring.core.service.impl;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Occasion;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.DiscountService;
import com.epam.spring.core.strategy.UserStrategy;

import java.util.Date;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class DiscountServiceImpl implements DiscountService {

    private List<UserStrategy> strategies;

    public double getDiscount(User user, Event event, Date date) {
        double discount =0;
        for(UserStrategy strategy: strategies) {
            discount += strategy.getDiscount(user, event, date);
        }
        return 0;
    }

	public void setStrategies(List<UserStrategy> strategies) {
		this.strategies = strategies;
	}
    
    
}
