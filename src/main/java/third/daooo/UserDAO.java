package third.daooo;

import third.model.User;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public interface UserDAO {

    public void saveUser(User user);

    public void update(User user);

    public List<User> getAllUsers();

    public User getUserByLogin(String username);

    public User getUserByEmail(String email);

    public User getUserByFacebookId(String facebookId);

    public User getUserByTwitterId(String twitterId);

    public void deleteUser(String username);

    public String getPassword(int userId);

    User getUserById(String userId);
}

