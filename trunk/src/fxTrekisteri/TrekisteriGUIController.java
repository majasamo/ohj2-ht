package fxTrekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.List;
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
import trekisteri.Kohde;
import trekisteri.Rekisteri;
import trekisteri.SailoException;
import trekisteri.Tyontekija;


/**
 * K�sittelee k�ytt�liittym�n tapahtumat.
 * @author Marko Moilanen
 * @version 4.4.2018
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
    
    private String nimi = "putsaus";
    private Rekisteri rekisteri;
    private Tyontekija tyontekijaValittuna;
    private TextArea areaTyontekija = new TextArea();
    
    
    /**
     * N�ytt�� valittuna olevan ty�ntekij�n tiedot.
     */
    private void naytaTyontekija() {
        this.tyontekijaValittuna = chooserTyontekijat.getSelectedObject();
        if (this.tyontekijaValittuna == null) return;
        this.areaTyontekija.setText("");
        
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(this.areaTyontekija)) {
            this.tyontekijaValittuna.tulosta(os);
            
            // N�ytet��n t�ss� v�liaikaisesti my�s kohteet:
            os.println("----------");
            List<Kohde> kohteet = this.rekisteri.annaKohteet(this.tyontekijaValittuna.getId());
            for (Kohde kohde : kohteet) {
                kohde.tulosta(os);
            }
        }        
    }
    
    
    /**
     * Alustaa j�senlistan kuuntelijan.
     */
    private void alusta() {
        this.panelTyontekija.setContent(this.areaTyontekija);
        this.areaTyontekija.setFont(new Font("Courier New", 12));
        this.chooserTyontekijat.clear();
        this.chooserTyontekijat.addSelectionListener(e -> this.naytaTyontekija());
    }
    
    
    /**
     * Tietojen tallennus.
     */
    private void tallenna() {
        try {
            this.rekisteri.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallentamisessa ongelma: " + e.getMessage());
        }
    }
    
    
    /**
     * Kysyy tiedoston nimen ja lukee kyseisen tiedoston.
     * @return true, jos onnistui, muuten false
     */
    private boolean avaa() { 
        String uusiNimi = AvaaController.kysyNimi(null, this.nimi);
        if (uusiNimi == null) return false;
        this.lueHakemisto(uusiNimi);
        return true;
    }
    
    
    /**
     * Lukee rekisterin tiedot annetusta hakemistosta.
     * @param hakemistonNimi hakemiston nimi
     * @return mahdollinen virheilmoitus, null, jos ei virhett�
     */
    private String lueHakemisto(String hakemistonNimi) {
        this.nimi = hakemistonNimi;
        
        try {
            this.rekisteri.lueTiedostoista(hakemistonNimi);
            this.hae(0);
            return null;
        } catch (SailoException e) {
            this.hae(0);
            String virhe = e.getMessage();
            if (virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    
    /**
     * Hakee ty�ntekij�n tiedot listaan.
     * @param sen ty�ntekij�n numero, joka haun j�lkeen aktivoidaan
     */
    private void hae(int tnro) {
        this.chooserTyontekijat.clear();
        int aktivoitava = 0;
        for (int i = 0; i < this.rekisteri.getTyolaisetLkm(); i++) {
            Tyontekija tyontekija = this.rekisteri.anna(i);
            if (tyontekija.getId() == tnro) aktivoitava = i;
            this.chooserTyontekijat.add(tyontekija.getNimi(), tyontekija);
        }
        
        this.chooserTyontekijat.setSelectedIndex(aktivoitava);
    }
    
    
    /**
     * Lis�� uuden ty�ntekij�n.
     */
    private void lisaaTyontekija() {  // Oletusk�sittelij�.
        Tyontekija tyontekija = new Tyontekija();
        tyontekija.rekisteroi();
        tyontekija.taytaTiedot();
        try {
            this.rekisteri.lisaa(tyontekija);
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
        //ModalController.showModal(TrekisteriGUIController.class.getResource("UusiKohdeView.fxml"), "Lis�� kohde",
                //null, "");
        // V�liaikainen ratkaisu: tehd��n uusi kohde, t�ytet��n se h�p�h�p�tiedoilla ja lis�t��n luetteloon.
        Kohde lisattavaKohde = new Kohde();
        lisattavaKohde.rekisteroi();
        lisattavaKohde.taytaTiedot();
        this.rekisteri.lisaa(lisattavaKohde);  // T�m� on siis hyvin tilap�inen ratkaisu! (T�ss�h�n kohde ja
                                               // kohteentekij� lis�t��n manuaalisesti erikseen.)
        
        this.rekisteri.lisaaKohteenTekija(this.tyontekijaValittuna.getId(), lisattavaKohde.getId());
        this.hae(this.tyontekijaValittuna.getId());
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