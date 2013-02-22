package third.DAO.Impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import third.DAO.DirectorsDAO;
import third.model.Director;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class DirectorsDAOImpl extends HibernateDaoSupport implements DirectorsDAO {

    private DirectorsDAO directorsDAO;

    @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        HibernateTemplate result = super.createHibernateTemplate(sessionFactory);
        result.setAllowCreate(false);
        return result;
    }

    public void setDirectorsDAO(DirectorsDAO directorsDAO) {
        this.directorsDAO = directorsDAO;
    }

    public void saveDirector(Director director) {
        getSession().save(director);
    }

    public void updateDirector(Integer id, Director director) {
        getSession().update(director);
    }

    public void deleteDirector(Director director) {
        getSession().delete(director);
    }

    public Director getParticularDirector(Director director) {
        Director particDir = (Director) getSession().createCriteria(Director.class)
                .add(Restrictions.eq("firstName", director.getFirstName()).ignoreCase())
                .add(Restrictions.eq("lastName", director.getLastName()).ignoreCase())
                .uniqueResult();
        return particDir;
    }

    public Director findWithThisMovie(Integer movieId) {
        Director director = (Director) getSession().createCriteria(Director.class)
                .createCriteria("hisMovies")
                .add(Restrictions.eq("id", movieId))
                .uniqueResult();
        return director;
    }

    public List<Director> findThisActorDirs(Integer actorId) {
        Query q = getSession().createQuery("select distinct d from Director d join d.hisMovies m join m.actors a where a.id =:actorId ")
                .setParameter("actorId", actorId);
        List<Director> directors = (List<Director>) q.list();
        return directors;
    }
}
