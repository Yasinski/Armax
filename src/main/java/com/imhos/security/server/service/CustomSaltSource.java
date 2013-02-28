package com.imhos.security.server.service;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="">Max Yasinski</a>
 * @updated 25.01.13 10:41
 */
public class CustomSaltSource implements SaltSource {

    @Override
    public Object getSalt(UserDetails user) {
        return user.getUsername();
    }
}
