package third.MovieSearcher;

import com.moviejukebox.themoviedb.model.Genre;
import com.moviejukebox.themoviedb.model.MovieDb;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class MovieIndexGenerator {

	private Map<Genre, List<String>> movieMapByGenre = new HashMap<Genre, List<String>>();

	public void addMovieToTheMap(MovieDb moviesSearchResult) throws IOException {
		for (Genre genre : moviesSearchResult.getGenres()) {
			List<String> genreMoviesList = movieMapByGenre.get(genre);
			if (genreMoviesList == null) {           // если списка файлов для данного жанра нет то
				genreMoviesList = new ArrayList<String>();       // создаем его
				movieMapByGenre.put(genre, genreMoviesList);      // заносим его в мапу
			}
			genreMoviesList.add(moviesSearchResult.getTitle().replaceAll(" ","_").replaceAll(":", ""));                // добавляем фильм в список
		}
	}

	public void generateGenreMoviesList(File parentDir) throws IOException {
		VelocityManager velocityManager = new VelocityManager();
		VelocityContext context = new VelocityContext();

		for (Genre genre : movieMapByGenre.keySet()) {
			FileWriter fw = null;
			File file = new File(parentDir + "/" + genre.getName() + ".html");// создаем файл с названием жанра
			List<String> genreMoviesList = movieMapByGenre.get(genre); // записываем названия фильмов из списка genreMoviesList в этот html файл
			context.put("genreMoviesList", genreMoviesList);
			try {
				fw = new FileWriter(file);
				velocityManager.writeContext(fw, context, "/genreMoviesList.html");
				System.out.println("Создан файл: " + file.getAbsolutePath());
			} finally {
				if (fw != null) {
					fw.close();
				}
			}
		}
	}

	public void createIndex(File parentDir) throws IOException {
		VelocityManager velocityManager = new VelocityManager();
		VelocityContext context = new VelocityContext();
		context.put("genresList", movieMapByGenre.keySet());
		File file = new File(parentDir + "/" + "Index.html");
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			velocityManager.writeContext(fw, context, "/index.html");
			System.out.println("Создан файл: " + file.getAbsolutePath());
		} finally {
			if (fw != null) {
				fw.close();
			}
		}

	}

}
