package main.Analysator;

import main.Tools.DataFrame;
import main.Statistics.Results;
// import Tools.SaveFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Analizator peaków
 *
 * @TODO
 * - na podstawie wyznaczonego szumu wyznacz piki
 * - oznacz piki porównuj¹c sygna³ oryginalny z przefiltrowanymi
 * - zmieñ flagê w DataFrame || odkopiuj DataFrame do nowej listy
 */
public class PeakFinder {
    private static final Integer INTERVAL = 1;
    private static final Float LIMIT = 5.0f;
    private static final String ORIGINAL = "org";
    private static final String FILTER_60 = "60";
    private static final String FILTER_80 = "80";
    private List<DataFrame> peaksToAnalysis; //dane do badania
   // private String fileName; //never used

    
    private static final Results picks = new Results();  //###### Obiekt przechowujacy dane do statystyki
    
    public void setFindedPeaks(Map<Integer, DataFrame> findedPeaks) {
        this.findedPeaks = findedPeaks;
    }

    public Map<Integer, DataFrame> getFindedPeaks() {
        return findedPeaks;
    }

    //    private List<DataFrame> findedPeaks; //znalezione piki
    private Map<Integer, DataFrame> findedPeaks = new HashMap<>();
    private Float border; //szumy
    private Float localMin;
    private Float localMax;
  //  private Float localSecondMax; //never used
  //  private int tempI; //never used

    /**
     *
     * @param peaksToAlanyse
     * @param border
     */
    public PeakFinder(List<DataFrame> peaksToAlanyse, Float border, String fileName) {
        this.peaksToAnalysis = peaksToAlanyse;
        this.border = border;
      //  this.fileName = fileName; //never used
    }

    public void findPeak(String whichFlow, int ii, boolean findAll) throws IOException {
        
    	
    	
    	int datasize = peaksToAnalysis.size();
//        System.out.println("Rozmiar pliku: "+datasize);

        localMin = border;
        // localSecondMax = border; //never used
        localMax = border; //namniejsza wartosc jaka moze byæ to granica szumu
        int end = datasize;
        int jump = INTERVAL;


        if(whichFlow == ORIGINAL){
             end = datasize;
             jump = INTERVAL;
        }else if(whichFlow == FILTER_60  || whichFlow == FILTER_80){
             end = 100;
             jump = 1;
        }else{
            System.out.println("Parameter whichFlow is null or empty or value incorrect");
        }


        for(int i= ii; i<end; i+=jump){ //petla po danych, co 10 pomiar
            Float dataToAnalysis = 0.0f;
            if(whichFlow == ORIGINAL){
                dataToAnalysis = peaksToAnalysis.get(i).getOrgData();
            }else if(whichFlow == FILTER_60){
                dataToAnalysis = peaksToAnalysis.get(i).getF60Data();
            }else if(whichFlow == FILTER_80){
                dataToAnalysis = peaksToAnalysis.get(i).getF80Data();
            }else{
                System.out.println("Parameter whichFlow is null or empty or value incorrect");
            }

//            System.out.println("for "+whichFlow+" "+dataToAnalysis+" "+i);
            if(dataToAnalysis < border){
//                System.out.println("if 1 "+dataToAnalysis+"  "+border);
                continue; //wynik do odrzucenia bo jets poni¿ej szumu
            }else{
//                System.out.println("else 1 ");
                if(dataToAnalysis>localMin && dataToAnalysis >= peaksToAnalysis.get(i-10).getOrgData()){
//                    System.out.println("if 2 ");
                    //caly czas idziemy do góry
                    localMax = dataToAnalysis;
//                    System.out.println("Max: "+localMax);

                }else if(dataToAnalysis<localMax){
//                    System.out.println("else 2 ");
                    //ca³y czas idziemy w dó³
                    localMin = dataToAnalysis;
//                    System.out.println("Min: "+localMin);
                    Float heightDiff = localMax - localMin;
                    Float heightBorder = (localMax*LIMIT)/100;
//                    System.out.println("--- Diff: "+heightDiff);
                    if(heightDiff > heightBorder){
                    	
                    	Boolean hOrg=false;
                    	Boolean h60=false;
                    	Boolean h80=false;
                    	
//                        System.out.println("--------LOOOL-------");
                        if(whichFlow == ORIGINAL) {
//                            System.out.println("####### org ######");
                            //DataFrame peak = getMaxValue(peaksToAnalysis.subList(i - INTERVAL, i + INTERVAL));
                            findedPeaks.put(i, peaksToAnalysis.get(i));
                            findedPeaks.get(i).setHitOrg(true);
//                            System.out.println("--------");
                            findAll = true;
                            
                            hOrg=true;
                            
                            findPeak(FILTER_60, i, findAll);
                        }else if(whichFlow == FILTER_60 && findAll == true) { //od tego miejsca sprawdzamy pierwszy filtr
                            //isHit
//                            System.out.println("     ####### 60 ######");
//                            System.out.println(findedPeaks.get(ii).toString());
                            findedPeaks.get(ii).setHit60(true);
                            
                            h60=true;
                            
                            findPeak(FILTER_80, ii , findAll);
                        }else if(whichFlow == FILTER_80 && findAll == true) { //od tego miejsca sprawdzamy grugi filtr
                            //isHit
//                            System.out.println("           ####### 80 ######");
                            findedPeaks.get(ii).setHit80(true);
                            
                            h80=true;
                            
                            findAll = false;
                        }
                        
                      //###################### pobranie wartoœci false/true
                                                
                        picks.AddData(hOrg, h60, h80);
                        
                      //######################   
                    }
                }
            }
        }
       
       peakAnalysis(picks); //##tutaj przy ka¿dym piku ta metoda jest uruchamiana
        
       //System.out.println(findedPeaks.values().toString());
        // SaveFile s = new SaveFile(findedPeaks, fileName); //never used
    }

