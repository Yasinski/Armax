//package third;
//
//import com.moviejukebox.themoviedb.model.Genre;
//import com.moviejukebox.themoviedb.model.MovieDb;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * writeme: Should be the description of the class
// *
// * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
// */
//
//public class DataBaseConnection {
//	private Connection connection;
//
//	//public static void main(String[] args) throws SQLException, ClassNotFoundException {
//	//	DataBaseConnection dbTest = new DataBaseConnection();
//
//	String createQuery = "CREATE TABLE Movies_Info (" +
//			"  id INT(11) AUTO_INCREMENT," +
//			"  Title VARCHAR(100)," +
//			"  Genres VARCHAR(200)," +
//			"  ReleaseDate VARCHAR (20)," +
//			"  PRIMARY KEY (id)" +
//			")" +
//			"ENGINE = INNODB; ";
//
//
//	public DataBaseConnection() throws SQLException, ClassNotFoundException {
//		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2", "root", "root");
//		if (connection == null) {
//			System.err.println("Нет соединения с БД!");
//			System.exit(1);
//		}
//	}
//
//	public void saveInDataBase(MovieDb moviesSearchResult) throws ClassNotFoundException, SQLException {
//		for (Genre genre : moviesSearchResult.getGenres()) {
//			insert(moviesSearchResult.getTitle(), genre.getName(),moviesSearchResult.getReleaseDate() );
//		}
//	}
//
//	public void create() throws SQLException, ClassNotFoundException {
//		int rs1 = connection.prepareStatement(createQuery).executeUpdate();
//		System.out.println(rs1);
//	}
//
//	public void insert(String title,String genre,String releaseDate) throws SQLException, ClassNotFoundException {
//
//		String insertQuery = "insert into Movies_Info (Title, Genres, ReleaseDate) values ('" + title + "','" + genre + "','" + releaseDate + "')  ";
//		int rs1 = connection.prepareStatement(insertQuery).executeUpdate();
//		System.out.println(rs1);
//	}
//
//	public void update(String updateQuery) throws SQLException, ClassNotFoundException {
//		int rs1 = connection.prepareStatement(updateQuery).executeUpdate();
//		System.out.println(rs1);
//	}
//
//	public void select(String column) throws SQLException, ClassNotFoundException {    //не используется, нужно дописать
//		                                                                                 // под универсальный запрос
//		String selectQuery = "select * from Movies_Info ";
//		ResultSet rs2 = connection.prepareStatement(selectQuery).executeQuery();
//		while (rs2.next()) {
//			String info = rs2.getString(column);
//			System.out.println(info);
//		}
//	}
//
//	public void closeConnection() throws SQLException {
//		connection.close();
//	}
//
//}
