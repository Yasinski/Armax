package com.imhos.security.server.social.twitter;

import com.imhos.security.server.social.facebook.FacebookCallback;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.RequestToken;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 12.02.13
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class TwitterController {
    private String twitterCallBackUrl;
    private Twitter twitter;
    private RequestToken requestToken;

    public TwitterController(String appID, String appSecret, String twitterCallBackUrl) throws TwitterException {
        twitter= new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(appID, appSecret);
        this.twitterCallBackUrl = twitterCallBackUrl;
        requestToken = twitter.getOAuthRequestToken();
    }

    public Twitter getTwitter(){
        return twitter;
    }

    public RequestToken getRequestToken() throws TwitterException {
        return requestToken;

    }

    public String getAuthorizeUrl(RequestToken requestToken,boolean rememberMe){
        String authorizeUrl = requestToken.getAuthenticationURL();
      return  authorizeUrl
//              + "?" + TwitterCallback.REMEMBER_ME_PARAMETER + "=" + rememberMe
              ;
    }


}
