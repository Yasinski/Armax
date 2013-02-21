package com.imhos.security.server.social;

import com.imhos.security.server.model.UserConnection;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import third.DAO.Impl.UserConnectionDAOImpl;
import third.DAO.UserDAO;
import third.model.User;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 20.02.13
 * Time: 8:42
 * To change this template use File | Settings | File Templates.
 */

public class HibernateConnectionRepository implements ConnectionRepository {


    private UserConnectionDAOImpl userConnectionDAO;

    private final String userId;

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;
    private final ServiceProviderConnectionMapper connectionMapper;
    private UserDAO userDAO;

    public void setUserConnectionDAO(UserConnectionDAOImpl userConnectionDAO) {
        this.userConnectionDAO = userConnectionDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public HibernateConnectionRepository(String userId, UserConnectionDAOImpl userConnectionDAO,
                                         ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        connectionMapper = new ServiceProviderConnectionMapper(connectionFactoryLocator, textEncryptor);
        this.userConnectionDAO = userConnectionDAO;
        this.userId = userId;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    //+
    public MultiValueMap<String, Connection<?>> findAllConnections() {
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

    //+
    public List<Connection<?>> findConnections(String providerId) {
        return connectionMapper.mapEntities(userConnectionDAO.getAll(userId, providerId));
    }

    @SuppressWarnings("unchecked")
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        List<?> connections = findConnections(getProviderId(apiType));
        return (List<Connection<A>>) connections;
    }

    //+
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers) {
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

    //+
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        try {
            return connectionMapper.mapEntity(userConnectionDAO.get(userId, connectionKey.getProviderId(),
                                                                    connectionKey.getProviderUserId()));
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchConnectionException(connectionKey);
        }
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
        if (connection == null) {
            throw new NotConnectedException(providerId);
        }
        return connection;
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) findPrimaryConnection(providerId);
    }

    //+
    @Transactional
    public void addConnection(Connection<?> connection) {
        try {
            ConnectionData data = connection.createData();
            int rank = userConnectionDAO.getRank(userId, data.getProviderId());
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

    //+
    public void updateConnection(Connection<?> connection) {
        ConnectionData data = connection.createData();

        UserConnection uc = userConnectionDAO.get(userId, data.getProviderId(), data.getProviderUserId());
        if (uc != null) {
            uc.setDisplayName(data.getDisplayName());
            uc.setProfileUrl(data.getProfileUrl());
            uc.setImageUrl(data.getImageUrl());
            uc.setAccessToken(encrypt(data.getAccessToken()));
            uc.setSecret(encrypt(data.getSecret()));
            uc.setRefreshToken(encrypt(data.getRefreshToken()));
            uc.setExpireTime(data.getExpireTime());

            userConnectionDAO.update(uc);
        }
    }

    //+
    public void removeConnections(String providerId) {
        userConnectionDAO.remove(userId, providerId);
    }

    //+
    public void removeConnection(ConnectionKey connectionKey) {
        userConnectionDAO.remove(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
    }

    //+
    private Connection<?> findPrimaryConnection(String providerId) {
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

    private <A> String getProviderId(Class<A> apiType) {
        return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
    }

    private String encrypt(String text) {
        return text != null ? textEncryptor.encrypt(text) : text;
    }
}
