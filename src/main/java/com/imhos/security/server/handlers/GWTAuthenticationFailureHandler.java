package com.imhos.security.server.handlers;

import com.imhos.security.server.service.serializer.AuthenticationExceptionSerializer;
import com.imhos.security.server.service.serializer.Serializer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 06.02.13
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */

public class GWTAuthenticationFailureHandler implements AuthenticationFailureHandler {


    private Serializer<AuthenticationException> authenticationExceptionSerializer = new AuthenticationExceptionSerializer();


    public void setAuthenticationExceptionSerializer(Serializer<AuthenticationException> authenticationExceptionSerializer) {
        this.authenticationExceptionSerializer = authenticationExceptionSerializer;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(authenticationExceptionSerializer.serialize(exception));
    }
}
