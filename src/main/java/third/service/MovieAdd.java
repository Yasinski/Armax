package third.service;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.HumanExistsException;
import third.facade.MoviesUpdater;
import third.model.Actor;
import third.model.Director;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class MovieAdd  implements Controller {
	private MoviesUpdater moviesUpdater;

	public void setMoviesUpdater(MoviesUpdater moviesUpdater) {
		this.moviesUpdater = moviesUpdater;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String title = request.getParameter("title");
		String genre = request.getParameter("genre");
		String dirFName = request.getParameter("dirFName");
		String dirLName = request.getParameter("dirLName");
		String actFirstName = request.getParameter("actFirstName");
		String actLastName = request.getParameter("actLastName");

		Director director = new Director(dirFName, dirLName);
		Actor actor = new Actor(actFirstName, actLastName);
		Set<String> genres = new HashSet<String>();
		genres.add(genre);

		try {
			moviesUpdater.addWholeMovie(title, genres, director, actor);
			return new ModelAndView("/start/");
		} catch (HumanExistsException e) {
			request.setAttribute("error", "Данные некорректны! Заполните форму еще раз:");
			return new ModelAndView("/add_movie_form.jsp");
		}
	}
}