package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Ty�ntekij� tiet�� omat tietonsa ja id-numeronsa (eri kuin henkil�numero).
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
     * Antaa satunnaisen kokonaisluvun annetulta v�lilt�
     * TODO: t�m� metodi poistetaan, kun sit� ei tarvita.
     * @param ala alaraja
     * @param yla yl�raja
     * @return satunnaisluku v�lilt� [ala, yla[
     */
    public static int rand(int ala, int yla) {
        double n = (yla - ala) * Math.random() + ala; 
        return (int) Math.round(n);
    }
    
    
    /**
     * Tarkastaa henkil�numeron oikeellisuuden.
     * @param hlonro tarkastettava henkil�numero
     * @return true, jos henkil�numero on oikea
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
     * Apumetodi, joka t�ytt�� testiarvot.
     * Henkil�numero arvotaan.
     * TODO: t�m� metodi poistetaan, kun sit� ei en�� tarvita.
     */
    public void taytaTiedot() {
        this.nimi = "Hermanson Taavi-Ernesti";
        this.hlonumero = rand(1000, 9999);
        this.aloitusvuosi = 2012;
        this.koulutus = "";
        this.lisatietoja = "";
    }
    
         
    /**
     * Tulostaa ty�ntekij�n tiedot.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.tyolainenId + " " + this.nimi + " " + this.hlonumero);
        out.println("aloittanut " + this.aloitusvuosi);
        out.println("koulutus: " + this.koulutus);
        out.println("lis�tietoja: " + this.lisatietoja);
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
     *   
     *   // Per�kk�in rekister�ityjen ty�ntekij�iden id:t ovat per�kk�isi�.
     *   int idV = virtanen.getId();
     *   int idM = mottonen.getId();
     *   idV === idM - 1;
     *   
     *   // Jo rekister�idyn ty�ntekij�n id ei muutu.
     *   virtanen.rekisteroi();
     *   virtanen.getId() === idV;
     * </pre>
     */
    public void rekisteroi() {
        if (this.getId() != 0) return;  // Jos id on jo annettu, ei tehd� mit��n.
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
     * Palauttaa ty�ntekij�n nimen.
     * TODO: jos t�m�n jossakin vaiheessa voi testata, niin tee testit.
     * @return ty�ntekij�n nimi merkkijonona
     */
    public String getNimi() {
        return this.nimi;
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
        
        mottonen.tulosta(System.out); System.out.println();
        virtanen.tulosta(System.out); System.out.println();
        
        mottonen.taytaTiedot();
        virtanen.taytaTiedot();
        
        mottonen.tulosta(System.out); System.out.println();
        virtanen.tulosta(System.out); System.out.println();
    }
}
