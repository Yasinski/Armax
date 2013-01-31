package third.service;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.DBActorQueryer;
import third.model.Actor;

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

public class DirectorActorsList implements Controller {
	private DBActorQueryer dbActorQueryer;

	public void setDbActorQueryer(DBActorQueryer dbActorQueryer) {
		this.dbActorQueryer = dbActorQueryer;
	}
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		Integer id = Integer.parseInt(request.getParameter("Id"));
		List<Actor> actors = dbActorQueryer.findThisDirectorActors(id);
		if (actors != null && !actors.isEmpty()) {
			request.setAttribute("actors", actors);
			return new ModelAndView("/actors_info.jsp");
		} else {
			return new ModelAndView("/jsp/error.jsp");
		}
	}
}

