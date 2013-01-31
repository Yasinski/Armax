package third.MovieSearcher;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

import java.io.Writer;
import java.util.Properties;

/**
 *
 * небольшой пример использования шаблонов Velocity: http://y3x.ru/2011/12/velocity-templates/
 */

public class VelocityManager {

    private VelocityEngine velocityEngine;

    public VelocityManager() {
        Properties properties = new Properties();
        // указываем где брать ресурсы "file" "jar" "class"
        properties.put("resource.loader", "file");
        properties.put("file.resource.loader.class", FileResourceLoader.class.getName());
        // путь где будут лежать ресурсы (шаблоны)
        // путь относительно текущего каталога
        // текущий каталог выполнеия программы можно посмотреть так:
        // System.out.println(new File(".").getAbsolutePath());
        // впрчем если нужно то можно указать любой абсолютный путь
        properties.put("file.resource.loader.path", "./src/main/resources/templates");

        // Создаем движок шаблонов Velocity
        velocityEngine = new VelocityEngine();
        velocityEngine.init(properties);
    }

    //public void WriteIn throws IOException {

//        VelocityManager velocityManager = new VelocityManager();
//        // Создаем контекст из которого будут браться данные
//        VelocityContext context = new VelocityContext();
//        // Упаковываем данные в контекст
//        context.put("title", moviesSearchResult.getTitle()); // по ключу "title" будет находиться значение "Movie Title"
//	      context.put("release date", moviesSearchResult.getReleaseDate());
//	      context.put("genres", "Genre name");
//

//
//        try {
//            fileWriter = new FileWriter(file);
//
//            velocityManager.writeContext(fileWriter, context, "/movie.html");
//
//            System.out.println("Создан файл: " + file.getAbsolutePath());
//        } finally {
//
//
//            if (fileWriter != null) {
//                fileWriter.close();
//            }
//        }
//
//    }


    /**
     * Метод записывает результат соединения шаблона с данными
     *
     * @param writer           "писатель" который будет записывать результат
     * @param context          контекст из которого будут браться данные
     * @param templateFileName путь к файлу шаблона
     */
    public void writeContext(Writer writer, VelocityContext context, String templateFileName) {
        Template template;
        try {
            template = velocityEngine.getTemplate(templateFileName);
            template.merge(context, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}