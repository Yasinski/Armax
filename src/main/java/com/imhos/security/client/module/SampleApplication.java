package com.imhos.security.client.module;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.imhos.security.shared.model.User;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class SampleApplication implements EntryPoint {

    public static final String NOT_LOGGED_IN = "Not logged in";
    private Label message = new Label(NOT_LOGGED_IN);
    private Button login = new Button("Login");
    private Button loginFacebook = new Button("Sign in with Facebook");
    private Button logout = new Button("Logout");
    private Button button = new Button("Button");
    private TextBox username = new TextBox();
    private PasswordTextBox password = new PasswordTextBox();
    private CheckBox rememberMe = new CheckBox("rememberMe");

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        registerLoginHandler(this);

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
        loginFacebook.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                loginFacebook(rememberMe.getValue());
            }
        });

        RootPanel.get("slot1").add(message);
        RootPanel.get("slot1").add(username);
        RootPanel.get("slot1").add(password);
        RootPanel.get("slot1").add(rememberMe);
        RootPanel.get("slot1").add(login);
        RootPanel.get("slot1").add(loginFacebook);
        RootPanel.get("slot1").add(logout);


        RootPanel.get("slot2").add(button);
    }

    private void loginFacebook(boolean rememberMe) {
        Window.open("/facebookconnect/?rememberMe=" + rememberMe, "Facebook Login", "width=500,height=300");
    }

    private void login(String login, String pass, boolean rememberMe) {

        RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/j_spring_security_check");
        rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
        rb.setRequestData("j_username=" + URL.encode(login) + "&j_password=" + URL.encode(pass)
                + "&_spring_security_remember_me=" + URL.encode(rememberMe + "")
        );

        rb.setCallback(new RequestCallback() {
            public void onError(Request request, Throwable exception) {
                Window.alert("Error");
            }

            public void onResponseReceived(Request request, Response response) {
                if (response.getStatusCode() == 200) {
                    String json = response.getText();
//                    JSONValue value = JSONParser.parseLenient(json);
                    handleLogin(deserialize(json));

                    Window.alert("Success "
                            // + user.getUsername()
                    );
                } else {
                    Window.alert(response.getStatusCode() + "," + response.getStatusText());
                }
            }
        });

        try {
            rb.send();
        } catch (RequestException re) {
            re.printStackTrace();
        }
    }

    private User deserialize(String json) {
        JSONValue parsed = JSONParser.parseStrict(json);
        JSONObject jsonObj = parsed.isObject();
        User user = new User();
        if (jsonObj != null) {
            user.setUsername(jsonObj.get(User.USERNAME_FIELD).toString());
            JSONArray array = jsonObj.get(User.AUTHORITIES_FIELD).isArray();
            for (int i = 0; i < array.size(); i++) {
                JSONValue obj = array.get(i);
                user.getAuthorities().add(obj.toString());
            }
        }
        return user;
    }

    void logout() {
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/logout");
        rb.setHeader("Content-Type", "application/x-www-form-urlencoded");

        rb.setCallback(new RequestCallback() {
            public void onError(Request request, Throwable exception) {
                Window.alert("[logout] Error");
            }

            public void onResponseReceived(Request request, Response response) {
                if (response.getStatusCode() == 200) {
                    message.setText(NOT_LOGGED_IN);
                    Window.alert("[logout] Success");
                } else {
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


    public void handleLogin(String  user) {
        handleLogin(deserialize(user));
    }
    public void handleLogin(User user) {
        message.setText(user.getUsername());
    }

    public native void registerLoginHandler(SampleApplication impl) /*-{

        $wnd.handleLogin = $entry(function (user) {
            impl.@com.imhos.security.client.module.SampleApplication::handleLogin(Ljava/lang/String;)(user);
        });
    }-*/;
}