    public void peakAnalysis(Results picks){
        //analiza pików
    	//System.out.println(findedPeaks.values().toString()); 
    	
    	//Integer OrgSize = picks.getHitOrg().size(); //ilosc wszystkich pickow
    	Integer OrgSize = picks.trueCounter(picks.getHitOrg()); //ilosc wszystkich pickow
    	Integer h60TrueCount = picks.trueCounter(picks.getHit60()); //ilosc trafien filtr-60
    	Integer h80TrueCount = picks.trueCounter(picks.getHit80()); //ilosc trafien filtr-80
    	
    	//wyrzucenie na ekran liczby pickow.
    	
    	//System.out.println("Liczba pick'ow:\nWykres oryginalny: "+OrgSize+", filtr 60: "+h60TrueCount+", filtr 80: "+h80TrueCount);
    	   	
    	/* zabezpieczenie przed dzieleniem przez 0 - gdyby w danej partii plików nie by³o pick'ów */
    	
    	Float lostPercent60;// = 2.0f; 
    	Float lostPercent80;// = 2.0f;

    	if (h60TrueCount>0) 
    	{
    		lostPercent60 = OrgSize-(((float)h60TrueCount)/(float)OrgSize); //wersja pokazuj¹ca, ile procent utracono
    		//lostPercent60 = OrgSize*((float)OrgSize/((float)h60TrueCount)); //wersja procentów z zapisem, o którym wspomina³ Szadkowski
    	} else {
    		lostPercent60 = (float) 0.0;
    	}
    		
    	if (h80TrueCount>0)
    	{
    		lostPercent80 = OrgSize-(((float)h60TrueCount)/(float)OrgSize); //analogicznie, j.w.
    		//lostPercent80 = OrgSize*((float)OrgSize/((float)h80TrueCount));
    		
    	} else {
    		lostPercent80 = (float) 0.0;
    	}
    	
    	//wyrzucenie na ekran % utraty pickow.
    	
    	//System.out.printf("Procent utraconych pik'ów: filtr 60: %.2f",lostPercent60);
    	//System.out.printf(", filtr 80: %.2f %n",lostPercent80);
    	
    	
    	/*---zapis do pliku - koñcowy wynik w jednej zmiennej string---*/
    	String endScore = "";
    	endScore +="Zbiorczy wynik koncowy dla wszystkich pick'ow:\nDane oryginalne: "+OrgSize+", filtr 60: "+h60TrueCount+", filtr 80: "+h80TrueCount;
    	endScore +="\nProcent utraconych pik'ow: filtr 60: "+lostPercent60+", filtr 80: "+lostPercent80;
    	
    	picks.csvWriter(endScore);
    }

    /**
     * @param numbers
     * @return
     */
    public static DataFrame getMaxValue(List<DataFrame> numbers){
        DataFrame maxValue = numbers.get(0);
        for(int i=1;i < numbers.size();i++){
            if(numbers.get(i).getOrgData() > maxValue.getOrgData()){
                maxValue = numbers.get(i);
            }
        }
        return maxValue;
    }

//    /**
//     *
//     * @return
//     */
//    public List<DataFrame> getFindedPeaks() {
//        return findedPeaks;
//    }
//
//    /**
//     *
//     * @param findedPeaks
//     */
//    public void setFindedPeaks(List<DataFrame> findedPeaks) {
//        this.findedPeaks = findedPeaks;
//    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {    	
        return "PeakFinder{" +
                "peaksToAlanyse=" + peaksToAnalysis +
                ", findedPeaks=" + findedPeaks +
                ", border=" + border +
                '}';
    }
}