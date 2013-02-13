package com.imhos.security.server.facebook;

import com.google.gson.Gson;
import com.imhos.security.server.CustomUserAuthentication;
import com.imhos.security.shared.model.AuthenticationError;
import com.imhos.security.shared.model.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.social.RevokedAuthorizationException;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.DBUserQueryer;
import third.model.Role;
import third.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 08.02.13
 * Time: 7:23
 * To change this template use File | Settings | File Templates.
 */
public class FacebookCallback implements Controller {
    public static final String FACEBOOK_AUTH_CODE_PARAMETER = "code";
    public static final String REMEMBER_ME_PARAMETER = "rememberMe";
    private DBUserQueryer dbUserQueryer;
    private TokenBasedRememberMeServices rememberMeServices;
    private FacebookController facebookController;

    public void setRememberMeServices(TokenBasedRememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }


    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
        this.dbUserQueryer = dbUserQueryer;
    }

    public void setFacebookController(FacebookController facebookController) {
        this.facebookController = facebookController;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String authCode = request.getParameter(FACEBOOK_AUTH_CODE_PARAMETER);
        if(authCode == null||authCode.isEmpty()) {
            return sentLoginError(response, AuthenticationError.THIRD_PARTY_AUTHORIZATION_REJECTED);
        }
        String rememberMeParameter = request.getParameter(REMEMBER_ME_PARAMETER);
        boolean rememberMe = "true".equals(rememberMeParameter);

        AccessGrant accessGrant;
        FacebookProfile profile;
        try {
            accessGrant = facebookController.getFacebookAccessGrant(authCode, rememberMe);
            profile = facebookController.getFacebookProfile(accessGrant).userOperations().getUserProfile();
        } catch (RevokedAuthorizationException e) {
            return sentLoginError(response, AuthenticationError.THIRD_PARTY_AUTHORIZATION_REJECTED);
        }
        String profileId = profile.getId();
        String profileName = profile.getName();
        //todo: email should be used as login
        String email = profile.getEmail();

        User user = dbUserQueryer.getUserByFacebookId(profileId);
        if(user == null) {
            Set<Role> authorities = new HashSet<Role>();
            authorities.add(Role.ROLE_USER);
            user = new User(profileId, profileName, "facebook",
                            accessGrant.getAccessToken(), authorities, true);
            dbUserQueryer.saveUser(user);
        } else {
            //todo: all profile fields should be updated
            user.setFacebookToken(accessGrant.getAccessToken());
            dbUserQueryer.updateUser(user);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication = new CustomUserAuthentication(user, authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(rememberMe) {
            rememberMeServices.onLoginSuccess(request, response, authentication);
        } else {
            rememberMeServices.logout(request, response, authentication);
        }
        return sentLoginSuccess(response, user);
    }

    private ModelAndView sentLoginSuccess(HttpServletResponse response, User user) throws IOException {
//        todo: use StringBuilder or forward to jsp view
        response.getWriter().print("<script>\n" +
                                           "window.opener.handleLoginSuccess('" + serialize(user) + "');\n" +
                                           "window.close();\n" +
                                           "</script>");
        return null;
    }

    private ModelAndView sentLoginError(HttpServletResponse response, AuthenticationError error) throws IOException {
//        todo: use StringBuilder or forward to jsp view
        response.getWriter().print("<script>\n" +
                                           "window.opener.handleLoginError('" + serialize(error) + "');\n" +
                                           "window.close();\n" +
                                           "</script>");
        return null;
    }

    //todo: should be separated to some kind of AuthenticationError serializer class
    private String serialize(AuthenticationError error) {
        return String.valueOf(error.ordinal());
    }

    //todo: should be separated to some kind of User serializer class
    private String serialize(UserDetails authentication) {

        UserDetailsImpl user = new UserDetailsImpl();
        user.setUsername(authentication.getUsername());
        List<String> authorities = new ArrayList<String>();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            authorities.add(grantedAuthority.toString());
        }
        user.setAuthorities(authorities);

        Gson gson = new Gson();
        return gson.toJson(user);
    }
}

