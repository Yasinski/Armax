package third.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

@Entity
@DiscriminatorValue("DIR")
public class Director extends Human {

	@OneToMany(mappedBy = "director")
	private Set<Movie> hisMovies = new HashSet<Movie>();

	public Director() {
	}

	public Director(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Director(Integer id, String firstName, String lastName, Set<Movie> hisMovies) {
		this.id  = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hisMovies = hisMovies;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Set<Movie> getHisMovies() {
		return hisMovies;
	}

	public void setHisMovies(Set<Movie> hisMovies) {
		this.hisMovies = hisMovies;
	}

}
