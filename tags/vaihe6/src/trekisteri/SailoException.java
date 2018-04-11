package trekisteri;

/**
 * Poikkeusluokka tietorakenteesta johtuville poikkeuksille.
 * @author Marko Moilanen
 * @version 5.3.2018
 */
public class SailoException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    
    /**
     * Luo uuden poikkeuksen.
     * @param viesti poikkeukseen liittyvä viesti
     */
    public SailoException(String viesti) {
       super(viesti); 
    }
}
