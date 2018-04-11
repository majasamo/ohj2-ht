package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Ty�ntekij� tiet�� omat tietonsa ja id-numeronsa (eri kuin henkil�numero).
 * @author Marko Moilanen
 * @version 11.4.2018
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
     * Asettaa ty�ntekij�n id-numeron. Samalla varmistetaan, ett�
     * seuraavaksi annettava id on ajan tasalla.
     * @param numero
     */
    private void setId(int numero) {
        this.tyolainenId = numero;
        if (this.tyolainenId >= seuraavaId) seuraavaId = this.tyolainenId + 1;
    }
    
    
    /**
     * Selvitt�� ja tallentaa ty�ntekij�n tiedot tolppamerkein erotellusta merkkijonosta.
     * @param tiedot ty�ntekij�n tiedot tolppamerkein erotettuna
     */
    public void parse(String tiedot) {
        StringBuilder rivi = new StringBuilder(tiedot);
        
        // Luetaan tiedot. String-muuttujista ylim��r�iset v�lily�nnit pois,
        // int-muuttujien oletusarvoiksi t�m�nhetkinen arvo (eli luultavasti 0).
        this.setId(Mjonot.erota(rivi, '|', this.tyolainenId));
        this.nimi = Mjonot.erota(rivi, '|').trim();
        this.hlonumero = Mjonot.erota(rivi, '|', this.hlonumero);
        this.aloitusvuosi = Mjonot.erota(rivi, '|', this.aloitusvuosi);
        this.koulutus = Mjonot.erota(rivi, '|').trim();
        this.lisatietoja = Mjonot.erota(rivi, '|').trim();
    }
    
    
    /**
     * Palauttaa ty�ntekij�n tiedot tolppamerkein eroteltuna merkkijonona.
     * @return ty�ntekij�n tiedot
     * @example 
     * <pre name="test">
     *   Tyontekija virtanen = new Tyontekija();
     *   virtanen.parse(" 18 | Virtanen Matti |");
     *   virtanen.toString() === "18|Virtanen Matti|0|0||";
     *   
     *   Tyontekija mottonen = new Tyontekija();
     *   mottonen.parse("71  | Mottonen Mikko | 2012 | 1995  | ei   ole | ei ole");
     *   mottonen.toString() === "71|Mottonen Mikko|2012|1995|ei   ole|ei ole"; 
     * </pre>
     */
    @Override
    public String toString() {
        return "" + this.getId() + "|"
                  + this.getNimi() + "|"
                  + this.hlonumero + "|"
                  + this.aloitusvuosi + "|"
                  + this.koulutus + "|"
                  + this.lisatietoja;
    }

    
    
    // Saantimetodit k�ytt�liittym�ss� n�ytett�ville attribuuteille. 
    //********************************************************************************
    
    
    /**
     * Palauttaa ty�ntekij�n nimen.
     * TODO: jos t�m�n jossakin vaiheessa voi testata, niin tee testit.
     * @return ty�ntekij�n nimi merkkijonona
     */
    public String getNimi() {
        return this.nimi;
    }

    
    /**
     * Palauttaa ty�ntekij�n henkil�numeron.
     * @return ty�ntekij�n henkil�numero
     */
    public int getHlonumero() {
        return this.hlonumero;
    }
    
    
    /**
     * Palauttaa ty�ntekij�n aloitusvuoden.
     * @return ty�ntekij�n aloitusvuosi
     */
    public int getAloitusvuosi() {
        return this.aloitusvuosi;
    }
    
    
    /**
     * Palauttaa tiedot ty�ntekij�n koulutuksesta.
     * @return ty�ntekij�n koulutustiedot
     */
    public String getKoulutus() {
        return this.koulutus;
    }
    
    
    /**
     * Palauttaa muut mahdolliset ty�ntekij�� koskevat tiedot.
     * @return lis�tietoja-kent�n sis�lt�
     */
    public String getLisatietoja() {
        return this.lisatietoja;
    }
    
    // Viivan yl�puolella saantimetodit k�ytt�liittym�ss� n�ytett�ville attribuuteille.
    //********************************************************************************    

    
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
        
        Tyontekija matti = new Tyontekija();
        matti.parse(" 18 |   Virtanen Matti |");
        System.out.println(matti);
    }
}
