package imhos.movie.searcher;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 16.05.12
 * Time: 01:53
 * To change this template use File | Settings | File Templates.
 */

import com.moviejukebox.themoviedb.TheMovieDb;
import com.moviejukebox.themoviedb.model.Genre;
import com.moviejukebox.themoviedb.model.MovieDb;
import com.moviejukebox.themoviedb.model.TmdbConfiguration;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class MovieSearcher {

    private static final String DEFAULT_LANGUAGE = "en";   // язык поиска поумолчанию
    private String apiKey;     //ключ авторизации на сервисе

    public MovieSearcher(String apiKey) {
        this.apiKey = apiKey;
    }


    public static void main(String[] args) throws  IOException {
        // в конструктор MovieInfoSearcher передаем ключ авторизации который я нашел в инете
        MovieSearcher movieSearcher = new MovieSearcher("5a1a77e2eba8984804586122754f969f");

//        String searchQuery1 = "CHRONICLES_OF_NARNIA";      // запрос поиска только по имени
        String searchQuery1 = "Mission Impossible (1996)";      // запрос поиска только по имени
        System.out.println("Search query: " + searchQuery1);
        MoviesSearchResult moviesSearchResult1 = movieSearcher.findMovie(searchQuery1, "ru");   // поиск на русском языке
        printResult(moviesSearchResult1);

//        String searchQuery2 = "Властелин колец  короля 2003";   // запрос поиска по имени и год выпуска вернет более точный результат
//        System.out.println("\n\n\nSearch query: " + searchQuery2);
//        MoviesSearchResult moviesSearchResult2 = movieSearcher.findMovie(searchQuery2);    // поиск на языке по умолчанию
//        printResult(moviesSearchResult2);
//
//        FileDownloader pageSaverImpl3 = new FileDownloader();
//        String imgURL = movieSearcher.getConfiguration().getBaseUrl() + "original/" + moviesSearchResult1.getMostRelevantMovie().getPosterPath();
//        System.out.println(imgURL);
//        pageSaverImpl3.downloadFile(imgURL, "d://" + moviesSearchResult1.getMostRelevantMovie().getTitle() + ".jpg");

    }

    /**
     * Выводит результат поиска в консоль
     *
     * @param moviesSearchResult результат поиска фильмов
     */
    public static void printResult(MoviesSearchResult moviesSearchResult) {
        if (moviesSearchResult == null) {
            System.err.println("No results found ");          // если поиск не вернул результатов выводим ошибку
            return;                                          // и выходим из метода (прерываем его выполнение)
        }
        System.out.println("Most relevant result:");
        System.out.println("Title: " + moviesSearchResult.getMostRelevantMovie().getTitle());
        System.out.println("Release date: " + moviesSearchResult.getMostRelevantMovie().getReleaseDate());
        System.out.print("Genres: ");
        for (Genre genre : moviesSearchResult.getMostRelevantMovie().getGenres()) {
            System.out.print(genre.getName() + " ");
        }
        System.out.println("\nOverview: " + moviesSearchResult.getMostRelevantMovie().getOverview());

        System.out.print("\n\n");
        System.out.println("Other results(" + moviesSearchResult.getOtherResults().size() + "):");
        for (MovieDb movie : moviesSearchResult.getOtherResults()) {
            System.out.println(movie.getTitle());
        }

    }


    public TmdbConfiguration getConfiguration() throws IOException {
        TheMovieDb tmdb = new TheMovieDb(apiKey);
        TmdbConfiguration tmdbConfiguration = tmdb.getConfiguration();
        String baseUrl = tmdbConfiguration.getBaseUrl();
        return tmdbConfiguration;
    }


    /**
     * Метод ищет фильм с помощью специального сервиса поиска фильмов http://themoviedb.org
     * язык поиска поумолчанию (английский)
     *
     * @param searchQuery строка запроса по который надо найти фильм
     * @return результат поиска
     * @throws IOException
     */
    public MoviesSearchResult findMovie(String searchQuery) throws IOException {
        return findMovie(searchQuery, DEFAULT_LANGUAGE);
    }

    /**
     * Метод ищет фильм с помощью специального сервиса поиска фильмов http://themoviedb.org
     *
     * @param searchQuery строка запроса по который надо найти фильм
     * @param language    язык результата поиска
     * @return результат поиска
     * @throws IOException
     */

    public MoviesSearchResult findMovie(String searchQuery, String language) throws IOException {
        // используем для поиска класс из сторонней библиотеки themoviedbapi-3.2-SNAPSHOT-r164.jar
        TheMovieDb tmdb = new TheMovieDb(apiKey);
        // при помощи сторонней библиотеки отправляем запрос на сервер http://api.themoviedb.org
        List<MovieDb> movieList = tmdb.searchMovie(searchQuery, language, true);

        if (movieList == null || movieList.isEmpty()) {    // если список результатов равен null или пуст то возвращаем null
            return null;
        }
        // в полученном результате считаем что первый результат наиболее релевантный
        // получаем уникальный номер (ID) наиболее релевантного результата
        int mostRelevantResultId = movieList.get(0).getId();

        // отправляем второй запрос для получения более детальной информации о наиболее релевантном фильме
        MovieDb mostRelevantMovie = tmdb.getMovieInfo(mostRelevantResultId, language);

        // удаляем первый результат из списка остальных фильмов
        movieList.remove(0);
        return new MoviesSearchResult(mostRelevantMovie, movieList);

    }


    /**
     * результат поиска фильмов
     */

    public class MoviesSearchResult {


        private MovieDb mostRelevantMovie;   // наиболее релевантный результат
        private List<MovieDb> otherResults;  // другие найденный результаты удовлетворяющие запросу пользователя

        private MoviesSearchResult(MovieDb mostRelevantMovie, List<MovieDb> otherResults) {
            this.mostRelevantMovie = mostRelevantMovie;
            this.otherResults = otherResults;
        }


        public MovieDb getMostRelevantMovie() {
            return mostRelevantMovie;
        }

        public void setMostRelevantMovie(MovieDb mostRelevantMovie) {
            this.mostRelevantMovie = mostRelevantMovie;
        }

        public List<MovieDb> getOtherResults() {
            return otherResults;
        }

        public void setOtherResults(List<MovieDb> otherResults) {
            this.otherResults = otherResults;
        }
    }
}
