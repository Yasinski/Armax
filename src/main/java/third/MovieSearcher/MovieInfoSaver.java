package third.MovieSearcher;

import com.moviejukebox.themoviedb.model.MovieDb;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


class MovieInfoSaver {

	// в сигнатуре метода указываем что он может выкинуть IOException тем самым говорим разработчику,
	// который будет работать с этим классом в будущем, что выполнения класса может выкинуть ошибку
	// отлавливать ошибку здесь не стоит так как мы не можем знать как будет использоваться этот метод в будущем
	// например разработчику может понадобиться остановить выполнение программы в случае такой ошибки,
	// но если мы отловим ошибку здесь и только выведем ее в лог то разработчик ее конечно увидит но не сможет программно остановить дальнейшее выполнение
	public void saveInFile(MovieDb moviesSearchResult, File parentDir, String fileName) throws IOException {

		VelocityManager velocityManager = new VelocityManager();
		// Создаем контекст из которого будут браться данные
		VelocityContext context = new VelocityContext();
		// Упаковываем данные в контекст
		context.put("moviePoster", fileName + ".jpg");
		context.put("title", moviesSearchResult.getTitle()); // по ключу "title" будет находиться значение "Movie Title"
		context.put("releaseDate", moviesSearchResult.getReleaseDate());
		context.put("genresList", moviesSearchResult.getGenres());
		context.put("overview", moviesSearchResult.getOverview());

		File file = new File(parentDir + "/" + moviesSearchResult.getTitle().replaceAll(" ","_").replaceAll(":", "") + ".html");
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			velocityManager.writeContext(fw, context, "/test.html");

			System.out.println("Создан файл: " + file.getAbsolutePath());
		} finally {
			// все Writer или Stream ОБЯЗАТЕЛЬНО должны быть закрыты методом close()
			// этим вызовом будут освобождены все системные ресурсы, связанные с потоком
			// закрывать его внутри try{} не верно так как
			// если возникнет ошибка после создания writer/stream но до close(), например в методе write() то writer/stream не будет закрыт
			// поэтому правильнее всего закрывать его в finally
			// блок finally отрабатывает ВСЕГДА после блока try не зависимо от того произошла в нем ошибка или нет


			if (fw != null) {
				fw.close();
			}
		}
	}
}