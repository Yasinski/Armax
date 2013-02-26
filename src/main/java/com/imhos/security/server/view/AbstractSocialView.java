package com.imhos.security.server.view;

import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 13.02.13
 * Time: 7:36
 * To change this template use File | Settings | File Templates.
 */

public abstract class AbstractSocialView extends AbstractView {

    public static final String SCRIPT_PREFIX = "<script>window.opener.";
    public static final String SCRIPT_POSTFIX = ";window.close();</script>";


    public String buildResponse() {
        StringBuilder builder = new StringBuilder();
        builder.append(SCRIPT_PREFIX);
        appendJSMethodCall(builder);
        builder.append(SCRIPT_POSTFIX);
        return builder.toString();
    }

    public abstract void appendJSMethodCall(StringBuilder builder);

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication authentication = (Authentication) request.getUserPrincipal();
        response.getWriter().write(buildResponse());

    }
}