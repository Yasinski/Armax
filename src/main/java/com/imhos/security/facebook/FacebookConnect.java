package com.imhos.security.facebook;

import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.DBUserQueryer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 08.02.13
 * Time: 7:13
 * To change this template use File | Settings | File Templates.
 */

public class FacebookConnect implements Controller {

    private String appID;
    private String appSecret;

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        FacebookController facebookController = new FacebookController(appID, appSecret);
        String authorizeUrl = facebookController.getAuthorizeUrl();
        response.sendRedirect(authorizeUrl);
        return null;
    }
}
