package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.UserDao;
import com.epam.spring.core.exception.BookingException;
import com.epam.spring.core.exception.FinanceException;
import com.epam.spring.core.model.Ticket;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void register(User user) {
        userDao.create(user);
    }

    public void remove(User user) {
        userDao.delete(user);
    }

    public User getById(int id) {
        return userDao.getById(id);
    }

    public User getUserByEmail(String email) {
        List<User> users = userDao.read();
        User foundUser = null;
        for(User user: users) {
            if(user.getEmail().equals(email)) {
                foundUser = user;
                break;
            }
        }
        return foundUser;
    }

    public List<User> getUsersByName(String name) {
        List<User> users = userDao.read();
        List<User> foundUsers = new ArrayList<User>();
        for(User user: users) {
            if(user.getName().equals(name)) {
                foundUsers.add(user);
            }
        }
        return foundUsers;
    }

    public List<Ticket> getBookedTickets(User user) {
        return user.getPurchasedTickets();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {SQLException.class, DataAccessException.class})
    public void fillAccount(String email, double money) {
        if(money <= 0) {
            throw new FinanceException("Invalid sum of money");
        }
        User user = getUserByEmail(email);
        if(user == null) {
            throw new FinanceException("User not found");
        } else {
            user.setMoney(user.getMoney() + money);
            userDao.update(user);
        }
    }

    public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
    
    
}
