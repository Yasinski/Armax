package third.facade;

import third.dao.UserDAO;
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

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }


    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    //todo: !!!! implement an AccessDeniedException handler in service layer
    //    @Secured("ROLE_ADMIN")
    public User getUserByLogin(String username) {
        return userDAO.getUserByLogin(username);
    }

    public User getUserByFacebookId(String facebookId) {
        return userDAO.getUserByFacebookId(facebookId);
    }

    public User getUserByTwitterId(String twitterId) {
        return userDAO.getUserByTwitterId(twitterId);
    }


    public void deleteUser(String username) {
        userDAO.deleteUser(username);
    }

    public String getPassword(int userId) {
        return userDAO.getPassword(userId);
    }

}
