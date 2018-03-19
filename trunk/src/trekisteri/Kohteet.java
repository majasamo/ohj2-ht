package trekisteri;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Kohteet-luokka vastaa kohteiden lis��misest�, poistamisesta ja niin
 * edelleen.
 * @author Marko Moilanen
 * @version 6.3.2018
 */
public class Kohteet {

    private Collection<Kohde> alkiot = new ArrayList<Kohde>();
    
    
    /**
     * Lis�� uuden kohteen.
     * @param lisattava kohde, joka lis�t��n
     * @example
     * <pre name="test">
     *   Kohteet kohteet = new Kohteet();
     *   Kohde kohde1 = new Kohde();
     *   Kohde kohde2 = new Kohde();
     *   kohteet.getLkm() === 0;
     *   kohteet.lisaa(kohde1); kohteet.getLkm() === 1;
     *   kohteet.lisaa(kohde2); kohteet.getLkm() === 2;
     *   kohteet.lisaa(kohde1); kohteet.getLkm() === 3;
     *   kohteet.lisaa(kohde1); kohteet.getLkm() === 4;
     * </pre>
     */
    public void lisaa(Kohde lisattava) {
        this.alkiot.add(lisattava);
    }
    
    
    /**
     * Palauttaa kohteiden lukum��r�n.
     * @return kohteiden lukum��r�
     */
    public int getLkm() {
        return this.alkiot.size();
    }
    
    
    /**
     * Palauttaa annettua id-numeroa vastaavan kohteen.
     * @param kohdeId haettavan kohteen id-numero 
     * @return id-numeroa vastaava kohde; jos ei l�ydy, palautetaan null
     * @example TODO: testit
     */
    public Kohde anna(int kohdeId) {
        for (Kohde kohde : this.alkiot) {
            if (kohde.getId() == kohdeId) return kohde;
        }
        return null;
    }
       
    
    /**
     * P��ohjelma testaamista varten.
     * @param args ei k�yt�ss�
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
