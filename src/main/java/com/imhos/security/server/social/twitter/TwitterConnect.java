package com.imhos.security.server.social.twitter;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import twitter4j.http.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import twitter4j.Twitter;

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
