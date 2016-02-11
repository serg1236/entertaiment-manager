package com.epam.spring.core;

import com.epam.spring.core.repository.Repository;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Sergiy_Dakhniy
 */
public class App {

    public static void main(String args[]) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Repository repository = context.getBean(Repository.class);
        context.close();
    }
}
