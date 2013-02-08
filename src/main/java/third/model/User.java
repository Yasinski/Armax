package third.model;

import org.hibernate.annotations.CollectionOfElements;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class User implements UserDetails {


	@Id
	@GeneratedValue
	@Column
	private int id;

    @Column
    private String facebookId;

	@Column
	private String username;

	@Column
	private String password;

	@CollectionOfElements
	@JoinTable
	@Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities = new HashSet<Role>();

	@Column
	private boolean enabled;

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

    public User(String username, String password, Set<Role> authorities, boolean enabled) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    public User(String facebookId, String username, String password, Set<Role> authorities, boolean enabled) {
		this.authorities = authorities;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
        this.facebookId = facebookId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }
}
