package main.Tools;

import main.Statistics.Results;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    
    private ArrayList<String> soriginal;
    private ArrayList<String> sbessel60;
    private ArrayList<String> sbessel80;
    private ArrayList<String> sStateOrg;
    private ArrayList<String> sState60;
    private ArrayList<String> sState80;
    private Results Result;
    
    private List<DataFrame> dataFrame = new ArrayList<>();
    private Float noise;
    private Double maxValueInFile = 0.0;

    /**
     * Czytanie pliku i wsadzanie danych do obiektu dataFrame
     * Obliczanie szumu i maksymalnej wartości
     *
     * @param path
     * @throws IOException
     */
    public void read(String path) throws IOException {
        Float sumValue = 0.0f;

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
  //          System.out.println(i+".\t czytana wartość: "+original.get(i) + ", \t Suma: " + sumValue + ", \t Maksymalna wartośc: " + maxValueInFile );
        }
        noise = sumValue / original.size();
    }
    
    /**
     * Czytanie pliku wynikowego -  wsadzanie danych do obiektu Result - tutaj jest problem z wczytywaniem;
     * Zmienne przygotowane oddzielnie jako string.
     *
     * @param path
     * @throws IOException
     */
    public void readScore(String path) throws IOException {
        Float sumValue = 0.0f;
        DecimalFormat df = new DecimalFormat("#,###,###.0000");
              		
        //String line = "";
        
        BufferedReader file = new BufferedReader(new FileReader(path));
        file.readLine();                    // analogicznie do powyzszego wczytania - inny format danych, ale inna metoda
        soriginal = convertToArrayRead(file);    // 2-gi wiersz
        sbessel60 = convertToArrayRead(file);    // 3-ci wiersz
        sbessel80 = convertToArrayRead(file);    // 4-ty wiersz
        sStateOrg = convertToArrayRead(file);    // 5-ty wiersz
        sState60 = convertToArrayRead(file);    // 6-ty wiersz
        sState80 = convertToArrayRead(file);    // 7-ty wiersz
        file.close();
        
 
        for (int i = 0; i < original.size(); i++) {
          //  dataFrame.add(new DataFrame(original.get(i), bessel60.get(i), bessel80.get(i)), sStateOrg.get(i), sState60.get(i), sState80.get(i));
        	dataFrame.add(new DataFrame(original.get(i), bessel60.get(i), bessel80.get(i)));
            
            sumValue += original.get(i);
            if (i > 0) {
                if (original.get(i) > maxValueInFile) {
                    maxValueInFile = Double.valueOf(original.get(i));
                }
            } else {
                maxValueInFile = Double.valueOf(original.get(i));
            }
            
            //counting.addData(dataFrame.get(i).isHitOrg(), dataFrame.get(i).isHit60(), dataFrame.get(i).isHit80());
            Result.addData(dataFrame.get(i).isHitOrg(), dataFrame.get(i).isHit60(), dataFrame.get(i).isHit80());
        }
        
        peakAnalysis(Result);
        noise = sumValue / original.size();
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
     * Konwersja stringa do tablicy - proba dla pliku wynikowego
     * @param plik
     * @return
     * @throws IOException
     */
    private ArrayList<String> convertToArrayRead(BufferedReader plik) throws IOException {
        String[] explosion = plik.readLine().split(", "); //zmieniony split, (w DataFrame zapis rowniez do tego formatu zmieniony) 
        System.out.println(plik.readLine());
        ArrayList<String> list = new ArrayList<>();
        
        for (String text : explosion) {
            if (!text.equals("")) list.add(text);
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
    public Float getNoise() {
        return noise;
    }

    /**
     * Zwracam maksymalną wartosc w pliku
     * @return
     */
    public Double getMaxValueInFile() {
        return maxValueInFile;
    }
    
    /**
     * Metoda do analizowania pikow
     * @param picks
     */
    public void peakAnalysis(Results picks){
    	//Integer OrgSize = picks.getHitOrg().size(); //ilosc wszystkich pickow
    	Integer OrgSize = picks.trueCounter(picks.getHitOrg()); //ilosc wszystkich pickow
    	Integer h60TrueCount = picks.trueCounter(picks.getHit60()); //ilosc trafien filtr-60
    	Integer h80TrueCount = picks.trueCounter(picks.getHit80()); //ilosc trafien filtr-80

    	//do usuniecia po przetestowaniu - wyrzucenie na ekran liczby pickow.
    	//System.out.println("Liczba pick'ow:\nWykres oryginalny: "+OrgSize+", filtr 60: "+h60TrueCount+", filtr 80: "+h80TrueCount);
    	// zabezpieczenie przed dzieleniem przez 0 - gdyby w danej partii plików nie było pick'ów

    	Float lostPercent60;// = 2.0f;
    	Float lostPercent80;// = 2.0f;

    	if (h60TrueCount>0)
    	{
    		lostPercent60 = OrgSize-(((float)h60TrueCount)/(float)OrgSize); //wersja pokazująca, ile procent utracono
    		//lostPercent60 = 100*((float)OrgSize/((float)h60TrueCount)); //wersja procentów z zapisem
    	} else {
    		lostPercent60 = (float) 0.0;
    	}

    	if (h80TrueCount>0)
    	{
    		lostPercent80 = OrgSize-(((float)h80TrueCount)/(float)OrgSize); //analogicznie, j.w.
    		//lostPercent80 = 100*((float)OrgSize/((float)h80TrueCount));

    	} else {
    		lostPercent80 = (float) 0.0;
    	}

    	//do usuniecia po przetestowaniu - wyrzucenie na ekran % utraty pickow.

    	//System.out.printf("Procent utraconych pik'ów: filtr 60: %.2f",lostPercent60);
    	//System.out.printf(", filtr 80: %.2f %n",lostPercent80);

    	/*do usuniecia po przetestowaniu - zapis do pliku - koncowy wynik w jednej zmiennej string---*/
    	String endScore = "";
    	//endScore +="Zbiorczy wynik koncowy dla wszystkich pick'ow:\nDane oryginalne: "+OrgSize+", filtr 60: "+h60TrueCount+", filtr 80: "+h80TrueCount;
    	//endScore +="\nProcent utraconych pik'ow: filtr 60: "+lostPercent60+", filtr 80: "+lostPercent80;

    	endScore +="Sumaryczna_liczba_pick'ow:\n";
    	endScore +="Pick_prawdziwy:"+" filtr60:"+" filtr80:\n";
    	endScore += OrgSize+" "+h60TrueCount+" "+h80TrueCount;
    	endScore +="\nProcent_utraconych_pik'ow:\n";
    	endScore +="filtr60:"+" filtr_80:\n";
    	endScore +=lostPercent60+" "+lostPercent80;

    	picks.csvWriter(endScore);
    }

}
