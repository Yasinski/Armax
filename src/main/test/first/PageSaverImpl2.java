package first;
//package First;                                название пэкиджа должно быть с маленькой буквы


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


public class PageSaverImpl2 {


    public void savePage(String pageAdress) {

        try {
            URL lab = new URL(pageAdress);
            InputStreamReader isr = new InputStreamReader(lab.openStream());
            BufferedReader d = new BufferedReader(isr);
            StringBuilder siteContent = new StringBuilder();
            String line = "";
            while ((line = d.readLine()) != null) {
                siteContent = siteContent.append(line).append("\n");    // более правильный способ соеденить строки.
                                                                        // использование StringBuilder работает значительно быстрее

                                                                        // здесь можешь почитать детальнее http://habrahabr.ru/post/102468/
                                                                        // в конце добавляем спецсимвол \n переноса строки.

            }
            saveInFile(siteContent.toString());                         // преобразовываем StringBuilder в String и передаем полученную строку методу
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //сохранение прочитанного в файл
    private void saveInFile(String content) {
//        String pArray[] = {content};                                для текущей реализации это не нужно
        File file = new File("d:\\page2.txt");
        try {
            FileWriter fw = new FileWriter(file);
//            for (String a : pArray) {                               для текущей реализации это не нужно
            fw.write(content);
//            }                                                       для текущей реализации это не нужно
            fw.close();
            System.out.println("Файл создан");
        } catch (IOException e) {
            System.err.println("ошибка файла: " + e);
        }
    }
}


