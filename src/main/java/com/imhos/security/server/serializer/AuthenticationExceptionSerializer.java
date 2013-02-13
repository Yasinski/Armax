package com.imhos.security.server.serializer;

import com.imhos.security.server.SocialAuthenticationRejectedException;
import com.imhos.security.shared.model.AuthenticationError;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 13.02.13
 * Time: 7:10
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationExceptionSerializer implements Serializer<AuthenticationException> {

    @Override
    public String serialize(AuthenticationException exception) {
        // todo: handle more Authentication Exception
        AuthenticationError error;
        if (exception instanceof SocialAuthenticationRejectedException) {
            error = AuthenticationError.THIRD_PARTY_AUTHORIZATION_REJECTED;
        } else {
            error = AuthenticationError.BAD_CREDENTIALS;
        }
        return String.valueOf(error.ordinal());
    }
}
