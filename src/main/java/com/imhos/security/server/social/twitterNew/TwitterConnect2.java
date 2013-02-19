package com.imhos.security.server.social.twitterNew;

import com.imhos.security.server.social.twitter.TwitterCallback;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.social.twitter.connect.TwitterServiceProvider;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 19.02.13
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */

public class TwitterConnect2 implements Controller {

    private TwitterController2 twitterController2;

    public void setTwitterController2(TwitterController2 twitterController2) {
        this.twitterController2 = twitterController2;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String rememberMeParameter = request.getParameter("rememberMe");
        boolean rememberMe = "true".equals(rememberMeParameter);
        String authorizeUrl = twitterController2.getAuthorizeUrl(rememberMe);
        response.sendRedirect(authorizeUrl);
        return null;
    }
}
