package third.facade;

import com.moviejukebox.themoviedb.model.Genre;
import com.moviejukebox.themoviedb.model.MovieDb;
import org.hibernate.classic.Session;
import third.model.Movie;

import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class HibernateInfoSaver {

	String title;
	String releaseDate;
	Set<String> genres;

	public void _hibernateSaveInfo(MovieDb moviesSearchResult) {
		Movie movie = new Movie();
		for (Genre g : moviesSearchResult.getGenres()) {
			movie.getGenres().add(g.getName());
		}
		movie.setTitle(moviesSearchResult.getTitle());
		movie.setReleaseDate(moviesSearchResult.getReleaseDate());
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(movie);
		session.getTransaction().commit();
		session.close();
	}

	public Movie loadInfo(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Movie result = (Movie) session.load(Movie.class, id);
		session.getTransaction().commit();
		return result;
	}
}