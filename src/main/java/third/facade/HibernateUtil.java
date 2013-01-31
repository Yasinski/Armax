package third.facade;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import third.model.Actor;
import third.model.Director;
import third.model.Movie;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            AnnotationConfiguration aconf = new AnnotationConfiguration()
                    .addAnnotatedClass(Movie.class)
                    .addAnnotatedClass(Actor.class)
                    .addAnnotatedClass(Director.class);
            sessionFactory = aconf.configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}