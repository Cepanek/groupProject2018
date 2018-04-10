package main.Analysator;

import main.Tools.DataFrame;
import main.Tools.NoiseReductor;

import java.util.List;

/**
 * Analizator peaków
 *
 * @TODO
 * - na podstawie wyznaczonego szumu wyznacz piki
 * - oznacz piki porównując sygnał oryginalny z przefiltrowanymi
 * - zmień flagę w DataFrame || odkopiuj DataFrame do nowej listy
 */
public class PeakFinder {
    private List<DataFrame> peaksToAlanyse;
    private List<DataFrame> findedPeaks;
    private NoiseReductor border;

    /**
     *
     * @param peaksToAlanyse
     * @param border
     */
    public PeakFinder(List<DataFrame> peaksToAlanyse, NoiseReductor border) {
        this.peaksToAlanyse = peaksToAlanyse;
        this.border = border;
    }

    /**
     *
     * @return
     */
    public List<DataFrame> getFindedPeaks() {
        return findedPeaks;
    }

    /**
     *
     * @param findedPeaks
     */
    public void setFindedPeaks(List<DataFrame> findedPeaks) {
        this.findedPeaks = findedPeaks;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "PeakFinder{" +
                "peaksToAlanyse=" + peaksToAlanyse +
                ", findedPeaks=" + findedPeaks +
                ", border=" + border +
                '}';
    }
}
