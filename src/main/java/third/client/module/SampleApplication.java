package third.client.module;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class SampleApplication implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button login = new Button("Login");
        final Button logout = new Button("Logout");
        final Button button = new Button("Button");
        final TextBox username = new TextBox();
        final PasswordTextBox password = new PasswordTextBox();
        final CheckBox rememberMe = new CheckBox("rememberMe");

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

//        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
        RootPanel.get("slot1").add(username);
        RootPanel.get("slot1").add(password);
        RootPanel.get("slot1").add(rememberMe);
        RootPanel.get("slot1").add(login);
        RootPanel.get("slot1").add(logout);
//    }

        RootPanel.get("slot2").add(button);
    }

    private void login(String login, String pass, boolean rememberMe) {

//        RequestFactory
        RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/j_spring_security_check");
//        rb.setUser("Content-Type", "application/x-www-form-urlencoded");
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
                if (response.getStatusCode() == 200) {
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
}
