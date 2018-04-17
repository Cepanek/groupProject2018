package main.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Otwieranie plików
 *
 * @TODO
 * - otwórz plik
 * - zrzut danych do listy obiektów typu DataFrame
 */
public class OpenFile {
	
	//private final static String INPUT_FOLDER = "H:\\OFFLINE\\Offline\\electron\\ch1_org_filtered";
	private final static String INPUT_FOLDER = "electron\\ch1_org_filtered";
	private static Double[] original;
    private static Double[] bessel60;
    private static Double[] bessel80;
    
    
    public void readFiles() throws FileNotFoundException, IOException
    {
    	File folder;     // katalog z plikami
        File[] files;     // tablica plikow
        OpenFile data;    // dane z pojedynczego pliku
        ArrayList<OpenFile> dataCollect;    // dane z wszystkich plikow

        folder = new File(INPUT_FOLDER);    // tworze katalog
        files = folder.listFiles();    // pobieram pliki z katalogu
        dataCollect = new ArrayList<>();    // lista na dane
        
        for (File file : files)    // petla po plikach
        {
        	// sciezka + nazwa pliku, ktory obecnie wczytuje
            System.out.println("Loading: " + file.getAbsolutePath());
            data = new OpenFile();    // tworze obiekt z danymi
            read(file.getAbsolutePath());    // wczytuje dane

            data.getOriginal();
            data.getBessel60();
            data.getBessel80();
            
            dataCollect.add(data);    // dodaje dane do kolekcji
            System.out.println("[OK]");
            
        }

    }
    
    public static void read(String path) throws FileNotFoundException, IOException
    {
        BufferedReader file;
        
        file = new BufferedReader(new FileReader(path));
        file.readLine();    // pomijam 1-szy wiersz
        original = convertToArray(file);    // 2-gi wiersz
        bessel60 = convertToArray(file);    // 3-ci wiersz
        bessel80 = convertToArray(file);    // 4-ty wiersz
        file.close();
    }
    
    public Double[] getOriginal() { return original; }
    public Double[] getBessel60() { return bessel60; }
    public Double[] getBessel80() { return bessel80; }
    
    private static Double[] convertToArray(BufferedReader plik) throws IOException
    {
        ArrayList<Double> list;
        Double[] array;
        Double value;
        String line;
        String[] explosion;
        
        line = plik.readLine();
        explosion = line.split("\\s+", -1);
        list = new ArrayList<>();
      
        for (String text : explosion)
        {
            if (!text.equals(""))
            {
                value = Double.parseDouble(text);    // pojedyncza wartosc
                list.add(value);    // dodaje do listy
                //DataFrame df = new DataFrame(3.14f, 3.45555f, 2.34f);  przyklad
                //System.out.println(df.getOrgData());
                //System.out.println(df.getF60Data());
                //System.out.println(df.getF80Data());
            }
        }
        
        System.out.println(list.size());
        array = new Double[list.size()];
        return list.toArray(array);
    }
	
}
