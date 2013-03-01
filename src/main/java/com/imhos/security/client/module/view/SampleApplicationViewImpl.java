package com.imhos.security.client.module.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="">Max Yasinski</a>
 * @updated 25.01.13 10:41
 */
public class SampleApplicationViewImpl extends FlowPanel implements SampleApplicationView {
    public static final String NOT_LOGGED_IN = "Not logged in";
    private Label message = new Label(NOT_LOGGED_IN);
    private SubmitButton login = new SubmitButton("Login");
    private Button loginFacebook = new Button("Sign in with Facebook");
    private Button loginTwitter = new Button("Sign in with Twitter");
    private Button logout = new Button("Logout");
    private TextBox username = new TextBox();
    private PasswordTextBox password = new PasswordTextBox();
    private CheckBox rememberMe = new CheckBox("rememberMe");

    public SampleApplicationViewImpl() {
        add(message);
        add(username);
        add(password);
        add(rememberMe);
        add(login);
        add(loginFacebook);
        add(loginTwitter);
        add(logout);
    }

    @Override
    public HasClickHandlers getLogin() {
        return login;
    }

    @Override
    public HasClickHandlers loginFacebook() {
        return loginFacebook;
    }

    @Override
    public HasClickHandlers loginTwitter() {
        return loginTwitter;
    }

    @Override
    public HasClickHandlers getLogout() {
        return logout;
    }

    @Override
    public Label getMessage() {
        return message;
    }

    @Override
    public HasValue<String> getUsername() {
        return username;
    }

    @Override
    public HasValue<String> getPassword() {
        return password;
    }

    @Override
    public HasValue<Boolean> getRememberMe() {
        return rememberMe;
    }

}
