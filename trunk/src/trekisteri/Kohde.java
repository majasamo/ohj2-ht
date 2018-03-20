package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Kohde tietää oman id:nsä ja nimensä.
 * @author Marko Moilanen
 * @version 20.3.2018
 */
public class Kohde {

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
     *   kohde1.getId() === 1;
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
     * Tulostaa kohteen tiedot.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.kohdeId + " " + this.nimi);
        //TODO: korjaa (tarvitaanko tätä?)
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
