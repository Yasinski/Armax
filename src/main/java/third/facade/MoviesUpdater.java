package third.facade;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import third.DAO.ActorsDAO;
import third.DAO.DirectorsDAO;
import third.DAO.MoviesDAO;
import third.model.Actor;
import third.model.Director;
import third.model.Movie;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;


public class MoviesUpdater {
	private ActorsDAO actorsDAO;
	private DirectorsDAO directorsDAO;
	private MoviesDAO moviesDAO;

	public void setActorsDAO(ActorsDAO actorsDAO) {
		this.actorsDAO = actorsDAO;
	}

	public void setDirectorsDAO(DirectorsDAO directorsDAO) {
		this.directorsDAO = directorsDAO;
	}

	public void setMoviesDAO(MoviesDAO moviesDAO) {
		this.moviesDAO = moviesDAO;
	}


	public Movie updateWholeMovie(Integer id, String genre, Director director, Actor actor) throws ConstraintViolationException, HumanExistsException {
		try {
			actorsDAO.saveActor(actor);
		} catch (ConstraintViolationException e) {
			throw new HumanExistsException(e);
		}
		try {
			directorsDAO.saveDirector(director);
		} catch (ConstraintViolationException e) {
			throw new HumanExistsException(e);
		}
		try {
			Movie movie = moviesDAO.getMovie(id);
			Set<String> genresList = movie.getGenres();
			genresList.add(genre);
			movie.setGenres(genresList);
			Set<Actor> actorsList = movie.getActors();
			actorsList.add(actor);
			movie.setActors(actorsList);
			movie.setDirector(director);
			return movie;
		} catch (Exception e) {
			System.err.println("Ошибка при обновлении" + " " + e);
			throw new RuntimeException(e);
		}
	}

	public Movie addWholeMovie(String title, Set<String> genres, Director director, Actor actor) throws HumanExistsException {
		Movie movie = new Movie();
		Set<Actor> actors = new HashSet<Actor>();
		try {
			actorsDAO.saveActor(actor);
		} catch (ConstraintViolationException e) {
			throw new HumanExistsException(e);
		}
		Director particDir = directorsDAO.getParticularDirector(director);				   //  проверим, есть ли в БД такой режиссер.
		if (particDir != null) {  // значит, в БД такой режиссер существует
			director = particDir;
		} else {
			directorsDAO.saveDirector(director);
		}
		actors.add(actor);
		movie.setTitle(title);
		movie.setGenres(genres);
		movie.setActors(actors);
		movie.setDirector(director);
		Session session = null;
		try {
			moviesDAO.saveMovie(movie);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Ошибка при вставке" + " " + e);
			session.getTransaction().rollback();
		}
		return movie;
	}

	public boolean addMovie_Title(String title) throws SQLException {
		boolean successfulAdd = false;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Movie movie = new Movie();
			movie.setTitle(title);
			moviesDAO.saveMovie(movie);
			successfulAdd = true;
		} catch (Exception e) {
			System.err.println("Ошибка при вставке" + " " + e);
			session.getTransaction().rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return successfulAdd;
	}
}