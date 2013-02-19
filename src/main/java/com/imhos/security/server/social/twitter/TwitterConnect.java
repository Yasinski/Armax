package com.imhos.security.server.social.twitter;

import com.imhos.security.server.social.facebook.FacebookController;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;
//import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 12.02.13
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class TwitterConnect implements Controller {

    private TwitterController twitterController;

    public void setTwitterController(TwitterController twitterController) {
        this.twitterController = twitterController;
    }


    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rememberMeParameter = request.getParameter("rememberMe");
        boolean rememberMe = "true".equals(rememberMeParameter);
        RequestToken requestToken = twitterController.getRequestToken();
        String authorizeUrl = twitterController.getAuthorizeUrl(requestToken, rememberMe);
        response.sendRedirect(authorizeUrl);
        return null;
    }
}
