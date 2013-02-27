package com.imhos.security.client.module;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.imhos.security.shared.model.AuthenticationError;
import com.imhos.security.shared.model.UserDetails;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class SampleApplication implements EntryPoint, LoginHandler<AuthenticationError> {

    public static final String NOT_LOGGED_IN = "Not logged in";
    private Label message = new Label(NOT_LOGGED_IN);
    private SubmitButton login = new SubmitButton("Login");
    private Button loginFacebook = new Button("Sign in with Facebook");
    private Button loginTwitter = new Button("Sign in with Twitter");
    private Button logout = new Button("Logout");
    private Button button = new Button("Button");
    private TextBox username = new TextBox();
    private PasswordTextBox password = new PasswordTextBox();
    private CheckBox rememberMe = new CheckBox("rememberMe");
    private Deserializer<UserDetails> userDetailsDeserializer;
    private Deserializer<AuthenticationError> authenticationErrorDeserializer;
    private LoginHandler<AuthenticationError> loginHandler;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        registerLoginHandler(this);

        userDetailsDeserializer = new UserDetailsDeserializerGwtImpl();
        authenticationErrorDeserializer = new AuthenticationErrorDeserializerImpl();
        loginHandler = this;

        //        SubmitButton
        //        FormPanel form = new FormPanel();
        //        form.setAction("javascript:__gwt_login()");
        //        FlowPanel formPanel = new FlowPanel();
        //        form.add(formPanel);

        login.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                login(username.getText(), password.getText(), rememberMe.getValue());
            }
        });
        logout.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                logout();
            }
        });
        loginFacebook.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                loginFacebook(rememberMe.getValue());
            }
        });
        loginTwitter.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                loginTwitter(rememberMe.getValue());
            }
        });

//        formPanel.add(username);
//        formPanel.add(password);
//        formPanel.add(rememberMe);
//        formPanel.add(login);
//        RootPanel.get("slot1").add(form);

        RootPanel.get("slot1").add(message);
        RootPanel.get("slot1").add(username);
        RootPanel.get("slot1").add(password);
        RootPanel.get("slot1").add(rememberMe);
        RootPanel.get("slot1").add(login);
        RootPanel.get("slot1").add(loginFacebook);
        RootPanel.get("slot1").add(loginTwitter);
        RootPanel.get("slot1").add(logout);


        RootPanel.get("slot2").add(button);

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                SampleApplicationService.App.getInstance().getMessage("test", new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("[Failure] " + caught.getMessage());

                    }

                    @Override
                    public void onSuccess(String result) {
                        Window.alert("Success " + result);

                    }
                });
            }
        });

        checkAuthentication();
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
                    message.setText(NOT_LOGGED_IN);
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
        message.setText(user.getUsername());
    }

    public void handleLoginError(AuthenticationError error) {
        message.setText(error.toString());
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

    public native void registerLoginHandler(SampleApplication impl) /*-{

        $wnd.handleLoginSuccess = $entry(function (user) {
            impl.@com.imhos.security.client.module.SampleApplication::handleLoginSuccess(Ljava/lang/String;)(user);
        });
        $wnd.handleLoginError = $entry(function (user) {
            impl.@com.imhos.security.client.module.SampleApplication::handleLoginError(Ljava/lang/String;)(user);
        });
    }-*/;
}
