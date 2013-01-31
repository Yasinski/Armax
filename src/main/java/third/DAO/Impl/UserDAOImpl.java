package third.DAO.Impl;

import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import third.DAO.UserDAO;
import third.model.Users;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class UserDAOImpl extends HibernateDaoSupport implements UserDAO {

	private UserDAO userDAO;

	@Override
	protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
		HibernateTemplate result = super.createHibernateTemplate(sessionFactory);
		result.setAllowCreate(false);
		return result;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void saveUser(Users user) {
		getSession().save(user);
	}

	public List<Users> getAllUsers() {
		return getSession().createCriteria(Users.class)
				.list();
	}

	public Users getUserByLogin(String username) {
		Users users = (Users) getSession().createCriteria(Users.class)
				.add(Restrictions.eq("username", username))
				.setFetchMode("authorities", FetchMode.JOIN)
				.uniqueResult();
		return users;
		
	}

	public void deleteUser(String username) {
		Users user = getUserByLogin(username);
		if (user != null) {
			getSession().delete(user);
		}

	}

}
