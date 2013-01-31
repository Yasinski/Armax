package third.facade;

import org.hibernate.exception.ConstraintViolationException;
import third.DAO.ActorsDAO;
import third.model.Actor;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class DBActorQueryer {
	private ActorsDAO actorsDAO;

	public void setActorsDAO(ActorsDAO actorsDAO) {
		this.actorsDAO = actorsDAO;
	}

	//ACTOR'S METHODS
	public void saveActor(Actor actor) throws ConstraintViolationException, HumanExistsException{
		try {
			actorsDAO.saveActor(actor);
		} catch (ConstraintViolationException e) {
			throw e;
		}
	}

	public void updateActor(Integer id, Actor actor) {
		actorsDAO.updateActor(id, actor);
	}

	public void deleteActor(Actor actor) {
		actorsDAO.deleteActor(actor);
	}

	public List<Actor> getActors() {
		return actorsDAO.getActors();
	}

	public Actor getParticularActor(Actor actor) {
		return actorsDAO.getParticularActor(actor);
	}

	public List<Actor> findAll() {
		return actorsDAO.findAll();
	}

	public List<Actor> findThisDirectorActors(Integer directorId) {
		return actorsDAO.findThisDirectorActors(directorId);
	}

	public List<Actor> findWithThisMovie(Integer movieId) {
		return actorsDAO.findWithThisMovie(movieId);
	}
}
