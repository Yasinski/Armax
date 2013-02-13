package com.imhos.security.server.handlers;

import com.imhos.security.server.serializer.Serializer;
import com.imhos.security.server.serializer.AuthenticationSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 06.02.13
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */

public class GWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    private Serializer<Authentication> serializer = new AuthenticationSerializer();

    public void setSerializer(Serializer<Authentication> serializer) {
        this.serializer = serializer;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(serializer.serialize(authentication));
    }

}
