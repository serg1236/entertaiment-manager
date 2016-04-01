package com.epam.spring.core.security;

import com.epam.spring.core.model.User;
import com.epam.spring.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiy_Dakhniy
 */
public class MultiroleUserDetailsService implements UserDetailsService {

    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(username);
        System.out.println("Okaaaay auth for: " + username);
        if(user == null) {
            throw new UsernameNotFoundException("Cannot find user with email: " + username);
        }
        String[] roles = user.getRoles().split(",");
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for(String role: roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
