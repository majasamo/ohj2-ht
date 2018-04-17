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
     *   Kohde k1 = new Kohde(); k1.parse("1|Firma");
     *   Kohde k2 = new Kohde(); k2.parse("2|Yritys");
     *   Kohde k3 = new Kohde(); k3.parse("4|Oy");
     *   
     *   Tyontekija t1 = new Tyontekija(); t1.parse("1");
     *   Tyontekija t2 = new Tyontekija(); t2.parse("3");
     *   Tyontekija t3 = new Tyontekija(); t3.parse("4");
     *   Tyontekija t4 = new Tyontekija(); t4.parse("5");
     *   
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
        Kohde kohde1 = new Kohde();
        kohde1.taytaTiedot();
        kohde1.rekisteroi();
        
        Kohde kohde2 = new Kohde();
        kohde2.taytaTiedot();
        kohde2.rekisteroi();
        
        Kohde kohde3 = new Kohde();
        kohde3.taytaTiedot();
        kohde3.rekisteroi();
        
        Tyontekija tyol1 = new Tyontekija();
        tyol1.rekisteroi();
        
        Tyontekija tyol2 = new Tyontekija();
        tyol2.rekisteroi();
        
        KohteenTekijat tekijat = new KohteenTekijat(); // Työntekijä - kohde
        tekijat.lisaa(tyol1.getId(), kohde1.getId());  // 1 - 1
        tekijat.lisaa(tyol1.getId(), kohde2.getId());  // 1 - 2
        tekijat.lisaa(tyol1.getId(), kohde3.getId());  // 1 - 3
        tekijat.lisaa(tyol2.getId(), kohde1.getId());  // 2 - 1        
        
        List<Integer> ykkosenKohteet = tekijat.annaKohdeIdt(tyol1.getId());        
        List<Integer> kakkosenKohteet = tekijat.annaKohdeIdt(tyol2.getId());
        
        for (int id : ykkosenKohteet) {
            System.out.println(id);
        }
        System.out.println("-------");        
        for (int id : kakkosenKohteet) {
            System.out.println(id);
        }
    }
}
