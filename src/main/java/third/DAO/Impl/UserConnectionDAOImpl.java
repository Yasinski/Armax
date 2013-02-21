package third.DAO.Impl;

import com.imhos.security.server.model.UserConnection;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.MultiValueMap;
import third.DAO.UserConnectionDAO;

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
        return (Set<String>) getSession().createCriteria(UserConnection.class)
                .setProjection(Projections.property("userId"))
                .add(Restrictions.eq("providerId", providerId))
                .add(Restrictions.in("providerUserId", providerUserIds))
                .list();
    }

    @Override
    public List<UserConnection> getPrimary(String userId, String providerId) {
        return (List<UserConnection>) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("providerId", providerId))
                .add(Restrictions.eq("rank", 1))
                .list();
    }

    @Override
    public int getRank(String userId, String providerId) {
        //     todo: reimplement to hibernate api
        String result = getSession().createCriteria(UserConnection.class)
                .setProjection(Projections.sqlProjection("coalesce(max(rank) + 1, 1)", new String[]{"rank"},
                                                         new Type[]{Hibernate.STRING}))
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("providerId", providerId))
                .uniqueResult().toString();
        return Integer.valueOf(result);
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
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.sqlRestriction(providerUsersCriteriaSql.toString()))
                .addOrder(Order.asc("providerId"))
                .addOrder(Order.asc("rank"))
                .list();
    }

    @Override
    public List<UserConnection> getAll(String userId) {
        return (List<UserConnection>) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("userId", userId))
                .addOrder(Order.asc("providerId"))
                .addOrder(Order.asc("rank"))
                .list();
    }

    @Override
    public List<UserConnection> getAll(String userId, String providerId) {
        return (List<UserConnection>) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("providerId", providerId))
                .addOrder(Order.asc("rank"))
                .list();
    }

    @Override
    public UserConnection get(String userId, String providerId, String providerUserId) {
        return (UserConnection) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("providerId", providerId))
                .add(Restrictions.eq("providerUserId", providerUserId))
                .uniqueResult();
    }

    @Override
    public List<UserConnection> get(String providerId, String providerUserId)
            throws IncorrectResultSizeDataAccessException {
        return (List<UserConnection>) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("providerId", providerId))
                .add(Restrictions.eq("providerUserId", providerUserId))
                .list();
    }

    @Override
    public void remove(String userId, String providerId) {
        UserConnection userConnection = (UserConnection) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("providerId", providerId))
                .uniqueResult();
        getSession().delete(userConnection);
    }

    @Override
    public void remove(String userId, String providerId, String providerUserId) {
        UserConnection userConnection = (UserConnection) getSession().createCriteria(UserConnection.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("providerId", providerId))
                .add(Restrictions.eq("providerUserId", providerUserId))
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
}
