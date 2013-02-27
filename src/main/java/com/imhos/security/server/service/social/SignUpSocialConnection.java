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
    private UsersConnectionService usersConnectionService;

    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
        this.dbUserQueryer = dbUserQueryer;
    }

    public void setUsersConnectionService(UsersConnectionService usersConnectionService) {
        this.usersConnectionService = usersConnectionService;
    }

    @Override
    public String execute(Connection<?> connection) {
//        try {
        UserProfile userProfile = connection.fetchUserProfile();
        String providerUserId = connection.getKey().getProviderUserId();
        String providerId = connection.getKey().getProviderId();
        String email = userProfile.getEmail();
        if (email == null) {
            email = providerUserId + "@" + providerId;
        }
        User user = dbUserQueryer.getUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setAuthorities(Role.ROLE_USER);
            user.setEmail(email);
            user.setFullName(userProfile.getName());
//            user.setUsername(userProfile.getUsername());
            user.setPassword(providerId);
            dbUserQueryer.saveUser(user);
        } else if (!user.isProfileSubmittedByUser() &&
                connection.equals(usersConnectionService.findPrimaryConnection(providerId, user.getId()))) {
            user.setEmail(email);
            user.setFullName(userProfile.getName());
            dbUserQueryer.updateUser(user);
        }
        return user.getId();
//        } catch (DuplicateKeyException e) {
//            throw new DuplicateConnectionException(connection.getKey());
//        }
    }


}
