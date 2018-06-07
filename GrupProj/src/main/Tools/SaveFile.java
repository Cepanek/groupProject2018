package main.Tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author Agnieszka Ceran, Mateusz Marchelewicz, Łukasz Janus, Łukasz Gwozdowski
 * @2018
 *
 */
public class SaveFile {

    /**
     *
     * Podajemy scieżkę (względną, lub bezwzględną) do plików do zapisu
     *          - albo Windowsowo: "H:\\OFFLINE\\Offline\\electron\\ch1_org_filtered"
     *          - albo ogólnie: "GrupProj/electron/ch1_org_filtered"
     *
     * @param values - mapa z danymi
     * @param fileName - nazwa pliku
     * @throws IOException
     */
    public SaveFile(Map<Integer, DataFrame> values, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter("GrupProj/electron/wyniki/"+fileName+".csv");
        values.toString();
        for ( Integer key : values.keySet() ) {
            fileWriter.write(key+","+values.get(key).toCsv());
        }
        fileWriter.flush();
        fileWriter.close();
    }
}
