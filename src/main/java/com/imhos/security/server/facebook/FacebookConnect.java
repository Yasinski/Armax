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

    private FacebookController facebookController;

    public void setFacebookController(FacebookController facebookController) {
        this.facebookController = facebookController;
    }


    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String rememberMeParameter = request.getParameter("rememberMe");
        boolean rememberMe = "true".equals(rememberMeParameter);
        String authorizeUrl = facebookController.getAuthorizeUrl(rememberMe);
        response.sendRedirect(authorizeUrl);
        return null;
    }
}
