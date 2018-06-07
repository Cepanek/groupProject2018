package main.Tools;

import main.Analysator.PeakFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Agnieszka Ceran, Mateusz Marchelewicz, Łukasz Janus, Łukasz Gwozdowski
 * * @2018
 */
public class ReadFolder {
    private static final String ORIGINAL = "org";
    private static final int START = 10;
    private String input;
    private Boolean lookForPeak = true;

    /**
     * Konstruktor ktorego parametrem jest konkretny folder
     *
     * @param input - folder który czytamy
     */
    public ReadFolder(String input) {
        this.input = input;
    }

    /**
     * - Czytanie w petli plikow,
     * - przetwarzanie danych na liste obiektow
     * - obliczanie szumu
     * - obliczanie maksymalnej wartosci
     *
     * @param flag - flaga sprawdzająca- czy odczyt danych, czy analiza
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void readFiles(int flag) throws FileNotFoundException, IOException {
        File folder = new File(input);
        File[] files = folder.listFiles();
        if (flag == 1) {
            for (File file : files) {
                OpenFile data = new OpenFile();                  // obiekt do odczytu kolejnych plik�w
                data.read(file.getAbsolutePath());          //  sciezka + nazwa pliku, ktory obecnie wczytuje
                /*------------------------------------------------------------------------------
                Przykład "dobrania się" do obiektu:
                data.getNoise();                            // tu jest poziom szumu
                data.getDataFrame();                        // tu jest lista obiektow
                data.getMaxValueInFile();                   // tu jest maksymalna wartosc
                ------------------------------------------------------------------------------*/
                PeakFinder peakFinder = new PeakFinder(data.getDataFrame(), data.getNoise(), file.getName());
                peakFinder.findPeak(ORIGINAL, START, lookForPeak);
            }
        } else if (flag == 2) {
            for (File file : files) {
                OpenFile data = new OpenFile();                  // obiekt do odczytu kolejnych plikow
                data.readScore(file.getAbsolutePath());          // wczytuje dane wynikowe
            }
            OpenFile.peakAnalysis(); //wywolanie analizy peakow - metoda statyczna w klasie OpenFile
        }
    }
}
