package main;

import main.Tools.ReadFolder;

/**
 * Ciało głównej aplikacji
 *
 * @TODO - otwórz iteracyjnie wszystkie pliki grupując kanałami
 * - odszum
 * - poszukaj pików / oznacz
 * - piki zapisz do pliku
 * - sprawdź, czy piki pojawiają się na wszystkich kanałach
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

        ReadFolder readFolder = new ReadFolder("GrupProj/electron/ch1_org_filtered");
        readFolder.readFiles();
    }


}
