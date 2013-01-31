package third.service;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.DBDirectorQueryer;
import third.model.Director;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class ActorDirectorsList implements Controller {
	private DBDirectorQueryer dbDirectorQueryer;

	public void setDbDirectorQueryer(DBDirectorQueryer dbDirectorQueryer) {
		this.dbDirectorQueryer = dbDirectorQueryer;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		Integer id = Integer.parseInt(request.getParameter("Id"));
		List<Director> directors = dbDirectorQueryer.findThisActorDirs(id);
		if (directors != null && !directors.isEmpty()) {
			request.setAttribute("directors", directors);
			return new ModelAndView("/directors_info.jsp");
		} else {
			return new ModelAndView("/jsp/error.jsp");
		}
	}
}