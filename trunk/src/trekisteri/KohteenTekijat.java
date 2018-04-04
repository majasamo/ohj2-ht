package trekisteri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * KohteenTekijat-luokka hallinnoi yksittäisiä kohteen tekijöitä.
 * @author Marko Moilanen
 * @version 4.4.2018
 */
public class KohteenTekijat {
    
    private Collection<KohteenTekija> alkiot = new LinkedList<KohteenTekija>();
    private String perusnimi = "kohteenTekijat";
    
    
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
     * Lisää luetteloon uuden kohteen tekijän.
     * @param lisattava kohteen tekijä, joka lisätään
     */
    public void lisaa(KohteenTekija lisattava) {
        this.alkiot.add(lisattava);
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
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                KohteenTekija tekija = new KohteenTekija();
                tekija.parse(rivi);
                this.lisaa(tekija);
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + this.getTiedostonNimi() + " ei aukea.");
        }
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
