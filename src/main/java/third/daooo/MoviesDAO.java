package third.daooo;

import third.model.Movie;

import java.sql.SQLException;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public interface MoviesDAO {

    public Movie getMovie(Integer movieId);

    public void saveMovie(Movie movie);

    public void updateMovie(Movie movie) throws SQLException;

    public void deleteMovie(Movie movie) throws SQLException;

    public List<Movie> findAll();

    public List<Movie> findWithoutAnyActor();

    public List<Movie> findWithThisActor(Integer id);

    public List<Movie> findAll_dirName();

    public List<Movie> findAll_dirName1();

    public List<Movie> findAll_dirName3();

    public List<Movie> searchByTitle(String searchString, Integer firstOnPage, Integer rowsOnPage);

    public Integer getCountOfFound(String searchString);

    public List<Movie> findLimit(Integer firstOnPage, Integer rowsOnPage);

    public Integer findCountOfMovies();

    public List<Movie> findWithThisDirector(Integer directorId);

}
