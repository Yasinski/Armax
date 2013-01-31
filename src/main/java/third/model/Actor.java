package third.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

@Entity
@DiscriminatorValue("ACT")
public class Actor extends Human{

	@ManyToMany(mappedBy = "actors",fetch = FetchType.LAZY)
	private Set<Movie> movies = new HashSet<Movie>();

	public Actor() {
	}

	public Actor(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Actor(Integer id, String firstName, String lastName, Set<Movie> movies) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.movies = movies;
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

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

}
