/**



        TO JU� RACZEJ NIE POTRZEBNE

/**/
package main.Tools;

import main.Analysator.PeakFinder;

import java.util.List;

/**
 * Odszumiacz
 *
 * @TODO
 * - policz �redni�
 * - wyznacz pr�g szumu
 */
public class NoiseReductor {

    private List<PeakFinder> peaks;
    private float noiseBorder;


    /**
     *
     * @param peaks
     */
    public NoiseReductor(List<PeakFinder> peaks) {
        this.peaks = peaks;
        this.noiseBorder= 0; //to trzeba bedzie policzyc
   }

    /**
     *
     * @return
     */
    public float getNoiseBorder() {
        return noiseBorder;
    }

    /**
     *
     * @param noiseBorder
     */
    public void setNoiseBorder(float noiseBorder) {
        this.noiseBorder = noiseBorder;
    }
}

/**/