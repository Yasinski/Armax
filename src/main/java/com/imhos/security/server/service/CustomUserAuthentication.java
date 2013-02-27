package com.imhos.security.server.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 08.02.13
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */

public class CustomUserAuthentication implements Authentication {
    private String name;
    private Object details;
    private UserDetails user;
    private boolean authenticated;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserAuthentication(UserDetails user, Object details) {
        this.name = user.getUsername();
        this.details = details;
        this.user = user;
        this.authorities = user.getAuthorities();
        this.authenticated = true;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }
}
