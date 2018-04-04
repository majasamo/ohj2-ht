package trekisteri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * Kohteet-luokka hallinnoi yksitt‰isi‰ kohteita.
 * @author Marko Moilanen
 * @version 4.4.2018
 */
public class Kohteet {

    private Collection<Kohde> alkiot = new ArrayList<Kohde>();
    private String perusnimi = "kohteet";
    
    
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
     * TODO testit
     */
    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonPerusnimi(tiedosto);
        try (Scanner lukija = new Scanner(new FileInputStream(new File(this.getTiedostonNimi())))) {
            
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Kohde kohde = new Kohde();
                kohde.parse(rivi);
                this.lisaa(kohde);
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + this.getTiedostonNimi() + " ei aukea.");
        }
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
       
    
    /**
     * P‰‰ohjelma testaamista varten.
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Kohteet kohteet = new Kohteet();
        Kohde kohde1 = new Kohde();
        Kohde kohde2 = new Kohde();
        System.out.println(kohteet.getLkm());
        
        kohteet.lisaa(kohde1);
        System.out.println(kohteet.getLkm());
        
        kohteet.lisaa(kohde2);
        System.out.println(kohteet.getLkm());
        
        kohteet.lisaa(kohde1);
        System.out.println(kohteet.getLkm());
    }

}
