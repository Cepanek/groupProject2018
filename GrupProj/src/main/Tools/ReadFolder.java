package main.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;



/**
 *
 */
public class ReadFolder {

    private String input;

    /**
     *
     * @param input
     */
    public ReadFolder(String input) {
        this.input = input;
    }

    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void readFiles() throws FileNotFoundException, IOException  {
        File folder = new File(input);    // tworze katalog
        File[] files  = folder.listFiles();;     // tablica plikow
        OpenFile data= new OpenFile();    // dane z pojedynczego pliku
        ArrayList<OpenFile> dataCollect = new ArrayList<>();    // lista na dane

        for (File file : files) {
            // sciezka + nazwa pliku, ktory obecnie wczytuje
            System.out.println("Loading: " + file.getAbsolutePath());
            //data = new OpenFile();    // tworze obiekt z danymi
            data.read(file.getAbsolutePath());    // wczytuje dane

//            System.out.println(data.getDataFrame().toString());

            dataCollect.add(data);    // dodaje dane do kolekcji
            System.out.println("[OK]");

        }

    }

}
