package com.imhos.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import third.facade.DBUserQueryer;


public class UserDetailsServiceImpl implements UserDetailsService {
	   private DBUserQueryer dbUserQueryer;

	public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
		this.dbUserQueryer = dbUserQueryer;
	}

	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        return dbUserQueryer.getUserByLogin(login);
	}

}

