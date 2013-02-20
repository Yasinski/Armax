package com.imhos.security.server.social.twitter;

import com.imhos.security.server.CustomUserAuthentication;
import com.imhos.security.server.SocialAuthenticationRejectedException;
import com.imhos.security.server.social.SocialResponseBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.DBUserQueryer;
import third.model.Role;
import third.model.User;
import twitter4j.Twitter;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//import org.springframework.social.twitter.api.Twitter;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 12.02.13
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public class TwitterCallback implements Controller {
    public static final String REMEMBER_ME_PARAMETER = "rememberMe";
    private TwitterController twitterController;
    private DBUserQueryer dbUserQueryer;
    //    private TwitterConnectionFactory connectionFactory;
    private TokenBasedRememberMeServices rememberMeServices;
    private SocialResponseBuilder socialResponseBuilder;

    public void setTwitterController(TwitterController twitterController) {
        this.twitterController = twitterController;
    }

    public void setSocialResponseBuilder(SocialResponseBuilder socialResponseBuilder) {
        this.socialResponseBuilder = socialResponseBuilder;
    }

    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
        this.dbUserQueryer = dbUserQueryer;
    }

    public void setRememberMeServices(TokenBasedRememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

//    @Override
//    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        OAuthService service = (OAuthService) request.getSession().getAttribute("twitter");
//        Token accessToken = service.getAccessToken((Token) request.getSession().getAttribute("request_token"), new Verifier(verifier));
//        OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, TWITTER_URL_CREDENTIALS);
//        service.signRequest(accessToken, oauthRequest);
//        Map<String, Json> userInfoResponse = Json.read(oauthRequest.send().getBody()).asJsonMap();
//        String twitterId = userInfoResponse.get("id").asString();
//        //verifying ...
//        User user = new User();
//        user.setFirstName((String) request.getSession().getAttribute("pageValueFirstName"));
//        //...
//        user = (user) userDAO.put(user);
//        TwitterAuthUser user = new TwitterAuthUser();
//        user.setAuthority(EnumSet.of(Authority.CUSTOMER));
//        user.setIdentificationName(twitterId);
//        //...
//        user.setOauthToken(accessToken.getToken());
//        user.setOauthTokenSecret(accessToken.getSecret());
//        user.setType(AuthenticationType.TWITTER);
//        user.setUser(customer);
//        authenticationDAO.put(user);
//        return new ModelAndView(new RedirectView("/registrate.complete", true, true, false));
//    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        // upon receiving the callback from the provider:
//        OAuthToken accessToken = oauthOperations.exchangeForAccessToken(
//                new AuthorizedRequestToken(requestToken, oauthVerifier), null);
//        Connection<Twitter> connection = connectionFactory.createConnection(accessToken);

//         RequestToken requestToken = twitter.getOAuthRequestToken();
//
//        String token = requestToken.getToken();
//        String tokenSecret = requestToken.getTokenSecret();
//
//        AccessToken accessToken =
//                twitter.getOAuthAccessToken(token, tokenSecret);
//        twitter.setOAuthAccessToken(accessToken);
//        int id = accessToken.getUserId();
//        User user = twitter.showUser(id+"");
//        String screenName = user.getScreenName();
//        System.out.println("NAME:  " + screenName);
        String rememberMeParameter = request.getParameter(REMEMBER_ME_PARAMETER);
        boolean rememberMe = "true".equals(rememberMeParameter);

        Twitter twitter = twitterController.getTwitter();
        RequestToken requestToken = twitterController.getRequestToken();
        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken);
        String profileName = accessToken.getScreenName();
        int profileId = accessToken.getUserId();
        String id = Integer.toString(profileId);
        User user = dbUserQueryer.getUserByTwitterId(id);
        if(user == null) {
            Set<Role> authorities = new HashSet<Role>();
            authorities.add(Role.ROLE_USER);
            user = new User(id, profileName, "twitter",
                            authorities, true);
            dbUserQueryer.saveUser(user);
        } else {
            System.out.println("blbbl");
            //todo: all profile fields should be updated
//            what we should save?
//            dbUserQueryer.updateUser(user);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication = new CustomUserAuthentication(user, authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(rememberMe) {
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
