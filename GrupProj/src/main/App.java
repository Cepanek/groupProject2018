package main;

import main.Tools.ReadFolder;

import java.time.ZonedDateTime;

/**
 * @author Agnieszka Ceran, Mateusz Marchelewicz, Łukasz Janus, Łukasz Gwozdowski
 * @2018
 *
 *  Podajemy scieżkę (względną, lub bezwzględną) do plików do analizy
 *             - albo Windowsowo: "H:\\OFFLINE\\Offline\\electron\\ch1_org_filtered"
 *             - albo ogólnie: "GrupProj/electron/ch1_org_filtered"
 */
public class App {

    public static void main(String[] args) throws Exception {

       System.out.println("Start: "+ ZonedDateTime.now() .toString());

        // Czytanie danych
        ReadFolder readFolder = new ReadFolder("GrupProj/electron/ch1_org_filtered");
        readFolder.readFiles(1);

        // Analiza plików
        ReadFolder toAnalyse = new ReadFolder("GrupProj/electron/wyniki/");
        toAnalyse.readFiles(2);

        System.out.println("Stop: "+ ZonedDateTime.now().toString());
        
    }
}
