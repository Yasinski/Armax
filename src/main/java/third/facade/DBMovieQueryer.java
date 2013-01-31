package third.facade;

import third.DAO.MoviesDAO;
import third.model.Movie;

import java.sql.SQLException;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class DBMovieQueryer {
	private MoviesDAO moviesDAO;

	public void setMoviesDAO(MoviesDAO moviesDAO) {
		this.moviesDAO = moviesDAO;
	}

	// MOVIES METHODS

	public Movie getMovie(Integer movieId){
		return moviesDAO.getMovie(movieId);
	}

	public void saveMovie(Movie movie) throws SQLException {
		moviesDAO.saveMovie(movie);
	}

	public void updateMovie(Movie movie) throws SQLException {
		moviesDAO.updateMovie(movie);
	}

	public void deleteMovie(Movie movie) throws SQLException {
		moviesDAO.deleteMovie(movie);
	}

	public List<Movie> findAll() {
		return moviesDAO.findAll();
	}

	public List<Movie> findWithoutAnyActor() {
		return moviesDAO.findWithoutAnyActor();
	}

	public List<Movie> findWithThisActor(Integer id) {
		return moviesDAO.findWithThisActor(id);
	}

	public List<Movie> findAll_dirName() {
		return moviesDAO.findAll_dirName();
	}

	public List<Movie> findAll_dirName1() {
		return moviesDAO.findAll_dirName1();
	}

	public List<Movie> findAll_dirName3() {
		return moviesDAO.findAll_dirName3();
	}

	public List<Movie> searchByTitle(String searchString, Integer firstOnPage, Integer rowsOnPage) {
		return moviesDAO.searchByTitle(searchString, firstOnPage, rowsOnPage);
	}

	public Integer getCountOfFound(String searchString) {
		return moviesDAO.getCountOfFound(searchString);
	}

	public List<Movie> findLimit(Integer firstOnPage, Integer rowsOnPage) {
		return moviesDAO.findLimit(firstOnPage, rowsOnPage);
	}

	public Integer findCountOfMovies() {
		return moviesDAO.findCountOfMovies();
	}

	public List<Movie> findWithThisDirector(Integer directorId) {
		return moviesDAO.findWithThisDirector(directorId);
	}
}
