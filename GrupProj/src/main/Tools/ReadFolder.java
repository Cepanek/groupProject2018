package main.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;


/**
 *
 */
public class ReadFolder {

    private String input;

    /**
     * Konstruktor którego parametrem jest konkretny folder
     * @param input
     */
    public ReadFolder(String input) {
        this.input = input;
    }

    /**
     *  - Czytanie w pętli plików,
     *  - przetwarzanie danych na listę obiektów
     *  - obliczanie szumu
     *  - obliczanie maksymalnej wartosci
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void readFiles() throws FileNotFoundException, IOException  {
        File folder = new File(input);                  // tworze katalog
        File[] files  = folder.listFiles();;            // tablica plikow
        OpenFile data= new OpenFile();                  // obiekt do odczytu kolejnych plików

        for (File file : files) {
            System.out.println("Loading: " + file.getAbsolutePath());               // sciezka + nazwa pliku, ktory obecnie wczytuje
            data.read(file.getAbsolutePath());          // wczytuje dane
            /*------------------------------------------------------------------------------
            data.getNoise();                            // tu jest poziom szumu
            data.getDataFrame();                        // tu jest lista obiektów
            data.getMaxValueInFile();                   // tu jest maksymalna wartość
            ------------------------------------------------------------------------------*/

            DecimalFormat df = new DecimalFormat("#,###,###.0000");
            System.out.println("Maksymalny szczyt to: " + df.format(data.getMaxValueInFile()) + ", a szum to: " + df.format(data.getNoise()));
            System.out.println("[OK]");
        }
    }
}
