package main.Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Otwieranie plików
 *
 * @TODO - otwórz plik
 * - zrzut danych do listy obiektów typu DataFrame
 */
public class OpenFile {

    private ArrayList<Float> original;
    private ArrayList<Float> bessel60;
    private ArrayList<Float> bessel80;
    private List<DataFrame> dataFrame = new ArrayList<>();

    /**
     * @param path
     * @throws IOException
     */
    public void read(String path) throws IOException {
        BufferedReader file;

        file = new BufferedReader(new FileReader(path));
        file.readLine();    // pomijam 1-szy wiersz
        original = convertToArray(file);    // 2-gi wiersz
        bessel60 = convertToArray(file);    // 3-ci wiersz
        bessel80 = convertToArray(file);    // 4-ty wiersz
        file.close();

        for (int i = 0; i < original.size(); i++) {
            dataFrame.add(new DataFrame(original.get(i), bessel60.get(i), bessel80.get(i)));
        }
    }


    /**
     * @param plik
     * @return
     * @throws IOException
     */
    private static ArrayList<Float> convertToArray(BufferedReader plik) throws IOException {
        String[] explosion = plik.readLine().split("\\s+", -1); //Plik->CzytajLinie->podziel
        ArrayList<Float> list = new ArrayList<>();

        for (String text : explosion) {
            if (!text.equals("")) {
                list.add(Float.parseFloat(text));
            }
        }

        System.out.println(list.size());
        return list;
    }

    /**
     * @return
     */
    public List<DataFrame> getDataFrame() {
        return dataFrame;
    }

}
