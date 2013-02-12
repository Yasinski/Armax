package com.imhos.security.shared.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 12.02.13
 * Time: 9:53
 * To change this template use File | Settings | File Templates.
 */
public class User {
    public static final String USERNAME_FIELD = "username";
    public static final String AUTHORITIES_FIELD = "AUTHORITIES";
    private String username;
    private List<String> authorities;

    public User() {
        authorities = new ArrayList<String>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
