package trekisteri;

import java.util.ArrayList;
import java.util.List;

/**
 * Luokka huolehtii muista kuin itse k�ytt�liittym��n kuuluvista asioista.
 * @author Marko Moilanen
 * @version 20.3.2018
 */
public class Rekisteri {

    private final Tyontekijat tyolaiset = new Tyontekijat();
    private final KohteenTekijat kohteenTekijat = new KohteenTekijat();
    private final Kohteet kohteet = new Kohteet();
    
    
    /**
     * Lis�� rekisteriin uuden ty�ntekij�n.
     * @param lisattava ty�ntekij�, joka lis�t��n
     * @throws SailoException jos lis��minen ei onnistu
     * @example
     * <pre name="test">
     *   #THROWS SailoException
     *   Rekisteri rekisteri = new Rekisteri();
     *   Tyontekija virtanen1 = new Tyontekija();
     *   Tyontekija virtanen2 = new Tyontekija();
     *   virtanen1.rekisteroi(); virtanen2.rekisteroi();
     *   rekisteri.getLkm() === 0;
     *   rekisteri.lisaa(virtanen1); rekisteri.getLkm() === 1;
     *   rekisteri.lisaa(virtanen2); rekisteri.getLkm() === 2;
     *   rekisteri.lisaa(virtanen1); rekisteri.getLkm() === 3;
     *   rekisteri.anna(0) === virtanen1;
     *   rekisteri.anna(1) === virtanen2;
     *   rekisteri.anna(2) === virtanen1;
     *   rekisteri.anna(3) === virtanen1; #THROWS IndexOutOfBoundsException
     *   rekisteri.lisaa(virtanen1); rekisteri.getLkm() === 4;
     *   rekisteri.lisaa(virtanen1); rekisteri.getLkm() === 5;
     *   rekisteri.lisaa(virtanen1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Tyontekija lisattava) throws SailoException {
        this.tyolaiset.lisaa(lisattava);
    }
    
    
    /**
     * Lis�� uuden kohteen.
     * @param lisattava lis�tt�v� kohde
     */
    public void lisaa(Kohde lisattava) {
        // TODO: Testej� ei ole, koska kohteet-attribuuttiin ei p��st� k�siksi.
        // Jos attribuutille joskus joudutaan tekem��n saantimetodi, niin lis��
        // testit.
        // TODO: Nyt t�t� metodia tarvitaan, koska t�t� kutsutaan TrekisteriGUIController-luokasta,
        // mutta tarvitaanko t�t� jatkossa?
        this.kohteet.lisaa(lisattava);
    }
    
    
    /**
     * Lis�� uuden ty�ntekij�lle uuden kohteen.
     * @param tyolainenId sen ty�ntekij�n id, jolle kohde lis�t��n
     * @param kohdeId sen kohteen id, joka ty�ntekij�lle lis�t��n
     */
    public void lisaaKohteenTekija(int tyolainenId, int kohdeId) {
        // TODO: Onko kohde kohteet-luettelossa, vai luodaanko sinne uusi?
        // TODO: Onko ty�ntekij� tyontekijat-luokassa? Jos ei ole, ei ole mit��n
        // j�rke� lis�t�.
        this.kohteenTekijat.lisaa(tyolainenId, kohdeId);
        // TODO: testit!
    }
        
    
    /**
     * Palauttaa listan ty�ntekij�n kohteista.
     * @param tyolainenId sen ty�ntekij�n id-numero, jonka
     * kohteet halutaan
     * @return ty�ntekij�n kohteet listana
     * TODO: testit (voi tehd� vasta sitten kun t�ss� luokassa on kohteiden ja ty�ntekij�iden
     * lis��minen)
     */
    public List<Kohde> annaKohteet(int tyolainenId) {
        List<Kohde> loydetyt = new ArrayList<Kohde>();  // T�nne lis�t��n.
        List<Integer> kohdeIdt = this.kohteenTekijat.annaKohdeIdt(tyolainenId);  // T��lt� etsit��n.
        
        for (int kohdeId : kohdeIdt) {
            Kohde lisattava = this.kohteet.anna(kohdeId);
            loydetyt.add(lisattava);
        }
        
        return loydetyt;
    }
    
    
    /**
     * Palauttaa i:nnen rekisteriss� olevan ty�ntekij�n.
     * TODO: rakennusteline.
     * @param i luku, joka kertoo, kuinka mones ty�ntekij� halutaan
     * @return i:s ty�ntekij�
     * @throws IndexOutOfBoundsException jos i on sallitun alueen ulkopuolella
     */
    public Tyontekija anna(int i) throws IndexOutOfBoundsException {
        return this.tyolaiset.anna(i);
    }
    
    
    /**
     * Palauttaa rekisteriss� olevien ty�ntekij�iden lukum��r�n.
     * @return ty�ntekij�iden lukum��r�
     */
    public int getLkm() {
        return this.tyolaiset.getLkm();
    }
        
    
    /**
     * P��ohjelma testaamista varten
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Rekisteri rekisteri = new Rekisteri();
        
        Tyontekija virtanen1 = new Tyontekija();
        Tyontekija virtanen2 = new Tyontekija();
        virtanen1.rekisteroi();
        virtanen1.taytaTiedot();
        virtanen2.rekisteroi();
        virtanen2.taytaTiedot();
        
        try {
            rekisteri.lisaa(virtanen1);
            rekisteri.lisaa(virtanen2);
            rekisteri.lisaa(virtanen2);
            rekisteri.lisaa(virtanen2);
            rekisteri.lisaa(virtanen2);
            rekisteri.lisaa(virtanen2);
        } catch (SailoException e) {
            // e.printStackTrace();
            System.err.println(e.getMessage());
        }
        
        System.out.println();        
        for (int i = 0; i < rekisteri.getLkm(); i++) {
            Tyontekija tyontekija = rekisteri.anna(i);
            System.out.println("Ty�ntekij� paikassa " + i);
            tyontekija.tulosta(System.out);
        }
    }
}
