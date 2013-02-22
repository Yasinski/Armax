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
    private UsersConnectionService usersConnectionService;


    public ConnectionRepositoryWrapper(String userId, UsersConnectionService usersConnectionService) {
        this.usersConnectionService = usersConnectionService;
        this.userId = userId;
    }

    public MultiValueMap<String, Connection<?>> findAllConnections() {
        return usersConnectionService.findAllConnections(userId);
    }

    public List<Connection<?>> findConnections(String providerId) {
        return usersConnectionService.findConnections(providerId, userId);
    }

    @SuppressWarnings("unchecked")
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        return usersConnectionService.findConnections(apiType, userId);
    }

    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers) {
        return usersConnectionService.findConnectionsToUsers(providerUsers, userId);
    }

    public Connection<?> getConnection(ConnectionKey connectionKey) {
        return usersConnectionService.getConnection(connectionKey, userId);
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        return usersConnectionService.getConnection(apiType, providerUserId, userId);
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        return usersConnectionService.getPrimaryConnection(apiType, userId);
    }

    @SuppressWarnings("unchecked")
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        return usersConnectionService.findPrimaryConnection(apiType, userId);
    }

    @Transactional
    public void addConnection(Connection<?> connection) {
        usersConnectionService.addConnection(connection, userId);
    }

    public void updateConnection(Connection<?> connection) {
        usersConnectionService.updateConnection(connection, userId);
    }

    public void removeConnections(String providerId) {
        usersConnectionService.removeConnections(providerId, userId);
    }

    public void removeConnection(ConnectionKey connectionKey) {
        usersConnectionService.removeConnection(connectionKey, userId);
    }

    private Connection<?> findPrimaryConnection(String providerId) {
        return usersConnectionService.findPrimaryConnection(providerId, userId);
    }

    private <A> String getProviderId(Class<A> apiType) {
        return usersConnectionService.getProviderId(apiType);
    }

    private String encrypt(String text) {
        return usersConnectionService.encrypt(text);
    }
}
