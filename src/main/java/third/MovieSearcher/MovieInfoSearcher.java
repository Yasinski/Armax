package third.MovieSearcher;

import com.moviejukebox.themoviedb.TheMovieDb;
import com.moviejukebox.themoviedb.model.MovieDb;

import java.io.IOException;
import java.util.List;


public class MovieInfoSearcher {

    private static final String DEFAULT_LANGUAGE = "en";   // язык поиска поумолчанию
    private static final String API_KEY = "5a1a77e2eba8984804586122754f969f";     //ключ авторизации на сервисе


    public MovieDb findMovie(String searchQuery, String language) throws  IOException {
        if (searchQuery.isEmpty()) {
            return null;
        }
        // используем для поиска класс из сторонней библиотеки themoviedbapi-3.2-SNAPSHOT-r164.jar
        TheMovieDb tmdb = new TheMovieDb(API_KEY);
        // при помощи сторонней библиотеки отправляем запрос на сервер http://api.themoviedb.org
        List<MovieDb> movieList = null;
        movieList = tmdb.searchMovie(searchQuery, DEFAULT_LANGUAGE, true);

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
        return mostRelevantMovie;

    }
}
