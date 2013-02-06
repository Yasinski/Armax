package third.client.module;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SampleApplicationServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);

	void login(String login, String password, AsyncCallback<Boolean> async);
}
