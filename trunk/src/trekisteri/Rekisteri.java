package trekisteri;

import java.util.ArrayList;
import java.util.List;

/**
 * Luokka huolehtii muista kuin itse käyttöliittymään kuuluvista asioista.
 * @author Marko Moilanen
 * @version 20.3.2018
 */
public class Rekisteri {

    private final Tyontekijat tyolaiset = new Tyontekijat();
    private final KohteenTekijat kohteenTekijat = new KohteenTekijat();
    private final Kohteet kohteet = new Kohteet();
    
    
    /**
     * Lisää rekisteriin uuden työntekijän.
     * @param lisattava työntekijä, joka lisätään
     * @throws SailoException jos lisääminen ei onnistu
     * @example
     * <pre name="test">
     *   #THROWS SailoException
     *   Rekisteri rekisteri = new Rekisteri();
     *   Tyontekija virtanen1 = new Tyontekija();
     *   Tyontekija virtanen2 = new Tyontekija();
     *   virtanen1.rekisteroi(); virtanen2.rekisteroi();
     *   rekisteri.getTyolaisetLkm() === 0;
     *   rekisteri.lisaa(virtanen1); rekisteri.getTyolaisetLkm() === 1;
     *   rekisteri.lisaa(virtanen2); rekisteri.getTyolaisetLkm() === 2;
     *   rekisteri.lisaa(virtanen1); rekisteri.getTyolaisetLkm() === 3;
     *   rekisteri.anna(0) === virtanen1;
     *   rekisteri.anna(1) === virtanen2;
     *   rekisteri.anna(2) === virtanen1;
     *   rekisteri.anna(3) === virtanen1; #THROWS IndexOutOfBoundsException
     *   rekisteri.lisaa(virtanen1); rekisteri.getTyolaisetLkm() === 4;
     *   rekisteri.lisaa(virtanen1); rekisteri.getTyolaisetLkm() === 5;
     *   rekisteri.lisaa(virtanen1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Tyontekija lisattava) throws SailoException {
        this.tyolaiset.lisaa(lisattava);
    }
    
    
    /**
     * Lisää uuden kohteen.
     * @param lisattava lisättävä kohde
     */
    public void lisaa(Kohde lisattava) {
        // TODO: Testejä ei ole, koska kohteet-attribuuttiin ei päästä käsiksi.
        // Jos attribuutille joskus joudutaan tekemään saantimetodi, niin lisää
        // testit.
        // TODO: Nyt tätä metodia tarvitaan, koska tätä kutsutaan TrekisteriGUIController-luokasta,
        // mutta tarvitaanko tätä jatkossa?
        this.kohteet.lisaa(lisattava);
    }
    
    
    /**
     * Lisää työntekijälle uuden kohteen.
     * @param tyolainenId sen työntekijän id, jolle kohde lisätään
     * @param kohdeId sen kohteen id, joka työntekijälle lisätään
     */
    public void lisaaKohteenTekija(int tyolainenId, int kohdeId) {
        // Huom.! Tämä ei koske tyontekijat-attribuuttiin. Lopullisessa ohjelmassa työntekijä
        // on olemassa ennen kuin sille lisätään kohdetta.
        this.kohteenTekijat.lisaa(tyolainenId, kohdeId);
        // TODO: Testit! Testejä ei voi vielä lisätä.
    }

    
    /**
     * Lisää työntekijälle uuden kohteen. 
     * @param tyolainenId sen työntekijän id, jolle kohde lisätään
     * @param kohdeNimi lisättävän kohteen nimi
     * @example
     * <pre name="test">
     *   Rekisteri rekisteri = new Rekisteri();
     *   Tyontekija tyol1 = new Tyontekija(); tyol1.rekisteroi();
     *   Tyontekija tyol2 = new Tyontekija(); tyol1.rekisteroi();
     *   
     *   rekisteri.lisaa(tyol1.getId(), "Toimisto Oy");
     *   rekisteri.lisaa(tyol1.getId(), "Toimisto Ab");
     *   rekisteri.getTyolaisetLkm() === 1;
     *   rekisteri.lisaa(tyol2.getId(), "Toimisto Oy");
     *   rekisteri.getTyolaisetLkm() === 2;
     *   
     *   // TODO: tähän lisää testejä sitten kun kaikki toimii.
     * </pre>
     */
    @SuppressWarnings("unused")
    public void lisaa(int tyolainenId, String kohdeNimi) {
        // TODO: täydennä.
        // Tarkista, onko nimeä vastaava kohde olemassa. Jos ei ole,
        // luo se.
        // Lisää uusi kohteenTekijä.
    }

    
    /**
     * Palauttaa listan työntekijän kohteista.
     * @param tyolainenId sen työntekijän id-numero, jonka
     * kohteet halutaan
     * @return työntekijän kohteet listana
     * TODO: testit (voi tehdä vasta sitten kun tässä luokassa on kohteiden ja työntekijöiden
     * lisääminen)
     */
    public List<Kohde> annaKohteet(int tyolainenId) {
        List<Kohde> loydetyt = new ArrayList<Kohde>();  // Tänne lisätään.
        List<Integer> kohdeIdt = this.kohteenTekijat.annaKohdeIdt(tyolainenId);  // Täältä etsitään.
        
        for (int kohdeId : kohdeIdt) {
            Kohde lisattava = this.kohteet.anna(kohdeId);
            loydetyt.add(lisattava);
        }
        
        return loydetyt;
    }
    
    
    /**
     * Palauttaa i:nnen rekisterissä olevan työntekijän.
     * TODO: rakennusteline.
     * @param i luku, joka kertoo, kuinka mones työntekijä halutaan
     * @return i:s työntekijä
     * @throws IndexOutOfBoundsException jos i on sallitun alueen ulkopuolella
     */
    public Tyontekija anna(int i) throws IndexOutOfBoundsException {
        return this.tyolaiset.anna(i);
    }
    
    
    /**
     * Palauttaa rekisterissä olevien työntekijöiden lukumäärän.
     * @return työntekijöiden lukumäärä
     */
    public int getTyolaisetLkm() {
        return this.tyolaiset.getLkm();
    }
        
    
    /**
     * Pääohjelma testaamista varten
     * @param args ei käytössä
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
        for (int i = 0; i < rekisteri.getTyolaisetLkm(); i++) {
            Tyontekija tyontekija = rekisteri.anna(i);
            System.out.println("Työntekijä paikassa " + i);
            tyontekija.tulosta(System.out);
        }
    }
}
