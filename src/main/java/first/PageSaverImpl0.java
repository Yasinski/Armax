package first;
//package First;                                название пэкиджа должно быть с маленькой буквы


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


public class PageSaverImpl0 {


    public void savePage(String pageAdress) {

        try {
            URL lab = new URL(pageAdress);
            InputStreamReader isr = new InputStreamReader(lab.openStream());
            BufferedReader d = new BufferedReader(isr);
            String line = "";
            while ((line = d.readLine()) != null) {
                saveInFile(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //сохранение прочитанного в файл
    private void saveInFile(String content) {
//        String pArray[] = {content};                                для текущей реализации это не нужно
        File file = new File("d:\\page0.txt");
        try {
            // при использовании конструктора FileWriter(File file, boolean append) файл будет ДОписываться а не переписываться
            FileWriter fw = new FileWriter(file, true);
//            for (String a : pArray) {                               для текущей реализации это не нужно
            fw.write(content);
            fw.write("\n");                                // в конце добавляем спецсимвол \n переноса строки.
                                                           // тоесть без этого символа все строки будут соеденены в одну строку (линию)
                                                           // с этим символом в файле каждая строка будет с новой строки

//            }                                                       для текущей реализации это не нужно
            fw.close();
            System.out.println("Файл дописан");
        } catch (IOException e) {
            System.err.println("ошибка файла: " + e);
        }
    }
}


