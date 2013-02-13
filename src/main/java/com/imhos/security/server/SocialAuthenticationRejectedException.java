package com.imhos.security.server;

import org.springframework.security.authentication.AccountStatusException;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 13.02.13 6:43
 */
public class SocialAuthenticationRejectedException extends AccountStatusException {



    public SocialAuthenticationRejectedException() {
        super("");
    }

    public SocialAuthenticationRejectedException(String msg) {
        super(msg);
    }

    public SocialAuthenticationRejectedException(String msg, Throwable t) {
        super(msg, t);
    }
}
