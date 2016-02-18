package com.epam.spring.core.service;

import com.epam.spring.core.model.DiscountStatistic;
import com.epam.spring.core.model.User;
import com.epam.spring.core.strategy.UserStrategy;

import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public interface DiscountStatisticService {
    DiscountStatistic createOrGetStatistic(Class<? extends UserStrategy> strategyType);
    DiscountStatistic getStatistic(Class<? extends UserStrategy> strategyType);
    int getUsagesByUser(Class<? extends UserStrategy> strategyType, User user);
    List<DiscountStatistic> getAll();
    void updateStatistic(DiscountStatistic statistic);
}
