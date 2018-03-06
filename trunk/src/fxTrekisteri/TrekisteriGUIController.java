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
 * Käsittelee käyttöliittymän tapahtumat.
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
     * Käsittelee tallennuskäskyn.
     */
    @FXML private void handleTallenna() {
        this.tallenna();
    }

    
    /**
     * Käsittelee Avaa-käskyn.
     */
    @FXML private void handleAvaa() {
        this.avaa();
    }
    
    
    /**
     * Käsittelee lopetuskäskyn.
     */
    @FXML private void handleLopeta() {
        this.tallenna();
        Platform.exit();
    }

    
    /**
     * Käsittelee uuden työntekijän lisäämisen.
     */
    @FXML private void handleLisaaTyontekija() {
        this.lisaaTyontekija();
    }


    /**
     * Käsittelee työntekijän poistamisen.
     */
    @FXML private void handlePoistaTyontekija() {
        this.poistaTyontekija();
    }
    
    
    /**
     * Käsittelee työntekijän tietojen muokkauksen.
     */
    @FXML private void handleMuokkaaTyontekija() {
        this.muokkaaTyontekija();
    }
    
    
    /**
     * Käsittelee kohteen lisäyksen.
     */
    @FXML private void handleLisaaKohde() {
        this.lisaaKohde();
    }
    
    
    /**
     * Käsittelee kohteen poistamisen.
     */
    @FXML private void handlePoistaKohde() {
        this.poistaKohde();
    }
    
    
    /**
     * Käsittelee Apua-käskyn.
     */
    @FXML private void handleApua() {
        this.naytaHelp();
    }
    
    
    /**
     * Käsittelee Tietoja-käskyn.
     */
    @FXML private void handleTietoja() {
        this.naytaTiedot();
    }
    
    
    // Suoraan käyttöliittymään liittyvät asiat ovat tämän yläpuolella.
    //********************************************************************************
    
    private Rekisteri rekisteri;
    private TextArea areaTyontekija = new TextArea();
    
    
    /**
     * Näyttää valittuna olevan työntekijän tiedot.
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
     * Alustaa jäsenlistan kuuntelijan.
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
        Dialogs.showMessageDialog("Ei osata vielä tallentaa.");
    }
    
    
    /**
     * Kysyy tiedoston nimen ja lukee kyseisen tiedoston.
     */
    private void avaa() {  // Oletuskäsittelijä.
        ModalController.showModal(TrekisteriGUIController.class.getResource("AvaaView.fxml"), "Avaa", null, "");
    }
    
    
    /**
     * Hakee jäsenen tiedot listaan.
     * @param sen työntekijän numero, joka haun jälkeen aktivoidaan
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
     * Lisää uuden työntekijän.
     */
    private void lisaaTyontekija() {  // Oletuskäsittelijä.
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
        //ModalController.showModal(TrekisteriGUIController.class.getResource("UusiView.fxml"), "Lisää työntekijä", 
                //null, "");
    }

    
    /**
     * Poistaa tyontekijän.
     */
    private void poistaTyontekija() {
        Dialogs.showMessageDialog("Ei toimi vielä.");
    }
    
    
    /**
     * Muuttaa työntekijän tietoja.
     */
    private void muokkaaTyontekija() { // Oletuskäsittelijä.
        ModalController.showModal(TrekisteriGUIController.class.getResource("MuokkaaView.fxml"), 
                "Muokkaa työntekijää", null, "");
    }
        
    
    /**
     * Lisää kohteen työntekijän kohdeluetteloon.
     */
    private void lisaaKohde() { // Oletuskäsittelijä.
        ModalController.showModal(TrekisteriGUIController.class.getResource("UusiKohdeView.fxml"), "Lisää kohde",
                null, "");
    }
    
    
    /**
     * Poistaa kohteen työntekijän kohdeluettelosta.
     */
    private void poistaKohde() {
        Dialogs.showMessageDialog("Ei toimi vielä.");
    }
    
    
    /**
     * Näyttää käyttöohjeen.
     */
    private void naytaHelp() {
        Dialogs.showMessageDialog("Ei toimi vielä.");
    }
    
    
    /**
     * Näyttää ohjelman tiedot.
     */
    private void naytaTiedot() {  // Oletuskäsittelijä.
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
     * Asettaa käyttöliittymässä käytettävän rekisterin.
     * @param rekisteri lisättävä rekisteri.
     */
    public void setRekisteri(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;        
    }
}