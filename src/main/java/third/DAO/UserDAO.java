package third.DAO;

import third.model.User;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public interface UserDAO {

	public void saveUser(User user);

	public List<User> getAllUsers();

	public User getUserByLogin(String username);

    public User getUserByFacebookId(String facebookId);

	public void deleteUser(String username);
}

