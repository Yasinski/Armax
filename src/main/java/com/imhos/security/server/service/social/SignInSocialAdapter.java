package com.imhos.security.server.service.social;

import com.imhos.security.server.model.UserConnection;
import com.imhos.security.server.service.CustomUserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import third.dao.UserConnectionDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 20.02.13
 * Time: 6:48
 * To change this template use File | Settings | File Templates.
 */
public class SignInSocialAdapter implements SignInAdapter {

    public static final String REMEMBER_ME_ATTRIBUTE = "rememberMe";
    private TokenBasedRememberMeServices rememberMeServices;
    private UserConnectionDAO userConnectionDAO;

    public void setUserConnectionDAO(UserConnectionDAO userConnectionDAO) {
        this.userConnectionDAO = userConnectionDAO;
    }

    public void setRememberMeServices(TokenBasedRememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        boolean rememberMe = (Boolean) request.getAttribute(REMEMBER_ME_ATTRIBUTE, RequestAttributes.SCOPE_SESSION);
        request.removeAttribute(REMEMBER_ME_ATTRIBUTE, RequestAttributes.SCOPE_SESSION);

        UserConnection userConnection = userConnectionDAO.get(userId, connection.getKey().getProviderId(),
                                                              connection.getKey().getProviderUserId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication = new CustomUserAuthentication(userConnection, authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (rememberMe) {
            rememberMeServices.onLoginSuccess((HttpServletRequest) request.getNativeRequest(),
                                              (HttpServletResponse) request.getNativeResponse(), authentication);
        } else {
            rememberMeServices.logout((HttpServletRequest) request.getNativeRequest(),
                                      (HttpServletResponse) request.getNativeResponse(), authentication);
        }
        return null;
    }
}
