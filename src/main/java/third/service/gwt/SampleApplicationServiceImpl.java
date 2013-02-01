package third.service.gwt;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import third.client.module.SampleApplicationService;


public class SampleApplicationServiceImpl extends RemoteServiceServlet implements SampleApplicationService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}