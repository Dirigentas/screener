package screener.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    public static ArrayList<String> read(String path) {

        ArrayList<String> metricList = new ArrayList<>();

        try {
            File file = new File(path);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                metricList.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            metricList.add("Error");
        }
        return metricList;
    }
}
