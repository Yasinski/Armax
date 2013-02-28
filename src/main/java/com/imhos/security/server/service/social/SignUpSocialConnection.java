package com.imhos.security.server.service.social;

import com.imhos.security.server.model.UserConnection;
import com.imhos.security.server.service.CustomSaltSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
    private Md5PasswordEncoder passwordEncoder;
    @Autowired
    private CustomSaltSource saltSource;

    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
        this.dbUserQueryer = dbUserQueryer;
    }

    public void setUsersConnectionService(UsersConnectionService usersConnectionService) {
        this.usersConnectionService = usersConnectionService;
    }

    public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String execute(Connection<?> connection) {
//        try {
        UserProfile userProfile = connection.fetchUserProfile();
        String providerUserId = connection.getKey().getProviderUserId();
        String providerId = connection.getKey().getProviderId();
        String email = userProfile.getEmail();
        if (email == null) {
            email = providerUserId + UserConnection.USERNAME_SEPARATOR + providerId;
        }
        User user = dbUserQueryer.getUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setAuthorities(Role.ROLE_USER);
            user.setEmail(email);
            user.setUsername(email);
            user.setDisplayName(userProfile.getName());
            //                todo: implement custom SaltSource
            user.setPassword(passwordEncoder.encodePassword(providerId, saltSource.getSalt(user)));
            dbUserQueryer.saveUser(user);
        } else if (!user.isProfileSubmittedByUser() &&
//                todo: have to simplify logic
                connection.equals(usersConnectionService.findPrimaryConnection(providerId, user.getId()))) {
            user.setUsername(email);
            user.setEmail(email);
            user.setDisplayName(userProfile.getName());
            dbUserQueryer.updateUser(user);
        }
        return user.getId();
//        } catch (DuplicateKeyException e) {
//            throw new DuplicateConnectionException(connection.getKey());
//        }
    }


}
