package third.service.gwt;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.imhos.security.client.module.SampleApplicationService;
import com.imhos.security.server.model.User;
import com.imhos.security.shared.model.GWTException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import third.dao.UserDAO;


public class SampleApplicationServiceImpl extends RemoteServiceServlet implements SampleApplicationService {
    // Implementation of sample interface method
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String getMessage(String msg) throws GWTException {
        userDAO.getAllUsers();
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!" +
                SecurityContextHolder.getContext().getAuthentication().getName() + "\"\n";
    }

    @Override
    public boolean login(String login, String password) {
        User user = userDAO.getUserByLogin(login);
        if (user != null) {
            String storedPass = user.getPassword();
            return storedPass.equals(password);
        } else {
            throw new AccessDeniedException("");
        }
    }


}