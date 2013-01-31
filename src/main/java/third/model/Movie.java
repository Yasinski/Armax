package third.model;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

@Entity
@Table
public class Movie implements Comparable {

	@Id
	@GeneratedValue
	@Column
	private Integer id;

	@Column
	private Integer rating;

	@NotNull
	@Column
	private String title;

	@ManyToOne(fetch = FetchType.LAZY)
	private Director director;

	@Column
	private String releaseDate;

	@CollectionOfElements
	@JoinTable
	@Column(name = "genre", nullable = false)
	private Set<String> genres = new HashSet<String>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
				name = "movie_actor",
				joinColumns = {@JoinColumn(name = "movie_id")},
				inverseJoinColumns = {@JoinColumn(name = "actor_id")}
		)
	private Set<Actor> actors = new HashSet<Actor>();

	public Movie() {
	}

	public Movie(String title) {
		this.title = title;
	}

	public Movie(String title, Set<String> genres, String releaseDate) {
		this.title = title;
		this.genres = genres;
		this.releaseDate = releaseDate;
	}

	public Movie(String title, Director director, Set<String> genres, String releaseDate) {
		this.title = title;
		this.director = director;
		this.genres = genres;
		this.releaseDate = releaseDate;
	}

	public Movie(String title, Set<String> genres, Director director, Set<Actor> actors) {
		this.title = title;
		this.director = director;
		this.genres = genres;
		this.actors = actors;
	}

	public Movie(Integer id, String title, Set<String> genres, String releaseDate, Integer rating, Director director, Set<Actor> actors) {
		this.id = id;
		this.rating = rating;
		this.title = title;
		this.director = director;
		this.genres = genres;
		this.releaseDate = releaseDate;
		this.actors = actors;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public void increaseRating() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public Set<String> getGenres() {
		return genres;
	}

	public void setGenres(Set<String> genres) {
		this.genres = genres;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Movie)) {
			throw new RuntimeException("Object not comparable");
		}
		Movie movie = (Movie) o;
		return this.getTitle().compareTo(movie.getTitle());
	}
}


