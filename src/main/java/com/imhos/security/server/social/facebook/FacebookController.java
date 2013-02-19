package com.imhos.security.server.social.facebook;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 10.02.13
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */

public class FacebookController {
    private String facebookCallBackUrl;
    private FacebookConnectionFactory connectionFactory;

    public FacebookController() {
    }


    public FacebookController(String appID, String appSecret, String facebookCallBackUrl) {
        connectionFactory = new FacebookConnectionFactory(appID, appSecret);
        this.facebookCallBackUrl = facebookCallBackUrl;
    }

    public AccessGrant getFacebookAccessGrant(String authCode, boolean rememberMe) {
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        AccessGrant accessGrant = oauthOperations.exchangeForAccess(authCode, getFacebookURL(rememberMe), null);
        return accessGrant;
    }

    public String getAuthorizeUrl(boolean rememberMe) {
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri(getFacebookURL(rememberMe));
        params.set("display", "popup");
        params.setScope("email");
        String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
        return authorizeUrl;
    }

    private String getFacebookURL(boolean rememberMe) {
        return facebookCallBackUrl + "?" + FacebookCallback.REMEMBER_ME_PARAMETER + "=" + rememberMe;
    }

    public Facebook getFacebookProfile(String token) {
        AccessGrant accessGrant = new AccessGrant(token);
        return getFacebookProfile(accessGrant);
    }

    // todo:solve encoding problem
    public Facebook getFacebookProfile(AccessGrant accessGrant) {
        Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
        Facebook facebook = connection.getApi();
        return facebook;
    }
//
//    public Facebook getFacebookProfile2(String token) {
//    FacebookTemplate facebook = new FacebookTemplate(token);
//        FacebookProfile profile = facebook.userOperations().getUserProfile();
//        String username = profile.getUsername();
//    return facebook;
//    }
}
