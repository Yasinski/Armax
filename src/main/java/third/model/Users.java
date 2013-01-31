package third.model;

import org.hibernate.annotations.CollectionOfElements;

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
public class Users {


	@Id
	@GeneratedValue
	@Column
	private int id;

	@Column
	private String username;

	@Column
	private String password;

	@CollectionOfElements
	@JoinTable
	@Column(name = "authority", nullable = false)
	private Set<String> authorities = new HashSet<String>();

	@Column
	private boolean enabled;

	public Users() {
	}

	public Users(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Users(String username, String password, Set<String> authorities, boolean enabled) {
		this.authorities = authorities;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
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

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
