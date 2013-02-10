package third.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 08.02.13
 * Time: 9:44
 * To change this template use File | Settings | File Templates.
 */
public enum Role implements GrantedAuthority {
    ROLE_ANONYMOUS,
    ROLE_USER,
    ROLE_ADMIN;


    @Override
    public String getAuthority() {
        return toString();
    }
}
