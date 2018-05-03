package main.Tools;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
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
        FileWriter fileWriter = new FileWriter("electron/wyniki/"+fileName+".csv");
        values.toString();
//        for(DataFrame d: values.values()){
//            fileWriter.write(d.toCsv());
//        }

        for ( Integer key : values.keySet() ) {
            fileWriter.write(key+","+values.get(key).toCsv());
        }

        fileWriter.flush();
        fileWriter.close();
    }
}
