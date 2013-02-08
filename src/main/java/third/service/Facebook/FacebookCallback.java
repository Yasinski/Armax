package third.service.facebook;

import com.imhos.security.CustomUserAuthentication;
import com.imhos.security.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
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
    private DBUserQueryer dbUserQueryer;

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
        String appID = "243747852427266";
        String appSecret = "cb94bc23e354c19c54fdf1ba13bdabea";

        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(appID, appSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        String authCode = request.getParameter("code");
        AccessGrant accessGrant =
                oauthOperations.exchangeForAccess(authCode, "http://localhost:8080/facebookcallback/", null);
        Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);

        Facebook facebook = connection.getApi();
        String profileId = facebook.userOperations().getUserProfile().getId();
        String profileName = facebook.userOperations().getUserProfile().getName();
        System.out.println("Facebook: " + profileId + " " + profileName);

        User user = dbUserQueryer.getUserByFacebookId(profileId);
        if (user == null) {
            Set<Role> authorities = new HashSet<Role>();
            authorities.add(Role.ROLE_USER);
            dbUserQueryer.saveUser(new User(profileId, profileName, authCode, authorities, true));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication = new CustomUserAuthentication(user, authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        Authentication authentication = userDetailsServiceImpl.trust(user);
        //        ...
        return null;
    }
}

