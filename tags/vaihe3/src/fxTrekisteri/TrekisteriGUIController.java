package fxTrekisteri;

import javafx.application.Platform;
import javafx.fxml.FXML;
import fi.jyu.mit.fxgui.*;


/**
 * K�sittelee k�ytt�liittym�n tapahtumat.
 * @author Marko Moilanen
 * @version 13.2.2018
 *
 */
public class TrekisteriGUIController {
    
    /**
     * K�sittelee tallennusk�skyn.
     */
    @FXML private void handleTallenna() {
        tallenna();
    }

    
    /**
     * K�sittelee Avaa-k�skyn.
     */
    @FXML private void handleAvaa() {
        avaa();
    }
    
    
    /**
     * K�sittelee lopetusk�skyn.
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }

    
    /**
     * K�sittelee uuden ty�ntekij�n lis��misen.
     */
    @FXML private void handleLisaaTyontekija() {
        lisaaTyontekija();
    }


    /**
     * K�sittelee ty�ntekij�n poistamisen.
     */
    @FXML private void handlePoistaTyontekija() {
        poistaTyontekija();
    }
    
    
    /**
     * K�sittelee kohteen lis�yksen.
     */
    @FXML private void handleLisaaKohde() {
        lisaaKohde();
    }
    
    
    /**
     * K�sittelee kohteen poistamisen.
     */
    @FXML private void handlePoistaKohde() {
        poistaKohde();
    }
    
    
    /**
     * K�sittelee Apua-k�skyn.
     */
    @FXML private void handleApua() {
        naytaHelp();
    }
    
    
    /**
     * K�sittelee Tietoja-k�skyn.
     */
    @FXML private void handleTietoja() {
        naytaTiedot();
    }
    
    
    /**
     * Tietojen tallennus.
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Ei osata viel� tallentaa.");
    }
    
    
    /**
     * Kysyy tiedoston nimen ja lukee kyseisen tiedoston.
     */
    private void avaa() {  // Oletusk�sittelij�.
        ModalController.showModal(TrekisteriGUIController.class.getResource("AvaaView.fxml"), "Avaa", null, "");
    }
    
    
    /**
     * Lis�� uuden ty�ntekij�n.
     */
    private void lisaaTyontekija() {  // Oletusk�sittelij�.
        ModalController.showModal(TrekisteriGUIController.class.getResource("UusiView.fxml"), "Lis�� ty�ntekij�", null, "");
    }

    
    /**
     * Poistaa tyontekij�n.
     */
    private void poistaTyontekija() {
        Dialogs.showMessageDialog("Ei toimi viel�.");
    }
        
    
    /**
     * Lis�� kohteen ty�ntekij�n kohdeluetteloon.
     */
    private void lisaaKohde() {
        Dialogs.showMessageDialog("Ei toimi viel�.");
    }
    
    
    /**
     * Poistaa kohteen ty�ntekij�n kohdeluettelosta.
     */
    private void poistaKohde() {
        Dialogs.showMessageDialog("Ei toimi viel�.");
    }
    
    
    /**
     * N�ytt�� k�ytt�ohjeen.
     */
    private void naytaHelp() {
        Dialogs.showMessageDialog("Ei toimi viel�.");
    }
    
    
    /**
     * N�ytt�� ohjelman tiedot.
     */
    private void naytaTiedot() {  // Oletusk�sittelij�.
        ModalController.showModal(TrekisteriGUIController.class.getResource("AboutView.fxml"), "Tietoja", null, "");
    }
    
    
     /**
     * Tarkistaa, onko tallennus tehty.
     * @return true, jos saa sulkea
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
}