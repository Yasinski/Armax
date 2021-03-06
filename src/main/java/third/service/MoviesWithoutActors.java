package third.service;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.DBMovieQueryer;
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

public class MoviesWithoutActors  implements Controller {
	private DBMovieQueryer dbMovieQueryer;

	public void setDbMovieQueryer(DBMovieQueryer dbMovieQueryer) {
		this.dbMovieQueryer = dbMovieQueryer;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<Movie> movies = dbMovieQueryer.findWithoutAnyActor();
		request.setAttribute("movies", movies);
		return new ModelAndView("/movies_info.jsp");
	}
}
