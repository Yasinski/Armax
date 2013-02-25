package third.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import third.dao.ActorsDAO;
import third.model.Actor;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class ActorsDAOImpl extends HibernateDaoSupport implements ActorsDAO {

    private ActorsDAO actorsDAO;

    @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        HibernateTemplate result = super.createHibernateTemplate(sessionFactory);
        result.setAllowCreate(false);
        return result;
    }

    public void setActorsDAO(ActorsDAO actorsDAO) {
        this.actorsDAO = actorsDAO;
    }

    public void saveActor(Actor actor) {
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		try {
//			session.beginTransaction();
        getSession().save(actor);
//			session.getTransaction().commit();
//		} catch (ConstraintViolationException e) {
//			session.close();
//			throw e;
//		}
    }

    public void updateActor(Integer id, Actor actor) {
        try {
            getSession().update(actor);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка при добавлении актера" + " " + e);
        }
    }

    public void deleteActor(Actor actor) {
        getSession().delete(actor);
    }

    public List<Actor> getActors() {
        List<Actor> actors = (List<Actor>) getSession().createCriteria(Actor.class)
                .list();
        return actors;
    }

    public Actor getParticularActor(Actor actor) {
        Actor particActor = (Actor) getSession().createCriteria(Actor.class)
                .add(Restrictions.eq("firstName", actor.getFirstName()).ignoreCase())
                .add(Restrictions.eq("lastName", actor.getLastName()).ignoreCase())
                .uniqueResult();
        return particActor;
    }

    public List<Actor> findAll() {
        List<Actor> actors = getSession().createCriteria(Actor.class).list();
        return actors;
    }

    public List<Actor> findThisDirectorActors(Integer directorId) {
        Query q = getSession().createQuery("select distinct a from Actor a join a.movies m join m.director d where d.id =:directorId ")
                .setParameter("directorId", directorId);
        List<Actor> actors = (List<Actor>) q.list();
        return actors;
    }

    public List<Actor> findWithThisMovie(Integer movieId) {
        List<Actor> actors = getSession().createCriteria(Actor.class)
                .createCriteria("movies")
                .add(Restrictions.eq("id", movieId))
                .list();
        return actors;
    }

}
