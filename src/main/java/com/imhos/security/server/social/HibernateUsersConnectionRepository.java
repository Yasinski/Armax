package com.imhos.security.server.social;

import com.imhos.security.server.model.UserConnection;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import third.DAO.Impl.UserConnectionDAOImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 20.02.13
 * Time: 8:41
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUsersConnectionRepository implements UsersConnectionRepository {

    private UserConnectionDAOImpl userConnectionDAO;

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    private ConnectionSignUp connectionSignUp;


    public HibernateUsersConnectionRepository(UserConnectionDAOImpl userConnectionDAO, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        this.userConnectionDAO = userConnectionDAO;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
        this.connectionSignUp = connectionSignUp;
    }

    //+
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        List<String> usrs = new ArrayList<String>();
        ConnectionKey key = connection.getKey();
        UserConnection user = userConnectionDAO.get(key.getProviderId(), key.getProviderUserId());
        if (user != null) {
            usrs.add(user.getUserId());
            return usrs;
        }

        if (connectionSignUp != null) {

            String newUserId = connectionSignUp.execute(connection);
            if (newUserId == null)
            //auto signup failed, so we need to go to a sign up form
            {
                return usrs;
            }
            createConnectionRepository(newUserId).addConnection(connection);
            usrs.add(newUserId);
        }
        //if empty we should go to the sign up form
        return usrs;
    }

    //+
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        return userConnectionDAO.findUsersConnectedTo(providerId, providerUserIds);
    }

    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new HibernateConnectionRepository(userId, userConnectionDAO, connectionFactoryLocator, textEncryptor);
    }
}
