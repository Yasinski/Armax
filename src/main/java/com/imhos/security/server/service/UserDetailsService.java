package com.imhos.security.server.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import third.facade.DBUserQueryer;


public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private DBUserQueryer dbUserQueryer;

    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
        this.dbUserQueryer = dbUserQueryer;
    }

    public DBUserQueryer getDbUserQueryer() {
        return dbUserQueryer;
    }

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return dbUserQueryer.getUserByLogin(login);
    }

}

