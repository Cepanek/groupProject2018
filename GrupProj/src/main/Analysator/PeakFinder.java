package main.Analysator;

import main.Statistics.Results;
import main.Tools.DataFrame;
import main.Tools.SaveFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Analizator peaków
 * #peaksToAnalysis to lista obiektów z danymi to szukania w nich pików
 * @TODO
 * - na podstawie wyznaczonego szumu wyznacz piki
 * - oznacz piki porównując sygnał oryginalny z przefiltrowanymi
 * - zmień flagę w DataFrame || odkopiuj DataFrame do nowej listy
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
    private static final Results picks = new Results();
    private Float border; //szumy
    private Float localMin;
    private Float localMax;

    /**
     * @param peaksToAlanyse
     * @param border
     */
    public PeakFinder(List<DataFrame> peaksToAlanyse, Float border, String fileName) {
        this.peaksToAnalysis = peaksToAlanyse;
        this.border = border;
        this.fileName = fileName;
    }

    /**
     * Funkcja przyjmuje:
     * @param whichFlow informacja o tym, które dane ma badać (oryginalne, po filtrze 60, po filtrze 80)
     * @param ii informacja o tym, od którego indeksu rozpocząć analizę
     * @param lookForPeak informacja o tym, czy ma szukać piku
     * Znalezione piki dodawane są do obiektu #findedPeak
     * Najmniejsza wartość jaką mogą przyjąć zmienne #localMin oraz #localMax to granica szumu #border
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
                    /** Pobierane wartości mają tendecję malejącą */
                    localMax = dataToAnalysis;

                }else if(dataToAnalysis<localMax){
                    /** Pobierane wartości mają tendecję malejącą */
                    localMin = dataToAnalysis;
                    Float heightDiff = localMax - localMin;
                    /**
                     * Procentowa część spadku w stosunku do wzrostu. Limit poniżej, którego znalezione teoretyczne
                     * piki będą odrzucane. Parametr definiowany na początku jako #LIMIT.
                     */
                    Float heightBorder = (localMax*LIMIT)/100;
                    if(heightDiff > heightBorder){
                    	Boolean hOrg=false;
                    	Boolean h60=false;
                    	Boolean h80=false;

                        if(whichFlow == ORIGINAL) {
                            findedPeaks.put(i, peaksToAnalysis.get(i));
                            findedPeaks.get(i).setHitOrg(true);
                            lookForPeak = true;
                            hOrg=true;
                            findPeak(FILTER_60, i, lookForPeak);
                        }else if(whichFlow == FILTER_60 && lookForPeak == true) {
                            h60=true;
                            findedPeaks.get(ii).setHit60(true);
                            findPeak(FILTER_80, ii , lookForPeak);
                        }else if(whichFlow == FILTER_80 && lookForPeak == true) {
                            findedPeaks.get(ii).setHit80(true);
                            h80=true;
                            lookForPeak = false;
                        }
                        picks.addData(hOrg, h60, h80);
                    }
                }
            }
        }
        SaveFile s = new SaveFile(findedPeaks, fileName);
        peakAnalysis(picks); //tutaj przy każdym piku ta metoda jest uruchamiana
    }

    /**
     * Metoda do analizowania pików
     * @param picks
     */
    public void peakAnalysis(Results picks){
    	//Integer OrgSize = picks.getHitOrg().size(); //ilosc wszystkich pickow
    	Integer OrgSize = picks.trueCounter(picks.getHitOrg()); //ilosc wszystkich pickow
    	Integer h60TrueCount = picks.trueCounter(picks.getHit60()); //ilosc trafien filtr-60
    	Integer h80TrueCount = picks.trueCounter(picks.getHit80()); //ilosc trafien filtr-80

    	//wyrzucenie na ekran liczby pickow.
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

    	//wyrzucenie na ekran % utraty pickow.

    	//System.out.printf("Procent utraconych pik'ów: filtr 60: %.2f",lostPercent60);
    	//System.out.printf(", filtr 80: %.2f %n",lostPercent80);

    	/*---zapis do pliku - końcowy wynik w jednej zmiennej string---*/
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

    /**
     * Funkcja do badania maksymalnej wartości z zakresu, używana gdy interwał jest większy niż 1
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
     * Metoda formatuje dane z otrzymanych pików
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

    public void setFindedPeaks(Map<Integer, DataFrame> findedPeaks) {
        this.findedPeaks = findedPeaks;
    }

    public Map<Integer, DataFrame> getFindedPeaks() {
        return findedPeaks;
    }
}
