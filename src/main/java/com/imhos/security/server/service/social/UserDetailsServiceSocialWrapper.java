package com.imhos.security.server.service.social;

import com.imhos.security.server.model.UserConnection;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.RejectedAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import third.DAO.UserConnectionDAO;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 10.02.13
 * Time: 9:02
 * To change this template use File | Settings | File Templates.
 */
public class UserDetailsServiceSocialWrapper implements UserDetailsService {

    private UserDetailsService userDetailsService;
    private UserConnectionDAO userConnectDAO;
    private ConnectionFactoryLocator connectionFactoryLocator;
    private TextEncryptor textEncryptor;

    public void setTextEncryptor(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    public void setConnectionFactoryLocator(ConnectionFactoryLocator connectionFactoryLocator) {
        this.connectionFactoryLocator = connectionFactoryLocator;
    }

    public void setUserConnectDAO(UserConnectionDAO userConnectDAO) {
        this.userConnectDAO = userConnectDAO;
    }


    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        int userNameSeparatorIndex = login.indexOf(UserConnection.USERNAME_SEPARATOR);
        if (userNameSeparatorIndex == -1) {
            return userDetailsService.loadUserByUsername(login);
        }
        String providerId = login.substring(0, userNameSeparatorIndex);
        String providerUserId = login.substring(userNameSeparatorIndex + 1);
        UserConnection userConnection = userConnectDAO.get(providerId, providerUserId);

        UsersConnectionService.ServiceProviderConnectionMapper connectionMapper
                = new UsersConnectionService.ServiceProviderConnectionMapper(connectionFactoryLocator, textEncryptor);
        Connection connection = connectionMapper.mapEntity(userConnection);
        try {
            if (!userConnection.getUsername().equals(connection.fetchUserProfile().getUsername())) {
                throw new UsernameNotFoundException("");
            }
        } catch (RejectedAuthorizationException e) {
            throw new SocialAuthenticationRejectedException(e.getMessage(), e);
        } catch (NotAuthorizedException e) {
            throw new SocialAuthenticationRejectedException(e.getMessage(), e);
        }
//        catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }

        return userConnection;

    }

}