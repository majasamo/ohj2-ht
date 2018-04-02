package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Työntekijä tietää omat tietonsa ja id-numeronsa (eri kuin henkilönumero).
 * @author Marko Moilanen
 * @version 20.3.2018
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
     * Antaa satunnaisen kokonaisluvun annetulta väliltä
     * TODO: tämä metodi poistetaan, kun sitä ei tarvita.
     * @param ala alaraja
     * @param yla yläraja
     * @return satunnaisluku väliltä [ala, yla[
     */
    public static int rand(int ala, int yla) {
        double n = (yla - ala) * Math.random() + ala; 
        return (int) Math.round(n);
    }
    
    
    /**
     * Tarkastaa henkilönumeron oikeellisuuden.
     * @param hlonro tarkastettava henkilönumero
     * @return true, jos henkilönumero on oikea
     * @example
     * <pre name="test">
     *   tarkastaHlonro(5) === false;
     *   tarkastaHlonro(-1000) === false;
     *   tarkastaHlonro(1000) === true;
     *   tarkastaHlonro(999) === false;
     *   tarkastaHlonro(1001) === true;
     *   tarkastaHlonro(-1001) === false;
     *   tarkastaHlonro(7508) === true;
     *   tarkastaHlonro(-7508) === false;
     *   tarkastaHlonro(9999) === true;
     *   tarkastaHlonro(-9999) === false;
     *   tarkastaHlonro(10000) === false;
     *   tarkastaHlonro(-10000) === false;
     *   tarkastaHlonro(10001) === false;
     *   tarkastaHlonro(-10001) === false;
     * </pre>
     */
    public static boolean tarkastaHlonro(int hlonro) {
        return (1000 <= hlonro && hlonro <= 9999);
    }
    
    
    /**
     * Apumetodi, joka täyttää testiarvot.
     * Henkilönumero arvotaan.
     * TODO: tämä metodi poistetaan, kun sitä ei enää tarvita.
     */
    public void taytaTiedot() {
        this.nimi = "Hermanson Taavi-Ernesti";
        this.hlonumero = rand(1000, 9999);
        this.aloitusvuosi = 2012;
        this.koulutus = "";
        this.lisatietoja = "";
    }
    
         
    /**
     * Tulostaa työntekijän tiedot.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.tyolainenId + " " + this.nimi + " " + this.hlonumero);
        out.println("aloittanut " + this.aloitusvuosi);
        out.println("koulutus: " + this.koulutus);
        out.println("lisätietoja: " + this.lisatietoja);
    }
    
    
    /**
     * Tulostaa työntekijän tiedot.
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Antaa työntekijälle id:n.
     * @example
     * <pre name="test">
     *   Tyontekija virtanen = new Tyontekija();
     *   virtanen.getId() === 0;
     *   virtanen.rekisteroi();
     *   Tyontekija mottonen = new Tyontekija();
     *   mottonen.getId() === 0;
     *   mottonen.rekisteroi();
     *   
     *   // Peräkkäin rekisteröityjen työntekijöiden id:t ovat peräkkäisiä.
     *   int idV = virtanen.getId();
     *   int idM = mottonen.getId();
     *   idV === idM - 1;
     *   
     *   // Jo rekisteröidyn työntekijän id ei muutu.
     *   virtanen.rekisteroi();
     *   virtanen.getId() === idV;
     * </pre>
     */
    public void rekisteroi() {
        if (this.getId() != 0) return;  // Jos id on jo annettu, ei tehdä mitään.
        this.tyolainenId = seuraavaId;
        seuraavaId++;
        // TODO: Kun tiedosto luetaan, kuinka saadaan seuraavaId pidettyä oikeana?
        // Nykyisellä toteutuksella homma ei toimi.
    }
    
    
    /**
     * Palauttaa työntekijän id:n.
     * @return työntekijän id-numero
     */
    public int getId() {
        return this.tyolainenId;
    }


    /**
     * Palauttaa työntekijän nimen.
     * TODO: jos tämän jossakin vaiheessa voi testata, niin tee testit.
     * @return työntekijän nimi merkkijonona
     */
    public String getNimi() {
        return this.nimi;
    }

    
    /**
     * Pääohjelma, jossa testataan luokkaa.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tyontekija mottonen = new Tyontekija();
        Tyontekija virtanen = new Tyontekija();
        
        mottonen.rekisteroi();
        virtanen.rekisteroi();
        
        mottonen.tulosta(System.out); System.out.println();
        virtanen.tulosta(System.out); System.out.println();
        
        mottonen.taytaTiedot();
        virtanen.taytaTiedot();
        
        mottonen.tulosta(System.out); System.out.println();
        virtanen.tulosta(System.out); System.out.println();
    }
}
