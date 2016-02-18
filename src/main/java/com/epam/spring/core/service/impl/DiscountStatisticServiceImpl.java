package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.DiscountStatisticDao;
import com.epam.spring.core.model.DiscountStatistic;
import com.epam.spring.core.model.EventStatistic;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.DiscountStatisticService;
import com.epam.spring.core.strategy.UserStrategy;

import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class DiscountStatisticServiceImpl implements DiscountStatisticService {

    DiscountStatisticDao dao;

    public DiscountStatistic createOrGetStatistic(Class<? extends UserStrategy> strategyType) {
        DiscountStatistic statistic = dao.getByStrategyType(strategyType);
        if (statistic == null) {
            statistic = new DiscountStatistic();
            statistic.setStrategyType(strategyType);
            dao.create(statistic);
        }
        //calling again to get with set id
        return dao.getByStrategyType(strategyType);
    }

    public DiscountStatistic getStatistic(Class<? extends UserStrategy> strategyType) {
        return dao.getByStrategyType(strategyType);
    }

    public int getUsagesByUser(Class<? extends UserStrategy> strategyType, User user) {
        return dao.getUsageByUser(strategyType, user);
    }

    public List<DiscountStatistic> getAll() {
        return dao.read();
    }

    public void updateStatistic(DiscountStatistic statistic) {
        dao.update(statistic);
    }

    public void setDao(DiscountStatisticDao dao) {
        this.dao = dao;
    }
}
