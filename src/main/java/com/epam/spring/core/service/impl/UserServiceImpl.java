package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.UserDao;
import com.epam.spring.core.model.Ticket;
import com.epam.spring.core.model.User;
import com.epam.spring.core.service.UserService;

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

    //looks redundant due to purchasedTickets property inside User.class
    //left to remain proper interface
    public List<Ticket> getBookedTickets(User user) {
        return user.getPurchasedTickets();
    }

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
    
    
}
