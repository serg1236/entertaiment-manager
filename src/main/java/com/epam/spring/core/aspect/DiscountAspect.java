package com.epam.spring.core.aspect;

import com.epam.spring.core.model.DiscountStatistic;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.DiscountStatisticService;
import com.epam.spring.core.strategy.UserStrategy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Sergiy_Dakhniy
 */
@Aspect
public class DiscountAspect {

    private DiscountStatisticService discountStatisticService;

    @Pointcut("execution(* com.epam.spring.core.*.UserStrategy.getDiscount(..))")
    public void getDiscountMethods(){};

    @SuppressWarnings("unchecked")
    @AfterReturning(pointcut = "getDiscountMethods() && args(user, event, date)", returning = "discountValue")
    public void countDiscountStatistic(JoinPoint joinPoint, double discountValue, User user, Event event, Date date) {
        if(discountValue > 0) {
            if(joinPoint.getTarget() instanceof UserStrategy) {
                Class<? extends UserStrategy> type = (Class<? extends UserStrategy>) joinPoint.getTarget().getClass();
                DiscountStatistic statistic = discountStatisticService.createOrGetStatistic(type);
                statistic.setTotalUsage(statistic.getTotalUsage() + 1);
                statistic.getUsagesByUser().put(user.getId(), discountStatisticService.getUsagesByUser(type, user) + 1);
                discountStatisticService.updateStatistic(statistic);
            }
        }

    }

    public void setDiscountStatisticService(DiscountStatisticService discountStatisticService) {
        this.discountStatisticService = discountStatisticService;
    }
}
