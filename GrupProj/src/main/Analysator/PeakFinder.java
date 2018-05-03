package main.Analysator;

import main.Tools.DataFrame;
import main.Tools.SaveFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Analizator peaków
 *
 * @TODO
 * - na podstawie wyznaczonego szumu wyznacz piki
 * - oznacz piki porównując sygnał oryginalny z przefiltrowanymi
 * - zmień flagę w DataFrame || odkopiuj DataFrame do nowej listy
 */
public class PeakFinder {
    private static final Integer INTERVAL = 5;
    private static final Float LIMIT = 5.0f;
    private static final String ORIGINAL = "org";
    private static final String FILTER_60 = "60";
    private static final String FILTER_80 = "80";
    private List<DataFrame> peaksToAnalysis; //dane do badania
    private String fileName;

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
    private Float localSecondMax;
    private int tempI;

    /**
     *
     * @param peaksToAlanyse
     * @param border
     */
    public PeakFinder(List<DataFrame> peaksToAlanyse, Float border, String fileName) {
        this.peaksToAnalysis = peaksToAlanyse;
        this.border = border;
        this.fileName = fileName;
    }

    public void findPeak(String whichFlow, int ii, boolean findAll) throws IOException {
        int datasize = peaksToAnalysis.size();
//        System.out.println("Rozmiar pliku: "+datasize);

        localMin = border;
        localSecondMax = border;
        localMax = border; //namniejsza wartosc jaka moze być to granica szumu
        int end = datasize;
        int jump = INTERVAL;


        if(whichFlow == ORIGINAL){
             end = datasize;
             jump = INTERVAL;
        }else if(whichFlow == FILTER_60  || whichFlow == FILTER_80){
             end = 15;
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
                continue; //wynik do odrzucenia bo jets poniżej szumu
            }else{
//                System.out.println("else 1 ");
                if(dataToAnalysis>localMin && dataToAnalysis >= peaksToAnalysis.get(i-10).getOrgData()){
//                    System.out.println("if 2 ");
                    //caly czas idziemy do góry
                    localMax = dataToAnalysis;
//                    System.out.println("Max: "+localMax);

                }else if(dataToAnalysis<localMax){
//                    System.out.println("else 2 ");
                    //cały czas idziemy w dół
                    localMin = dataToAnalysis;
//                    System.out.println("Min: "+localMin);
                    Float heightDiff = localMax - localMin;
                    Float heightBorder = (localMax*LIMIT)/100;
//                    System.out.println("--- Diff: "+heightDiff);
                    if(heightDiff > heightBorder){
//                        System.out.println("--------LOOOL-------");
                        if(whichFlow == ORIGINAL) {
                            System.out.println("####### org ######");
                            //DataFrame peak = getMaxValue(peaksToAnalysis.subList(i - INTERVAL, i + INTERVAL));
                            findedPeaks.put(i, peaksToAnalysis.get(i));
                            findedPeaks.get(i).setHitOrg(true);
                            System.out.println("--------");
                            findAll = true;
                            findPeak(FILTER_60, i, findAll);
                        }else if(whichFlow == FILTER_60 && findAll == true) { //od tego miejsca sprawdzamy pierwszy filtr
                            //isHit
                            System.out.println("     ####### 60 ######");
                            System.out.println(findedPeaks.get(ii).toString());
                            findedPeaks.get(ii).setHit60(true);
                            findPeak(FILTER_80, ii , findAll);
                        }else if(whichFlow == FILTER_80 && findAll == true) { //od tego miejsca sprawdzamy grugi filtr
                            //isHit
                            System.out.println("           ####### 80 ######");
                            findedPeaks.get(ii).setHit80(true);
                            findAll = false;
                        }
                    }
                }
            }
        }
       // peakAnalysis(findedPeaks);
        System.out.println(findedPeaks.values().toString());
        SaveFile s = new SaveFile(findedPeaks, fileName);
    }

    public void peakAnalysis(List<DataFrame> findedPeaks){
        //tu bedzie gruba analiza pików
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
