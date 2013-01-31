package third.DAO;

import org.hibernate.exception.ConstraintViolationException;
import third.model.Actor;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public interface ActorsDAO {

	public void saveActor(Actor actor) throws ConstraintViolationException;

	public void updateActor(Integer id, Actor actor);

	public void deleteActor(Actor actor);

	public List<Actor> getActors();

	public Actor getParticularActor(Actor actor);

	public List<Actor> findAll();

	public List<Actor> findThisDirectorActors(Integer directorId);

	public List<Actor> findWithThisMovie(Integer movieId);
}
