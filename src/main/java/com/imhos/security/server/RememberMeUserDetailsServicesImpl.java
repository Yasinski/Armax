package com.imhos.security.server;

import com.imhos.security.server.facebook.FacebookController;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.RejectedAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import third.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 10.02.13
 * Time: 9:02
 * To change this template use File | Settings | File Templates.
 */
public class RememberMeUserDetailsServicesImpl implements UserDetailsService {

    private String appID;
    private String appSecret;
    private UserDetailsService userDetailsService;

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }


    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = (User) userDetailsService.loadUserByUsername(login);
        if(user.getFacebookId() == null) {
            return user;
        } else {
            FacebookController facebookController = new FacebookController(appID, appSecret);
            try {
                Facebook facebook = facebookController.getFacebookProfile(user.getFacebookToken());
                if(user.getFacebookId().equals(facebook.userOperations().getUserProfile().getId())) {
                    return user;
                } else {
                    throw new UsernameNotFoundException("");
                }
//       todo: all kind of exceptions should be checked
            } catch (RejectedAuthorizationException e) {
                throw new RememberMeAuthenticationException(e.getMessage());
            } catch (NotAuthorizedException e) {
                throw new RememberMeAuthenticationException(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

}