package third.service;

/**
* writeme: Should be the description of the class
*
* @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
*/

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.DBUserQueryer;
import third.model.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class Registration implements Controller {

	private DBUserQueryer dbUserQueryer;


	public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
		this.dbUserQueryer = dbUserQueryer;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Set<String> authorities = new HashSet<String>();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String authority = request.getParameter("authority");
		boolean enabled = true;
		authorities.add(authority);

		if(username.equals("admin")){
			authorities.add("ROLE_ADMIN");
		}

		Users user = new Users(username, password,authorities, enabled);
		try {
			dbUserQueryer.saveUser(user);
			return new ModelAndView("/jsp/login.jsp");
		} catch (ConstraintViolationException e) {
			return new ModelAndView("/jsp/error_duplication.jsp");
		}
	}
}

