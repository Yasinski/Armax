package com.imhos.security.client.module;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.imhos.security.client.module.view.SampleApplicationViewImpl;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="">Max Yasinski</a>
 * @updated 25.01.13 10:41
 */
public class Application implements EntryPoint {
    private Button button = new Button("Button");

    @Override
    public void onModuleLoad() {
        LoginEditor loginEditor = new LoginEditor(new SampleApplicationViewImpl());
        RootPanel.get().add(button);
        RootPanel.get().add(loginEditor.asWidget());
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                SampleApplicationService.App.getInstance().getMessage("test", new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("[Failure] " + caught.getClass() + " " + caught.getMessage());

                    }

                    @Override
                    public void onSuccess(String result) {
                        Window.alert("Success " + result);

                    }
                });
            }
        });
    }
}
