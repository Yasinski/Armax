package first;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 12.05.12
 * Time: 8:45
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String... args) throws IOException {

        Properties movies = new Properties();
        InputStreamReader fis = new InputStreamReader(new FileInputStream("./src/third/movies.properties"), "UTF-8");

        movies.load(fis);
        String dirName = movies.getProperty("movies.dir");
        System.out.println(dirName);


        ResourceBundle rb = ResourceBundle.getBundle("third.movie", new UTF8Control());
        String propertyValue = rb.getString("test");

        System.out.println("test index" + propertyValue + "some changes new ch in test");
        String s = "test";
        int q = 3;
        int f4 = 3;
        int aa = 12;
        int ga = 1;
        int ssssga = 122;


    }


    public static class UTF8Control extends ResourceBundle.Control {
        public ResourceBundle newBundle
                (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            // The below is a copy of the default implementation.
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");
            ResourceBundle bundle = null;
            InputStream stream = null;
            if (reload) {
                URL url = loader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            } else {
                stream = loader.getResourceAsStream(resourceName);
            }
            if (stream != null) {
                try {
                    // Only this line is changed to make it to read properties files as UTF-8.
                    bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
                } finally {
                    stream.close();
                }
            }
            return bundle;
        }
    }

}
