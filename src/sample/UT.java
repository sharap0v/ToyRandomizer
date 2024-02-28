package sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Collections;

public class UT {
    public static SimpleDateFormat fullDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

    public static void writeFile(Toy toy){
        File file = new File("result.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
        try {
            Files.write(file.toPath(), Collections.singleton(fullDate.format(System.currentTimeMillis())+toy.toString()), StandardOpenOption.APPEND);
            System.out.println("result.txt saved.");
        } catch (IOException e) {
            System.out.println("result.txt don't saved.");
        }
    }

}
