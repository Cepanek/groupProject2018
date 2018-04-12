package main.Tools;



/**
 * Obiekt do analizy
 *
 * @TODO
 * - opakować w funkcje
 *
 * orgData - oryginalne dane
 * f60Data - po filtrze 60MHz;
 * f80Data - po filtrze 80MHz;
 * isHit- jeżeli trafienie we wszystkich 3, to TRUE;
 * isLost- jeżeli trafienie tylko oryginalne, a spłaszczone choć w jednym- TRUE
 *
 *
 */
public class DataFrame {
    private float orgData;
    private float f60Data;
    private float f80Data;
    private boolean isHit;
    private boolean isLost;

    /**
     *
     * @param orgData
     * @param f60Data
     * @param f80Data
     */
    public DataFrame(float orgData, float f60Data, float f80Data) {
        this.orgData = orgData;
        this.f60Data = f60Data;
        this.f80Data = f80Data;
    }

    /**
     *
     * @return
     */
    public float getOrgData() {
        return orgData;
    }

    /**
     *
     * @param orgData
     */
    public void setOrgData(float orgData) {
        this.orgData = orgData;
    }

    /**
     *
     * @return
     */
    public float getF60Data() {
        return f60Data;
    }

    /**
     *
     * @param f60Data
     */
    public void setF60Data(float f60Data) {
        this.f60Data = f60Data;
    }

    /**
     *
     * @return
     */
    public float getF80Data() {
        return f80Data;
    }

    /**
     *
     * @param f80Data
     */
    public void setF80Data(float f80Data) {
        this.f80Data = f80Data;
    }

    /**
     *
     * @return
     */
    public boolean isHit() {
        return isHit;
    }

    /**
     *
     * @param hit
     */
    public void setHit(boolean hit) {
        isHit = hit;
    }

    /**
     *
     * @return
     */
    public boolean isLost() {
        return isLost;
    }

    /**
     *
     * @param lost
     */
    public void setLost(boolean lost) {
        isLost = lost;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "DataFrame{" +
                "orgData=" + orgData +
                ", f60Data=" + f60Data +
                ", f80Data=" + f80Data +
                ", isHit=" + isHit +
                ", isLost=" + isLost +
                '}';
    }
}