package com.imhos.security.server.social;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
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
        setPostSignInUrl("/application");
    }

    @RequestMapping(value = "/{providerId}")
    public RedirectView signIn(@PathVariable String providerId, NativeWebRequest request) {
        return super.signIn(providerId, request);
    }

    @RequestMapping(value = "/cancel/{providerId}")
    public RedirectView canceledAuthorizationCallback() {
        return super.canceledAuthorizationCallback();
    }


}
