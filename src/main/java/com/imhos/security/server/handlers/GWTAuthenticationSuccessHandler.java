package com.imhos.security.server.handlers;

import com.google.gson.Gson;
import com.imhos.security.shared.model.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 06.02.13
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */

public class GWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        //todo: should be separated to some kind of User serializer class
        UserDetailsImpl user = new UserDetailsImpl();
        user.setUsername(authentication.getName());
        List<String> authorities = new ArrayList<String>();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            authorities.add(grantedAuthority.toString());
        }
        user.setAuthorities(authorities);

        Gson gson = new Gson();
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(user));
    }

}
