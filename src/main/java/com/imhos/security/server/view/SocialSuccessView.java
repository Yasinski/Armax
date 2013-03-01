package com.imhos.security.server.view;

import com.imhos.security.server.service.serializer.Serializer;
import org.springframework.security.core.Authentication;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 13.02.13
 * Time: 7:36
 * To change this template use File | Settings | File Templates.
 */

public class SocialSuccessView extends AbstractSocialView {


    private Serializer<Authentication> authenticationSerializer;
    private Authentication authentication;

    public SocialSuccessView(Serializer<Authentication> authenticationSerializer, Authentication authentication) {
        this.authenticationSerializer = authenticationSerializer;
        this.authentication = authentication;
    }


    @Override
    public void appendJSMethodCall(StringBuilder builder) {
        builder.append("handleLoginSuccess('");
        builder.append(authenticationSerializer.serialize(authentication));
        builder.append("')");
    }


}