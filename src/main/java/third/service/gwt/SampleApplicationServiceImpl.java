package third.service.gwt;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import third.client.module.SampleApplicationService;
import third.facade.DBUserQueryer;
import third.model.User;


public class SampleApplicationServiceImpl extends RemoteServiceServlet implements SampleApplicationService  {
    // Implementation of sample interface method
    private DBUserQueryer dbUserQueryer;

    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
        this.dbUserQueryer = dbUserQueryer;
    }

    public String getMessage(String msg) {
        User user = dbUserQueryer.getUserByLogin("admin");

        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!" +
                SecurityContextHolder.getContext().getAuthentication().getName()+"\"\n";
    }

    @Override
    public boolean login(String login, String password) {
        User user = dbUserQueryer.getUserByLogin(login);
        if (user!= null) {
            String storedPass = user.getPassword();
            return storedPass.equals(password);
        }else {
            throw new AccessDeniedException("");
        }
    }


}