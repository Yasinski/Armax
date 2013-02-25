package third.facade;

import third.dao.DirectorsDAO;
import third.model.Director;

import java.sql.SQLException;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class DBDirectorQueryer {

    private DirectorsDAO directorsDAO;

    public void setDirectorsDAO(DirectorsDAO directorsDAO) {
        this.directorsDAO = directorsDAO;
    }

    //DIRECTORS METHODS

    public void saveDirector(Director director) throws SQLException {
        directorsDAO.saveDirector(director);
    }

    public void updateDirector(Integer id, Director director) throws SQLException {
        directorsDAO.updateDirector(id, director);
    }

    public void deleteDirector(Director director) throws SQLException {
        directorsDAO.deleteDirector(director);
    }

    public Director getParticularDirector(Director director) throws SQLException {
        return directorsDAO.getParticularDirector(director);
    }

    public Director findWithThisMovie(Integer id) {
        return directorsDAO.findWithThisMovie(id);
    }

    public List<Director> findThisActorDirs(Integer id) {
        return directorsDAO.findThisActorDirs(id);
    }


}
