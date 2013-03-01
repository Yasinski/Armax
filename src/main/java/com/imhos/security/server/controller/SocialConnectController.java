package com.imhos.security.server.controller;

import com.imhos.security.server.service.serializer.Serializer;
import com.imhos.security.server.service.social.SignInSocialAdapter;
import com.imhos.security.server.service.social.SocialAuthenticationRejectedException;
import com.imhos.security.server.service.social.UserConnectionServiceWrapper;
import com.imhos.security.server.view.AbstractSocialView;
import com.imhos.security.server.view.SocialErrorView;
import com.imhos.security.server.view.SocialSuccessView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 20.02.13 0:39
 */
@Controller
@RequestMapping("/signin")
public class SocialConnectController {
    private final static Log LOGGER = LogFactory.getLog(SocialConnectController.class);
    private AbstractSocialView socialResponseView;
    private Serializer<Authentication> authenticationSerializer;
    private Serializer<AuthenticationException> authenticationExceptionSerializer;
    private ConnectionFactoryLocator connectionFactoryLocator;
    private final ConnectSupport webSupport = new ConnectSupport();
    private UserConnectionServiceWrapper usersConnectionRepository;
    private SignInSocialAdapter signInAdapter;
    private String signInUrl = "/signin";
    private String signUpUrl = "/signup";
    static final String SESSION_ATTRIBUTE = ProviderSignInAttempt.class.getName();
    static final String NEED_EMAIL = "&scope=email";


    public void setAuthenticationSerializer(Serializer<Authentication> authenticationSerializer) {
        this.authenticationSerializer = authenticationSerializer;
    }

    public void setAuthenticationExceptionSerializer(Serializer<AuthenticationException> authenticationExceptionSerializer) {
        this.authenticationExceptionSerializer = authenticationExceptionSerializer;
    }


    public SocialConnectController(ConnectionFactoryLocator connectionFactoryLocator,
                                   UserConnectionServiceWrapper usersConnectionRepository, SignInSocialAdapter signInAdapter) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.usersConnectionRepository = usersConnectionRepository;
        this.signInAdapter = signInAdapter;
    }

    public void setSocialResponseView(AbstractSocialView socialResponseView) {
        this.socialResponseView = socialResponseView;
    }

    @RequestMapping(value = "/{providerId}", params = "rememberMe")
    public RedirectView signIn(@PathVariable String providerId, @RequestParam boolean rememberMe, NativeWebRequest request) {
        request.setAttribute(SignInSocialAdapter.REMEMBER_ME_ATTRIBUTE, rememberMe, RequestAttributes.SCOPE_SESSION);
        ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId);
        try {
            return new RedirectView(webSupport.buildOAuthUrl(connectionFactory, request)
//                                            + NEED_EMAIL
            );
        } catch (Exception e) {
            LOGGER.warn("Exception while signing in (" + e.getMessage() + "). Redirecting to " + signInUrl);
            e.printStackTrace();
            return redirect(URIBuilder.fromUri(signInUrl).queryParam("error", "provider").build().toString());
        }
    }

    @RequestMapping(value = "/{providerId}", method = RequestMethod.GET, params = "oauth_token")
    public View oauth1Callback(@PathVariable String providerId, NativeWebRequest request) {
        try {
            OAuth1ConnectionFactory<?> connectionFactory = (OAuth1ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
            Connection<?> connection = webSupport.completeConnection(connectionFactory, request);
            return handleSignIn(connection, request);
        } catch (Exception e) {
            LOGGER.warn("Exception while handling OAuth1 callback (" + e.getMessage() + "). Redirecting to " + signInUrl);
            e.printStackTrace();
            return redirect(URIBuilder.fromUri(signInUrl).queryParam("error", "provider").build().toString());
        }

    }

    @RequestMapping(value = "/{providerId}", method = RequestMethod.GET, params = "code")
    public View oauth2Callback(@PathVariable String providerId, @RequestParam("code") String code, NativeWebRequest request) {
        try {
            OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
            Connection<?> connection = webSupport.completeConnection(connectionFactory, request);
            return handleSignIn(connection, request);
        } catch (Exception e) {
            LOGGER.warn("Exception while handling OAuth2 callback (" + e.getMessage() + "). Redirecting to " + signInUrl);
            e.printStackTrace();
            return redirect(URIBuilder.fromUri(signInUrl).queryParam("error", "provider").build().toString());
        }
    }


    @RequestMapping(value = "/{providerId}", method = RequestMethod.GET)
    public View sendError(NativeWebRequest request) {
        return new SocialErrorView(authenticationExceptionSerializer, new SocialAuthenticationRejectedException());
    }


    private View handleSignIn(Connection<?> connection, NativeWebRequest request) {
        List<String> userIds = usersConnectionRepository.findUserIdsWithConnection(connection);
        if (userIds.size() == 0) {
            ProviderSignInAttempt signInAttempt = new ProviderSignInAttempt(connection, connectionFactoryLocator, usersConnectionRepository);
            request.setAttribute(SESSION_ATTRIBUTE, signInAttempt, RequestAttributes.SCOPE_SESSION);
            return redirect(signUpUrl);
        } else if (userIds.size() == 1) {
            usersConnectionRepository.createConnectionRepository(userIds.get(0)).updateConnection(connection);
            signInAdapter.signIn(userIds.get(0), connection, request);
            Authentication authentication = (Authentication) request.getUserPrincipal();
            return new SocialSuccessView(authenticationSerializer, authentication);
//            return originalUrl != null ? redirect(originalUrl) : redirect(postSignInUrl);
        } else {
            return redirect(URIBuilder.fromUri(signInUrl).queryParam("error", "multiple_users").build().toString());
        }
    }

    private RedirectView redirect(String url) {
        return new RedirectView(url, true);
    }

}
