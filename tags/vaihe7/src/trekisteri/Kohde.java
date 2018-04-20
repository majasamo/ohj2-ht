package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kohde tietää oman id:nsä ja nimensä.
 * @author Marko Moilanen
 * @version 18.4.2018
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

    
    /**
     * @example
     * <pre name="test">
     *   Kohde k1 = new Kohde(); k1.parse("1|Firma");
     *   Kohde k2 = new Kohde(); k2.parse("2|Pankki");
     *   Kohde k3 = new Kohde(); k3.parse("3|Pankki");
     *   
     *   (k1.compareTo(k2) < 0) === true;
     *   (k1.compareTo(k3) < 0) === true;
     *   (k2.compareTo(k1) > 0) === true;
     *   k2.compareTo(k3) === 0;
     * </pre>
     */
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
        Kohde k = new Kohde();
        k.rekisteroi();
        k.parse("|jokinNimi");
        System.out.println(k);

    }
}
