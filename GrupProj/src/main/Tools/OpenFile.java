package main.Tools;

import main.Statistics.Results;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Otwieranie plikow
 *
 * - zrzut danych do listy obiektow typu DataFrame
 * - liczenie szumu
 * - obliczanie maksymalnego szczytu
 */
public class OpenFile {

    private ArrayList<Float> original;
    private ArrayList<Float> bessel60;
    private ArrayList<Float> bessel80;
    
    static private Results Result = new Results();
    
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
     * Czytanie pliku wynikowego -  wsadzanie danych o peak'ach do obiektu Result
     * 
     *
     * @param path
     * @throws IOException
     */
    public void readScore(String path) throws IOException {
    	    	 
        BufferedReader file = null;
        String line="";
        String SplitBy = ",";
        
        try {
        	file = new BufferedReader(new FileReader(path));
        	while((line = file.readLine())!= null) {
        		String[] wiersz = line.split(SplitBy); 
        		//System.out.print(wiersz[4]+" "+wiersz[5]+" "+wiersz[6]+"\n");
        		Result.addData(Boolean.parseBoolean(wiersz[4]), Boolean.parseBoolean(wiersz[5]),Boolean.parseBoolean(wiersz[6]));
        	}
        } catch(FileNotFoundException e) {
        	
        	e.printStackTrace();
        } catch (IOException e) {
        	
        	e.printStackTrace();
        } finally {
        	if (file != null) {
        		//Result.peakAnalysis(Result);
        		try {
        			file.close();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        }        
       // System.out.println(Result.getHitOrg().size());
    }
   
  	/**
       * Wywolanie analizy peakow
       */
    public static void peakAnalysis()
    {
    	Result.peakAnalysis(Result);
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
     * Zwracam liste/ kolekjcje obiektow
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
     * Zwracam maksymalna wartosc w pliku
     * @return
     */
    public Double getMaxValueInFile() {
        return maxValueInFile;
    }
    
    

}
