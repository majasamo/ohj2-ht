package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Ty�ntekij�-luokka.
 * @author Marko Moilanen
 * @version 22.2.2018
 *
 */
public class Tyontekija {
    
    private int tyolainenId;
    private String nimi = "";
    private int hlonumero;
    private int aloitusvuosi;
    private String koulutus = "";
    private String lisatietoja = "";
    
    private static int seuraavaId = 1;
    
    /**
     * Apumetodi, joka t�ytt�� testiarvot.
     */
    public void taytaTiedot() {
        this.nimi = "Hermanson Taavi-Ernesti";
        this.hlonumero = 0;
        this.aloitusvuosi = 2012;
        this.koulutus = "";
        this.lisatietoja = "";
    }
    
         
    /**
     * Tuostaa ty�ntekij�n tiedot.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("virtanen");
        //TODO: korjaa (tarvitaanko t�t�?)
    }
    
    
    /**
     * Tulostaa ty�ntekij�n tiedot.
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Antaa ty�ntekij�lle id:n.
     * @example
     * <pre name="test">
     *   Tyontekija virtanen = new Tyontekija();
     *   virtanen.getId() === 0;
     *   virtanen.rekisteroi();
     *   Tyontekija mottonen = new Tyontekija();
     *   mottonen.getId() === 0;
     *   mottonen.rekisteroi();
     *   int nv = virtanen.getId();
     *   int nm = mottonen.getId();
     *   nv === nm - 1;
     *   virtanen.rekisteroi();
     *   virtanen.getId() === nv;
     * </pre>
     */
    public void rekisteroi() {
        if (this.getId() != 0) return;  // Jos id on jo olemassa, ei tehd� mit��n.
        this.tyolainenId = seuraavaId;
        seuraavaId++;
        // TODO: Kun tiedosto luetaan, kuinka saadaan seuraavaId pidetty� oikeana?
        // Nykyisell� toteutuksella homma ei toimi.
    }
    
    
    /**
     * Palauttaa ty�ntekij�n id:n.
     * @return ty�ntekij�n id-numero
     */
    public int getId() {
        return this.tyolainenId;
    }
        
    
    /**
     * P��ohjelma, jossa testataan luokkaa.
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Tyontekija mottonen = new Tyontekija();
        Tyontekija virtanen = new Tyontekija();
        mottonen.rekisteroi();
        virtanen.rekisteroi();
        
        mottonen.tulosta(System.out);
        virtanen.tulosta(System.out);
        
        mottonen.taytaTiedot();
        virtanen.taytaTiedot();
        
        mottonen.tulosta(System.out);
        virtanen.tulosta(System.out);
    }
}
