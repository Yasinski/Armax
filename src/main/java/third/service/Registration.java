package third.service;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

import com.imhos.security.server.model.User;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.dao.UserDAO;
import third.model.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class Registration implements Controller {

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Set<Role> authorities = new HashSet<Role>();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String authority = request.getParameter("authority");
        boolean enabled = true;
        authorities.add(Role.ROLE_USER);

        if (username.equals("admin")) {
            authorities.add(Role.ROLE_ADMIN);
        }

        User user = new User(username, password, authorities, enabled);
        try {
            userDAO.saveUser(user);
            return new ModelAndView("/jsp/login.jsp");
        } catch (ConstraintViolationException e) {
            return new ModelAndView("/jsp/error_duplication.jsp");
        }
    }
}

