package main.Tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Zapisz plik
 *
 * @TODO
 * - zapisz piki
 * - zapisz trafienia z 3 fotopowielaczy
 */
public class SaveFile {
    public SaveFile(Map<Integer, DataFrame> values, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter("C:\\groupProject2018\\GrupProj\\electron\\wyniki\\"+fileName+".csv");
        values.toString();

        for ( Integer key : values.keySet() ) {
            fileWriter.write(key+","+values.get(key).toCsv());
        }

        fileWriter.flush();
        fileWriter.close();
    }
}
