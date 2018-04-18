package trekisteri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Kohteet-luokka hallinnoi yksitt‰isi‰ kohteita.
 * @author Marko Moilanen
 * @version 18.4.2018
 */
public class Kohteet {

    private Collection<Kohde> alkiot = new ArrayList<Kohde>();
    private String perusnimi = "kohteet";
    private boolean onkoMuutettu = false;
    
    
    /**
     * Lis‰‰ uuden kohteen.
     * @param lisattava kohde, joka lis‰t‰‰n
     * @example
     * <pre name="test">
     *   Kohteet kohteet = new Kohteet();
     *   Kohde kohde1 = new Kohde(); kohde1.rekisteroi();
     *   Kohde kohde2 = new Kohde(); kohde2.rekisteroi();
     *   kohteet.getLkm() === 0;
     *   kohteet.lisaa(kohde1); kohteet.getLkm() === 1;
     *   kohteet.lisaa(kohde2); kohteet.getLkm() === 2;
     *   kohteet.lisaa(kohde1); kohteet.getLkm() === 3;
     *   kohteet.lisaa(kohde1); kohteet.getLkm() === 4;
     *   kohteet.anna(kohde1.getId()) === kohde1;
     *   kohteet.anna(kohde2.getId()) === kohde2;
     *   kohteet.anna(kohde2.getId() + 1) === null;
     * </pre>
     */
    public void lisaa(Kohde lisattava) {
        this.alkiot.add(lisattava);
        this.onkoMuutettu = true;
    }
    
    
    /**
     * Palauttaa kohteiden lukum‰‰r‰n.
     * @return kohteiden lukum‰‰r‰
     */
    public int getLkm() {
        return this.alkiot.size();
    }
    
    
    /**
     * Palauttaa annettua id-numeroa vastaavan kohteen.
     * @param kohdeId haettavan kohteen id-numero 
     * @return id-numeroa vastaava kohde; jos ei lˆydy, palautetaan null
     */
    public Kohde anna(int kohdeId) {
        for (Kohde kohde : this.alkiot) {
            if (kohde.getId() == kohdeId) return kohde;
        }
        return null;
    }
    
    
    /**
     * Palauttaa listan niiden kohteiden id-numeroista, joiden nimi tai nimen
     * osa t‰sm‰‰ annetun hakusanan kanssa.
     * @param hakusana hakusana, jolla haetaan
     * @return lista hakusanaa vastaavien kohteiden id-numeroista. Jos hakusana
     * on tyhj‰ tai koostuu pelkist‰ v‰lilyˆnnteist‰, palautetaan kaikkien
     * kohteiden id:t.
     * @example
     * <pre name="test">
     * #import java.util.List;
     * #import java.util.ArrayList;
     *   Kohde k1 = new Kohde(); k1.parse("1|Firma");
     *   Kohde k2 = new Kohde(); k2.parse("2|Sepon autohuolto");
     *   Kohde k3 = new Kohde(); k3.parse("3|Mikon varaosa");
     *   Kohde k4 = new Kohde(); k4.parse("4|Mikan varaosa");
     *   
     *   Kohteet kohteet = new Kohteet();
     *   kohteet.lisaa(k1); kohteet.lisaa(k2); kohteet.lisaa(k3); kohteet.lisaa(k4);
     *   
     *   List<Integer> tulos1 = new ArrayList<Integer>();
     *   tulos1.add(1); tulos1.add(2); tulos1.add(3); tulos1.add(4);
     *   List<Integer> vastaus1 = kohteet.hae(""); vastaus1.sort(null);
     *   tulos1.equals(vastaus1) === true;
     *   List<Integer> vastaus2 = kohteet.hae("  "); vastaus2.sort(null);
     *   tulos1.equals(vastaus1) === true;
     *   
     *   List<Integer> tulos2 = new ArrayList<Integer>();
     *   tulos2.add(3); tulos2.add(4);
     *   List<Integer> vastaus3 = kohteet.hae(" Mik"); vastaus3.sort(null);
     *   tulos2.equals(vastaus3) === true;
     *   
     *   List<Integer> tulos3 = new ArrayList<Integer>();
     *   tulos3.add(1);
     *   List<Integer> vastaus4 = kohteet.hae(" f "); vastaus4.sort(null);
     *   tulos3.equals(vastaus4) === true;
     * </pre>
     */
    public List<Integer> hae(String hakusana) {
        String sana = hakusana.trim().toLowerCase();        
        List<Integer> tulos = new ArrayList<Integer>();
        
        for (Kohde kohde : this.alkiot) {
            if (WildChars.wildmat(kohde.getNimi().toLowerCase(), "*" + sana + "*"))
                tulos.add(kohde.getId());
        }

        return tulos;
    }
    
    
    /**
     * Palauttaa kohteen nime‰ vastaavan id-numeron. Jos kohdetta ei ole, se luodaan.
     * @param nimi kohteen nimi
     * @return nime‰ vastaava kohdeId
     * @example
     * <pre name="test">
     *   Kohde k1 = new Kohde(); k1.rekisteroi(); k1.parse("|Maansiirto Oy");
     *   Kohde k2 = new Kohde(); k2.rekisteroi(); k2.parse("|Urakointi Ky");
     *   Kohteet kohteet = new Kohteet(); kohteet.lisaa(k1); kohteet.lisaa(k2);
     *   
     *   kohteet.haeId("Maansiirto Oy") === k1.getId();
     *   kohteet.haeId("Urakointi Ky") === k2.getId();
     *   kohteet.getLkm() === 2;
     *   kohteet.haeId("Yritys");
     *   kohteet.getLkm() === 3;
     * </pre>
     */
    public int haeId(String nimi) {
        // Jos nimi on jo olemassa, palautetaan sit‰ vastaava id.
        for(Kohde kohde : this.alkiot) {
            if (kohde.getNimi().equals(nimi))
                return kohde.getId();
        }
        
        // Jos nimell‰ ei lˆydy kohdetta, luodaan uusi kohde, lis‰t‰‰n se
        // ja palautetaan sen id.
        Kohde uusi = new Kohde();
        uusi.rekisteroi();
        uusi.parse("|" + nimi);
        this.lisaa(uusi);
        this.onkoMuutettu = true;
        return uusi.getId();
    }
    
    
    /**
     * Luetaan tiedostosta, jonka nimi on annettu aiemmin.
     * @throws SailoException jos tiedoston lukeminen ei onnistu 
     */
    public void lueTiedostosta() throws SailoException {
        this.lueTiedostosta(this.getTiedostonPerusnimi());
    }
    
    
    /** 
     * Lukee kohteiden tiedot tiedostosta.
     * @param tiedosto luettavan tiedoston nimi ilman tiedostop‰‰tett‰
     * @throws SailoException jos tiedoston lukeminen ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * 
     *   String tiedosto = "testiyritys/kohteet";
     *   File fileTied = new File(tiedosto + ".dat");
     *   File fileHak = new File("testiyritys");
     *   fileHak.mkdir();
     *   fileTied.delete();
     *   
     *   Kohteet kohteet = new Kohteet(); 
     *   Kohde kohde1 = new Kohde(); kohde1.rekisteroi(); kohde1.parse("1|Firma Oy");
     *   int nro1 = kohde1.getId();
     *   Kohde kohde2 = new Kohde(); kohde2.rekisteroi(); kohde2.parse("4|Pankki Ab");
     *   int nro2 = kohde2.getId();
     *   kohteet.lueTiedostosta(tiedosto);  #THROWS SailoException
     *   
     *   // Lis‰t‰‰n ja tallennetaan. Muutosten pit‰isi tallentua tiedostoon.
     *   kohteet.lisaa(kohde1);
     *   kohteet.lisaa(kohde2);
     *   kohteet.tallenna();
     *   // Tuhotaan vanhat tiedot ja testataan, s‰ilyiv‰tkˆ tiedostoon tallennetut
     *   // tiedot.
     *   kohteet = new Kohteet();
     *   kohteet.lueTiedostosta(tiedosto);
     *   kohteet.anna(nro1).toString() === kohde1.toString();
     *   kohteet.anna(nro2).toString() === kohde2.toString();
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
                Kohde kohde = new Kohde();
                kohde.parse(rivi);
                this.lisaa(kohde);
            }
            this.onkoMuutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + this.getTiedostonNimi() + " ei aukea.");
        }
    }
    
    
    /**
     * Tallentaa kohteet tiedostoon.
     * @throws SailoException jos tiedostoon kirjoittaminen ei onnistu
     */
    public void tallenna() throws SailoException {
        if (!this.onkoMuutettu) return;  // Ei tallenneta turhaan.
        
        try (PrintStream kirjoittaja = new PrintStream(new FileOutputStream(this.getTiedostonNimi(), false))) {            
            for (Kohde kohde : this.alkiot) {
                kirjoittaja.println(kohde.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedostoon kirjoittaminen ei onnistu: " + e.getMessage());
        }
                                                                                                        
        this.onkoMuutettu = false;
    }
        
    
    /**
     * Asettaa tiedoston perusnimen (ilman tiedostop‰‰tett‰).
     * @param nimi asetettava nimi
     */
    public void setTiedostonPerusnimi(String nimi) {
        this.perusnimi = nimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen (ilman tiedostop‰‰tett‰).
     * @return tiedoston nimi
     */
    public String getTiedostonPerusnimi() {
        return this.perusnimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen (tiedostonp‰‰te mukana).
     * @return tallennettavan tiedoston nimi
     */
    public String getTiedostonNimi() {
        return this.perusnimi + ".dat";
    }
}
