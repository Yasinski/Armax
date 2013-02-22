package com.imhos.security.server.service.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import third.facade.DBUserQueryer;
import third.model.Role;
import third.model.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 21.02.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class SocialConnectionSignUp implements ConnectionSignUp {

    private DBUserQueryer dbUserQueryer;

    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
        this.dbUserQueryer = dbUserQueryer;
    }

    @Override
    public String execute(Connection<?> connection) {
//        try {
        Set<Role> authorities = new HashSet<Role>();
        authorities.add(Role.ROLE_USER);
        User user = new User();
        user.setAuthorities(authorities);
        dbUserQueryer.saveUser(user);
        return user.getId();
//        } catch (DuplicateKeyException e) {
//            throw new DuplicateConnectionException(connection.getKey());
//        }
    }


}
