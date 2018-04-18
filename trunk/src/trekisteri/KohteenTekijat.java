package trekisteri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * KohteenTekijat-luokka hallinnoi yksittäisiä kohteen tekijöitä.
 * @author Marko Moilanen
 * @version 15.4.2018
 */
public class KohteenTekijat {
    
    private Collection<KohteenTekija> alkiot = new LinkedList<KohteenTekija>();
    private String perusnimi = "kohteenTekijat";
    private boolean onkoMuutettu = false;
    
    
    /**
     * Palauttaa listan tyontekijälle kuuluvien kohdeiden id-numeroista. 
     * @param tyolainenId sen työntekijän id, jonka kohteita etsitään
     * @return lista työntekijälle kuuluvien kohteiden id-numeroista
     * @example
     * <pre name="test">
     * #import java.util.List;
     * #import java.util.ArrayList;
     *   Kohde kohde1 = new Kohde();
     *   kohde1.rekisteroi(); kohde1.taytaTiedot();
     *   Kohde kohde2 = new Kohde();
     *   kohde2.rekisteroi(); kohde2.taytaTiedot();
     *   Kohde kohde3 = new Kohde();
     *   kohde3.rekisteroi(); kohde3.taytaTiedot();
     *   
     *   Tyontekija tyol1 = new Tyontekija();
     *   tyol1.rekisteroi(); tyol1.taytaTiedot();
     *   Tyontekija tyol2 = new Tyontekija();
     *   tyol2.rekisteroi(); tyol2.taytaTiedot();
     *   
     *   KohteenTekijat tekijat = new KohteenTekijat();
     *   tekijat.lisaa(tyol1.getId(), kohde1.getId());
     *   tekijat.lisaa(tyol1.getId(), kohde2.getId());
     *   tekijat.lisaa(tyol1.getId(), kohde3.getId());
     *   tekijat.lisaa(tyol2.getId(), kohde3.getId());
     *   
     *   List<Integer> tulos1 = new ArrayList<Integer>();
     *   tulos1.add(kohde1.getId());
     *   tulos1.add(kohde2.getId());
     *   tulos1.add(kohde3.getId());
     *   
     *   List<Integer> tulos2 = new ArrayList<Integer>();
     *   tulos2.add(kohde3.getId());
     *   
     *   List<Integer> vaara = new ArrayList<Integer>();
     *   vaara.add(kohde2.getId());
     *   
     *   tekijat.annaKohdeIdt(tyol1.getId()).equals(tulos1) === true;
     *   tekijat.annaKohdeIdt(tyol1.getId()).equals(vaara) === false;
     *   tekijat.annaKohdeIdt(tyol2.getId()).equals(tulos2) === true;
     *   tekijat.annaKohdeIdt(tyol2.getId()).equals(vaara) === false;
     */
    public List<Integer> annaKohdeIdt(int tyolainenId) {
        List<Integer> loydetyt = new ArrayList<Integer>();
        for (KohteenTekija tekija : this.alkiot) {
            if (tekija.getTyolainenId() == tyolainenId) loydetyt.add(tekija.getKohdeId());
        }
        
        return loydetyt;
    }
    
    
    /**
     * Lisää luetteloon uuden kohteen tekijän.
     * @param tyolainenId lisättävän työntekijän id-numero
     * @param kohdeId lisättävän kohteen id-numero
     */
    public void lisaa(int tyolainenId, int kohdeId) {
        KohteenTekija lisattava = new KohteenTekija(tyolainenId, kohdeId);
        this.lisaa(lisattava);
    }
    
    
    /**
     * Palauttaa listan niiden työntekijöiden id-numeroista, 
     * jotka ovat ovat annettua kohdeId:tä vastaavan kohteen
     * tekijöitä.
     * @param kohdeId sen kohteen id-numero, jonka työntekijöitä haetaan
     * @return lista kohdetta tekevien työntekijöiden id-numeroista
     * @example
     * <pre name="test">
     *   // Työntekijä-id:t: 1, 2, 4, 5.
     *   // Kohde-id:t: 1, 2, 3.
     *   
     *   KohteenTekijat kt = new KohteenTekijat();
     *   kt.lisaa(1, 1); kt.lisaa(1, 2); 
     *   kt.lisaa(2, 1); 
     *   kt.lisaa(4, 2);
     *   kt.lisaa(5, 1);
     *   
     *   List<Integer> tulos1 = new ArrayList<Integer>();
     *   tulos1.add(1); tulos1.add(2); tulos1.add(5);
     *   List<Integer> vastaus1 = kt.hae(1); vastaus1.sort(null);
     *   tulos1.equals(vastaus1) === true;
     *   
     *   List<Integer> tulos2 = new ArrayList<Integer>();
     *   tulos2.add(1); tulos2.add(4);
     *   List<Integer> vastaus2 = kt.hae(2); vastaus2.sort(null);
     *   tulos2.equals(vastaus2) === true;
     *   
     *   List<Integer> tulos3 = new ArrayList<Integer>();
     *   List<Integer> vastaus3 = kt.hae(3); vastaus3.sort(null);
     *   tulos3.equals(vastaus3) === true;
     * </pre>
     */
    public List<Integer> hae(int kohdeId) {
        List<Integer> tulos = new ArrayList<Integer>();
        
        for (KohteenTekija kt : this.alkiot) {
            if (kt.getKohdeId() == kohdeId)
                tulos.add(kt.getTyolainenId());
        }
        
        return tulos;
    }
    
    
    /**
     * Lisää luetteloon uuden kohteen tekijän.
     * @param lisattava kohteen tekijä, joka lisätään
     */
    public void lisaa(KohteenTekija lisattava) {
        this.alkiot.add(lisattava);
        this.onkoMuutettu = true;
    }
    
     
    /**
     * Poistaa kohteen tekijän tietorakenteesta.
     * @param tyolainenId poistettavan kohteen tekijän työntekijän id-numero
     * @param kohdeId poistettavan kohteen tekijän kohteen id-numero
     * @example
     * <pre name="test">
     *   Tyontekija t1 = new Tyontekija(); t1.rekisteroi();
     *   Tyontekija t2 = new Tyontekija(); t2.rekisteroi();
     *
     *   Kohde k1 = new Kohde(); k1.rekisteroi(); int id1 = k1.getId();
     *   Kohde k2 = new Kohde(); k2.rekisteroi(); int id2 = k2.getId();
     *   
     *   KohteenTekijat tekijat = new KohteenTekijat();
     *   tekijat.lisaa(t1.getId(), id1);
     *   tekijat.lisaa(t1.getId(), id2);
     *   tekijat.lisaa(t2.getId(), id2);
     *   
     *   List<Integer> tulos1 = new ArrayList<Integer>();
     *   tulos1.add(id1);
     *   tulos1.add(id2);
     *   List<Integer> tulos2 = new ArrayList<Integer>();
     *   tulos2.add(id2);
     * 
     *   tekijat.annaKohdeIdt(t1.getId()).equals(tulos1) === true;
     *   tekijat.annaKohdeIdt(t2.getId()).equals(tulos2) === true;
     *   
     *   tekijat.poista(t2.getId(), id1);
     *   tekijat.annaKohdeIdt(t2.getId()).equals(tulos2) === true;
     *   
     *   tekijat.poista(t1.getId(), id1);
     *   tekijat.poista(t1.getId(), id2);
     *   tekijat.annaKohdeIdt(t1.getId()).isEmpty() === true;
     * </pre>
     */
    public void poista(int tyolainenId, int kohdeId) {
        KohteenTekija poistettava = null;
        for (KohteenTekija kt : this.alkiot) {
            if (kt.getTyolainenId() == tyolainenId && kt.getKohdeId() == kohdeId) {
                poistettava = kt;
                break;
            }            
        }
        
        this.alkiot.remove(poistettava);
        this.onkoMuutettu = true;
    }
    
    
    /**
     * Poistaa kaikki työntekijää vastaavat kohteen tekijät.
     * @param tyolainenId sen työntekijän id-numero, jota vastaavat
     * kohteen tekijät poistetaan
     * @example
     * <pre name="test">
     *   KohteenTekija kt1 = new KohteenTekija(); kt1.parse("1|1|1");
     *   KohteenTekija kt2 = new KohteenTekija(); kt2.parse("2|2|1");
     *   KohteenTekija kt3 = new KohteenTekija(); kt3.parse("3|2|3");
     *   KohteenTekija kt4 = new KohteenTekija(); kt4.parse("8|4|1");
     *   KohteenTekija kt5 = new KohteenTekija(); kt5.parse("9|2|4");
     *   KohteenTekijat tekijat = new KohteenTekijat();
     *   tekijat.lisaa(kt1); tekijat.lisaa(kt2); tekijat.lisaa(kt3); tekijat.lisaa(kt4); tekijat.lisaa(kt5);
     *   
     *   List<Integer> tulos1 = new ArrayList<Integer>(); tulos1.add(1);
     *   List<Integer> vast1 = tekijat.annaKohdeIdt(1);
     *   vast1.equals(tulos1) === true;
     *   tekijat.poista(1);
     *   List<Integer> tulos2 = new ArrayList<Integer>();
     *   List<Integer> vast2 = tekijat.annaKohdeIdt(1);
     *   vast2.equals(tulos2) === true;
     *   
     *   List<Integer> tulos3 = new ArrayList<Integer>(); tulos3.add(1); tulos3.add(3); tulos3.add(4);
     *   List<Integer> vast3 = tekijat.annaKohdeIdt(2); //vast3.sort(null); 
     *   vast3.equals(tulos3) === true;
     *   tekijat.poista(2);
     *   List<Integer> tulos4 = new ArrayList<Integer>();
     *   List<Integer> vast4 = tekijat.annaKohdeIdt(2);
     *   vast4.equals(tulos4) === true;
     * </pre>
     */
    public void poista(int tyolainenId) {
        // Kerätään ensin lista poistettavista.        
        List<KohteenTekija> poistettavat = new ArrayList<KohteenTekija>();        
        for (KohteenTekija kt : this.alkiot) {
            if (tyolainenId == kt.getTyolainenId())
                poistettavat.add(kt);
        }
        
        // Poistetaan:
        this.alkiot.removeAll(poistettavat);
        
        this.onkoMuutettu = true;
    }
    
    
    /**
     * Luetaan tiedostosta, jonka nimi on annettu aiemmin.
     * @throws SailoException jos tiedoston lukeminen ei onnistu 
     */
    public void lueTiedostosta() throws SailoException {
        this.lueTiedostosta(this.getTiedostonPerusnimi());
    }
    
    
    /** 
     * Lukee kohteen tekijöiden tiedot tiedostosta.
     * @param tiedosto luettavan tiedoston nimi ilman tiedostopäätettä
     * @throws SailoException jos tiedoston lukeminen ei onnistu
     * TODO testit
     */
    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonPerusnimi(tiedosto);
        try (Scanner lukija = new Scanner(new FileInputStream(new File(this.getTiedostonNimi())))) {
            
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                KohteenTekija tekija = new KohteenTekija();
                tekija.parse(rivi);
                this.lisaa(tekija);
            }
            this.onkoMuutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + this.getTiedostonNimi() + " ei aukea.");
        }
    }
    
    
    /**
     * Tallentaa kohteen tekijät tiedostoon.
     * @throws SailoException jos tiedostoon kirjoittaminen ei onnistu
     * TODO: testit
     */
    public void tallenna() throws SailoException {
        // TODO: Varmuuskopiointi?
        
        if (!this.onkoMuutettu) return;  // Ei tallenneta turhaan.
        
        try (PrintStream kirjoittaja = new PrintStream(new FileOutputStream(this.getTiedostonNimi(), false))) {            
            for (KohteenTekija tekija : this.alkiot) {
                kirjoittaja.println(tekija.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedostoon kirjoittaminen ei onnistu: " + e.getMessage());
        }
                                                                                                        
        this.onkoMuutettu = false;
    }    
    
    
    /**
     * Asettaa tiedoston perusnimen (ilman tiedostopäätettä).
     * @param nimi asetettava nimi
     */
    public void setTiedostonPerusnimi(String nimi) {
        this.perusnimi = nimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen (ilman tiedostopäätettä).
     * @return tiedoston nimi
     */
    public String getTiedostonPerusnimi() {
        return this.perusnimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen (tiedostonpääte mukana).
     * @return tallennettavan tiedoston nimi
     */
    public String getTiedostonNimi() {
        return this.perusnimi + ".dat";
    }
    
    
    /**
     * Pääohjelma testaamista varten.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        //
    }
}
