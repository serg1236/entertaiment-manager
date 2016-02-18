package com.epam.spring.core.model;

import com.epam.spring.core.service.UserService;
import com.epam.spring.core.strategy.UserStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class DiscountStatistic {
    private int id;
    private Class<? extends UserStrategy> strategyType;
    private int totalUsage = 0;
    private Map<Integer, Integer> usagesByUser = new HashMap<Integer, Integer>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStrategyType(Class<? extends UserStrategy> strategyType) {
        this.strategyType = strategyType;
    }

    public Class<? extends UserStrategy> getStrategyType() {
        return strategyType;
    }

    public int getTotalUsage() {
        return totalUsage;
    }

    public void setTotalUsage(int totalUsage) {
        this.totalUsage = totalUsage;
    }

    public Map<Integer, Integer> getUsagesByUser() {
        return usagesByUser;
    }

    public void setUsagesByUser(Map<Integer, Integer> usagesByUser) {
        this.usagesByUser = usagesByUser;
    }
}
