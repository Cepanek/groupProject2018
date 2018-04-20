package main.Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Otwieranie plików
 *
 * - zrzut danych do listy obiektów typu DataFrame
 * - liczenie szumu
 * - obliczanie maksymalnego szczytu
 */
public class OpenFile {

    private ArrayList<Float> original;
    private ArrayList<Float> bessel60;
    private ArrayList<Float> bessel80;
    private List<DataFrame> dataFrame = new ArrayList<>();
    private Double noise;
    private Double maxValueInFile = 0.0;

    /**
     * Czytanie pliku i wsadzanie danych do obiektu dataFrame
     * Obliczanie szumu i maksymalnej wartości
     *
     * @param path
     * @throws IOException
     */
    public void read(String path) throws IOException {
        Double sumValue = 0.0;

        BufferedReader file = new BufferedReader(new FileReader(path));
        file.readLine();                    // pomijam 1-szy wiersz
        original = convertToArray(file);    // 2-gi wiersz
        bessel60 = convertToArray(file);    // 3-ci wiersz
        bessel80 = convertToArray(file);    // 4-ty wiersz
        file.close();

        for (int i = 0; i < original.size(); i++) {
            dataFrame.add(new DataFrame(original.get(i), bessel60.get(i), bessel80.get(i)));
            sumValue += original.get(i);
            if (i > 0) {
                if (original.get(i) > maxValueInFile) {
                    maxValueInFile = Double.valueOf(original.get(i));
                }
            } else {
                maxValueInFile = Double.valueOf(original.get(i));
            }
//            System.out.println(i+".\t czytana wartość: "+original.get(i) + ", \t Suma: " + sumValue + ", \t Maksymalna wartośc: " + maxValueInFile );
        }
        noise = sumValue / (double) original.size();
    }

    /**
     * Konwersja stringa do tablicy
     * @param plik
     * @return
     * @throws IOException
     */
    private ArrayList<Float> convertToArray(BufferedReader plik) throws IOException {
        String[] explosion = plik.readLine().split("\\s+", -1); //Plik->CzytajLinie->podziel
        ArrayList<Float> list = new ArrayList<>();

        for (String text : explosion) {
            if (!text.equals("")) list.add(Float.parseFloat(text));
        }
//        System.out.println(list.size());
        return list;
    }

    /**
     * Zwracam listę/ kolekjcje obiektów
     * @return
     */
    public List<DataFrame> getDataFrame() {
        return dataFrame;
    }

    /**
     * Zwracam wynik obliczonego szumu
     * @return
     */
    public Double getNoise() {
        return noise;
    }

    /**
     * Zwracam maksymalną wartosc w pliku
     * @return
     */
    public Double getMaxValueInFile() {
        return maxValueInFile;
    }

}
