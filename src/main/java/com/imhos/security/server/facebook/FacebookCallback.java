package com.imhos.security.server.facebook;

import com.imhos.security.server.CustomUserAuthentication;
import com.imhos.security.server.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
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
    private String appID;
    private String appSecret;
    private String facebookCallBackUrl;
    private DBUserQueryer dbUserQueryer;
    private TokenBasedRememberMeServices rememberMeServices;

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setFacebookCallBackUrl(String facebookCallBackUrl) {
        this.facebookCallBackUrl = facebookCallBackUrl;
    }

    public void setRememberMeServices(TokenBasedRememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
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

        String authCode = request.getParameter(FACEBOOK_AUTH_CODE_PARAMETER);
        String rememberMe = request.getParameter(REMEMBER_ME_PARAMETER);
        String facebookCallBackUrl = this.facebookCallBackUrl + "?" + REMEMBER_ME_PARAMETER + "=" + rememberMe;
        FacebookController facebookController = new FacebookController(appID, appSecret, facebookCallBackUrl);

        AccessGrant accessGrant = facebookController.getFacebookAccessGrant(authCode);
        FacebookProfile profile = facebookController.getFacebookProfile(accessGrant).userOperations().getUserProfile();
        String profileId = profile.getId();
        String profileName = profile.getName();

        User user = dbUserQueryer.getUserByFacebookId(profileId);
        if(user == null) {
            Set<Role> authorities = new HashSet<Role>();
            authorities.add(Role.ROLE_USER);
            user = new User(profileId, profileName, "facebook",
                            accessGrant.getAccessToken(), authorities, true);
            dbUserQueryer.saveUser(user);
        } else {
            user.setFacebookToken(accessGrant.getAccessToken());
            dbUserQueryer.updateUser(user);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication = new CustomUserAuthentication(user, authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if("true".equals(rememberMe)) {
            rememberMeServices.onLoginSuccess(request, response, authentication);
        } else {
            rememberMeServices.logout(request, response, authentication);
        }
        response.getWriter().print("<script>\n" +
                                           "    window.opener.handleLogin('" + user.getUsername() + "');\n" +
                                           "            window.close();\n" +
                                           "        </script>");
        return null;
    }
}

