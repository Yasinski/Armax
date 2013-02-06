package third.client.module;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class SampleApplication implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button button = new Button("Click me");
        final TextBox username=new TextBox();
        final PasswordTextBox password=new PasswordTextBox();


        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                login(username.getText(), password.getText());
            }
        });

        RootPanel.get("slot1").add(username);
        RootPanel.get("slot1").add(password);
        RootPanel.get("slot1").add(button);
    }

    void login(String login,String pass) {
        RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/j_spring_security_check");
        rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
        rb.setRequestData("j_username=" + URL.encode(login + "&j_password=" + URL.encode(pass)));

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
}
