package third.DAO.Impl;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import third.DAO.MoviesDAO;
import third.model.Movie;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class MoviesDAOImpl extends HibernateDaoSupport implements MoviesDAO {

	private MoviesDAO moviesDAO;

	@Override
	protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
		HibernateTemplate result = super.createHibernateTemplate(sessionFactory);
		result.setAllowCreate(false);
		return result;
	}

	public void setMoviesDAO(MoviesDAO moviesDAO) {
		this.moviesDAO = moviesDAO;
	}

	public Movie getMovie(Integer movieId) {
		Movie movie = (Movie) getSession().createCriteria(Movie.class)
				.add(Restrictions.eq("id", movieId))
				.setFetchMode("genres", FetchMode.JOIN)
				.setFetchMode("actors", FetchMode.JOIN)
				.setFetchMode("director", FetchMode.JOIN)
				.uniqueResult();
		return movie;
	}

	public void saveMovie(Movie movie) {
		getSession().save(movie);
	}

	public void updateMovie(Movie movie) {
		getSession().update(movie);
	}

	public void deleteMovie(Movie movie) {
		getSession().delete(movie);
	}

	public List<Movie> findAll() {
		List<Movie> movies = getSession().createCriteria(Movie.class)
				.list();
		return movies;
	}

	public List<Movie> findAllWithAuthorsCount() {
		List<Movie> movies = getSession().createCriteria(Movie.class)
				.setProjection(Projections.projectionList()
						.add(Projections.id())
						.add(Projections.property("title"))
						.add(Projections.count("actors")))
				.list();
		return movies;
	}

	public List<Movie> findWithThisActor(Integer actorId) {
		List<Movie> movies = getSession().createCriteria(Movie.class)
				.createCriteria("actors")
				.add(Restrictions.eq("id", actorId))
				.list();
		return movies;
	}

	public List<Movie> findWithoutAnyActor() {
		List<Movie> movies = (List<Movie>) getSession().createCriteria(Movie.class)     //SELECT * FROM MOVIE WHERE IsEmpty(ACTORS)
				.add(Restrictions.isEmpty("actors"))
				.list();
		return movies;
	}

	public List<Movie> findAll_dirName1() {
		Query query = getSession().createQuery("select distinct m from Movie m  left join fetch m.director d left join fetch m.genres g order by (m.title) asc");
		return query.list();
	}

	public List<Movie> findAll_dirName() {
		Criteria criteria = getSession().createCriteria(Movie.class);
		criteria.setFetchMode("genres", FetchMode.JOIN);
		criteria.setFetchMode("actors", FetchMode.JOIN);
		criteria.setFetchMode("director", FetchMode.JOIN);
//	  criteria.setMaxResults(10);
		List<Movie> movies = criteria.list();
		return movies;
	}

	public List<Movie> findAll_dirName3() {
		List<Movie> movies = (List<Movie>) getSession().createSQLQuery("SELECT DISTINCT m.* FROM MOVIE m LEFT JOIN DIRECTOR {d} ON m.DIR_ID=d.DIRECTOR_ID").list();
		return movies;
	}

	public List<Movie> searchByTitle(String searchString, Integer firstOnPage, Integer rowsOnPage) {
		String queryString = "select distinct m from Movie m  left join fetch m.director d " +
				"left join fetch m.genres g where m.title like :searchString order by (m.title) asc";
		Query query = getSession().createQuery(queryString)
				.setParameter("searchString", "%" + searchString + "%")
				.setFirstResult(firstOnPage)
				.setMaxResults(rowsOnPage);
		return query.list();
	}

	public Integer getCountOfFound(String searchString) {
		String queryString = "select count(m) from Movie m  where m.title like :searchString";
		Query query = getSession().createQuery(queryString)
				.setParameter("searchString", "%" + searchString + "%");
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

	public List<Movie> findLimit(Integer firstOnPage, Integer rowsOnPage) {
		Query query = getSession().createQuery("select distinct m from Movie m  left join fetch m.director d left join fetch m.genres g order by (m.title) asc")
				.setFirstResult(firstOnPage)
				.setMaxResults(rowsOnPage);
		return query.list();
	}

	public Integer findCountOfMovies() {
		Integer count = (Integer) getSession().createCriteria(Movie.class)
				.setProjection(Projections.count("id"))
				.uniqueResult();
		return count;
	}

	public List<Movie> findWithThisDirector(Integer directorId) {
		List<Movie> movies = (List<Movie>) getSession().createQuery("select distinct m from Movie m join m.director d where d.id = :directorId")
				.setParameter("directorId", directorId)
				.list();
		return movies;
	}
}