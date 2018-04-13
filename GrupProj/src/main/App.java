package main;

import main.Tools.OpenFile;

import java.io.*;
import java.util.*;
/**
 * Ciało głównej aplikacji
 *
 * @TODO
 * - otwórz iteracyjnie wszystkie pliki grupując kanałami
 * - odszum
 * - poszukaj pików / oznacz
 * - piki zapisz do pliku
 * - sprawdź, czy piki pojawiają się na wszystkich kanałach
 * - wywal do pliku trafienia
 *
 */
public class App {
private final static String KATALOG_WEJSCIOWY = "electron\\ch1_org_filtered";
    
    public static void main(String[] args) throws Exception
    {
        File katalog;     // katalog z plikami
        File[] pliki;     // tablica plikow
        OpenFile dane;    // dane z pojedynczego pliku
        ArrayList<OpenFile> kolekcjaDanych;    // dane z wszystkich plikow

        katalog = new File(KATALOG_WEJSCIOWY);    // tworze katalog
        pliki = katalog.listFiles();    // pobieram pliki z katalogu
        kolekcjaDanych = new ArrayList<>();    // lista na dane
        
        for (File plik : pliki)    // petla po plikach
        {
        	// sciezka + nazwa pliku, ktory obecnie wczytuje
            System.out.println("Trwa wczytywanie: " + plik.getAbsolutePath());
            
            dane = new OpenFile();    // tworze obiekt z danymi
            dane.wczytaj(plik.getAbsolutePath());    // wczytuje dane

            dane.pobierzOryginalne();
            dane.pobierzBessel60();
            dane.pobierzBessel80();
            
            kolekcjaDanych.add(dane);    // dodaje dane do kolekcji
            System.out.println("[OK]");
        }
    }
}
