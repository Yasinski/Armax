package temp;

import third.model.Movie;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public interface MoviesDAO {

	Movie getMovie();

	Movie getMovie(String name);

	Movie getMovieByGenre(String genre);

	Movie getMovieByGenre(String genre, boolean sortByTitleAcs);

}
