package main.Tools;

import main.Analysator.PeakFinder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 *
 */
public class ReadFolder {
    private static final String ORIGINAL = "org";
    private static final int START = 10;
    private String input;
    private Boolean lookForPeak = true;

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
    public void readFiles(int flag) throws FileNotFoundException, IOException  {
        File folder = new File(input);                  // tworze katalog
        File[] files  = folder.listFiles();;            // tablica plikow
//        OpenFile data= new OpenFile();                  // obiekt do odczytu kolejnych plików
        if (flag == 1)
        {
        	for (File file : files) {
                System.out.println("Loading: " + file.getAbsolutePath());               // sciezka + nazwa pliku, ktory obecnie wczytuje
                OpenFile data= new OpenFile();                  // obiekt do odczytu kolejnych plików
                data.read(file.getAbsolutePath());          // wczytuje dane
                /*------------------------------------------------------------------------------
                data.getNoise();                            // tu jest poziom szumu
                data.getDataFrame();                        // tu jest lista obiektów
                data.getMaxValueInFile();                   // tu jest maksymalna wartość
                ------------------------------------------------------------------------------*/
                PeakFinder peakFinder = new PeakFinder(data.getDataFrame(), data.getNoise(), file.getName());
                peakFinder.findPeak(ORIGINAL, START, lookForPeak);
                DecimalFormat df = new DecimalFormat("#,###,###.0000");
                
        	}
            
        }
        	else if (flag ==2)
        {
        		
        		for (File file : files) {
                    System.out.println("Loading: " + file.getAbsolutePath());               // sciezka + nazwa pliku, ktory obecnie wczytuje
                    OpenFile data= new OpenFile();                  // obiekt do odczytu kolejnych plików
                    data.readScore(file.getAbsolutePath());          // wczytuje dane
                    /*------------------------------------------------------------------------------
                    data.getNoise();                            // tu jest poziom szumu
                    data.getDataFrame();                        // tu jest lista obiektów
                    data.getMaxValueInFile();                   // tu jest maksymalna wartość
                    ------------------------------------------------------------------------------*/  
                                        
        		System.out.println("[OK]"+data.getDataFrame());
        		}
        }
    }
}
