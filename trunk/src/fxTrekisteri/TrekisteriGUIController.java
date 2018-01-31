package fxTrekisteri;

import javafx.application.Platform;
import javafx.fxml.FXML;
import fi.jyu.mit.fxgui.*;


/**
 * Käsittelee käyttöliittymän tapahtumat.
 * 
 * @author Marko Moilanen
 * @version 13.1.2018
 *
 */
public class TrekisteriGUIController {
    /**
     * Käsittelee uuden työntekijän lisäämisen.
     */
    @FXML private void handleUusiTyontekija() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    
    /**
     * Käsittelee tallennuskäskyn.
     */
    @FXML private void handleTallenna() {
        tallenna();
    }

    
    /**
     * Käsittelee lopetuskäskyn.
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }

    
    /**
     * Tietojen tallennus.
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Ei toimi vielä");
    }
    
    
     /**
     * Tarkistaa, onko tallennus tehty.
     * @return true, jos saa sulkea, muuten false
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
}
