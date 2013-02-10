package third.facade;

import third.DAO.UserDAO;
import third.model.User;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class DBUserQueryer {

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    //    @Secured(Role.ROLE_ADMIN)
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserByLogin(String username) {
        return userDAO.getUserByLogin(username);
    }

    public User getUserByFacebookId(String facebookId) {
        return userDAO.getUserByFacebookId(facebookId);
    }

    public void deleteUser(String username) {
        userDAO.deleteUser(username);
    }

}
