package com.imhos.security.server.facebook;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

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
    private String facebookCallBackUrl;

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setFacebookCallBackUrl(String facebookCallBackUrl) {
        this.facebookCallBackUrl = facebookCallBackUrl;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String rememberMe = request.getParameter("rememberMe");

        String facebookCallBackUrl = this.facebookCallBackUrl + "?rememberMe=" + rememberMe;

        FacebookController facebookController = new FacebookController(appID, appSecret, facebookCallBackUrl);

        String authorizeUrl = facebookController.getAuthorizeUrl();
        response.sendRedirect(authorizeUrl);
        return null;
    }
}
