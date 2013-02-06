package third.service.gwt;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import third.client.module.SampleApplicationService;
import third.facade.DBUserQueryer;
import third.model.Users;
import third.service.gwt.auth.CustomUserAuthentication;


public class SampleApplicationServiceImpl extends RemoteServiceServlet implements SampleApplicationService {
    // Implementation of sample interface method
    private DBUserQueryer dbUserQueryer;

    public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
        this.dbUserQueryer = dbUserQueryer;
    }

    public String getMessage(String msg) {

        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    @Override
    public boolean login(String login, String password) {
        Users user = dbUserQueryer.getUserByLogin(login);
        if (user!= null) {
            String storedPass = user.getPassword();
            return storedPass.equals(password);
        }else {
            return false;
        }
    }

}