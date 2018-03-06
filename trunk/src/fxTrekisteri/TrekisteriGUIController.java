package fxTrekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import trekisteri.Rekisteri;
import trekisteri.SailoException;
import trekisteri.Tyontekija;


/**
 * K�sittelee k�ytt�liittym�n tapahtumat.
 * @author Marko Moilanen
 * @version 13.2.2018
 */
public class TrekisteriGUIController implements Initializable {
    
    @FXML private ListChooser<Tyontekija> chooserTyontekijat;
    @FXML private ScrollPane panelTyontekija; 
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }
    
    
    /**
     * K�sittelee tallennusk�skyn.
     */
    @FXML private void handleTallenna() {
        this.tallenna();
    }

    
    /**
     * K�sittelee Avaa-k�skyn.
     */
    @FXML private void handleAvaa() {
        this.avaa();
    }
    
    
    /**
     * K�sittelee lopetusk�skyn.
     */
    @FXML private void handleLopeta() {
        this.tallenna();
        Platform.exit();
    }

    
    /**
     * K�sittelee uuden ty�ntekij�n lis��misen.
     */
    @FXML private void handleLisaaTyontekija() {
        this.lisaaTyontekija();
    }


    /**
     * K�sittelee ty�ntekij�n poistamisen.
     */
    @FXML private void handlePoistaTyontekija() {
        this.poistaTyontekija();
    }
    
    
    /**
     * K�sittelee ty�ntekij�n tietojen muokkauksen.
     */
    @FXML private void handleMuokkaaTyontekija() {
        this.muokkaaTyontekija();
    }
    
    
    /**
     * K�sittelee kohteen lis�yksen.
     */
    @FXML private void handleLisaaKohde() {
        this.lisaaKohde();
    }
    
    
    /**
     * K�sittelee kohteen poistamisen.
     */
    @FXML private void handlePoistaKohde() {
        this.poistaKohde();
    }
    
    
    /**
     * K�sittelee Apua-k�skyn.
     */
    @FXML private void handleApua() {
        this.naytaHelp();
    }
    
    
    /**
     * K�sittelee Tietoja-k�skyn.
     */
    @FXML private void handleTietoja() {
        this.naytaTiedot();
    }
    
    
    // Suoraan k�ytt�liittym��n liittyv�t asiat ovat t�m�n yl�puolella.
    //********************************************************************************
    
    private Rekisteri rekisteri;
    private TextArea areaTyontekija = new TextArea();
    
    
    /**
     * N�ytt�� valittuna olevan ty�ntekij�n tiedot.
     */
    private void naytaTyontekija() {
        Tyontekija tyontekijaValittuna = chooserTyontekijat.getSelectedObject();
        if (tyontekijaValittuna == null) return;
        areaTyontekija.setText("");
        
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTyontekija)) {
            tyontekijaValittuna.tulosta(os);
        }        
    }
    
    
    /**
     * Alustaa j�senlistan kuuntelijan.
     */
    private void alusta() {
        this.panelTyontekija.setContent(this.areaTyontekija);
        areaTyontekija.setFont(new Font("Courier New", 12));
        this.chooserTyontekijat.clear();
        this.chooserTyontekijat.addSelectionListener(e -> this.naytaTyontekija());
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
     * Hakee j�senen tiedot listaan.
     * @param sen ty�ntekij�n numero, joka haun j�lkeen aktivoidaan
     */
    private void hae(int tnro) {
        this.chooserTyontekijat.clear();
        int aktivoitava = 0;
        for (int i = 0; i < this.rekisteri.getLkm(); i++) {
            Tyontekija tyontekija = rekisteri.anna(i);
            if (tyontekija.getId() == tnro) aktivoitava = i;
            this.chooserTyontekijat.add(tyontekija.getNimi(), tyontekija);
        }
        
        this.chooserTyontekijat.getSelectionModel().select(aktivoitava);
    }
    
    
    /**
     * Lis�� uuden ty�ntekij�n.
     */
    private void lisaaTyontekija() {  // Oletusk�sittelij�.
        Tyontekija tyontekija = new Tyontekija();
        tyontekija.rekisteroi();
        tyontekija.taytaTiedot();
        try {
            rekisteri.lisaa(tyontekija);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ei onnistu: " + e.getMessage());
            return;
        }
        this.hae(tyontekija.getId());        
        //ModalController.showModal(TrekisteriGUIController.class.getResource("UusiView.fxml"), "Lis�� ty�ntekij�", 
                //null, "");
    }

    
    /**
     * Poistaa tyontekij�n.
     */
    private void poistaTyontekija() {
        Dialogs.showMessageDialog("Ei toimi viel�.");
    }
    
    
    /**
     * Muuttaa ty�ntekij�n tietoja.
     */
    private void muokkaaTyontekija() { // Oletusk�sittelij�.
        ModalController.showModal(TrekisteriGUIController.class.getResource("MuokkaaView.fxml"), 
                "Muokkaa ty�ntekij��", null, "");
    }
        
    
    /**
     * Lis�� kohteen ty�ntekij�n kohdeluetteloon.
     */
    private void lisaaKohde() { // Oletusk�sittelij�.
        ModalController.showModal(TrekisteriGUIController.class.getResource("UusiKohdeView.fxml"), "Lis�� kohde",
                null, "");
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
        this.tallenna();
        return true;
    }
    
    
    /**
     * Asettaa k�ytt�liittym�ss� k�ytett�v�n rekisterin.
     * @param rekisteri lis�tt�v� rekisteri.
     */
    public void setRekisteri(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;        
    }
}