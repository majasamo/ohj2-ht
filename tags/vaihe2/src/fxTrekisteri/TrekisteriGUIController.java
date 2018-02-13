package fxTrekisteri;

import javafx.application.Platform;
import javafx.fxml.FXML;
import fi.jyu.mit.fxgui.*;


/**
 * K�sittelee k�ytt�liittym�n tapahtumat.
 * 
 * @author Marko Moilanen
 * @version 13.1.2018
 *
 */
public class TrekisteriGUIController {
    /**
     * K�sittelee uuden ty�ntekij�n lis��misen.
     */
    @FXML private void handleUusiTyontekija() {
        Dialogs.showMessageDialog("Ei toimi viel�");
    }
    
    
    /**
     * K�sittelee tallennusk�skyn.
     */
    @FXML private void handleTallenna() {
        tallenna();
    }

    
    /**
     * K�sittelee lopetusk�skyn.
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }

    
    /**
     * Tietojen tallennus.
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Ei toimi viel�");
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
