package com.epam.spring.core.aspect;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.User;
import com.epam.spring.core.random.LuckGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Date;

/**
 * Created by Sergiy_Dakhniy
 */
@Aspect
public class LuckyWinnerAspect {

    private LuckGenerator luckGenerator;

    @Pointcut("execution(* com.epam.spring.core.*.BookingService.getTicketPrice(..))")
    public void getTicketPriceMethod(){};

    @Around("getTicketPriceMethod()&& args(event, date, seat, user)")
    public double addSomeLuck(ProceedingJoinPoint joinPoint, Event event, Date date, int seat, User user) throws Throwable {
        boolean isLucky = luckGenerator.isLucky();
        if(!isLucky) {
            return (Double)joinPoint.proceed(new Object[] {event, date, seat, user});
        } else {
            return 0;
        }
    }

    public void setLuckGenerator(LuckGenerator luckGenerator) {
        this.luckGenerator = luckGenerator;
    }

}
