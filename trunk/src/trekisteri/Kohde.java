package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kohde tiet‰‰ oman id:ns‰ ja nimens‰.
 * @author Marko Moilanen
 * @version 3.4.2018
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
     * T‰ytt‰‰ kohteen tiedot.
     * TODO: t‰m‰ on rakennusteline.
     */
    public void taytaTiedot() {
        this.nimi = "Avotoimistoh‰ss‰kk‰ Oy " + rand(1, 100);  // Per‰‰n satunnainen luku erottelun vuoksi.
    }


    /**
     * Antaa satunnaisen kokonaisluvun annetulta v‰lilt‰
     * TODO: t‰m‰ metodi poistetaan, kun sit‰ ei tarvita.
     * @param ala alaraja
     * @param yla yl‰raja
     * @return satunnaisluku v‰lilt‰ [ala, yla[
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
     *   // Per‰kk‰in rekisterˆityjen kohteiden id:t ovat per‰kk‰isi‰.
     *   int id1 = kohde1.getId();
     *   int id2 = kohde2.getId();
     *   id2 - id1 === 1;
     *   
     *   // Jos sama kohde rekisterˆid‰‰n uudelleen, id ei muutu.
     *   kohde1.rekisteroi();
     *   kohde1.getId() === id1;
     * </pre>
     */
    public void rekisteroi() {
        if (this.getId() != 0) return;  // Jos id on jo annettu, ei tehd‰ mit‰‰n.
        this.kohdeId = seuraavaId;
        seuraavaId++;
    }

    
    /**
     * Tulostaa kohteen tiedot.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.kohdeId + " " + this.nimi);
        //TODO: korjaa (tarvitaanko t‰t‰?)
    }
    
    
    /**
     * Selvitt‰‰ ja tallentaa kohteen tiedot tolppamerkein erotellusta merkkijonosta.
     * @param tiedot kohteen tiedot tolppamerkein eroteltuna
     */
    public void parse(String tiedot) {
        StringBuilder rivi = new StringBuilder(tiedot);
        this.kohdeId = Mjonot.erota(rivi, '|', this.kohdeId);
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
     * P‰‰ohjelma testaamista varten.
     * @param args ei k‰ytˆss‰
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
