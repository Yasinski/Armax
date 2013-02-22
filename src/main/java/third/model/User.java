package third.model;

import com.imhos.security.server.model.UserConnection;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"login"}))
public class User implements UserDetails {


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column
    private String id;

    @Column
    private String username;

    @Column
    private String login;

    @Column
    private String password;


    @CollectionOfElements
    @JoinTable
    @Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities = new HashSet<Role>();

    @Column
    private boolean enabled = true;

    @OneToMany(mappedBy = "user")
    private Set<UserConnection> userConnections = new HashSet<UserConnection>();


    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public User(String username, String password, Set<Role> authorities, boolean enabled) {
        this.authorities = authorities;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }


    public User(String username, String login, String password, Set<Role> authorities, boolean enabled) {
        this.username = username;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
