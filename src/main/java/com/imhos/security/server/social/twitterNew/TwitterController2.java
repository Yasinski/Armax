package com.imhos.security.server.social.twitterNew;

import com.imhos.security.server.social.twitter.TwitterCallback;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.social.twitter.connect.TwitterServiceProvider;
import twitter4j.TwitterException;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 19.02.13
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class TwitterController2 {
    private String twitterCallBackUrl;
    private TwitterConnectionFactory connectionFactory;
    private OAuth1Operations oAuth1Operations;
    private OAuthToken requestToken;

    public TwitterController2(String appID, String appSecret, String twitterCallBackUrl) throws TwitterException {
        this.connectionFactory = new TwitterConnectionFactory(appID, appSecret);
        this.twitterCallBackUrl = twitterCallBackUrl;
        this.oAuth1Operations = connectionFactory.getOAuthOperations();
        this.requestToken = oAuth1Operations.fetchRequestToken(twitterCallBackUrl, null);
    }

    public OAuth1Operations getOAuthOperations() {
//        OAuth1Operations oAuth1Operations = connectionFactory.getOAuthOperations();
        return oAuth1Operations;
    }

    public OAuthToken getRequestToken() {
//        OAuthToken requestToken = oAuth1Operations.fetchRequestToken(twitterCallBackUrl, null);
        return requestToken;
    }

    public String getAuthorizeUrl(boolean rememberMe) {
        OAuth1Parameters params = new OAuth1Parameters();
        params.set("display", "popup");
        params.setCallbackUrl(twitterCallBackUrl + "?" + TwitterCallback.REMEMBER_ME_PARAMETER + "=" + rememberMe);
        String authorizeUrl = oAuth1Operations.buildAuthorizeUrl(requestToken.getValue(), params);
        return authorizeUrl;
    }

    public Twitter getTwitterProfile(OAuthToken accessToken) {
        Connection<Twitter> connection = connectionFactory.createConnection(accessToken);
        Twitter twitter = connection.getApi();
        return twitter;
    }
}
