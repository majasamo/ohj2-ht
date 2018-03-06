package trekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Kohde tiet�� oman id:ns� ja nimens�.
 * @author Marko Moilanen
 * @version 6.3.2018
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
     * T�ytt�� kohteen tiedot.
     * TODO: t�m� on rakennusteline.
     */
    public void taytaTiedot() {
        this.nimi = "Avotoimistoh�ss�kk� Oy";
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
     *   // Per�kk�in rekister�ityjen kohteiden id:t ovat per�kk�isi�.
     *   int id1 = kohde1.getId();
     *   int id2 = kohde2.getId();
     *   id2 - id1 === 1;
     *   
     *   // Jos sama kohde rekister�id��n uudelleen, id ei muutu.
     *   kohde1.rekisteroi();
     *   kohde1.getId() === id1;
     * </pre>
     */
    public void rekisteroi() {
        if (this.getId() != 0) return;  // Jos id on jo annettu, ei tehd� mit��n.
        this.kohdeId = seuraavaId;
        seuraavaId++;
    }

    
    /**
     * Tuostaa kohteen tiedot.
     * @param out tietovirta, johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.kohdeId + " " + this.nimi);
        //TODO: korjaa (tarvitaanko t�t�?)
    }
    
    
    /**
     * Tulostaa ty�ntekij�n tiedot.
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * P��ohjelma testaamista varten.
     * @param args ei k�yt�ss�
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
