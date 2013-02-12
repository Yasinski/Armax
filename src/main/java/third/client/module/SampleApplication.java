package third.client.module;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

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
                if(response.getStatusCode() == 200) {
                    //                    message.setText(user);
                    Window.alert("Success");
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

    void logout() {
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/logout");
        rb.setHeader("Content-Type", "application/x-www-form-urlencoded");

        rb.setCallback(new RequestCallback() {
            public void onError(Request request, Throwable exception) {
                Window.alert("[logout] Error");
            }

            public void onResponseReceived(Request request, Response response) {
                if(response.getStatusCode() == 200) {
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


    public void handleLogin(String user) {
        message.setText(user);
    }

    public native void registerLoginHandler(SampleApplication impl) /*-{

        $wnd.handleLogin = $entry(function (user) {
            impl.@third.client.module.SampleApplication::handleLogin(Ljava/lang/String;)(user);
        });
    }-*/;
}
