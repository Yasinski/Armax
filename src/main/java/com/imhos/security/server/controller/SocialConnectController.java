package com.imhos.security.server.controller;

import com.imhos.security.server.service.social.SocialAuthenticationRejectedException;
import com.imhos.security.server.service.social.SocialResponseBuilder;
import com.imhos.security.server.service.social.SocialSignInAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 20.02.13 0:39
 */
public class SocialConnectController extends ProviderSignInController {

    private SocialResponseBuilder socialResponseBuilder;

    public SocialConnectController(ConnectionFactoryLocator connectionFactoryLocator,
                                   UsersConnectionRepository usersConnectionRepository, SignInAdapter signInAdapter) {
        super(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
        setPostSignInUrl("/postSignIn/");
    }

    public void setSocialResponseBuilder(SocialResponseBuilder socialResponseBuilder) {
        this.socialResponseBuilder = socialResponseBuilder;
    }

    @RequestMapping(value = "/{providerId}")
    public RedirectView signIn(@PathVariable String providerId, NativeWebRequest request) {
        boolean rememberMe = "true".equals(request.getParameter(SocialSignInAdapter.REMEMBER_ME_ATTRIBUTE));
        request.setAttribute(SocialSignInAdapter.REMEMBER_ME_ATTRIBUTE, rememberMe, RequestAttributes.SCOPE_SESSION);
        super.signIn(providerId, request);
        Authentication authentication = (Authentication) request.getUserPrincipal();
        try {
            ((HttpServletResponse) request.getNativeResponse()).getWriter().write(socialResponseBuilder.buildResponse(authentication));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @RequestMapping(value = "/error")
    public RedirectView canceledAuthorizationCallback() {
        return super.canceledAuthorizationCallback();
    }

    public void sendError(NativeWebRequest request) {
        try {
            HttpServletResponse response = (HttpServletResponse) request.getNativeResponse();
            response.getWriter().write(socialResponseBuilder.buildResponse(new SocialAuthenticationRejectedException()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
