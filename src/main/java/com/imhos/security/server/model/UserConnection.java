package com.imhos.security.server.model;

import org.hibernate.validator.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import third.model.User;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 20.02.13
 * Time: 7:43
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"providerId", "providerUserId"}),
        @UniqueConstraint(columnNames = {"username", "providerId", "rank"})})           //column "user doesn't exist"
public class UserConnection implements UserDetails {

    public static String USERNAME_SEPARATOR = "@";
    @Id
    @Column
    private String username;


    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column
    private int rank;

    @Column
    private String providerId;

    @Column
    private String providerUserId;

    @Column
    private String displayName;

    @Column
    private String profileUrl;

    @Column
    private String imageUrl;

    @Column
    @Length(max = 1000)
    private String accessToken;

    @Column
    private String secret;

    @Column
    private String refreshToken;

    @Column
    private Long expireTime;


    public UserConnection() {
    }

    public UserConnection(User user, String providerId, String providerUserId, int rank, String displayName,
                          String profileUrl, String imageUrl, String accessToken, String secret, String refreshToken,
                          Long expireTime) {
        this.user = user;  // ты про это? да ну так это база данных они должны быть связаны чтобы легко поднять
        // из  юзерконнекшна юзера а у того взять например роли
        //у нас же есть юзер айди (был) по нему и поднимать. просто сейчас его нет. и надо переписывать методы с джоинами

        this.providerId = providerId;
        this.providerUserId = providerUserId;
        this.rank = rank;
        this.displayName = displayName;
        this.profileUrl = profileUrl;
        this.imageUrl = imageUrl;
        this.accessToken = accessToken;
        this.secret = secret;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
        this.username = providerUserId + USERNAME_SEPARATOR + providerId;
    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public User getUser() {
        return user;
    }

    public String getUserId() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
