package third.service;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.MoviesUpdater;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class MovieAddShort implements Controller {
	private MoviesUpdater moviesUpdater;

	public void setMoviesUpdater(MoviesUpdater moviesUpdater) {
		this.moviesUpdater = moviesUpdater;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
		String title = request.getParameter("title");

		if (moviesUpdater.addMovie_Title(title)) {
			return new ModelAndView("/start/");
		} else {
			request.setAttribute("error", "Данные некорректны! Заполните форму еще раз:");
			return new ModelAndView("/add_movie_short_form.jsp");
		}
	}
}
