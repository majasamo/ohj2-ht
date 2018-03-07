package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * KohteenTekija yhdist�� toisiinsa yhden kohteen ja yhden ty�ntekij�n.
 * @author Marko Moilanen
 * @version 7.3.2018
 */
public class KohteenTekija {

    private int kohteenTekijaId;
    private final int tyolainenId;
    private final int kohdeId;
    
    private static int seuraaravaId = 1;
    
    
    /**
     * Luo uuden kohteen tekij�n.
     * @param tyontekija ty�tekij�, joka liitet��n kohteeseen
     * @param kohde kohde, joka liitet��n ty�ntekij��n
     */
    public KohteenTekija(Tyontekija tyontekija, Kohde kohde) {
        this.rekisteroi();
        this.tyolainenId = tyontekija.getId();
        this.kohdeId = kohde.getId();
    }
    
    
    /**
     * Antaa kohteen tekij�lle id:n.
     * TODO: public vai private?
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
     *   KohteenTekija tekija1 = new KohteenTekija(virtanen1, kohde1);
     *   KohteenTekija tekija2 = new KohteenTekija(virtanen1, kohde2);
     *   KohteenTekija tekija3 = new KohteenTekija(virtanen2, kohde2);
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
        this.kohteenTekijaId = seuraaravaId;
        seuraaravaId++;
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
     * Tulostaa ty�ntekij�n ja kohteen tiedot.
     * TODO: testit
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.kohteenTekijaId);
        out.println("Tekij�: " + this.tyolainenId);
        out.println("Kohde: " + this.kohdeId);
    }
    
    
    /**
     * Tulostaa ty�tekij�n ja kohteen tiedot.
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * P��ohjelma testaamista varten.
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Tyontekija virtanen1 = new Tyontekija();
        Tyontekija virtanen2 = new Tyontekija();
        virtanen1.rekisteroi();
        virtanen2.rekisteroi();
        Kohde kohde1 = new Kohde();
        Kohde kohde2 = new Kohde();
        kohde1.rekisteroi();
        kohde2.rekisteroi();
        
        KohteenTekija tekija1 = new KohteenTekija(virtanen1, kohde1);
        tekija1.tulosta(System.out);
        tekija1.rekisteroi();
        tekija1.tulosta(System.out);
        
        System.out.println("**********");
        
        KohteenTekija tekija2 = new KohteenTekija(virtanen1, kohde2);
        tekija2.tulosta(System.out);
        tekija2.rekisteroi();
        tekija2.tulosta(System.out);
        
        System.out.println("**********");
        
        KohteenTekija tekija3 = new KohteenTekija(virtanen2, kohde2);
        tekija3.tulosta(System.out);
        tekija3.rekisteroi();
        tekija3.tulosta(System.out);
        tekija3.rekisteroi();
        tekija3.tulosta(System.out);
    }

}
