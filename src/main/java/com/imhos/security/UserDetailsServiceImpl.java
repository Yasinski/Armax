package com.imhos.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import third.facade.DBUserQueryer;
import third.model.Users;

import java.util.HashSet;
import java.util.Set;


public class UserDetailsServiceImpl implements UserDetailsService {
	   private DBUserQueryer dbUserQueryer;

	public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
		this.dbUserQueryer = dbUserQueryer;
	}

	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		Users user = dbUserQueryer.getUserByLogin(login);
		if (user == null) {
			throw new UsernameNotFoundException("User " + login + " doesn't exist!");
		}
		String username = user.getUsername();
		String password = user.getPassword();
		Set<String> authorities = user.getAuthorities();
		boolean enabled = user.isEnabled();
		Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
		for (String authority : authorities) {
			set.add(new GrantedAuthorityImpl(authority));
		}
		return new User(username, password, enabled, true, true, true, set);
	}

}

