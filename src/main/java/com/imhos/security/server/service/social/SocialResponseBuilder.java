package com.imhos.security.server.service.social;

import com.imhos.security.server.serializer.Serializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 13.02.13
 * Time: 7:36
 * To change this template use File | Settings | File Templates.
 */

public class SocialResponseBuilder {

    public static final String SCRIPT_PREFIX = "<script>window.opener.";
    public static final String SCRIPT_POSTFIX = "');window.close();</script>";

    private Serializer<Authentication> authenticationSerializer;
    private Serializer<AuthenticationException> authenticationExceptionSerializer;


    public void setAuthenticationSerializer(Serializer<Authentication> authenticationSerializer) {
        this.authenticationSerializer = authenticationSerializer;
    }

    public void setAuthenticationExceptionSerializer(Serializer<AuthenticationException> authenticationExceptionSerializer) {
        this.authenticationExceptionSerializer = authenticationExceptionSerializer;
    }

    public String buildResponse(AuthenticationException error) {
        StringBuilder builder = new StringBuilder();
        builder.append(SCRIPT_PREFIX + "handleLoginError('");
        builder.append(authenticationExceptionSerializer.serialize(error));
        builder.append(SCRIPT_POSTFIX);
        return builder.toString();
    }

    public String buildResponse(Authentication user) {
        StringBuilder builder = new StringBuilder();
        builder.append(SCRIPT_PREFIX + "handleLoginSuccess('");
        builder.append(authenticationSerializer.serialize(user));
        builder.append(SCRIPT_POSTFIX);
        return builder.toString();
    }

}