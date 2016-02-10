package com.epam.spring.core;

import com.epam.spring.core.model.User;
import com.epam.spring.core.repository.Repository;

import java.util.Date;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class Main {

    public static void main(String args[]) {
        Repository repository = new Repository();

        Map<Integer, User> users = repository.getItems(User.class);
        User user = new User("dff", "dafdsf", new Date());
        users.put(user.getId(), user);
        System.out.println(repository.getItems(User.class));

    }
}
