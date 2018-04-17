package trekisteri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import fi.jyu.mit.ohj2.WildChars;

/**
 * Ty�ntekijat-luokka hallinnoi yksitt�isi� ty�ntekij�it�.
 * @author Marko Moilanen
 * @version 17.4.2018
 */
public class Tyontekijat implements Iterable<Tyontekija> {
    
    private int lkm;
    private Tyontekija[] alkiot = new Tyontekija[LKM_MAX];
    private static final int LKM_MAX = 5;
    
    private String perusnimi = "tyolaiset";
    private boolean onkoMuutettu = false;  // Onko tallentamattomia muutoksia?
    
        
    /**
     * Lis�� uuden ty�ntekij�n.
     * @param lisattava ty�ntekij�, joka lis�t��n
     * @example
     * <pre name="test">
     *   Tyontekijat tyontekijat = new Tyontekijat();
     *   Tyontekija virtanen1 = new Tyontekija();
     *   Tyontekija virtanen2 = new Tyontekija();
     *   tyontekijat.getLkm() === 0;
     *   tyontekijat.lisaa(virtanen1); tyontekijat.getLkm() === 1;
     *   tyontekijat.lisaa(virtanen2); tyontekijat.getLkm() === 2;
     *   tyontekijat.lisaa(virtanen1); tyontekijat.getLkm() === 3;
     *   tyontekijat.anna(0) === virtanen1;
     *   tyontekijat.anna(1) === virtanen2;
     *   tyontekijat.anna(2) === virtanen1;
     *   (tyontekijat.anna(1) == virtanen1) === false;
     *   (tyontekijat.anna(1) == virtanen2) === true;
     *   tyontekijat.anna(3) === virtanen1; #THROWS IndexOutOfBoundsException
     *   tyontekijat.lisaa(virtanen1); tyontekijat.getLkm() === 4;
     *   tyontekijat.lisaa(virtanen1); tyontekijat.getLkm() === 5;
     *   tyontekijat.lisaa(virtanen1); tyontekijat.getLkm() === 6;  // Koon kasvatus.
     *   tyontekijat.lisaa(virtanen1); tyontekijat.getLkm() === 7;  
     * </pre>
     */
    public void lisaa(Tyontekija lisattava) {
        if (this.lkm >= this.alkiot.length) this.kasvataKokoa();
        this.alkiot[this.lkm] = lisattava;
        this.lkm++;
        this.onkoMuutettu = true;
    }
    
    
    /**
     * Kasvattaa tietorakenteen kokoa.
     */
    private void kasvataKokoa() {
        Tyontekija[] uusi = new Tyontekija[2 * this.alkiot.length];
        
        for (int i = 0; i < this.lkm; i++) {
            uusi[i] = this.alkiot[i];
        }
        
        this.alkiot = uusi;
    }
        
    
    /**
     * Palauttaa ty�ntekij�iden lukum��r�n.
     * @return ty�ntekij�iden lukum��r�
     */
    public int getLkm() {
        return this.lkm;
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
     *   Tyontekijat luettelo = new Tyontekijat();
     *   luettelo.lisaa(tyol1); luettelo.lisaa(tyol2);
     *   
     *   Tyontekija tyol1B = tyol1.clone();
     *   Tyontekija extra = new Tyontekija(); 
     *   extra.rekisteroi(); int idE = extra.getId();
     *   
     *   luettelo.getLkm() === 2;
     *   luettelo.korvaa(tyol1B);
     *   luettelo.getLkm() === 2;
     *   luettelo.hae(id1) === tyol1B;
     *   
     *   luettelo.korvaa(extra);
     *   luettelo.getLkm() === 3;
     *   luettelo.hae(idE) === extra;
     * </pre>
     */
    public void korvaa(Tyontekija korvaaja) {
        for (int i = 0; i < this.lkm; i++) {
            if (this.alkiot[i].getId() == korvaaja.getId()) {
                this.alkiot[i] = korvaaja;
                this.onkoMuutettu = true;
                return;
            }               
        }
        
        this.lisaa(korvaaja);
        this.onkoMuutettu = true;
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
     *   Tyontekijat luettelo = new Tyontekijat();
     *   luettelo.lisaa(tyol1); luettelo.lisaa(tyol2);
     *   
     *   luettelo.hae(id1) === tyol1;
     *   luettelo.hae(id2) === tyol2;
     *   luettelo.hae(id3) === null;
     * </pre>
     */
    public Tyontekija hae(int tyolainenId) {
        for (Tyontekija tyontekija : this) {
            if (tyontekija.getId() == tyolainenId) return tyontekija;
        }
        
        return null;
    }
    
    
    /**
     * Palauttaa listan ty�ntekij�ist�, jotka toteuttavat annetun hakuehdon.
     * @param hakuehto ehto, jonka mukaan haetaan. Ehdon on oltava jokin ty�ntekij�n tietoihin
     * kuuluva kentt�.
     * @param hakusana merkkijono, jonka perusteella haetaan
     * @return j�rjestetty lista ehdon toteuttavista ty�ntekij�ist�. Jos hakusana on tyhj� 
     * tai koostuu pelkist� v�lily�nneist�, palautetaan
     * kaikki ty�ntekij�t. Ty�ntekij�t j�rjestet��n aakkosj�rjestykseen nimen mukaan.
     * @example
     * <pre name="test">
     * #import java.util.List;
     * #import java.util.ArrayList;
     *   Tyontekijat tekijat = new Tyontekijat();
     *   Tyontekija tyol1 = new Tyontekija(); tyol1.parse("1|Virtanen Jaakko|1111|2000|ei ole|");
     *   Tyontekija tyol2 = new Tyontekija(); tyol2.parse("2|Ahonen Jaakko|1110|1994|on|");
     *   Tyontekija tyol3 = new Tyontekija(); tyol3.parse("3|Virtanen Petteri|2222|1995|on|");
     *   Tyontekija tyol4 = new Tyontekija(); tyol4.parse("4|Suhonen Jaakko|3333|2014|ei ole|");
     *   tekijat.lisaa(tyol1); tekijat.lisaa(tyol2); tekijat.lisaa(tyol3); tekijat.lisaa(tyol4);
     *   
     *   List<Tyontekija> tulos1 = new ArrayList<Tyontekija>(); 
     *   tulos1.add(tyol2); tulos1.add(tyol4); tulos1.add(tyol1);
     *   tekijat.hae("nimi", "Jaakko  ").equals(tulos1) === true;
     *   
     *   List<Tyontekija> tulos2 = new ArrayList<Tyontekija>();
     *   tekijat.hae("nimi", "ei").equals(tulos2) === true;
     *   tekijat.hae("aloitusvuosi", "198").equals(tulos2) === true;
     *   tekijat.hae("lis�tietoja", "peruspesut").equals(tulos2) === true;
     *   
     *   List<Tyontekija> tulos3 = new ArrayList<Tyontekija>();
     *   tulos3.add(tyol4); tulos3.add(tyol1);
     *   tekijat.hae("koulutus", "ei").equals(tulos3) === true;
     *   tekijat.hae("aloitusvuosi", "2").equals(tulos3) === true;
     *   
     *   List<Tyontekija> tulos4 = new ArrayList<Tyontekija>();
     *   tulos4.add(tyol4);
     *   tekijat.hae("aloitusvuosi", "201").equals(tulos4) === true;
     * </pre>
     */
    public List<Tyontekija> hae(String hakuehto, String hakusana) {
        ArrayList<Tyontekija> tulos = new ArrayList<Tyontekija>();
        String ehto = hakuehto.trim();
        String sana = hakusana.trim().toLowerCase();
        
        if (ehto.equals("nimi")) {
            tulos = this.haeNimi(sana);
        } else if (ehto.equals("henkil�numero")) {
            tulos = this.haeHlonumero(sana);
        } else if (ehto.equals("aloitusvuosi")) {
            tulos = this.haeAloitusvuosi(sana);
        } else if (ehto.equals("koulutus")) {
            tulos = this.haeKoulutus(sana);
        } else if (ehto.equals("lis�tietoja")) {
            tulos = this.haeLisatietoja(sana);
        } else {
            // Ei tehd� mit��n.
        }
        
        tulos.sort(null);
        return tulos;
    }
    
    
    /**
     * Palauttaa listan ty�ntekij�ist�, joiden nimi tai nimen osa t�sm�� hakusanan kanssa.
     * @param sana hakusana
     * @return lista ty�ntekij�ist�, joiden nimi tai nimen osa t�sm�� hakusanan kanssa
     */
    private ArrayList<Tyontekija> haeNimi(String sana) {
        ArrayList<Tyontekija> tulos = new ArrayList<Tyontekija>();
        
        for (Tyontekija tyontekija : this) {
            if (WildChars.wildmat(tyontekija.getNimi().toLowerCase(), "*" + sana + "*"))
                tulos.add(tyontekija);
        }
        
        return tulos;
    }

    
    /**
     * Palauttaa listan ty�ntekij�ist�, joiden henkil�numero tai sen osa t�sm�� hakusanan kanssa.
     * @param sana hakusana
     * @return lista ty�ntekij�ist�, joiden henkil�numero tai sen osa t�sm�� hakusanan kanssa
     */
    private ArrayList<Tyontekija> haeHlonumero(String sana) {
        ArrayList<Tyontekija> tulos = new ArrayList<Tyontekija>();
        
        for (Tyontekija tyontekija : this) {
            if (WildChars.wildmat("" + tyontekija.getHlonumero(), "*" + sana + "*"))
                tulos.add(tyontekija);
        }
        
        return tulos;
    }    

    
    /**
     * Palauttaa listan ty�ntekij�ist�, joiden aloitusvuosi tai sen osa t�sm�� hakusanan kanssa.
     * @param sana hakusana
     * @return lista ty�ntekij�ist�, joiden aloitusvuosi tai sen osa t�sm�� hakusanan kanssa
     */
    private ArrayList<Tyontekija> haeAloitusvuosi(String sana) {
        ArrayList<Tyontekija> tulos = new ArrayList<Tyontekija>();
        
        for (Tyontekija tyontekija : this) {
            if (WildChars.wildmat("" + tyontekija.getAloitusvuosi(), "*" + sana + "*"))
                tulos.add(tyontekija);
        }
        
        return tulos;
    }    

    
    /**
     * Palauttaa listan ty�ntekij�ist�, joiden koulutus-kent�n sis�lt� tai sen osa t�sm�� hakusanan kanssa.
     * @param sana hakusana
     * @return lista ty�ntekij�ist�, joiden koulutus-kent�n sis�lt� tai sen osa t�sm�� hakusanan kanssa
     */
    private ArrayList<Tyontekija> haeKoulutus(String sana) {
        ArrayList<Tyontekija> tulos = new ArrayList<Tyontekija>();
        
        for (Tyontekija tyontekija : this) {
            if (WildChars.wildmat(tyontekija.getKoulutus(), "*" + sana + "*"))
                tulos.add(tyontekija);
        }
        
        return tulos;
    }    
        
    
    /**
     * Palauttaa listan ty�ntekij�ist�, joiden lis�tietoja-kent�n sis�lt� tai sen osa t�sm�� hakusanan kanssa.
     * @param sana hakusana
     * @return lista ty�ntekij�ist�, joiden lis�tietoja-kent�n sis�lt� tai sen osa t�sm�� hakusanan kanssa
     */
    private ArrayList<Tyontekija> haeLisatietoja(String sana) {
        ArrayList<Tyontekija> tulos = new ArrayList<Tyontekija>();
        
        for (Tyontekija tyontekija : this) {
            if (WildChars.wildmat(tyontekija.getLisatietoja(), "*" + sana + "*"))
                tulos.add(tyontekija);
        }
        
        return tulos;
    }        
    
    
    /**
     * Palauttaa i:nnen ty�ntekij�n.
     * TODO: t�m� on rakennusteline. ... vai onko?
     * @param i luku,joka kertoo, kuinka mones ty�ntekij� halutaan
     * @return i:s ty�ntekij�
     * @throws IndexOutOfBoundsException jos i on sallitun alueen ulkopuolella 
     */
    public Tyontekija anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i) 
            throw new IndexOutOfBoundsException("Laiton indeksi " + i);
        return this.alkiot[i];
    }
    
    
    /**
     * Luetaan tiedostosta, jonka nimi on annettu aiemmin.
     * @throws SailoException jos tiedoston lukeminen ei onnistu 
     */
    public void lueTiedostosta() throws SailoException {
        this.lueTiedostosta(this.getTiedostonPerusnimi());
    }
    
    
    /** 
     * Lukee ty�ntekij�iden tiedot tiedostosta.
     * @param tiedosto luettavan tiedoston nimi ilman tiedostop��tett�
     * @throws SailoException jos tiedoston lukeminen ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * 
     *   String tiedosto = "testiyritys/tyolaiset";
     *   File fileTied = new File(tiedosto + ".dat");
     *   File fileHak = new File("testiyritys");
     *   fileHak.mkdir();
     *   fileTied.delete();
     *   
     *   Tyontekijat tyontekijat = new Tyontekijat();
     *   Tyontekija virtanen1 = new Tyontekija(); virtanen1.taytaTiedot();
     *   Tyontekija virtanen2 = new Tyontekija(); virtanen2.taytaTiedot();
     *   tyontekijat.lueTiedostosta(tiedosto);  #THROWS SailoException
     *   
     *   // Lis�t��n ja tallennetaan. Muutosten pit�isi tallentua tiedostoon.
     *   tyontekijat.lisaa(virtanen1);
     *   tyontekijat.lisaa(virtanen2);
     *   tyontekijat.tallenna();
     *   // Tuhotaan vanhat tiedot ja testataan, s�ilyiv�tk� tiedostoon tallennetut
     *   // tiedot.
     *   tyontekijat = new Tyontekijat();
     *   tyontekijat.lueTiedostosta(tiedosto);
     *   Iterator<Tyontekija> vipellin = tyontekijat.iterator();
     *   vipellin.next().toString() === virtanen1.toString();  // Olioviitteiden vertaaminen ei toimi.
     *   vipellin.next().toString() === virtanen2.toString();
     *   vipellin.hasNext() === false;
     *   
     *   fileTied.delete() === true;
     *   fileHak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonPerusnimi(tiedosto);
        try (Scanner lukija = new Scanner(new FileInputStream(new File(this.getTiedostonNimi())))) {
            
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Tyontekija tyontekija = new Tyontekija();
                tyontekija.parse(rivi);
                this.lisaa(tyontekija);
            }
            this.onkoMuutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + this.getTiedostonNimi() + " ei aukea.");
        }
    }
    
    
    /**
     * Tallentaa ty�ntekij�t tiedostoon.
     * @throws SailoException jos tiedostoon kirjoittaminen ei onnistu
     */
    public void tallenna() throws SailoException {
        // TODO: Varmuuskopiointi?
        
        if (!this.onkoMuutettu) return;  // Ei tallenneta turhaan.
        
        try (PrintStream kirjoittaja = new PrintStream(new FileOutputStream(this.getTiedostonNimi(), false))) {            
            for (Tyontekija tyontekija : this) {
                kirjoittaja.println(tyontekija.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedostoon kirjoittaminen ei onnistu: " + e.getMessage());
        }
                                                                                                        
        this.onkoMuutettu = false;
    }
    
    
    /**
     * Asettaa tiedoston perusnimen (ilman tiedostop��tett�).
     * @param nimi asetettava nimi
     */
    public void setTiedostonPerusnimi(String nimi) {
        this.perusnimi = nimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen (ilman tiedostop��tett�).
     * @return tiedoston nimi
     */
    public String getTiedostonPerusnimi() {
        return this.perusnimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen (tiedostonp��te mukana).
     * @return tallennettavan tiedoston nimi
     */
    public String getTiedostonNimi() {
        return this.perusnimi + ".dat";
    }
    

    /**
     * Palauttaa iteraattorin, jolla voi k�yd� l�pi tietorakenteeseen tallennetut
     * ty�ntekij�t.
     * @return iteraattori
     */
    @Override
    public Iterator<Tyontekija> iterator() {
        return new TyontekijatIter();
    }
    
    
    /**
     * Luokka ty�ntekij�iden iteroimista varten.
     * @author Marko Moilanen
     * @version 2.4.2018
     * @example 
     * <pre name="test">
     * #THROWS SailoException
     * #THROWS NoSuchElementException
     * #import java.util.Iterator;
     * #import java.util.NoSuchElementException;
     * 
     *   Tyontekijat tyontekijat = new Tyontekijat();
     *   Tyontekija tyol1 = new Tyontekija(); tyol1.rekisteroi();
     *   Tyontekija tyol2 = new Tyontekija(); tyol2.rekisteroi();
     *   Tyontekija tyol3 = new Tyontekija(); tyol3.rekisteroi(); 
     *   tyontekijat.lisaa(tyol1);
     *   tyontekijat.lisaa(tyol2);
     *   tyontekijat.lisaa(tyol3);
     *   tyontekijat.lisaa(tyol2);

     *   // foreach-silmukka:
     *   StringBuilder idt = new StringBuilder();
     *   for (Tyontekija tyol : tyontekijat) {
     *     idt.append(tyol.getId());
     *   }
     *   idt.toString() === "" + tyol1.getId() + tyol2.getId() + tyol3.getId() + tyol2.getId();
     *   
     *   // Iteraattori:
     *   Iterator<Tyontekija> vipellin = tyontekijat.iterator();
     *   vipellin.next() === tyol1;
     *   vipellin.next() === tyol2;
     *   vipellin.next() === tyol3;
     *   vipellin.next() === tyol2;
     *   
     *   vipellin.next();  #THROWS NoSuchElementException
     * </pre>
     */
    public class TyontekijatIter implements Iterator<Tyontekija> {
        
        private int kohdalla = 0;
        
        
        /**
         * Palauttaa tiedon siit�, onko seuraavaa ty�ntekij��.
         * @return true, jos seuraava ty�ntekij� on olemassa
         */
        @Override
        public boolean hasNext() {
            return (this.kohdalla < getLkm());
        }

        
        /**
         * Palauttaa seuraavan ty�ntekij�n.
         * @return seuraava ty�ntekij�
         * @thows NoSuchElementException jos seuraavaa ty�ntekij�� ei ole
         */
        @Override
        public Tyontekija next() {
            if (!hasNext()) throw new NoSuchElementException("Ei en�� alkioita.");
            return anna(this.kohdalla++);
        }
    }
    
    
    /**
     * P��ohjelma testaamista varten.
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {        
             
        Tyontekijat tekijat = new Tyontekijat();
        Tyontekija tyol1 = new Tyontekija(); tyol1.parse("1|Virtanen Jaakko|1111|ei ole|");
        Tyontekija tyol2 = new Tyontekija(); tyol2.parse("2|Ahonen Jaakko|1110|on|");
        Tyontekija tyol3 = new Tyontekija(); tyol3.parse("3|Virtanen Petteri|2222|on|");
        Tyontekija tyol4 = new Tyontekija(); tyol4.parse("4|Suhonen Jaakko|3333|ei ole|");
        tekijat.lisaa(tyol1); tekijat.lisaa(tyol2); tekijat.lisaa(tyol3); tekijat.lisaa(tyol4);

        List<Tyontekija> tulos1 = new ArrayList<Tyontekija>(); tulos1.add(tyol2); tulos1.add(tyol1);
        tekijat.hae("nimi", "Jaakko  ");//.equals(tulos1);
        tekijat.hae("henkil�numero", "3");

    }
}
