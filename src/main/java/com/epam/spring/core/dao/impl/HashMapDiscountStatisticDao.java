package com.epam.spring.core.dao.impl;

import com.epam.spring.core.dao.DiscountStatisticDao;
import com.epam.spring.core.model.DiscountStatistic;
import com.epam.spring.core.model.User;
import com.epam.spring.core.repository.Repository;
import com.epam.spring.core.strategy.UserStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class HashMapDiscountStatisticDao implements DiscountStatisticDao {

    private Repository repository;
    private static int discountStatisticsCount = 0;

    public List<DiscountStatistic> read() {
        return new ArrayList<DiscountStatistic>(repository.getDiscountStatistics().values());
    }

    public void create(DiscountStatistic entry) {
        entry.setId(discountStatisticsCount++);
        repository.getDiscountStatistics().put(entry.getId(), entry);
    }

    public void delete(DiscountStatistic entry) {
        repository.getDiscountStatistics().remove(entry.getId());
    }

    public void update(DiscountStatistic entry) {
        repository.getDiscountStatistics().put(entry.getId(), entry);
    }

    public DiscountStatistic getById(int id) {
        return repository.getDiscountStatistics().get(id);
    }

    public int getUsageByUser(Class<? extends UserStrategy> strategyType, User user) {
        DiscountStatistic statistic = getByStrategyType(strategyType);
        if(statistic != null) {
            Integer usages = statistic.getUsagesByUser().get(user.getId());
            return usages == null? 0 : usages;
        }
        return 0;
    }

    public DiscountStatistic getByStrategyType (Class<? extends UserStrategy> strategyType) {
        List<DiscountStatistic> statistics = read();
        for (DiscountStatistic statistic : statistics) {
            if(statistic.getStrategyType().getName().equals(strategyType.getName())) {
                return statistic;
            }
        }
        return null;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
