package second;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class FileSearcher {


    public static void main(String[] args) {
        FileSearcher fileSearcher = new FileSearcher();
        List<String> foundFilePaths = fileSearcher.searchFile("z://", "readme.txt");
        if (foundFilePaths.isEmpty()) {
            System.err.println("File not found :(");
        } else {
            System.out.println(foundFilePaths.size() + " files found:");
            for (String foundFilePath : foundFilePaths) {
                System.out.println(foundFilePath);
            }
        }
    }


    public List<String> searchFile(String dirName, String fileName) {
        List<String> foundFilePaths = new ArrayList<String>();    // создаем список для хранения путей файлов найденных в текущей папке и ее подпапках
        File dirFile = new File(dirName);
        File[] subFiles = dirFile.listFiles();
        if (subFiles == null) {
            System.out.println("Directory can not be read: \"" + dirName + "\"");      // выводим ошибку если папка не может быть прочитана
        } else if (subFiles.length == 0) {
            System.out.println("Directory is empty: \"" + dirName + "\"");             // выводим ошибку если папка пуста
        } else {
            System.out.println("Searching in dir: \"" + dirName + "\"");
            for (File childFile : subFiles) {
                if (childFile.isDirectory()) {
                    List<String> foundChildFilePaths = searchFile(childFile.getPath(), fileName);   // получаем список путей файлов найденных в дочерней папке
                    foundFilePaths.addAll(foundChildFilePaths);   // добавляем пути найденные в дочерней папке и ее подпапках  в список текущей папки
                } else if (childFile.getName().equals(fileName)) {
                    foundFilePaths.add(childFile.getPath());  // добавляем путь найденного файла в список найденных файлов
                }
            }
        }
        return foundFilePaths;  // возвращаем список путей файлов найденных в текущей папке и ее подпапках
    }
}