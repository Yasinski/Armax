package third.client.module;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/sample")
public interface SampleApplicationService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    boolean login(String login, String password);

    /**
     * Utility/Convenience class.
     * Use SampleApplicationService.App.getInstance() to access static instance of MySampleApplicationServiceAsync
     */
    public static class App {
        private static SampleApplicationServiceAsync ourInstance = GWT.create(SampleApplicationService.class);

        public static synchronized SampleApplicationServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
