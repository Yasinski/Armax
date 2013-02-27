package third.MovieSearcher;

import com.moviejukebox.themoviedb.model.MovieDb;
import third.facade.HibernateInfoSaver;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MoviesInfoGenerator {


    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        SettingsDefaultImpl settings = SettingsDefaultImpl.getInstance();
        String dirName = settings.getSettings("movies.dir");
        MoviesInfoGenerator moviesInfoGenerator = new MoviesInfoGenerator();   //
        moviesInfoGenerator.createDescriptionForMovies(dirName);
    }

    // я вынес эту логику в отдельный метод и сделал его не static
    // это нужно для того чтобы класс сохранял свою целостность даже удалить метод main
    // и использовать его (класс) в других местах программы создав инстанс класса MoviesInfoGenerator
    public void createDescriptionForMovies(String dirName) throws IOException, ClassNotFoundException, SQLException {
        // для того чтобы класс было удобно читать лучше не смешивать разную логику в одном классе
        // а выносить ее в свой предназначенный для этого класс.
        // разбивай классы логически у каждого отдельного класса должно быть свое предназначение
        MovieInfoSearcher movieInfoSearcher = new MovieInfoSearcher();
        MovieInfoSaver rs = new MovieInfoSaver();
        MovieIndexGenerator mig = new MovieIndexGenerator();
        // vol.1: DataBaseConnection dbc = new DataBaseConnection();
        //dbc.create();
        HibernateInfoSaver hibsave = new HibernateInfoSaver();
        File parentDir = new File(dirName);
        File[] subFiles = parentDir.listFiles();
        if (subFiles == null) {
            System.err.println("Directory \"" + parentDir + "\" can not be read");
        } else if (subFiles.length == 0) {
            System.err.println("Directory \"" + parentDir + "\" is empty");
        } else {
            for (File childFile : subFiles) {
                if (childFile.isFile()) {
                    //                   MoviesInfoGenerator moviesInfoSearcher = new MoviesInfoGenerator("5a1a77e2eba8984804586122754f969f");  нет необходимости
                    String fileName = getFileName(childFile);
                    MovieNameFilter filter = new MovieNameFilter();
                    String fileNameForSearch = filter.filterMovieName(fileName);
                    // что-то можно закомментить, на код не повлияет
                    MovieDb moviesSearchResult;
                    try {
                        moviesSearchResult = movieInfoSearcher.findMovie(fileNameForSearch, "ru");
                    } catch (IOException e) {
                        System.err.println("Search failed! movie name: " + fileNameForSearch);  // выводим в консоль ошибку если произошла ошибка при поиске фильма
                        continue;           //прерываем выполнение текущей итерации и продолжаем выполнение цикла со следующей
                    }
                    if (moviesSearchResult != null) {
                        System.out.println("Result for: " + fileName);
                        mig.addMovieToTheMap(moviesSearchResult);
                        rs.saveInFile(moviesSearchResult, parentDir, fileName);
                        // vol.1:  dbc.saveInDataBase(moviesSearchResult);
                        hibsave._hibernateSaveInfo(moviesSearchResult);

                        if (moviesSearchResult.getPosterPath() == null) {
                            System.out.println("Poster Path for \"" + fileName + "\" not found");
                            continue;            // если нету постера то прерываем текущую итерацию и продолжаем выполнение цикла со следущей итерации
                        }
                        // далее идет логика скачивания постера
                        FileDownloader fd = new FileDownloader();
                        String url = "http://cf2.imgobject.com/t/p/original/" + moviesSearchResult.getPosterPath();
                        String filePath = parentDir + "/" + fileName + ".jpg";
                        //                       System.out.println("Result for: " + moviesInfoSearcher.getFileName(childFile));
                        //                       rs.saveInFile(moviesSearchResult, moviesInfoSearcher.getFilePath(childFile.getAbsolutePath()) + ".txt");
                        try {
                            //                          здесь можно использовать ранее уже найденно имя файла в переменной fileName и имеющуюся переменную parentDir
                            fd.downloadFile(url, filePath);

                        } catch (IOException e) {
                            //                          отлавливаем ошибку записи файла.
                            //                          сейчас мы предполагаем что программа не должна встретить проблем с записью описаний в файл
                            //                          но если возникает такая ситуация то останавливаем дальнейшее выполнение программы выкинув RuntimeException
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.err.println("Movie \"" + fileNameForSearch + "\" not found");      // выводим в консоль ошибку если ничего не нашло
                    }
                } else if (childFile.isDirectory()) {
                    createDescriptionForMovies(childFile.getPath());
                }
            }
        }
        // я вынес генерацию списка файлов из цикла чтобы генерация происходила только один раз в конце а не каждый раз при нахождении инфы о фильме
        mig.generateGenreMoviesList(parentDir);
        mig.createIndex(parentDir);
        // vol.1:  dbc.closeConnection();
    }

    public String getFileName(File childFile) {
        String fileName = childFile.getName();
        int i = fileName.lastIndexOf('.');
        if (i != -1) {
            fileName = fileName.substring(0, i);
        }
        return fileName;
    }


}

