package com.imhos.security;

import com.imhos.security.facebook.FacebookController;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.social.RevokedAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import third.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 10.02.13
 * Time: 9:02
 * To change this template use File | Settings | File Templates.
 */
public class RememberMeUserDetailsServicesImpl extends UserDetailsServiceImpl {

    private String appID;
    private String appSecret;

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    //    private DBUserQueryer dbUserQueryer;
    //
    //    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
    //        this.dbUserQueryer = dbUserQueryer;
    //    }

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = (User) super.loadUserByUsername(login);
        if(user.getFacebookId() == null) {
            return user;
        } else {
            FacebookController facebookController = new FacebookController(appID, appSecret);
            Facebook facebook = facebookController.getFacebookProfile(user.getFacebookToken());
            try {
                if(user.getFacebookId().equals(facebook.userOperations().getUserProfile().getId())) {
                    return user;
                } else {
                    System.out.println("Аккаунт был удален!");
                    throw new UsernameNotFoundException("Аккаунт был удален");
                }
            } catch (RevokedAuthorizationException e) {
                throw new RememberMeAuthenticationException(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

}