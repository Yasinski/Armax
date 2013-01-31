package third.DAO;

import third.model.Users;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public interface UserDAO {

	public void saveUser(Users user);

	public List<Users> getAllUsers();

	public Users getUserByLogin(String username);

	public void deleteUser(String username);
}

