package com.imhos.security.server.service.social;

import com.imhos.security.server.model.UserConnection;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import third.dao.UserDAO;
import third.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 10.02.13
 * Time: 9:02
 * To change this template use File | Settings | File Templates.
 */
public class UserDetailsServiceSocialWrapper implements UserDetailsService {

    private ConnectionFactoryLocator connectionFactoryLocator;
    private TextEncryptor textEncryptor;
    private UserDAO userDAO;

    public void setTextEncryptor(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    public void setConnectionFactoryLocator(ConnectionFactoryLocator connectionFactoryLocator) {
        this.connectionFactoryLocator = connectionFactoryLocator;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        int userNameSeparatorIndex = login.indexOf(UserConnection.USERNAME_SEPARATOR);
//        if (userNameSeparatorIndex == -1) {
//            return userDetailsService.loadUserByUsername(login);
//        }
//        String providerId = login.substring(0, userNameSeparatorIndex);
//        String providerUserId = login.substring(userNameSeparatorIndex + 1);
//        String email = providerUserId + "@" + providerId;
        User user = userDAO.getUserByEmail(email);
        UserConnection userConnection = user.getLastConnection();

        UsersConnectionService.ServiceProviderConnectionMapper connectionMapper
                = new UsersConnectionService.ServiceProviderConnectionMapper(connectionFactoryLocator, textEncryptor);
        Connection connection;
        try {
            connection = connectionMapper.mapEntity(userConnection);
        } catch (IllegalStateException e) {
            throw new UsernameNotFoundException("");
        }
        if (!connection.test()) {
//            todo: delete userConnection??????
            throw new UsernameNotFoundException("");
        }

        return user;

    }

}