package third.DAO;

import third.model.Director;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public interface DirectorsDAO {

	public void saveDirector(Director director)  ;

	public void updateDirector(Integer id, Director director)  ;

	public void deleteDirector(Director director)  ;

	public Director getParticularDirector(Director director)  ;

	public Director findWithThisMovie(Integer id);

	public List<Director> findThisActorDirs(Integer id);
}
