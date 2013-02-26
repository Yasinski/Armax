package com.imhos.security.server.view;

import com.imhos.security.server.serializer.Serializer;
import org.springframework.security.core.AuthenticationException;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 13.02.13
 * Time: 7:36
 * To change this template use File | Settings | File Templates.
 */

public class SocialErrorView extends AbstractSocialView {

    private Serializer<org.springframework.security.core.AuthenticationException> authenticationExceptionSerializer;
    private AuthenticationException authenticationException;

    public SocialErrorView(Serializer<AuthenticationException> authenticationExceptionSerializer, AuthenticationException authenticationException) {
        this.authenticationExceptionSerializer = authenticationExceptionSerializer;
        this.authenticationException = authenticationException;
    }

    @Override
    public void appendJSMethodCall(StringBuilder builder) {
        builder.append("handleLoginError('");
        builder.append(authenticationExceptionSerializer.serialize(authenticationException));
        builder.append("')");
    }


}