package third.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.DBActorQueryer;
import third.facade.HumanExistsException;
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

public class ActorAdd implements Controller {
	private DBActorQueryer dbActorQueryer;

	public void setDbActorQueryer(DBActorQueryer dbActorQueryer) {
		this.dbActorQueryer = dbActorQueryer;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");

		Actor actor = new Actor(firstName, lastName);
		try {
			dbActorQueryer.saveActor(actor);
			List<Actor> actors = dbActorQueryer.findAll();
			request.setAttribute("actors", actors);
			return new ModelAndView("/actors_info.jsp");
		} catch (ConstraintViolationException e) {
			return new ModelAndView("/jsp/error_duplication.jsp");
		} catch (HumanExistsException e) {
			e.printStackTrace();
			return new ModelAndView("/jsp/error_duplication.jsp");
		}
	}
}
