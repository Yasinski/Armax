package third.service;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.facade.DBMovieQueryer;
import third.facade.DBUserQueryer;
import third.facade.Pagination;
import third.model.Movie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ServletStart implements Controller {
	private DBMovieQueryer dbMovieQueryer;
	private DBUserQueryer dbUserQueryer;
	private Pagination pagination;

	public void setDbMovieQueryer(DBMovieQueryer dbMovieQueryer) {
		this.dbMovieQueryer = dbMovieQueryer;
	}

	public void setDbUserQueryer(DBUserQueryer dbUserQueryer) {
		this.dbUserQueryer = dbUserQueryer;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Integer currentPage = 1;
		Integer rowsOnPage = 10;
		if (request.getParameter("nextPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("nextPage"));  //номер страницы, для которой идет выборка из базы.
		}                                                                    //на jsp это будет номер текущей страницы
		if (request.getParameter("rowsOnPage") != null) {
			rowsOnPage = Integer.parseInt(request.getParameter("rowsOnPage"));
		}
		Integer firstOnPage = pagination.countFirstOnPage(currentPage, rowsOnPage);
		List<Movie> movies;
		Integer overallCount;
		String searchStr = "";

		if (request.getParameter("searchStr") != null) {
			searchStr = request.getParameter("searchStr");
			movies = dbMovieQueryer.searchByTitle(searchStr, firstOnPage, rowsOnPage);
			overallCount = dbMovieQueryer.getCountOfFound(searchStr);
		} else {
			movies = dbMovieQueryer.findLimit(firstOnPage, rowsOnPage);
			overallCount = dbMovieQueryer.findCountOfMovies();
		}
		Integer numberOfPages = pagination.countNumberOfPages(overallCount, rowsOnPage);
		if (movies != null && !movies.isEmpty()) {

//			//временно. засетаем юзеров в базу)
//			Set<String> authotities = new HashSet<String>();
//			authotities.add("ROLE_USER");   // ROLE_ADMIN
//
//			Users user1 = new Users("user1", "1111", authotities, true);
//			Users user3 = new Users("user3", "3333", authotities, true);
//			Users user4 = new Users("user4", "4444", authotities, false);
////			Users admin = new Users("admin", "admin", authotities, true);
//
//			dbUserQueryer.saveUser(user1);
//			dbUserQueryer.saveUser(user3);
//			dbUserQueryer.saveUser(user4);
////			dbUserQueryer.saveUser(admin);
			//

			request.setAttribute("movies", movies);
			request.setAttribute("searchStr", searchStr);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("numberOfPages", numberOfPages);
			return new ModelAndView("/movies_info.jsp");
		} else {
			return new ModelAndView("/jsp/error.jsp");
		}
	}
}

