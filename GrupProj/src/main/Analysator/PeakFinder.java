package main.Analysator;

import main.Tools.DataFrame;
import main.Tools.SaveFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Agnieszka Ceran, Mateusz Marchelewicz, Łukasz Janus, Łukasz Gwozdowski
 * @2018
 *
 * Analizator peakow
 * #peaksToAnalysis to lista obiektow z danymi to szukania w nich pikow
 *
 * LIMIT to informacja z jaką różnicą procentową pomiedzy lokalnym maksimum i minimum, maksimum traktowane jest jako peak, lub pomijane, jako szum/odbicie
 */
public class PeakFinder {
    private static final Integer INTERVAL = 1;
    private static final Float LIMIT = 5.0f;
    private static final String ORIGINAL = "org";
    private static final String FILTER_60 = "60";
    private static final String FILTER_80 = "80";
    private List<DataFrame> peaksToAnalysis;
    private String fileName;
    private Map<Integer, DataFrame> findedPeaks = new HashMap<>();

    private Float border; //szumy
    private Float localMin;
    private Float localMax;

    /**
     * @param peaksToAlanyse
     * @param border
     * @param fileName
     */
    public PeakFinder(List<DataFrame> peaksToAlanyse, Float border, String fileName) {
        this.peaksToAnalysis = peaksToAlanyse;
        this.border = border;
        this.fileName = fileName;
    }

    /**
     * Funkcja przyjmuje:
     * @param whichFlow informacja o tym, ktore dane ma badal (oryginalne, po filtrze 60, po filtrze 80)
     * @param ii informacja o tym, od ktorego indeksu rozpoczac analize
     * @param lookForPeak informacja o tym, czy ma szukac piku
     * Znalezione piki dodawane sa do obiektu #findedPeak
     * Najmniejsza wartosc jaka moga przyjac zmienne #localMin oraz #localMax to granica szumu #border
     *
     *
     *
     * @throws IOException
     */
    public void findPeak(String whichFlow, int ii, boolean lookForPeak) throws IOException {
        int datasize = peaksToAnalysis.size();
        int end = datasize;
        int jump = INTERVAL;
        localMin = border;
        localMax = border;

        if(whichFlow == ORIGINAL){
             end = datasize;
             jump = INTERVAL;
        }else if(whichFlow == FILTER_60  || whichFlow == FILTER_80){
             end = 100;
             jump = 1;
        }else{
            System.out.println("Parameter whichFlow is null or empty or value incorrect");
        }

        for(int i= ii; i<end; i+=jump){
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
            if(dataToAnalysis < border){
                continue;
            }else{
                if(dataToAnalysis>localMin && dataToAnalysis >= peaksToAnalysis.get(i-INTERVAL).getOrgData()){
                    /** Pobierane wartosci maja tendecje malejaca */
                    localMax = dataToAnalysis;

                }else if(dataToAnalysis<localMax){
                    /** Pobierane wartosci maja tendecje malejaca� */
                    localMin = dataToAnalysis;
                    Float heightDiff = localMax - localMin;
                    /**
                     * Procentowa czesc spadku w stosunku do wzrostu. Limit ponizej, ktorego znalezione teoretyczne
                     * piki beda odrzucane. Parametr definiowany na poczatku jako #LIMIT.
                     */
                    Float heightBorder = (localMax*LIMIT)/100;
                    if(heightDiff > heightBorder){
                    	
                        if(whichFlow == ORIGINAL) {
                            findedPeaks.put(i, peaksToAnalysis.get(i));
                            findedPeaks.get(i).setHitOrg(true);
                            lookForPeak = true;
                            findPeak(FILTER_60, i, lookForPeak);
                        }else if(whichFlow == FILTER_60 && lookForPeak == true) {
                            findedPeaks.get(ii).setHit60(true);
                            findPeak(FILTER_80, ii , lookForPeak);
                        }else if(whichFlow == FILTER_80 && lookForPeak == true) {
                            findedPeaks.get(ii).setHit80(true);
                            lookForPeak = false;
                        }
                    }
                }
            }
        }
        SaveFile s = new SaveFile(findedPeaks, fileName);
    }


    /**
     * Funkcja do badania maksymalnej wartosci z zakresu, uzywana gdy interwal� jest wiekszy niz 1
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

    /**
     * Metoda formatuje dane z otrzymanych peakow
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

    /**
     *
     * @param findedPeaks
     */
    public void setFindedPeaks(Map<Integer, DataFrame> findedPeaks) {
        this.findedPeaks = findedPeaks;
    }

    /**
     *
     * @return
     */
    public Map<Integer, DataFrame> getFindedPeaks() {
        return findedPeaks;
    }
}
