package third.dao.impl;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import third.dao.UserDAO;
import third.model.User;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class UserDAOImpl implements UserDAO {

    private UserDAO userDAO;
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void saveUser(User user) {
        getSession().save(user);
    }

    public void updateUser(User user) {
        getSession().update(user);
    }

    public List<User> getAllUsers() {
        return getSession().createCriteria(User.class)
                .list();
    }

    public User getUserByLogin(String username) {
        User user = (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("username", username))
                .setFetchMode("authorities", FetchMode.JOIN)
                .uniqueResult();
        return user;

    }

    public User getUserByFacebookId(String facebookId) {
        return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("facebookId", facebookId))
                .setFetchMode("authorities", FetchMode.JOIN)
                .uniqueResult();

    }

    public User getUserByTwitterId(String twitterId) {
        return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("twitterId", twitterId))
                .setFetchMode("authorities", FetchMode.JOIN)
                .uniqueResult();
    }


    public void deleteUser(String username) {
        User user = getUserByLogin(username);
        if(user != null) {
            getSession().delete(user);
        }

    }

    public String getPassword(int userId) {
        return (String) getSession().createCriteria(User.class)
                .setProjection(Projections.property("password"))
                .add(Restrictions.eq("id", userId))
                .uniqueResult();
    }

    @Override
    public User getUserById(String userId) {
        // todo: implement method asap
        return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("id", userId))
                .uniqueResult();

//        User user = (User)sessionFactory.getCurrentSession().createCriteria(User.class)
//                .createCriteria("userConnections")
//                .add(Restrictions.eq("id", userId))
//                .uniqueResult();
//         return user;
    }


}
