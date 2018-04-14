package trekisteri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka huolehtii muista kuin itse käyttöliittymään kuuluvista asioista.
 * @author Marko Moilanen
 * @version 14.4.2018
 */
public class Rekisteri {

    private Tyontekijat tyolaiset = new Tyontekijat();
    private Kohteet kohteet = new Kohteet();
    private KohteenTekijat kohteenTekijat = new KohteenTekijat();
    
    
    /**
     * Lisää rekisteriin uuden työntekijän.
     * @param lisattava työntekijä, joka lisätään
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
     *   rekisteri.lisaa(virtanen1); rekisteri.getTyolaisetLkm() === 6;
     * </pre>
     */
    public void lisaa(Tyontekija lisattava) {
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
     *   // Tämä testi ei vielä toimi!
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
    
    
    /**p
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
     * Asettaa tiedostojen perusnimet (työntekijöille, kohteille ja kohteen
     * tekijöille).
     * @param nimi kansion nimi
     */
    public void setTiedostot(String nimi) {
        File hakemisto = new File(nimi);
        hakemisto.mkdirs();
        String hakemistonNimi = "";
        if (nimi.length() > 0) hakemistonNimi = nimi + "/";
        this.tyolaiset.setTiedostonPerusnimi(hakemistonNimi + "tyolaiset");
        this.kohteet.setTiedostonPerusnimi(hakemistonNimi + "kohteet");
        this.kohteenTekijat.setTiedostonPerusnimi(hakemistonNimi + "kohteenTekijat");
    }
    
    
    /**
     * Etsii rekisteristä työntekijän, jolla on sama id-numero kuin 
     * korvaajalla, ja korvaa ensiksi mainitun. Jos kyseisen id-numeron omaavaa
     * työntekijää ei ole, korvaaja lisätään uutena työntekijänä.
     * @param korvaaja korvaava työntekijä
     * @example 
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     *   Tyontekija tyol1 = new Tyontekija();
     *   tyol1.rekisteroi(); int id1 = tyol1.getId();
     *   Tyontekija tyol2 = new Tyontekija();
     *   tyol2.rekisteroi(); int id2 = tyol2.getId();
     *   
     *   Rekisteri rekisteri = new Rekisteri();
     *   rekisteri.lisaa(tyol1); rekisteri.lisaa(tyol2);
     *   
     *   Tyontekija tyol1B = tyol1.clone();
     *   Tyontekija extra = new Tyontekija(); 
     *   extra.rekisteroi(); int idE = extra.getId();
     *   
     *   rekisteri.getTyolaisetLkm() === 2;
     *   rekisteri.korvaa(tyol1B);
     *   rekisteri.getTyolaisetLkm() === 2;
     *   rekisteri.hae(id1) === tyol1B;
     *   
     *   rekisteri.korvaa(extra);
     *   rekisteri.getTyolaisetLkm() === 3;
     *   rekisteri.hae(idE) === extra;
     * </pre>

     */
    public void korvaa(Tyontekija korvaaja) {
        this.tyolaiset.korvaa(korvaaja);
    }
    
    
    /**
     * Palauttaa työntekijän, jolla on annettu id-numero.
     * @param tyolainenId id-numero, jonka perusteella haetaan
     * @return annettua id-numeroa vastaava työntekijä. Jos id ei vastaa
     * yhtään tietorakenteeseen tallennettua työntekijää, palautetaan null.
     * @example
     * <pre name="test">
     *   Tyontekija tyol1 = new Tyontekija();
     *   tyol1.rekisteroi(); int id1 = tyol1.getId();
     *   Tyontekija tyol2 = new Tyontekija();
     *   tyol2.rekisteroi(); int id2 = tyol2.getId();
     *   int id3 = id1 + id2; 
     *
     *   Rekisteri rekisteri = new Rekisteri();
     *   rekisteri.lisaa(tyol1); rekisteri.lisaa(tyol2);
     *   
     *   rekisteri.hae(id1) === tyol1;
     *   rekisteri.hae(id2) === tyol2;
     *   rekisteri.hae(id3) === null;
     * </pre>
     */
    public Tyontekija hae(int tyolainenId) {
        return this.tyolaiset.hae(tyolainenId);
    }
    
    
    /**
     * Lukee rekisteriin tulevat tiedot tiedostoista.
     * @param nimi luettavan hakemiston nimi
     * @throws SailoException jos lukeminen ei onnistu
     * TODO: testit
     */
    public void lueTiedostoista(String nimi) throws SailoException {
        // Aluksi tyhjennetään mahdolliset aiemmin tallennetut tiedot:
        this.tyolaiset = new Tyontekijat();
        this.kohteet = new Kohteet();
        this.kohteenTekijat = new KohteenTekijat();
        
        this.setTiedostot(nimi);
        this.tyolaiset.lueTiedostosta();
        this.kohteet.lueTiedostosta();
        this.kohteenTekijat.lueTiedostosta();
    }

    
    /**
     * Tallentaa rekisterin tiedot tiedostoihin.
     * @throws SailoException jos tallentamisessa on ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";  // Kerätään kaikki mahdolliset virheet yhteen pötköön.
        
        try {
            this.tyolaiset.tallenna();
        } catch (SailoException e) {
            virhe = e.getMessage();
        }
        
        try {
            this.kohteet.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }
        
        try {
            this.kohteenTekijat.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }
        
        if (virhe.length() > 0) throw new SailoException(virhe);
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
        
        rekisteri.lisaa(virtanen1);
        rekisteri.lisaa(virtanen2);
        rekisteri.lisaa(virtanen2);
        rekisteri.lisaa(virtanen2);
        rekisteri.lisaa(virtanen2);
        rekisteri.lisaa(virtanen2);

        System.out.println();        
        for (int i = 0; i < rekisteri.getTyolaisetLkm(); i++) {
            Tyontekija tyontekija = rekisteri.anna(i);
            System.out.println("Työntekijä paikassa " + i);
            tyontekija.tulosta(System.out);
        }
    }
}
