package com.imhos.security.shared.model;

import java.util.ArrayList;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 12.02.13 12:26
 */
public class UserDetailsDTO implements UserDetails {

    public static final String USERNAME_FIELD = "username";
    public static final String AUTHORITIES_FIELD = "authorities";
    private String username;
    private List<String> authorities;

    public UserDetailsDTO() {
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
