package com.imhos.security.client.module;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.imhos.security.client.module.deserializer.AuthenticationErrorDeserializerImpl;
import com.imhos.security.client.module.deserializer.Deserializer;
import com.imhos.security.client.module.deserializer.UserDetailsDeserializerGwtImpl;
import com.imhos.security.client.module.view.SampleApplicationView;
import com.imhos.security.shared.model.AuthenticationError;
import com.imhos.security.shared.model.UserDetails;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class LoginEditor implements LoginHandler<AuthenticationError> {

    public static final String NOT_LOGGED_IN = "Not logged in";
    private SampleApplicationView view;
    private Deserializer<UserDetails> userDetailsDeserializer;
    private Deserializer<AuthenticationError> authenticationErrorDeserializer;
    private LoginHandler<AuthenticationError> loginHandler;

    public LoginEditor(SampleApplicationView loginView) {
        this.view = loginView;
        registerLoginHandler(this);

        userDetailsDeserializer = new UserDetailsDeserializerGwtImpl();
        authenticationErrorDeserializer = new AuthenticationErrorDeserializerImpl();
        loginHandler = this;

        //        SubmitButton
        //        FormPanel form = new FormPanel();
        //        form.setAction("javascript:__gwt_login()");
        //        FlowPanel formPanel = new FlowPanel();
        //        form.add(formPanel);

        view.getLogin().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                login(view.getUsername().getValue(), view.getPassword().getValue(), view.getRememberMe().getValue());
            }
        });
        view.getLogout().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                logout();
            }
        });
        view.loginFacebook().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                loginFacebook(view.getRememberMe().getValue());
            }
        });
        view.loginTwitter().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                loginTwitter(view.getRememberMe().getValue());
            }
        });

        checkAuthentication();
    }

    public Widget asWidget() {
        return (Widget) view;
    }

    private void loginFacebook(boolean rememberMe) {
        Window.open("/connect/signin/facebook/?rememberMe=" + rememberMe + "&scope=email", "Facebook Login", "width=500,height=300," +
                "location=1,left=0,top=0");
    }

    private void loginTwitter(boolean rememberMe) {
        Window.open("/connect/signin/twitter/?rememberMe=" + rememberMe, "Twitter Login", "width=500,height=300," +
                "location=1,left=0,top=0");
    }

    private void login(String login, String pass, boolean rememberMe) {

        RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/j_spring_security_check");
        rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
        rb.setRequestData("j_username=" + URL.encode(login) + "&j_password=" + URL.encode(pass)
                                  + "&_spring_security_remember_me=" + URL.encode(rememberMe + "")
        );

        rb.setCallback(new RequestCallback() {
            public void onError(Request request, Throwable exception) {
                loginHandler.handleLoginError(AuthenticationError.REQUEST_EXCEPTION);
            }

            public void onResponseReceived(Request request, Response response) {
                if (Response.SC_OK == response.getStatusCode()) {
//                    JsonUtils.safeEval(response.getText())
                    handleLoginSuccess(response.getText());
                } else {
                    handleLoginError(response.getText());
                }
            }
        });

        try {
            rb.send();
        } catch (RequestException re) {
            re.printStackTrace();
        }
    }


    void logout() {
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/logout");
        rb.setHeader("Content-Type", "application/x-www-form-urlencoded");

        rb.setCallback(new RequestCallback() {
            public void onError(Request request, Throwable exception) {
                //todo: logout handler should be implemented
                Window.alert("[logout] Error");
            }

            public void onResponseReceived(Request request, Response response) {
                if (Response.SC_OK == response.getStatusCode()) {
                    //todo: logout handler should be implemented
                    view.getMessage().setText(NOT_LOGGED_IN);
                    Window.alert("[logout] Success");
                } else {
                    //todo: logout handler should be implemented
                    Window.alert("[logout] " + response.getStatusCode() + "," + response.getStatusText());
                }
            }
        });

        try {
            rb.send();
        } catch (RequestException re) {
            re.printStackTrace();
        }
    }


    public void handleLoginSuccess(UserDetails user) {
        view.getMessage().setText(user.getUsername());
    }

    public void handleLoginError(AuthenticationError error) {
        view.getMessage().setText(error.toString());
    }

    private void handleLoginSuccess(String json) {
        loginHandler.handleLoginSuccess(userDetailsDeserializer.deserialize(json));
    }

    private void handleLoginError(String json) {
        loginHandler.handleLoginError(authenticationErrorDeserializer.deserialize(json));
    }


    public boolean checkAuthentication() {
        String authenticationJSON = getAuthenticationJSON();
        if (authenticationJSON != null && !getAuthenticationJSON().isEmpty()) {
            handleLoginSuccess(authenticationJSON);
            return true;
        } else {
            return false;
        }
    }

    public native String getAuthenticationJSON() /*-{
        return $wnd.authentication;
    }-*/;

    public native void registerLoginHandler(LoginEditor impl) /*-{

        $wnd.handleLoginSuccess = $entry(function (user) {
            impl.@com.imhos.security.client.module.LoginEditor::handleLoginSuccess(Ljava/lang/String;)(user);
        });
        $wnd.handleLoginError = $entry(function (user) {
            impl.@com.imhos.security.client.module.LoginEditor::handleLoginError(Ljava/lang/String;)(user);
        });
    }-*/;
}
