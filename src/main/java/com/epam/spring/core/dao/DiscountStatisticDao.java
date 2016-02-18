package com.epam.spring.core.dao;

import com.epam.spring.core.model.DiscountStatistic;
import com.epam.spring.core.model.User;
import com.epam.spring.core.strategy.UserStrategy;

/**
 * Created by Sergiy_Dakhniy
 */
public interface DiscountStatisticDao extends Dao<DiscountStatistic> {
    int getUsageByUser(Class<? extends UserStrategy> strategy, User user);
    DiscountStatistic getByStrategyType(Class<? extends UserStrategy> strategy);
}
