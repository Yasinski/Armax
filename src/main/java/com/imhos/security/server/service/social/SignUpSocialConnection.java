package com.imhos.security.server.service.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import third.facade.DBUserQueryer;
import third.model.Role;
import third.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 21.02.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class SignUpSocialConnection implements ConnectionSignUp {

    private DBUserQueryer dbUserQueryer;

    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
        this.dbUserQueryer = dbUserQueryer;
    }

    @Override
    public String execute(Connection<?> connection) {
//        try {
        UserProfile userProfile = connection.fetchUserProfile();
        String email = userProfile.getEmail();
        if (email == null) {
            email = connection.getKey().getProviderUserId() + "@" + connection.getKey().getProviderId();
        }
        User user = dbUserQueryer.getUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setAuthorities(Role.ROLE_USER);
            user.setEmail(email);
            user.setFullName(userProfile.getName());
            dbUserQueryer.saveUser(user);
        }
        return user.getId();
//        } catch (DuplicateKeyException e) {
//            throw new DuplicateConnectionException(connection.getKey());
//        }
    }


}
