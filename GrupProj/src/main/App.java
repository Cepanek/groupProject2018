package main;

import main.Tools.ReadFolder;

import java.time.ZonedDateTime;

/**
 * Cialo glownej aplikacji
 *
 * @TODO - otworz iteracyjnie wszystkie pliki grupujac kanalami
 * - odszum
 * - poszukaj pikow / oznacz
 * - piki zapisz do pliku
 * - sprawdz, czy piki pojawiaja… sie na wszystkich kanalach
 * - wywal do pliku trafienia
 */
public class App {

    public static void main(String[] args) throws Exception {

        /*
         * 	private final static String INPUT_FOLDER = "H:\\OFFLINE\\Offline\\electron\\ch1_org_filtered";
         * 	/** DLA WINDOWSA **
         *  private final static String INPUT_FOLDER = "electron\\ch1_org_filtered";
         *  /** DLA LINUXA **
         *private final static String INPUT_FOLDER = "GrupProj/electron/ch1_org_filtered";
         */
        System.out.println("Start: "+ ZonedDateTime.now() .toString());
        ReadFolder readFolder = new ReadFolder("H:\\OFFLINE\\Offline\\electron\\ch1_org_filtered");
        readFolder.readFiles(1);
        
        ReadFolder toAnalyse = new ReadFolder("H:\\OFFLINE\\Offline\\electron\\wyniki");
        toAnalyse.readFiles(2);
        
        
        System.out.println("Stop: "+ ZonedDateTime.now().toString());
        
    }


}
