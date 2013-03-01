package com.imhos.security.server.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import third.dao.UserDAO;


public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userDAO.getUserByLogin(login);
    }

}

