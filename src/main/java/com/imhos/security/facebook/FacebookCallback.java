package com.imhos.security.facebook;

import com.imhos.security.CustomUserAuthentication;
import com.imhos.security.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.Facebook;
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
    private String appID;
    private String appSecret;
    private DBUserQueryer dbUserQueryer;

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    private UserDetailsServiceImpl userDetailsServiceImpl;

    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
        this.dbUserQueryer = dbUserQueryer;
    }

    public void setUserDetailsServiceImpl(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String authCode = request.getParameter("code");
        FacebookController facebookController = new FacebookController(appID, appSecret);
        AccessGrant accessGrant = facebookController.getFacebookAccessGrant(authCode);
        Facebook facebook = facebookController.getFacebookProfile(accessGrant);
        String profileId = facebook.userOperations().getUserProfile().getId();
        String profileName = facebook.userOperations().getUserProfile().getName();

        User user = dbUserQueryer.getUserByFacebookId(profileId);
        if(user == null) {
            Set<Role> authorities = new HashSet<Role>();
            authorities.add(Role.ROLE_USER);
            dbUserQueryer.saveUser(new User(profileId, profileName, "facebook",
                    accessGrant.getAccessToken(), authorities, true));
        } else {
            user.setFacebookToken(accessGrant.getAccessToken());
            dbUserQueryer.updateUser(user);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication = new CustomUserAuthentication(user, authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ModelAndView("/application/");
    }
}

