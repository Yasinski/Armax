package third.daooo.impl;

import com.imhos.security.server.model.UserConnection;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.MultiValueMap;
import third.daooo.UserConnectionDAO;
import third.model.User;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 20.02.13
 * Time: 9:05
 * To change this template use File | Settings | File Templates.
 */
public class UserConnectionDAOImpl implements UserConnectionDAO {

    private UserConnectionDAO userConnectionDAO;

    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setUserConnectionDAO(UserConnectionDAO userConnectionDAO) {
        this.userConnectionDAO = userConnectionDAO;
    }


    @Override
    public Set<String> findUsersConnectedTo(String providerId, Set<String> providerUserIds) {
        return (Set<String>) getSession().createCriteria(User.class)
                .setProjection(Projections.property("id"))
                .createCriteria("userConnections")
                .add(Restrictions.eq("providerId", providerId))
                .add(Restrictions.in("providerUserId", providerUserIds))
                .list();
    }

    @Override
    public List<UserConnection> getPrimary(String userId, String providerId) {
        return (List<UserConnection>) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("providerId", providerId))
                .add(Restrictions.eq("rank", 1))
                .createCriteria("user")
                .add(Restrictions.eq("id", userId))
                .list();
    }

    @Override
    public Integer getMaxRank(String userId, String providerId) {
        //     todo: reimplement to hibernate api
        //     todo: separate coalesce business logic from daooo layer
        Integer result = (Integer) getSession().createCriteria(UserConnection.class)
                .setProjection(Projections.max("rank"))
                .add(Restrictions.eq("providerId", providerId))
                .createCriteria("user")
                .add(Restrictions.eq("id", userId))
                .uniqueResult();
        return result;
    }

    @Override
    public List<UserConnection> getAll(String userId, MultiValueMap<String, String> providerUsers) {

        StringBuilder providerUsersCriteriaSql = new StringBuilder();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);
//     todo: reimplement to hibernate api
        for (Iterator<Map.Entry<String, List<String>>> it = providerUsers.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, List<String>> entry = it.next();
            String providerId = entry.getKey();
            providerUsersCriteriaSql.append("providerId = :providerId_").append(providerId)
                    .append(" and providerUserId in (:providerUserIds_").append(providerId).append(")");
            parameters.addValue("providerId_" + providerId, providerId);
            parameters.addValue("providerUserIds_" + providerId, entry.getValue());
            if (it.hasNext()) {
                providerUsersCriteriaSql.append(" or ");
            }

        }
        return (List<UserConnection>) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.sqlRestriction(providerUsersCriteriaSql.toString()))
                .addOrder(Order.asc("providerId"))
                .createCriteria("user")
                .add(Restrictions.eq("id", userId))
                .addOrder(Order.asc("rank"))
                .list();
    }

    @Override
    public List<UserConnection> getAll(String userId) {
        return (List<UserConnection>) getSession().createCriteria(UserConnection.class)
                .addOrder(Order.asc("providerId"))
                .createCriteria("user")
                .add(Restrictions.eq("id", userId))
                .addOrder(Order.asc("rank"))
                .list();
    }

    @Override
    public List<UserConnection> getAll(String userId, String providerId) {
        return (List<UserConnection>) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("providerId", providerId))
                .createCriteria("user")
                .add(Restrictions.eq("id", userId))
                .addOrder(Order.asc("rank"))
                .list();
    }

    @Override
    public UserConnection get(String userId, String providerId, String providerUserId) {
        Criteria criteria = getSession().createCriteria(UserConnection.class);
        criteria.add(Restrictions.eq("providerId", providerId))
                .add(Restrictions.eq("providerUserId", providerUserId))
                .createCriteria("user")
                .add(Restrictions.eq("id", userId));
        return (UserConnection) criteria.uniqueResult();

    }

    @Override
    public UserConnection get(String providerId, String providerUserId)
            throws IncorrectResultSizeDataAccessException {
        UserConnection userConnection = (UserConnection) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("providerId", providerId))
                .add(Restrictions.eq("providerUserId", providerUserId))
                .uniqueResult();
        return userConnection;
    }

    public UserConnection getByEmail(String email) {
        return (UserConnection) getSession().createCriteria(UserConnection.class)
                .createCriteria("user")
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    public UserConnection getByUserName(String username) {
        return (UserConnection) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("username", username))
                .uniqueResult();
    }


    @Override
    public void remove(String userId, String providerId) {
        UserConnection userConnection = (UserConnection) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("providerId", providerId))
                .createCriteria("user")
                .add(Restrictions.eq("id", userId))
                .uniqueResult();
        getSession().delete(userConnection);
    }

    @Override
    public void remove(String userId, String providerId, String providerUserId) {
        UserConnection userConnection = (UserConnection) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("providerId", providerId))
                .add(Restrictions.eq("providerUserId", providerUserId))
                .createCriteria("user")
                .add(Restrictions.eq("id", userId))
                .uniqueResult();
        getSession().delete(userConnection);
    }

    @Override
    public void save(UserConnection userConnection) {
        getSession().save(userConnection);
    }

    @Override
    public void update(UserConnection userConnection) {
        getSession().update(userConnection);
    }

    public void delete(UserConnection userConnection) {
        getSession().delete(userConnection);
    }

}
