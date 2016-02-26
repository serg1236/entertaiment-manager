package com.epam.spring.core.dao.db.impl;

import com.epam.spring.core.dao.DiscountStatisticDao;
import com.epam.spring.core.dao.db.util.InsertStatementFactory;
import com.epam.spring.core.model.DiscountStatistic;
import com.epam.spring.core.model.User;
import com.epam.spring.core.repository.Repository;
import com.epam.spring.core.strategy.UserStrategy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class DbDiscountStatisticDao implements DiscountStatisticDao {

    private JdbcTemplate jdbcTemplate;
    private static final String TOTAL_USAGES = "TOTAL_USAGES";
    private static final String STRATEGY_ID = "STRATEGY_ID";
    private static final String ID = "ID";
    private static final String USER_ID = "USER_ID";
    private static final String USAGES = "USAGES";
    private static final String STRATEGY = "STRATEGY";
    private static final String CLASS_NAME = "CLASS_NAME";
    private static final String DISCOUNT_STATISTIC = "DISCOUNT_STATISTIC";

    public List<DiscountStatistic> read() {
        return jdbcTemplate.query("SELECT * FROM DISCOUNT_STATISTIC", getDiscountStatisticRowMapper());
    }

    public void create(DiscountStatistic entry) {
        String strategyClassName = entry.getStrategyType().getName();
        InsertStatementFactory strategyStatementFactory = new InsertStatementFactory(STRATEGY, ID);
        Map<String,Object> strategyParams = new HashMap<String, Object>();
        strategyParams.put(CLASS_NAME, strategyClassName);
        int strategyId = strategyStatementFactory.executeQuery(jdbcTemplate, strategyParams);
        InsertStatementFactory statisticStatementFactory = new InsertStatementFactory(DISCOUNT_STATISTIC, ID);
        Map<String, Object> statisticParams = new HashMap<String, Object>();
        statisticParams.put(STRATEGY_ID, strategyId);
        statisticParams.put(TOTAL_USAGES, entry.getTotalUsage());
        int statisticId = statisticStatementFactory.executeQuery(jdbcTemplate, statisticParams);
        for(Integer key: entry.getUsagesByUser().keySet()) {
            int usages = entry.getUsagesByUser().get(key);
            jdbcTemplate.update("INSERT INTO DISCOUNT_TO_USER(USER_ID, STATISTIC_ID, USAGES) VALUES(?,?,?)", key, statisticId, usages);
        }
    }

    public void delete(DiscountStatistic entry) {
        throw new RuntimeException("Discount statistic cannot be deleted");
    }

    public void update(DiscountStatistic entry) {
        jdbcTemplate.update("UPDATE DISCOUNT_STATISTIC SET TOTAL_USAGES=? WHERE ID=?", entry.getTotalUsage(), entry.getId());
        for(Integer key : entry.getUsagesByUser().keySet()) {
            int usages = entry.getUsagesByUser().get(key);
            int rowsAffected = jdbcTemplate.update("UPDATE DISCOUNT_TO_USER SET USAGES=? WHERE USER_ID=? AND STATISTIC_ID=?", usages, key, entry.getId());
            if(rowsAffected==0) {
                jdbcTemplate.update("INSERT INTO DISCOUNT_TO_USER(USER_ID, STATISTIC_ID, USAGES) VALUES(?,?,?)", key, entry.getId(), usages);
            }
        }

    }

    public DiscountStatistic getById(int id) {
        DiscountStatistic discountStatistic = null;
        try {
            discountStatistic = jdbcTemplate.queryForObject("SELECT * FROM DISCOUNT_STATISTIC WHERE ID=?", new Object[]{id},
                    getDiscountStatisticRowMapper());
        }finally {
            return discountStatistic;
        }
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

    private RowMapper<DiscountStatistic> getDiscountStatisticRowMapper() {
        return new RowMapper<DiscountStatistic>() {
            public DiscountStatistic mapRow(ResultSet resultSet, int i) throws SQLException {
                DiscountStatistic discountStatistic = new DiscountStatistic();
                discountStatistic.setTotalUsage(resultSet.getInt(TOTAL_USAGES));
                discountStatistic.setId(resultSet.getInt(ID));
                discountStatistic.setUsagesByUser(getUsagesByUser(discountStatistic.getId()));
                discountStatistic.setStrategyType(getStrategyClass(resultSet.getInt(STRATEGY_ID)));
                return discountStatistic;
            }
        };
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<Integer, Integer> getUsagesByUser(int statisticId) {
        return jdbcTemplate.query("SELECT * FROM DISCOUNT_TO_USER WHERE STATISTIC_ID=?", new Object[]{statisticId},
        new ResultSetExtractor<Map<Integer,Integer>>() {
            public Map<Integer, Integer> extractData(ResultSet rs) throws SQLException {
                Map<Integer, Integer> map = new HashMap<Integer, Integer>();
                while (rs.next()) {
                    int userId = rs.getInt(USER_ID);
                    int usages = rs.getInt(USAGES);
                    map.put(userId, usages);
                }
                return map;
            };
        });
    }

    private Class<? extends UserStrategy> getStrategyClass(int id) {
        Class<? extends UserStrategy> strategyClass = null;
        String className =  jdbcTemplate.queryForObject("SELECT CLASS_NAME FROM STRATEGY WHERE ID=?", new Object[]{id}, String.class);
        try {
            strategyClass = (Class<? extends UserStrategy>) Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strategyClass;
    }


}
