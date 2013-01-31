package third.service;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.DBActorQueryer;
import third.facade.DBDirectorQueryer;
import third.facade.DBMovieQueryer;
import third.model.Actor;
import third.model.Director;
import third.model.Movie;

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

public class MoviePage implements Controller {
	private DBMovieQueryer dbMovieQueryer;
	private DBActorQueryer dbActorQueryer;
	private DBDirectorQueryer dbDirectorQueryer;

	public void setDbMovieQueryer(DBMovieQueryer dbMovieQueryer) {
		this.dbMovieQueryer = dbMovieQueryer;
	}

	public void setDbActorQueryer(DBActorQueryer dbActorQueryer) {
		this.dbActorQueryer = dbActorQueryer;
	}

	public void setDbDirectorQueryer(DBDirectorQueryer dbDirectorQueryer) {
		this.dbDirectorQueryer = dbDirectorQueryer;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


		String strId = request.getParameter("Id");
		Integer id = Integer.parseInt(strId);
		Movie movie = dbMovieQueryer.getMovie(id);
		Director director = dbDirectorQueryer.findWithThisMovie(id);
		List<Actor> actors = dbActorQueryer.findWithThisMovie(id);

		request.setAttribute("movie", movie);
		request.setAttribute("director", director);
		request.setAttribute("actors", actors);
		return new ModelAndView("/jsp/movie.jsp");
	}
}