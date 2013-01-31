//package third.DAO;
//
//import third.DAO.Impl.ActorsDAOImpl;
//import third.DAO.Impl.DirectorsDAOImpl;
//import third.DAO.Impl.MoviesDAOImpl;
//
///**
//* writeme: Should be the description of the class
//*
//* @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
//*/
//
//public class DAOFactory {
//
//	private static ActorsDAO actorsDAO = null;
//	private static DirectorsDAO directorsDAO = null;
//	private static MoviesDAO moviesDAO = null;
//	private static DAOFactory instance = null;
//
//	public static synchronized DAOFactory getInstance() {
//		if (instance == null) {
//			instance = new DAOFactory();
//		}
//		return instance;
//	}
//
//	public ActorsDAO getActorsDAO() {
//		if (actorsDAO == null) {
//			actorsDAO = new ActorsDAOImpl();
//		}
//		return actorsDAO;
//	}
//
//	public DirectorsDAO getDirectorsDAO() {
//		if (directorsDAO == null) {
//			directorsDAO = new DirectorsDAOImpl();
//		}
//		return directorsDAO;
//	}
//
//	public MoviesDAO getMoviesDAO() {
//		if (moviesDAO == null) {
//			moviesDAO = new MoviesDAOImpl();
//		}
//		return moviesDAO;
//	}
//
//	public static void setActorsDAO(ActorsDAO actorsDAO) {
//		DAOFactory.actorsDAO = actorsDAO;
//	}
//
//	public static void setDirectorsDAO(DirectorsDAO directorsDAO) {
//		DAOFactory.directorsDAO = directorsDAO;
//	}
//
//	public static void setMoviesDAO(MoviesDAO moviesDAO) {
//		DAOFactory.moviesDAO = moviesDAO;
//	}
//
//	public static void setInstance(DAOFactory instance) {
//		DAOFactory.instance = instance;
//	}
//}