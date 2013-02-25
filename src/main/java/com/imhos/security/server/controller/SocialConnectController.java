package com.imhos.security.server.controller;

import com.imhos.security.server.service.social.SocialSignInAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 20.02.13 0:39
 */
public class SocialConnectController extends ProviderSignInController {

    public SocialConnectController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository, SignInAdapter signInAdapter) {
        super(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
        setPostSignInUrl("/postSignIn/");
    }

    @RequestMapping(value = "/{providerId}")
    public RedirectView signIn(@PathVariable String providerId, NativeWebRequest request) {
        boolean rememberMe = "true".equals(request.getParameter(SocialSignInAdapter.REMEMBER_ME_ATTRIBUTE));
        request.setAttribute(SocialSignInAdapter.REMEMBER_ME_ATTRIBUTE, rememberMe, RequestAttributes.SCOPE_SESSION);
        return super.signIn(providerId, request);
    }
//    public RedirectView oauth1Callback(@PathVariable String providerId, NativeWebRequest request) {
//        boolean rememberMe = (Boolean) request.getAttribute(REMEMBER_ME_ATTRIBUTE, RequestAttributes.SCOPE_SESSION);
//        request.removeAttribute(REMEMBER_ME_ATTRIBUTE, RequestAttributes.SCOPE_SESSION);
//        return   super.oauth1Callback(providerId,  request) ;
//    }
//
//    public RedirectView oauth2Callback(@PathVariable String providerId, @RequestParam("code") String code, NativeWebRequest request) {
//      return   super.oauth2Callback(providerId,code,  request) ;
//    }

    @RequestMapping(value = "/cancel/{providerId}")
    public RedirectView canceledAuthorizationCallback() {
        return super.canceledAuthorizationCallback();
    }


}
