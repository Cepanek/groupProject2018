package main.Analysator;

import main.Tools.DataFrame;

/**
 * Porownaj piki z kanalow
 *
 * @TODO
 * - jezeli pik wystepuje w 3 miejscach- odkopiuj do nowej listy trafien potwierdzonych
 */
public class ChanelComparator {

    private DataFrame ch1;
    private DataFrame ch2;
    private DataFrame ch3;

    /**
     *
     * @param ch1
     * @param ch2
     * @param ch3
     */
    public ChanelComparator(DataFrame ch1, DataFrame ch2, DataFrame ch3) {
        this.ch1 = ch1;
        this.ch2 = ch2;
        this.ch3 = ch3;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "ChanelComparator{" +
                "ch1=" + ch1 +
                ", ch2=" + ch2 +
                ", ch3=" + ch3 +
                '}';
    }
}
