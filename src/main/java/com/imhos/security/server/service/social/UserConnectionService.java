package com.imhos.security.server.service.social;

import com.imhos.security.server.model.User;
import com.imhos.security.server.model.UserConnection;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import third.dao.UserConnectionDAO;
import third.dao.UserDAO;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 20.02.13
 * Time: 8:42
 * To change this template use File | Settings | File Templates.
 */

public class UserConnectionService {

    private UserConnectionService userConnectionService;
    private UserConnectionDAO userConnectionDAO;
    private ConnectionFactoryLocator connectionFactoryLocator;
    private TextEncryptor textEncryptor;
    private ServiceProviderConnectionMapper connectionMapper;
    private UserDAO userDAO;

    private ConnectionSignUp connectionSignUp;

    public void setUserConnectionService(UserConnectionService userConnectionService) {
        this.userConnectionService = userConnectionService;
    }

    public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
        this.connectionSignUp = connectionSignUp;
    }

    public void setUserConnectionDAO(UserConnectionDAO userConnectionDAO) {
        this.userConnectionDAO = userConnectionDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserConnectionService() {
    }

    public UserConnectionService(ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        connectionMapper = new ServiceProviderConnectionMapper(connectionFactoryLocator, textEncryptor);
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }


    public MultiValueMap<String, Connection<?>> findAllConnections(String userId) {
        List<Connection<?>> resultList = connectionMapper.mapEntities(userConnectionDAO.getAll(userId));

        MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
        Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
        for (String registeredProviderId : registeredProviderIds) {
            connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());
        }
        for (Connection<?> connection : resultList) {
            String providerId = connection.getKey().getProviderId();
            if (connections.get(providerId).size() == 0) {
                connections.put(providerId, new LinkedList<Connection<?>>());
            }
            connections.add(providerId, connection);
        }
        return connections;
    }


    public List<Connection<?>> findConnections(String providerId, String userId) {
        return connectionMapper.mapEntities(userConnectionDAO.getAll(userId, providerId));
    }

    @SuppressWarnings("unchecked")
    public <A> List<Connection<A>> findConnections(Class<A> apiType, String userId) {
        List<?> connections = findConnections(getProviderId(apiType), userId);
        return (List<Connection<A>>) connections;
    }


    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers, String userId) {
        if (providerUsers.isEmpty()) {
            throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
        }

        List<Connection<?>> resultList = connectionMapper.mapEntities(userConnectionDAO.getAll(userId, providerUsers));

        MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<String, Connection<?>>();
        for (Connection<?> connection : resultList) {
            String providerId = connection.getKey().getProviderId();
            List<String> userIds = providerUsers.get(providerId);
            List<Connection<?>> connections = connectionsForUsers.get(providerId);
            if (connections == null) {
                connections = new ArrayList<Connection<?>>(userIds.size());
                for (int i = 0; i < userIds.size(); i++) {
                    connections.add(null);
                }
                connectionsForUsers.put(providerId, connections);
            }
            String providerUserId = connection.getKey().getProviderUserId();
            int connectionIndex = userIds.indexOf(providerUserId);
            connections.set(connectionIndex, connection);
        }
        return connectionsForUsers;
    }


    public Connection<?> getConnection(ConnectionKey connectionKey, String userId) {
        try {
            return connectionMapper.mapEntity(userConnectionDAO.get(userId, connectionKey.getProviderId(),
                                                                    connectionKey.getProviderUserId()));
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchConnectionException(connectionKey);
        }
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId, String userId) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId), userId);
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType, String userId) {
        String providerId = getProviderId(apiType);
        Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId, userId);
        if (connection == null) {
            throw new NotConnectedException(providerId);
        }
        return connection;
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType, String userId) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) findPrimaryConnection(providerId, userId);
    }


    public void addConnection(Connection<?> connection, String userId) {
        try {
// todo: connection.createData().getDisplayName()  returns displayName with "@" prefix. we have to handle this some how
            ConnectionData data = connection.createData();
            Integer rank = userConnectionDAO.getMaxRank(userId, data.getProviderId());    //??
            if (rank == null) {
                rank = 1;
            } else {
                rank++;
            }

            User user = userDAO.getUserById(userId);
            UserConnection userConnection = new UserConnection(user, data.getProviderId(), data.getProviderUserId(), rank,
                                                               data.getDisplayName(), data.getProfileUrl(), data.getImageUrl(),
                                                               encrypt(data.getAccessToken()), encrypt(data.getSecret()),
                                                               encrypt(data.getRefreshToken()), data.getExpireTime());
            userConnectionDAO.save(userConnection);
        } catch (DuplicateKeyException e) {
            throw new DuplicateConnectionException(connection.getKey());
        }
    }


    public void updateConnection(Connection<?> connection, String userId) {
        ConnectionData data = connection.createData();

        UserConnection userConnection = userConnectionDAO.get(userId, data.getProviderId(), data.getProviderUserId());
        if (userConnection != null) {
            userConnection.setDisplayName(data.getDisplayName());
            userConnection.setProfileUrl(data.getProfileUrl());
            userConnection.setImageUrl(data.getImageUrl());
            userConnection.setAccessToken(encrypt(data.getAccessToken()));
            userConnection.setSecret(encrypt(data.getSecret()));
            userConnection.setRefreshToken(encrypt(data.getRefreshToken()));
            userConnection.setExpireTime(data.getExpireTime());

            userConnectionDAO.update(userConnection);
        }
    }


    public void removeConnections(String providerId, String userId) {
        userConnectionDAO.removeConnections(userId, providerId);
    }


    public void removeConnection(ConnectionKey connectionKey) {
        userConnectionDAO.removeConnection(connectionKey.getProviderId(), connectionKey.getProviderUserId());
    }


    public Connection<?> findPrimaryConnection(String providerId, String userId) {
        List<Connection<?>> connections = connectionMapper.mapEntities(userConnectionDAO.getPrimary(userId, providerId));
        if (connections.size() > 0) {
            return connections.get(0);
        } else {
            return null;
        }
    }


    public final static class ServiceProviderConnectionMapper {
        private ConnectionFactoryLocator connectionFactoryLocator;
        private TextEncryptor textEncryptor;


        public ServiceProviderConnectionMapper(ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
            this.connectionFactoryLocator = connectionFactoryLocator;
            this.textEncryptor = textEncryptor;
        }

        public List<Connection<?>> mapEntities(List<UserConnection> userConnections) {
            List<Connection<?>> result = new ArrayList<Connection<?>>();
            for (UserConnection su : userConnections) {
                result.add(mapEntity(su));
            }
            return result;
        }

        public Connection<?> mapEntity(UserConnection userConnection) {
            ConnectionData connectionData = mapConnectionData(userConnection);
            ConnectionFactory<?> connectionFactory =
                    connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
            return connectionFactory.createConnection(connectionData);
        }

        private ConnectionData mapConnectionData(UserConnection userConnection) {
            return new ConnectionData(userConnection.getProviderId(), userConnection.getProviderUserId(),
                                      userConnection.getDisplayName(), userConnection.getProfileUrl(),
                                      userConnection.getImageUrl(), decrypt(userConnection.getAccessToken()),
                                      decrypt(userConnection.getSecret()), decrypt(userConnection.getRefreshToken()),
                                      expireTime(userConnection.getExpireTime()));
        }

        private String decrypt(String encryptedText) {
            return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
        }

        private Long expireTime(Long expireTime) {
            return expireTime == null || expireTime == 0 ? null : expireTime;
        }

    }

    public <A> String getProviderId(Class<A> apiType) {
        return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
    }

    public String encrypt(String text) {
        return text != null ? textEncryptor.encrypt(text) : text;
    }

    //    todo: method name should be find* (have to fix transaction read-only problem)
    public List<String> addUserIdsWithConnection(Connection<?> connection) {
        List<String> usrs = new ArrayList<String>();
        ConnectionKey key = connection.getKey();
        UserConnection user = userConnectionDAO.get(key.getProviderId(), key.getProviderUserId());
        if (user != null) {
            usrs.add(user.getUserId());
            return usrs;
        }

        if (connectionSignUp != null) {
            String newUserId = addNewUserWithConnection(connection);
            if (newUserId != null) {
                usrs.add(newUserId);
            }
        }
        //if empty we should go to the sign up form
        return usrs;
    }

    //    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public String addNewUserWithConnection(Connection<?> connection) {
        String newUserId = connectionSignUp.execute(connection);
        if (newUserId == null)
        //auto signup failed, so we need to go to a sign up form
        {
            return null;
        }
        addConnection(connection, newUserId);
        return newUserId;
    }

    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        return userConnectionDAO.findUsersConnectedTo(providerId, providerUserIds);
    }

    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new ConnectionRepositoryWrapper(userId, this);
    }

}
