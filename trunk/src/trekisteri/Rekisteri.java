package trekisteri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka huolehtii muista kuin itse k�ytt�liittym��n kuuluvista asioista.
 * @author Marko Moilanen
 * @version 19.4.2018
 */
public class Rekisteri {

    private Tyontekijat tyolaiset = new Tyontekijat();
    private Kohteet kohteet = new Kohteet();
    private KohteenTekijat kohteenTekijat = new KohteenTekijat();
    
    
    /**
     * Lis�� rekisteriin uuden ty�ntekij�n.
     * @param lisattava ty�ntekij�, joka lis�t��n
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
     * Lis�� uuden kohteen.
     * @param lisattava lis�tt�v� kohde
     */
    private void lisaa(Kohde lisattava) {
        // TODO: Testej� ei ole, koska kohteet-attribuuttiin ei p��st� k�siksi.
        // Jos attribuutille joskus joudutaan tekem��n saantimetodi, niin lis��
        // testit.
        // TODO: Nyt t�t� metodia tarvitaan, koska t�t� kutsutaan TrekisteriGUIController-luokasta,
        // mutta tarvitaanko t�t� jatkossa?
        this.kohteet.lisaa(lisattava);
    }
    
    
    /**
     * Lis�� ty�ntekij�lle uuden kohteen. Jos annetun nimist� kohdetta ei ole viel�
     * olemassa, se luodaan ja tallennetaan tietorakenteeseen.
     * @param tyolainenId sen ty�ntekij�n id, jolle kohde lis�t��n
     * @param kohdeNimi sen kohteen nimi, joka ty�ntekij�lle lis�t��n
     * TODO testit?
     */
    public void lisaaKohteenTekija(int tyolainenId, String kohdeNimi) {
        //
    }

    
    /**
     * Lis�� ty�ntekij�lle uuden kohteen. Jos annetun nimist� kohdetta ei viel� ole, 
     * se luodaan ja lis�t��n tietorakenteeseen. Jos taas nime� vastaava kohde on
     * olemassa (tarkalleen samanlaisena), uutta kohdetta ei luoda.
     * @param tyolainenId sen ty�ntekij�n id, jolle kohde lis�t��n
     * @param kohdeNimi lis�tt�v�n kohteen nimi
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
     *   // TODO: t�h�n lis�� testej� sitten kun kaikki toimii.
     *   // T�m� testi ei viel� toimi!
     * </pre>
     */
    public void lisaa(int tyolainenId, String kohdeNimi) {
        int kohdeId = this.kohteet.haeId(kohdeNimi);
        this.kohteenTekijat.lisaa(tyolainenId, kohdeId);
    }
    
    
    /**
     * Palauttaa j�rjestetyn listan ty�ntekij�n kohteista.
     * @param tyolainenId sen ty�ntekij�n id-numero, jonka
     * kohteet halutaan
     * @return ty�ntekij�n kohteet listana. Kohteet j�rjestet��n aakkosj�rjestykseen.
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
        
        loydetyt.sort(null);
        return loydetyt;
    }
    
    
    /**
     * Poistaa ty�ntekij�lt� kohteen.
     * @param tyolainenId sen ty�ntekij�n id, jolta kohde poistetaan
     * @param kohdeId  poistettavan kohteen id
     */
    public void poista(int tyolainenId, int kohdeId) {
        this.kohteenTekijat.poista(tyolainenId, kohdeId);
    }
    
    
    /**
     * Poistaa ty�ntekij�n.
     * @param tyolainenId poistettavan ty�ntekij�n id-numero
     */
    public void poista(int tyolainenId) {
        this.tyolaiset.poista(tyolainenId);
        this.kohteenTekijat.poista(tyolainenId);
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
    public int getTyolaisetLkm() {
        return this.tyolaiset.getLkm();
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet (ty�ntekij�ille, kohteille ja kohteen
     * tekij�ille).
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
     * Etsii rekisterist� ty�ntekij�n, jolla on sama id-numero kuin 
     * korvaajalla, ja korvaa ensiksi mainitun. Jos kyseisen id-numeron omaavaa
     * ty�ntekij�� ei ole, korvaaja lis�t��n uutena ty�ntekij�n�.
     * @param korvaaja korvaava ty�ntekij�
     * @example 
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     *   Tyontekija tyol1 = new Tyontekija();
     *   tyol1.rekisteroi(); int id1 = tyol1.getId();
     *   Tyontekija tyol2 = new Tyontekija();
     *   tyol2.rekisteroi();
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
     * Palauttaa ty�ntekij�n, jolla on annettu id-numero.
     * @param tyolainenId id-numero, jonka perusteella haetaan
     * @return annettua id-numeroa vastaava ty�ntekij�. Jos id ei vastaa
     * yht��n tietorakenteeseen tallennettua ty�ntekij��, palautetaan null.
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
     * Palauttaa listan ty�ntekij�ist�, jotka toteuttavat annetun hakuehdon.
     * @param hakuehto ehto, jonka mukaan haetaan. Ehdon on oltava jokin ty�ntekij�n tietoihin
     * kuuluva kentt� tai "kohteet".
     * @param hakusana merkkijono, jonka perusteella haetaan
     * @return j�rjestetty lista ehdon toteuttavista ty�ntekij�ist�. Jos hakusana on
     * tyhj� tai koostuu pelkist� v�lily�nneist�, palautetaan
     * kaikki ty�ntekij�t. Ty�ntekij�t j�rjestet��n aakkosj�rjestykseen nimen mukaan.
     * @example
     */
    public List<Tyontekija> hae(String hakuehto, String hakusana) {
        // Hakuehtona on kohde:
        if (hakuehto.equals("kohteet")) return this.haeKohteella(hakusana);
        
        // Hakuehtona on jokin ty�ntekij�n kentt�:
        return this.tyolaiset.hae(hakuehto, hakusana);
        //TODO: testit 
    }
    
    
    /**
     * Palauttaa listan ty�ntekij�ist�, joiden jokin kohde sis�lt�� annetun hakusanan.
     * @param hakusana merkkijono, jonka perusteella haetaan
     * @return j�rjestetty lista ehdon toteuttavista ty�tekij�ist�. Jos hakusana
     * on tyhj� tai koostuu pelkist� v�lily�nneist�, palautetaan kaikki
     * ty�ntekij�t. Ty�ntekij�t j�rjestet��n aakkosj�rjestykseen
     * nimen mukaan.
     */
    private List<Tyontekija> haeKohteella(String hakusana) {
        List<Integer> kohdeIdt = this.kohteet.hae(hakusana);
        List<Integer> tyolainenIdt = new ArrayList<Integer>();
        
        // Lis�t��n jokainen kohdetta vastaava ty�ntekij�-id
        // listaan, mutta vain yhden kerran.
        for (int kohdeId : kohdeIdt) {
            for (int tyolainenId : this.kohteenTekijat.hae(kohdeId)) {
                if (!tyolainenIdt.contains(tyolainenId))
                    tyolainenIdt.add(tyolainenId);
            }
        }
        
        List<Tyontekija> tulos = new ArrayList<Tyontekija>();
        for (int tyolainenId : tyolainenIdt) {
            tulos.add(this.tyolaiset.hae(tyolainenId));
        }
        tulos.sort(null);
        
        return tulos;
    }
    
    
    /**
     * Lukee rekisteriin tulevat tiedot tiedostoista.
     * @param nimi luettavan hakemiston nimi
     * @throws SailoException jos lukeminen ei onnistu
     * TODO: testit
     */
    public void lueTiedostoista(String nimi) throws SailoException {
        // Aluksi tyhjennet��n mahdolliset aiemmin tallennetut tiedot:
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
        String virhe = "";  // Ker�t��n kaikki mahdolliset virheet yhteen p�tk��n.
        
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
        
        rekisteri.lisaa(virtanen1);
        rekisteri.lisaa(virtanen2);
        rekisteri.lisaa(virtanen2);
        rekisteri.lisaa(virtanen2);
        rekisteri.lisaa(virtanen2);
        rekisteri.lisaa(virtanen2);

        System.out.println();        
        for (int i = 0; i < rekisteri.getTyolaisetLkm(); i++) {
            Tyontekija tyontekija = rekisteri.anna(i);
            System.out.println("Ty�ntekij� paikassa " + i);
            tyontekija.tulosta(System.out);
        }
    }
}
