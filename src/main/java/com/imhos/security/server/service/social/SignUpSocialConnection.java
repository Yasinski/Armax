package com.imhos.security.server.service.social;

import com.imhos.security.server.model.User;
import com.imhos.security.server.model.UserConnection;
import com.imhos.security.server.service.CustomSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import third.dao.UserDAO;
import third.model.Role;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 21.02.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class SignUpSocialConnection implements ConnectionSignUp {

    private UserDAO userDAO;
    private UserConnectionService userConnectionService;
    private Md5PasswordEncoder passwordEncoder;
    private CustomSaltSource saltSource;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setUserConnectionService(UserConnectionService userConnectionService) {
        this.userConnectionService = userConnectionService;
    }

    public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setSaltSource(CustomSaltSource saltSource) {
        this.saltSource = saltSource;
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
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setAuthorities(Role.ROLE_USER);
            user.setEmail(email);
            user.setUsername(email);
            user.setDisplayName(userProfile.getName());
            //                todo: implement custom SaltSource     fixed!
            user.setPassword(passwordEncoder.encodePassword(providerId, saltSource.getSalt(user)));
            userDAO.saveUser(user);
        } else if (!user.isProfileSubmittedByUser() &&
//                todo: have to simplify logic
                connection.equals(userConnectionService.findPrimaryConnection(providerId, user.getId()))) {
            user.setUsername(email);
            user.setEmail(email);
            user.setDisplayName(userProfile.getName());
            userDAO.update(user);
        }
        return user.getId();
//        } catch (DuplicateKeyException e) {
//            throw new DuplicateConnectionException(connection.getKey());
//        }
    }


}
