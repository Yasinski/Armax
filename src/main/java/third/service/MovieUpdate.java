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

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class MovieUpdate implements Controller {
	private MoviesUpdater moviesUpdater;

	public void setMoviesUpdater(MoviesUpdater moviesUpdater) {
		this.moviesUpdater = moviesUpdater;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String strId = request.getParameter("Id");
		Integer id = Integer.parseInt(strId);
		String genre = request.getParameter("genre");
		String dirFName = request.getParameter("dirFName");
		String dirLName = request.getParameter("dirLName");
		String actFirstName = request.getParameter("actFirstName");
		String actLastName = request.getParameter("actLastName");

		Director director = new Director(dirFName, dirLName);
		Actor actor = new Actor(actFirstName, actLastName);

		try {
			moviesUpdater.updateWholeMovie(id, genre, director, actor);
			return new ModelAndView("/start/");
		} catch (HumanExistsException e) {
			request.setAttribute("error", "Данные некорректны! Заполните форму еще раз:");
			return new ModelAndView("/update_movie_form.jsp");
		}
	}
}