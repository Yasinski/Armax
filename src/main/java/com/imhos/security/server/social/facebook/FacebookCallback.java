package com.imhos.security.server.social.facebook;

import com.imhos.security.server.CustomUserAuthentication;
import com.imhos.security.server.SocialAuthenticationRejectedException;
import com.imhos.security.server.social.SocialResponseBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.HashSet;
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
    //    todo: dbUserQueryer should be an interface
    private DBUserQueryer dbUserQueryer;
    private TokenBasedRememberMeServices rememberMeServices;
    private FacebookController facebookController;
    private SocialResponseBuilder socialResponseBuilder;

    public void setRememberMeServices(TokenBasedRememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }


    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
        this.dbUserQueryer = dbUserQueryer;
    }

    public void setFacebookController(FacebookController facebookController) {
        this.facebookController = facebookController;
    }

    public void setSocialResponseBuilder(SocialResponseBuilder socialResponseBuilder) {
        this.socialResponseBuilder = socialResponseBuilder;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String authCode = request.getParameter(FACEBOOK_AUTH_CODE_PARAMETER);
        if (authCode == null || authCode.isEmpty()) {
            return sentLoginError(response, new SocialAuthenticationRejectedException());
        }
        String rememberMeParameter = request.getParameter(REMEMBER_ME_PARAMETER);
        boolean rememberMe = "true".equals(rememberMeParameter);

        AccessGrant accessGrant;
        FacebookProfile profile;
        try {
            accessGrant = facebookController.getFacebookAccessGrant(authCode, rememberMe);
            profile = facebookController.getFacebookProfile(accessGrant).userOperations().getUserProfile();
        } catch (RevokedAuthorizationException e) {
            return sentLoginError(response, new SocialAuthenticationRejectedException());
        }
        String profileId = profile.getId();
        String profileName = profile.getName();
        //todo: email should be used as login and username should be separated field
        String email = profile.getEmail();

        User user = dbUserQueryer.getUserByFacebookId(profileId);
        if (user == null) {
            Set<Role> authorities = new HashSet<Role>();
            authorities.add(Role.ROLE_USER);
            user = new User(profileId, profileName, email, "facebook",
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

        if (rememberMe) {
            rememberMeServices.onLoginSuccess(request, response, authentication);
        } else {
            rememberMeServices.logout(request, response, authentication);
        }
        return sentLoginSuccess(response, authentication);
    }

    private ModelAndView sentLoginSuccess(HttpServletResponse response, Authentication user) throws IOException {
        response.getWriter().print(socialResponseBuilder.buildResponse(user));
        return null;
    }

    private ModelAndView sentLoginError(HttpServletResponse response, SocialAuthenticationRejectedException error) throws IOException {
        response.getWriter().print(socialResponseBuilder.buildResponse(error));
        return null;
    }

}

