package com.imhos.security.server.service.social;

import com.imhos.security.server.model.UserConnection;
import com.imhos.security.server.service.CustomUserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
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
//todo: rename to smth like SocialConnectionSignUp
public class SocialSignInAdapter implements SignInAdapter {
    public static final String REMEMBER_ME_PARAMETER = "rememberMe";
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
        String rememberMeParameter = request.getParameter(REMEMBER_ME_PARAMETER);
        boolean rememberMe = "true".equals(rememberMeParameter);

        ConnectionData data = connection.createData();
        UserConnection userConnection = userConnectionDAO.get(userId, data.getProviderId(), data.getProviderUserId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication = new CustomUserAuthentication(userConnection, authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(rememberMe) {
            rememberMeServices.onLoginSuccess((HttpServletRequest) request.getNativeRequest(),
                                              (HttpServletResponse) request.getNativeResponse(), authentication);
        } else {
            rememberMeServices.logout((HttpServletRequest) request.getNativeRequest(),
                                      (HttpServletResponse) request.getNativeResponse(), authentication);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
