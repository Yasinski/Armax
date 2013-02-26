package com.imhos.security.server.controller;

import com.imhos.security.server.serializer.Serializer;
import com.imhos.security.server.service.social.SignInSocialAdapter;
import com.imhos.security.server.service.social.SocialAuthenticationRejectedException;
import com.imhos.security.server.view.AbstractSocialView;
import com.imhos.security.server.view.SocialErrorView;
import com.imhos.security.server.view.SocialSuccessView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 20.02.13 0:39
 */
@Controller
@RequestMapping("/signin")
public class SocialConnectController {
    private AbstractSocialView socialResponseView;
    private ProviderSignInController providerSignInController;
    private Serializer<Authentication> authenticationSerializer;
    private Serializer<AuthenticationException> authenticationExceptionSerializer;


    public void setAuthenticationSerializer(Serializer<Authentication> authenticationSerializer) {
        this.authenticationSerializer = authenticationSerializer;
    }

    public void setAuthenticationExceptionSerializer(Serializer<AuthenticationException> authenticationExceptionSerializer) {
        this.authenticationExceptionSerializer = authenticationExceptionSerializer;
    }

    public SocialConnectController(ConnectionFactoryLocator connectionFactoryLocator,
                                   UsersConnectionRepository usersConnectionRepository, SignInAdapter signInAdapter) {
        providerSignInController = new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
//        setPostSignInUrl("/postSignIn/");
    }

    public void setSocialResponseView(AbstractSocialView socialResponseView) {
        this.socialResponseView = socialResponseView;
    }

    @RequestMapping(value = "/{providerId}", params = "rememberMe")
    public RedirectView signIn(@PathVariable String providerId, @RequestParam boolean rememberMe, NativeWebRequest request) {
        request.setAttribute(SignInSocialAdapter.REMEMBER_ME_ATTRIBUTE, rememberMe, RequestAttributes.SCOPE_SESSION);
        return providerSignInController.signIn(providerId, request);
    }

    @RequestMapping(value = "/{providerId}", method = RequestMethod.GET, params = "oauth_token")
    public View oauth1Callback(@PathVariable String providerId, NativeWebRequest request) {
        providerSignInController.oauth1Callback(providerId, request);
        Authentication authentication = (Authentication) request.getUserPrincipal();
        return new SocialSuccessView(authenticationSerializer, authentication);
    }

    @RequestMapping(value = "/{providerId}", method = RequestMethod.GET, params = "code")
    public View oauth2Callback(@PathVariable String providerId, @RequestParam("code") String code, NativeWebRequest request) {
        providerSignInController.oauth2Callback(providerId, code, request);
        Authentication authentication = (Authentication) request.getUserPrincipal();
        return new SocialSuccessView(authenticationSerializer, authentication);
    }


    @RequestMapping(value = "/{providerId}", method = RequestMethod.GET)
    public View sendError(NativeWebRequest request) {
        return new SocialErrorView(authenticationExceptionSerializer, new SocialAuthenticationRejectedException());
    }


}
