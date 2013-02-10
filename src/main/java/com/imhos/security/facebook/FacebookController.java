package com.imhos.security.facebook;

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
    public static final String FACEBOOK_CALL_BACK_URL = "http://localhost:8080/facebookcallback/";
    private FacebookConnectionFactory connectionFactory;

    public FacebookController(String appID, String appSecret) {
        connectionFactory = new FacebookConnectionFactory(appID, appSecret);
    }

    public AccessGrant getFacebookAccessGrant(String authCode) {
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        AccessGrant accessGrant =
                oauthOperations.exchangeForAccess(authCode, FACEBOOK_CALL_BACK_URL, null);
        return accessGrant;
    }

    public String getAuthorizeUrl() {
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri(FACEBOOK_CALL_BACK_URL);
        String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
        return authorizeUrl;
    }

    public Facebook getFacebookProfile(String token) {
        AccessGrant accessGrant = new AccessGrant(token);
        return getFacebookProfile(accessGrant);
    }

    public Facebook getFacebookProfile(AccessGrant accessGrant) {
        Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
        Facebook facebook = connection.getApi();
        return facebook;
    }
}
