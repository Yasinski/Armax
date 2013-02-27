package third.dao;

import com.imhos.security.server.model.UserConnection;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 20.02.13
 * Time: 9:15
 * To change this template use File | Settings | File Templates.
 */
public interface UserConnectionDAO {

    public Set<String> findUsersConnectedTo(
            String providerId,
            Set<String> providerUserIds);

    public List<UserConnection> getPrimary(String userId, String providerId);

    public Integer getMaxRank(String userId, String providerId);

    public List<UserConnection> getAll(
            String userId,
            MultiValueMap<String, String> providerUsers);

    public List<UserConnection> getAll(String userId);

    public List<UserConnection> getAll(String userId, String providerId);

    public UserConnection get(
            String userId,
            String providerId,
            String providerUserId);

    public UserConnection get(String providerId, String providerUserId)
            throws IncorrectResultSizeDataAccessException;

    public UserConnection getByEmail(String email);

    public UserConnection getByUserName(String username);

    public void removeConnections(String userId, String providerId);

    public void removeConnection(String providerId, String providerUserId);

    public void save(UserConnection userConnection);

    public void update(UserConnection userConnection);

}
