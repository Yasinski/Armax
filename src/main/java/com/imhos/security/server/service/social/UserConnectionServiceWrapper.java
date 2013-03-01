package com.imhos.security.server.service.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 20.02.13
 * Time: 8:41
 * To change this template use File | Settings | File Templates.
 */
public class UserConnectionServiceWrapper implements UsersConnectionRepository {

    private UserConnectionService userConnectionService;

    public UserConnectionServiceWrapper(UserConnectionService userConnectionService) {
        this.userConnectionService = userConnectionService;
    }


    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        return userConnectionService.addUserIdsWithConnection(connection);
    }

    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        return userConnectionService.findUserIdsConnectedTo(providerId, providerUserIds);
    }

    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new ConnectionRepositoryWrapper(userId, userConnectionService);
    }
}
