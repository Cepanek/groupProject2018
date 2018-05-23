package main.Tools;


import java.text.DecimalFormat;

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
    private boolean isHitOrg;
    private boolean isHit60;
    private boolean isHit80;

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
    * Konstruktor testowo dorzucony przy probie odczytu plikow wynikowych, do usuniecia
    * @param orgData
    * @param f60Data
    * @param f80Data
    * @param orgData
    * @param f60Data
    * @param f80Data
    */
   public DataFrame(float orgData, float f60Data, float f80Data, boolean bisHitOrg, boolean bisHit60, boolean bisHit80 ) {
       this.orgData = orgData;
       this.f60Data = f60Data;
       this.f80Data = f80Data;
       this.isHitOrg = bisHitOrg;
       this.isHit60 = bisHit60;
       this.isHit80 = bisHit80;
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
    public boolean isHit60() {
        return isHit60;
    }

    /**
     *
     * @param hit
     */
    public void setHit60(boolean hit) {
        isHit60 = hit;
    }
    /**
     *
     * @return
     */
    public boolean isHit80() {
        return isHit80;
    }

    /**
     *
     * @param hit
     */
    public void setHit80(boolean hit) {
        isHit80 = hit;
    }
    /**
     *
     * @return
     */
    public boolean isHitOrg() {
        return isHitOrg;
    }

    /**
     *
     * @param hit
     */
    public void setHitOrg(boolean hit) {
        isHitOrg = hit;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "DataFrame{" +
                "orgData=" + orgData +
                ", f60Data=" + f60Data +
                ", f80Data=" + f80Data +
                ", isHitOrg=" + isHitOrg +
                ", isHit60=" + isHit60 +
                ", isHit80=" + isHit80 +
                '}';
    }

    /**
     * @return
     */
    public String toCsv() {
        DecimalFormat df = new DecimalFormat("#,###,###,###.0000");
        return
                df.format(orgData) + ", "+
                df.format(f60Data) + ", "+
                df.format(f80Data) + ", "+
                isHitOrg + ", "+
                isHit60 + ", "+
                isHit80 + "\n";
    }
}
