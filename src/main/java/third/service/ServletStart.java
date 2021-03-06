package third.service;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import third.dao.UserDAO;
import third.facade.DBMovieQueryer;
import third.facade.Pagination;
import third.model.Movie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ServletStart implements Controller {
    private DBMovieQueryer dbMovieQueryer;
    private Pagination pagination;
    private Md5PasswordEncoder passwordEncoder;
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setDbMovieQueryer(DBMovieQueryer dbMovieQueryer) {
        this.dbMovieQueryer = dbMovieQueryer;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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
//        User user1 = new User("user1", "user1@gmail.com", "user1@gmail.com", passwordEncoder.encodePassword("1111", "user1@gmail.com"), authorities, true);
//        User user3 = new User("user3", "user3@gmail.com", "user3@gmail.com", passwordEncoder.encodePassword("3333", "user3@gmail.com"), authorities, true);
//        User admin = new User("admin", "admin@gmail.com", "admin@gmail.com", passwordEncoder.encodePassword("admin", "admin@gmail.com"), authorities2, true);
//
//        userDAO.saveUser(user1);
//        userDAO.saveUser(user3);
//        userDAO.saveUser(admin);
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

