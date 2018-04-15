package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kohde tietää oman id:nsä ja nimensä.
 * @author Marko Moilanen
 * @version 15.4.2018
 */
public class Kohde implements Comparable<Kohde> {

    private int kohdeId;
    private String nimi;
    
    private static int seuraavaId = 1;
    
    
    /**
     * Palauttaa kohteen id:n.
     * @return kohteen id-numero
     */
    public int getId() {
        return this.kohdeId;
    }
    
    
    /**
     * Palauttaa kohteen nimen.
     * @return kohteen nimi
     */
    public String getNimi() {
        return this.nimi;
    }
    
    
    /**
     * Täyttää kohteen tiedot.
     * TODO: tämä on rakennusteline.
     */
    public void taytaTiedot() {
        this.nimi = "Avotoimistohässäkkä Oy " + rand(1, 100);  // Perään satunnainen luku erottelun vuoksi.
    }


    /**
     * Antaa satunnaisen kokonaisluvun annetulta väliltä
     * TODO: tämä metodi poistetaan, kun sitä ei tarvita.
     * @param ala alaraja
     * @param yla yläraja
     * @return satunnaisluku väliltä [ala, yla[
     */
    public static int rand(int ala, int yla) {
        double n = (yla - ala) * Math.random() + ala; 
        return (int) Math.round(n);
    }
    
    
    /**
     * Antaa kohteelle id:n.
     * @example
     * <pre name="test">
     *   Kohde kohde1 = new Kohde();
     *   kohde1.getId() === 0;
     *   kohde1.rekisteroi();
     *   Kohde kohde2 = new Kohde();
     *   kohde2.getId() === 0;
     *   kohde2.rekisteroi();
     *   
     *   // Peräkkäin rekisteröityjen kohteiden id:t ovat peräkkäisiä.
     *   int id1 = kohde1.getId();
     *   int id2 = kohde2.getId();
     *   id2 - id1 === 1;
     *   
     *   // Jos sama kohde rekisteröidään uudelleen, id ei muutu.
     *   kohde1.rekisteroi();
     *   kohde1.getId() === id1;
     * </pre>
     */
    public void rekisteroi() {
        if (this.getId() != 0) return;  // Jos id on jo annettu, ei tehdä mitään.
        this.kohdeId = seuraavaId;
        seuraavaId++;
    }

    
    //TODO testit
    @Override
    public int compareTo(Kohde verrattava) {
        return this.getNimi().compareTo(verrattava.getNimi());
    }

    
    /**
     * Tulostaa kohteen tiedot.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.kohdeId + " " + this.nimi);
        //TODO: korjaa (tarvitaanko tätä?)
    }
    
    
    /**
     * Asettaa kohteen id-numeron. Samalla varmistetaan, että
     * seuraavaksi annettava id on ajan tasalla.
     * @param numero
     */
    private void setId(int numero) {
        this.kohdeId = numero;
        if (this.kohdeId >= seuraavaId) seuraavaId = this.kohdeId + 1;
    }    
    
    
    /**
     * Selvittää ja tallentaa kohteen tiedot tolppamerkein erotellusta merkkijonosta.
     * @param tiedot kohteen tiedot tolppamerkein eroteltuna
     */
    public void parse(String tiedot) {
        StringBuilder rivi = new StringBuilder(tiedot);
        this.setId(Mjonot.erota(rivi, '|', this.kohdeId));
        this.nimi = Mjonot.erota(rivi, '|').trim();
    }
    
    
    /**
     * Palauttaa kohteen tiedot tolppamerkein eroteltuna merkkijonona.
     * @return kohteen tiedot merkkijonona
     * @example
     * <pre name="test">
     *   Kohde kohde1 = new Kohde();
     *   kohde1.parse("  91 | Toimisto Oy  ");
     *   kohde1.toString() === "91|Toimisto Oy";
     *   
     *   Kohde kohde2 = new Kohde();
     *   kohde2.parse("  73  | ");
     *   kohde2.toString() === "73|";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + this.getId() + "|"
                  + this.nimi;
    }
    
    
    /**
     * Tulostaa kohteen tiedot.
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Pääohjelma testaamista varten.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kohde kohde1 = new Kohde();
        Kohde kohde2 = new Kohde();
        
        kohde1.tulosta(System.out);
        kohde2.tulosta(System.out);
        
        kohde1.rekisteroi(); kohde1.taytaTiedot();
        kohde2.rekisteroi(); kohde2.taytaTiedot();
        
        kohde1.tulosta(System.out);
        kohde2.tulosta(System.out);

    }
}
