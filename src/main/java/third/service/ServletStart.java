package third.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
    @Autowired
    private Md5PasswordEncoder passwordEncoder;

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
//        временно. засетаем юзеров в базу)
//        Set<Role> authorities = new HashSet<Role>();
//        authorities.add(Role.ROLE_USER);
//        Set<Role> authorities2 = new HashSet<Role>();
//        authorities2.add(Role.ROLE_USER);
//        authorities2.add(Role.ROLE_ADMIN);
//
//        User user1 = new User("user1@gmail.com", passwordEncoder.encodePassword("1111", "user1@gmail.com"), authorities, true);
//        User user3 = new User("user2@gmail.com", passwordEncoder.encodePassword("2222", "user2@gmail.com"), authorities, true);
//        User user4 = new User("user4@gmail.com", passwordEncoder.encodePassword("4444", "user4@gmail.com"), authorities, false);
//        User admin = new User("admin@gmail.com", passwordEncoder.encodePassword("admin", "admin@gmail.com"), authorities2, true);
//
//        dbUserQueryer.saveUser(user1);
//        dbUserQueryer.saveUser(user3);
//        dbUserQueryer.saveUser(user4);
//        dbUserQueryer.saveUser(admin);
//
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

