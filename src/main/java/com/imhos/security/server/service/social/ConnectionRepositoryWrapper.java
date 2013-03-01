package com.imhos.security.server.service.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 20.02.13
 * Time: 8:42
 * To change this template use File | Settings | File Templates.
 */

public class ConnectionRepositoryWrapper implements ConnectionRepository {


    private final String userId;
    private UserConnectionService userConnectionService;


    public ConnectionRepositoryWrapper(String userId, UserConnectionService userConnectionService) {
        this.userConnectionService = userConnectionService;
        this.userId = userId;
    }

    public MultiValueMap<String, Connection<?>> findAllConnections() {
        return userConnectionService.findAllConnections(userId);
    }

    public List<Connection<?>> findConnections(String providerId) {
        return userConnectionService.findConnections(providerId, userId);
    }

    @SuppressWarnings("unchecked")
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        return userConnectionService.findConnections(apiType, userId);
    }

    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers) {
        return userConnectionService.findConnectionsToUsers(providerUsers, userId);
    }

    public Connection<?> getConnection(ConnectionKey connectionKey) {
        return userConnectionService.getConnection(connectionKey, userId);
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        return userConnectionService.getConnection(apiType, providerUserId, userId);
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        return userConnectionService.getPrimaryConnection(apiType, userId);
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        return userConnectionService.findPrimaryConnection(apiType, userId);
    }

    @Transactional
    public void addConnection(Connection<?> connection) {
        userConnectionService.addConnection(connection, userId);
    }

    public void updateConnection(Connection<?> connection) {
        userConnectionService.updateConnection(connection, userId);
    }

    public void removeConnections(String providerId) {
        userConnectionService.removeConnections(providerId, userId);
    }

    public void removeConnection(ConnectionKey connectionKey) {
        userConnectionService.removeConnection(connectionKey);
    }

    private Connection<?> findPrimaryConnection(String providerId) {
        return userConnectionService.findPrimaryConnection(providerId, userId);
    }

    private <A> String getProviderId(Class<A> apiType) {
        return userConnectionService.getProviderId(apiType);
    }

    private String encrypt(String text) {
        return userConnectionService.encrypt(text);
    }
}
