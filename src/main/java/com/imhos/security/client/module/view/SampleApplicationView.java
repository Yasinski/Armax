package com.imhos.security.client.module.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="">Max Yasinski</a>
 * @updated 25.01.13 10:41
 */
public interface SampleApplicationView {

    HasClickHandlers getLogin();

    HasClickHandlers loginFacebook();

    HasClickHandlers loginTwitter();

    HasClickHandlers getLogout();

    Label getMessage();

    HasValue<String> getUsername();

    HasValue<String> getPassword();

    HasValue<Boolean> getRememberMe();

}
