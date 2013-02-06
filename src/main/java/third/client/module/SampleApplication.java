package third.client.module;

import com.google.gwt.core.client.EntryPoint;
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
        final Label label = new Label();


        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (label.getText().equals("")) {
                    SampleApplicationService.App.getInstance().login("user1", "1111", new MyAsyncCallback(label));

                } else {
                    label.setText("");
                }
            }
        });

        // Assume that the host HTML has elements defined whose
        // IDs are "slot1", "slot2".  In a real app, you probably would not want
        // to hard-code IDs.  Instead, you could, for example, search for all
        // elements with a particular CSS class and replace them with widgets.
        //
        RootPanel.get("slot1").add(button);
        RootPanel.get("slot2").add(label);
    }

    private static class MyAsyncCallback implements AsyncCallback<Boolean> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(Boolean loginSuccess) {
            if (loginSuccess) {
                label.setText("Success");
            } else {
                label.setText("Denied");

            }
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
