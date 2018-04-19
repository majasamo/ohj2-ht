package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * KohteenTekija yhdist�� toisiinsa yhden kohteen ja yhden ty�ntekij�n.
 * @author Marko Moilanen
 * @version 19.4.2018
 */
public class KohteenTekija {

    private int kohteenTekijaId;
    private int tyolainenId;
    private int kohdeId;
    
    private static int seuraavaId = 1;
    
    
    /**
     * Luo uuden kohteen tekij�n.
     * @param tyontekija ty�tekij�, joka liitet��n kohteeseen
     * @param kohde kohde, joka liitet��n ty�ntekij��n
     */
    public KohteenTekija(Tyontekija tyontekija, Kohde kohde) {
        this(tyontekija.getId(), kohde.getId());  
    }
    
    
    /**
     * Luo uuden kohteen tekij�n.
     * @param tyolainenId kohteeseen liitett�v�n ty�ntekij�n id-numero
     * @param kohdeId ty�ntekij��n liitett�v�n kohteen id-numero
     */
    public KohteenTekija(int tyolainenId, int kohdeId) {
        //this.rekisteroi();  // TODO: Pit��k�h�n t�m� tehd� muualla?
        this.tyolainenId = tyolainenId;
        this.kohdeId = kohdeId;
    }
    
    
    /**
     * Luo uuden kohteen tekij�n.
     */
    public KohteenTekija() { }
    
    
    /**
     * Antaa kohteen tekij�lle id:n.
     * @example
     * <pre name="test">
     *   Tyontekija virtanen1 = new Tyontekija();
     *   virtanen1.rekisteroi();
     *   Tyontekija virtanen2 = new Tyontekija();
     *   virtanen2.rekisteroi();
     *   Kohde kohde1 = new Kohde();
     *   kohde1.rekisteroi();
     *   Kohde kohde2 = new Kohde();
     *   kohde2.rekisteroi();
     *   
     *   KohteenTekija tekija1 = new KohteenTekija(virtanen1, kohde1); tekija1.rekisteroi();
     *   KohteenTekija tekija2 = new KohteenTekija(virtanen1, kohde2); tekija2.rekisteroi();
     *   KohteenTekija tekija3 = new KohteenTekija(virtanen2, kohde2); tekija3.rekisteroi();
     *   
     *   // Per�kk�in rekister�idyill� olioilla on per�kk�iset id:t.
     *   int id1 = tekija1.getId();
     *   int id2 = tekija2.getId();
     *   id2 - id1 === 1;
     *   
     *   // Uudelleenrekister�inti ei muuta mit��n.
     *   tekija1.rekisteroi();
     *   tekija1.getId() === id1;
     *   
     *   tekija1.getTyolainenId() === virtanen1.getId();
     *   tekija1.getKohdeId() === kohde1.getId();
     *   tekija2.getTyolainenId() === virtanen1.getId();
     *   tekija2.getKohdeId() === kohde2.getId();
     *   tekija3.getTyolainenId() === virtanen2.getId();
     *   tekija3.getKohdeId() === kohde2.getId();
     * </pre>
     */
    public void rekisteroi() {
        if (this.kohteenTekijaId != 0) return;
        this.kohteenTekijaId = seuraavaId;
        seuraavaId++;
    }
    
    
    /**
     * Palauttaa id:n.
     * @return kohteen tekij�n id-numero
     */
    public int getId() {
        return this.kohteenTekijaId;
    }
    
    
    /**
     * Palauttaa ty�ntekij�n id:n.
     * @return ty�ntekij�n id-numero
     */
    public int getTyolainenId() {
        return this.tyolainenId;
    }
    
    
    /**
     * Palauttaa kohteen id:n.
     * @return kohteen id-numero
     */
    public int getKohdeId() {
        return this.kohdeId;
    }


    /**
     * Asettaa kohteen tekij�n id-numeron. Samalla varmistetaan, ett�
     * seuraavaksi annettava id on ajan tasalla.
     * @param numero
     */
    private void setId(int numero) {
        this.kohteenTekijaId = numero;
        if (this.kohteenTekijaId >= seuraavaId) seuraavaId = this.kohteenTekijaId + 1;
    }    
    
    
    /**
     * Selvitt�� ja tallentaa kohteentekij�n tiedot tolppamerkein erotellusta
     * merkkijonosta.
     * @param tiedot kohteentekij�n tiedot tolppamerkein eroteltuna
     */
    public void parse(String tiedot) {
        StringBuilder rivi = new StringBuilder(tiedot);
        this.setId(Mjonot.erota(rivi, '|', this.kohteenTekijaId));
        this.tyolainenId = Mjonot.erota(rivi, '|', this.tyolainenId);
        this.kohdeId = Mjonot.erota(rivi, '|', this.kohdeId);
    }
    
    
    /**
     * Palauttaa kohteentekij�n tiedot tolppamerkein eroteltuna merkkijonona.
     * @return kohteentekij�n tiedot merkkijonona
     * @example 
     * <pre name="test">
     *   KohteenTekija kt = new KohteenTekija();
     *   kt.parse("  10  | 12   | 3  ");
     *   kt.toString() === "10|12|3";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + this.kohteenTekijaId + "|"
                  + this.tyolainenId + "|"
                  + this.kohdeId;
    }
    
    
    /**
     * Tulostaa ty�ntekij�n ja kohteen id:t.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.kohteenTekijaId);
        out.println("Tekij�: " + this.tyolainenId);
        out.println("Kohde: " + this.kohdeId);
    }
    
    
    /**
     * Tulostaa ty�tekij�n ja kohteen id:t.
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }        
}
