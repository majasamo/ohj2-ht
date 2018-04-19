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
     *   
     *   rekisteri.poista(virtanen1.getId()); rekisteri.getTyolaisetLkm() === 5;
     *   rekisteri.poista(virtanen1.getId()); rekisteri.getTyolaisetLkm() === 4;
     *   rekisteri.poista(virtanen1.getId()); rekisteri.getTyolaisetLkm() === 3;
     *   rekisteri.poista(virtanen1.getId()); rekisteri.getTyolaisetLkm() === 2;
     *   rekisteri.poista(virtanen1.getId()); rekisteri.getTyolaisetLkm() === 1;
     *   rekisteri.poista(virtanen1.getId()); rekisteri.getTyolaisetLkm() === 1;
     *   
     *   rekisteri.poista(virtanen2.getId()); rekisteri.getTyolaisetLkm() === 0;
     *   rekisteri.poista(virtanen2.getId()); rekisteri.getTyolaisetLkm() === 0;
     * </pre>
     */
    public void lisaa(Tyontekija lisattava) {
        this.tyolaiset.lisaa(lisattava);
    }

    
    /**
     * Lis�� ty�ntekij�lle uuden kohteen. Jos annetun nimist� kohdetta ei viel� ole, 
     * se luodaan ja lis�t��n tietorakenteeseen. Jos taas nime� vastaava kohde on
     * olemassa (tarkalleen samanlaisena), uutta kohdetta ei luoda. Huom.! T�m� metodi ei tallenna
     * ty�ntekij�� tietorakenteeseen.
     * @param tyolainenId sen ty�ntekij�n id, jolle kohde lis�t��n
     * @param kohdeNimi lis�tt�v�n kohteen nimi
     * @example
     * <pre name="test">
     * #import java.util.List;
     * #import java.util.ArrayList;
     *   Rekisteri rekisteri = new Rekisteri();
     *   Tyontekija tyol1 = new Tyontekija(); tyol1.rekisteroi(); tyol1.parse("|Koikkalainen");
     *   Tyontekija tyol2 = new Tyontekija(); tyol1.rekisteroi(); tyol2.parse("|Ahonen");
     *   rekisteri.lisaa(tyol1); rekisteri.lisaa(tyol2);
     *   
     *   rekisteri.lisaa(tyol1.getId(), "Toimisto Oy");
     *   rekisteri.lisaa(tyol1.getId(), "Toimisto Ab");
     *   rekisteri.lisaa(tyol2.getId(), "Toimisto Oy");
     *   
     *   List<Tyontekija> tulos1 = rekisteri.hae("kohteet", " toimisto ");
     *   List<Tyontekija> vastaus1 = new ArrayList<Tyontekija>(); 
     *   vastaus1.add(tyol2); vastaus1.add(tyol1);
     *   tulos1.equals(vastaus1) === true;
     *   
     *   List<Tyontekija> tulos2 = rekisteri.hae("kohteet", " toimisto a");
     *   List<Tyontekija> vastaus2 = new ArrayList<Tyontekija>(); 
     *   vastaus2.add(tyol1);
     *   tulos2.equals(vastaus2) === true;
     * </pre>
     */
    public void lisaa(int tyolainenId, String kohdeNimi) {
        int kohdeId = this.kohteet.haeId(kohdeNimi);  // T�m� siis tarvittaessa luo ja
                                                      // rekister�i uuden kohteen.
        this.kohteenTekijat.lisaa(tyolainenId, kohdeId);
    }
    
    
    /**
     * Palauttaa j�rjestetyn listan ty�ntekij�n kohteista.
     * @param tyolainenId sen ty�ntekij�n id-numero, jonka
     * kohteet halutaan
     * @return ty�ntekij�n kohteet listana. Kohteet j�rjestet��n aakkosj�rjestykseen.
     * @example
     * <pre name="test">
     * #THROWS IndexOutOfBoundsException
     *   Tyontekija tyol1 = new Tyontekija(); tyol1.rekisteroi();
     *   int id1 = tyol1.getId();
     *   Tyontekija tyol2 = new Tyontekija(); tyol2.rekisteroi();
     *   int id2 = tyol2.getId();
     *   Rekisteri rekisteri = new Rekisteri();
     *   rekisteri.lisaa(tyol1); rekisteri.lisaa(tyol2);
     *        
     *   rekisteri.lisaa(id1, "Toimisto Oy");
     *   rekisteri.lisaa(id1, "Avain Ab");
     *   rekisteri.lisaa(id1, "Firma Ab");
     *   rekisteri.lisaa(id2, "Avain Ab");
     *   
     *   List<Kohde> vastaus1 = rekisteri.annaKohteet(id1);
     *   vastaus1.get(0).toString().substring(2) === "Avain Ab";
     *   vastaus1.get(1).toString().substring(2) === "Firma Ab";
     *   vastaus1.get(2).toString().substring(2) === "Toimisto Oy";
     *   vastaus1.get(3); #THROWS IndexOutOfBoundsException
     *   
     *   List<Kohde> vastaus2 = rekisteri.annaKohteet(id2);
     *   vastaus2.get(0).toString().substring(2) === "Avain Ab";
     *   vastaus2.get(1); #THROWS IndexOutOfBoundsException
     * </pre>
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
     * @param i luku, joka kertoo, kuinka mones ty�ntekij� halutaan
     * @return i:s ty�ntekij�
     * @throws IndexOutOfBoundsException jos i on sallitun alueen ulkopuolella
     */
    public Tyontekija anna(int i) throws IndexOutOfBoundsException {
        // T�t� metodia ei oikeasti k�ytet� en�� muuhun kuin testaamiseen.
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
     * kaikki ty�ntekij�t. Paitsi: jos hakuehto on "kohteet" ja hakusana "" (tai v�lily�nneist� koostuva),
     * palautetaan vain ne ty�ntekij�t, joilla on v�hint��n yksi kohde. Ty�ntekij�t j�rjestet��n aakkosj�rjestykseen nimen mukaan.
     * @example
     * <pre name="test">
     *   Rekisteri rekisteri = new Rekisteri();
     *   Tyontekija tyol1 = new Tyontekija(); tyol1.parse("1|Virtanen Jaakko|1111|2000|ei ole|");
     *   Tyontekija tyol2 = new Tyontekija(); tyol2.parse("2|Ahonen Jaakko|1110|1994|on|");
     *   Tyontekija tyol3 = new Tyontekija(); tyol3.parse("3|Virtanen Petteri|2222|1995|on|");
     *   Tyontekija tyol4 = new Tyontekija(); tyol4.parse("4|Suhonen Jaakko|3333|2014|ei ole|");
     *   rekisteri.lisaa(tyol1); rekisteri.lisaa(tyol2); rekisteri.lisaa(tyol3); rekisteri.lisaa(tyol4);
     *   rekisteri.lisaa(tyol1.getId(), "Toimisto Oy");
     *   rekisteri.lisaa(tyol1.getId(), "Firma Ab");
     *   rekisteri.lisaa(tyol3.getId(), "Toimisto Oy");
     *   rekisteri.lisaa(tyol4.getId(), "Toimisto Oy");
     *   
     *   List<Tyontekija> tulos1 = new ArrayList<Tyontekija>(); 
     *   tulos1.add(tyol2); tulos1.add(tyol4); tulos1.add(tyol1);
     *   rekisteri.hae("nimi", "Jaakko  ").equals(tulos1) === true;
     *   
     *   List<Tyontekija> tulos2 = new ArrayList<Tyontekija>();
     *   rekisteri.hae("nimi", "ei").equals(tulos2) === true;
     *   rekisteri.hae("aloitusvuosi", "198").equals(tulos2) === true;
     *   rekisteri.hae("lis�tietoja", "peruspesut").equals(tulos2) === true;
     *   
     *   List<Tyontekija> tulos3 = new ArrayList<Tyontekija>();
     *   tulos3.add(tyol4); tulos3.add(tyol1);
     *   rekisteri.hae("koulutus", "ei").equals(tulos3) === true;
     *   rekisteri.hae("aloitusvuosi", "2").equals(tulos3) === true;
     *   
     *   List<Tyontekija> tulos4 = new ArrayList<Tyontekija>();
     *   tulos4.add(tyol4);
     *   rekisteri.hae("aloitusvuosi", "201").equals(tulos4) === true;
     *   
     *   List<Tyontekija> tulos5 = new ArrayList<Tyontekija>();
     *   tulos5.add(tyol1);
     *   rekisteri.hae("kohteet", " fir ").equals(tulos5) === true;
     *   
     *   List<Tyontekija> tulos6 = new ArrayList<Tyontekija>();
     *   tulos6.add(tyol4); tulos6.add(tyol1); tulos6.add(tyol3);
     *   rekisteri.hae("kohteet", " isto   ").equals(tulos6) === true;
     *
     *   List<Tyontekija> tulos7 = new ArrayList<Tyontekija>();    // T�ss� tapauksessa metodi ei palauta
     *   tulos7.add(tyol4); tulos7.add(tyol1); tulos7.add(tyol3);  // ty�ntekij�� tyol2, koska tyol2:lla
     *   rekisteri.hae("kohteet", "  ").equals(tulos7) === true;   // ei ole yht��n kohdetta.
     * </pre>
     */
    public List<Tyontekija> hae(String hakuehto, String hakusana) {
        // Hakuehtona on kohde:
        if (hakuehto.equals("kohteet")) return this.haeKohteella(hakusana);
        
        // Hakuehtona on jokin ty�ntekij�n kentt�:
        return this.tyolaiset.hae(hakuehto, hakusana);
    }
    
    
    /**
     * Palauttaa j�rjestetyn listan ty�ntekij�ist�, joiden jokin kohde sis�lt�� annetun hakusanan.
     * @param hakusana merkkijono, jonka perusteella haetaan
     * @return j�rjestetty lista ehdon toteuttavista ty�tekij�ist�. Jos hakusana
     * on tyhj� tai koostuu pelkist� v�lily�nneist�, palautetaan kaikki
     * ty�ntekij�t, joilla on v�hint��n yksi kohde. Ty�ntekij�t j�rjestet��n aakkosj�rjestykseen
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
}
