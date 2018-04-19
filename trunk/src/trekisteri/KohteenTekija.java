package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * KohteenTekija yhdistää toisiinsa yhden kohteen ja yhden työntekijän.
 * @author Marko Moilanen
 * @version 19.4.2018
 */
public class KohteenTekija {

    private int kohteenTekijaId;
    private int tyolainenId;
    private int kohdeId;
    
    private static int seuraavaId = 1;
    
    
    /**
     * Luo uuden kohteen tekijän.
     * @param tyontekija työtekijä, joka liitetään kohteeseen
     * @param kohde kohde, joka liitetään työntekijään
     */
    public KohteenTekija(Tyontekija tyontekija, Kohde kohde) {
        this(tyontekija.getId(), kohde.getId());  
    }
    
    
    /**
     * Luo uuden kohteen tekijän.
     * @param tyolainenId kohteeseen liitettävän työntekijän id-numero
     * @param kohdeId työntekijään liitettävän kohteen id-numero
     */
    public KohteenTekija(int tyolainenId, int kohdeId) {
        //this.rekisteroi();  // TODO: Pitääköhän tämä tehdä muualla?
        this.tyolainenId = tyolainenId;
        this.kohdeId = kohdeId;
    }
    
    
    /**
     * Luo uuden kohteen tekijän.
     */
    public KohteenTekija() { }
    
    
    /**
     * Antaa kohteen tekijälle id:n.
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
     *   // Peräkkäin rekisteröidyillä olioilla on peräkkäiset id:t.
     *   int id1 = tekija1.getId();
     *   int id2 = tekija2.getId();
     *   id2 - id1 === 1;
     *   
     *   // Uudelleenrekisteröinti ei muuta mitään.
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
     * @return kohteen tekijän id-numero
     */
    public int getId() {
        return this.kohteenTekijaId;
    }
    
    
    /**
     * Palauttaa työntekijän id:n.
     * @return työntekijän id-numero
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
     * Asettaa kohteen tekijän id-numeron. Samalla varmistetaan, että
     * seuraavaksi annettava id on ajan tasalla.
     * @param numero
     */
    private void setId(int numero) {
        this.kohteenTekijaId = numero;
        if (this.kohteenTekijaId >= seuraavaId) seuraavaId = this.kohteenTekijaId + 1;
    }    
    
    
    /**
     * Selvittää ja tallentaa kohteentekijän tiedot tolppamerkein erotellusta
     * merkkijonosta.
     * @param tiedot kohteentekijän tiedot tolppamerkein eroteltuna
     */
    public void parse(String tiedot) {
        StringBuilder rivi = new StringBuilder(tiedot);
        this.setId(Mjonot.erota(rivi, '|', this.kohteenTekijaId));
        this.tyolainenId = Mjonot.erota(rivi, '|', this.tyolainenId);
        this.kohdeId = Mjonot.erota(rivi, '|', this.kohdeId);
    }
    
    
    /**
     * Palauttaa kohteentekijän tiedot tolppamerkein eroteltuna merkkijonona.
     * @return kohteentekijän tiedot merkkijonona
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
     * Tulostaa työntekijän ja kohteen id:t.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.kohteenTekijaId);
        out.println("Tekijä: " + this.tyolainenId);
        out.println("Kohde: " + this.kohdeId);
    }
    
    
    /**
     * Tulostaa työtekijän ja kohteen id:t.
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }        
}
