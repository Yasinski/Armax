package third.facade;

import org.springframework.security.access.annotation.Secured;
import third.DAO.UserDAO;
import third.model.Users;

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

	public void saveUser(Users user){
		  userDAO.saveUser(user);
	}

	public List<Users> getAllUsers(){
		 return userDAO.getAllUsers();
	}

//    @Secured("ROLE_ADMIN")
    public Users getUserByLogin(String username){
		 return userDAO.getUserByLogin(username);
	}

	public void deleteUser(String username){
		userDAO.deleteUser(username);
	}

}
