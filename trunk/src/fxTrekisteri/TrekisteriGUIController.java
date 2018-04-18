package fxTrekisteri;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import trekisteri.Kohde;
import trekisteri.Rekisteri;
import trekisteri.SailoException;
import trekisteri.Tyontekija;


/**
 * K�sittelee k�ytt�liittym�n tapahtumat.
 * @author Marko Moilanen
 * @version 18.4.2018
 */
public class TrekisteriGUIController implements Initializable {
    
    @FXML private ListChooser<Tyontekija> chooserTyontekijat;
    @FXML private ListChooser<Kohde> chooserKohteet;
    @FXML private ComboBoxChooser<String> chooserHakuehto;
    @FXML private TextField textFieldHakusana;
    @FXML private ScrollPane panelTyontekija; 
    @FXML private GridPane gridTyontekija; 
    
    @FXML private TextField editNimi;
    @FXML private TextField editHlonumero;
    @FXML private TextField editAloitusvuosi;
    @FXML private TextField editKoulutus;
    @FXML private TextField editLisatietoja;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.alusta();        
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
        this.haeTyontekijat(0);
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
    
    
    /**
     * K�sittelee hakuehdon valinnan.
     */
    @FXML private void handleHakuehto() {
        this.haeTyontekijat(0);
        this.naytaTyontekija();
    }
    
    
    /**
     * K�sittelee hakusanan antamisen.
     */
    @FXML private void handleHakusana() {
        this.haeTyontekijat(0);
        this.naytaTyontekija();
    }
    
    
    // Suoraan k�ytt�liittym��n liittyv�t asiat ovat t�m�n yl�puolella.
    //********************************************************************************
    
    private String nimi = "putsaus";
    private Rekisteri rekisteri;
    private Tyontekija tyontekijaValittuna;
    private Kohde kohdeValittuna;
    private TextField[] tiedot;
    
    
    /**
     * N�ytt�� valittuna olevan ty�ntekij�n tiedot.
     */
    private void naytaTyontekija() {
        this.tyontekijaValittuna = chooserTyontekijat.getSelectedObject();        
        TyontekijaController.naytaTyontekija(this.tyontekijaValittuna, this.tiedot);  // Jos tyontekijaValittuna == null,
                                                                                   // kutsuttu metodi ei tee mit��n.
        this.naytaKohteet();
        
        // Jos ket��n ei ole valittuna, ei n�ytet� mit��n. 
        this.gridTyontekija.setVisible(this.tyontekijaValittuna != null);
    }
    
    
    /**
     * N�ytt�� valittuna olevan ty�ntekij�n kohteet.
     */
    private void naytaKohteet() {
        // Jos valittua ty�ntekij�� ei ole, ei n�ytet� mit��n eik� jatketa ohjelman
        // suoritusta.
        if (this.tyontekijaValittuna == null) {
            this.chooserKohteet.setVisible(false);
            return;
        }
        
        this.chooserKohteet.setVisible(true);
        
        // Jos tyontekijaValittuna == null, sen id:n hakeminen aiheuttaa NullPointerExceptionin.
        // Siksi edell� oleva if-lause tarvitaan.
        List<Kohde> kohteet = this.rekisteri.annaKohteet(this.tyontekijaValittuna.getId());        
        this.chooserKohteet.clear();
        for (Kohde kohde : kohteet) {
            this.chooserKohteet.add(kohde.getNimi(), kohde);
        }        
    }
    
    
    /**
     * Tekee tarvittavat alustukset. TODO: tarkenna.
     */
    private void alusta() {
        this.chooserTyontekijat.clear();
        this.chooserTyontekijat.addSelectionListener(e -> this.naytaTyontekija());
        
        this.tiedot = new TextField[] { this.editNimi, this.editHlonumero, this.editAloitusvuosi,
                                       this.editKoulutus, this.editLisatietoja };        
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
    public boolean avaa() {
        String uusiNimi = AvaaController.kysyNimi(null, this.nimi);
        if (uusiNimi == null) return false;
        this.lueHakemisto(uusiNimi);
        return true;
    }
    
    
    /**
     * Lukee rekisterin tiedot annetusta hakemistosta.
     * @param hakemistonNimi hakemiston nimi
     * @return mahdollinen virheilmoitus. Jos virhett� ei ole, palautetaan null.
     */
    private String lueHakemisto(String hakemistonNimi) {
        this.nimi = hakemistonNimi;
        
        try {
            this.rekisteri.lueTiedostoista(hakemistonNimi);
            this.haeTyontekijat(0);
            return null;
        } catch (SailoException e) {
            this.haeTyontekijat(0);
            String virhe = e.getMessage();
            if (virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    
    /**
     * Hakee ty�ntekij�t listaan.
     * @param tyontekijaId sen ty�ntekij�n id-numero, joka haun j�lkeen aktivoidaan
     */
    private void haeTyontekijat(int tyontekijaId) {
        this.chooserTyontekijat.clear();
        int aktivoitava = 0;
        
        String hakuehto = this.chooserHakuehto.getSelectedText();
        String hakusana = this.textFieldHakusana.getText();        
        List<Tyontekija> tyontekijat = this.rekisteri.hae(hakuehto, hakusana);
        
        for (int i = 0; i < tyontekijat.size(); i++) {
            Tyontekija tyontekija = tyontekijat.get(i);
            if (tyontekija.getId() == tyontekijaId) aktivoitava = i;
            this.chooserTyontekijat.add(tyontekija.getNimi(), tyontekija);
        }
        
        this.chooserTyontekijat.setSelectedIndex(aktivoitava);
    }
    
    
    /**
     * Lis�� uuden ty�ntekij�n.
     */
    private void lisaaTyontekija() {
        Tyontekija uusi = new Tyontekija();
        uusi = TyontekijaController.kysyTyontekija(null, uusi);
        if (uusi == null) return;
        
        uusi.rekisteroi();
        this.rekisteri.lisaa(uusi);
        this.haeTyontekijat(uusi.getId());
    }

    
    /**
     * Poistaa tyontekij�n.
     */
    private void poistaTyontekija() {
        this.tyontekijaValittuna = this.chooserTyontekijat.getSelectedObject();
        if (this.tyontekijaValittuna == null) return;
        
        this.rekisteri.poista(this.tyontekijaValittuna.getId());
    }
    
    
    /**
     * Muuttaa ty�ntekij�n tietoja.
     */
    private void muokkaaTyontekija() {        
        try {
            Tyontekija tyontekija;
            tyontekija = TyontekijaController.kysyTyontekija(null, this.tyontekijaValittuna.clone());
            if (tyontekija == null) return;  // T�ss� tapauksessa painettiin Peruuta-nappia.
            this.rekisteri.korvaa(tyontekija);
            this.haeTyontekijat(tyontekija.getId());
        } catch (CloneNotSupportedException e) {
            // T�m�n ei pit�isi koskaan tapahtua:
            System.out.println("Ongelma kloonin luomisessa: " + e.getMessage());
        }
    }
        
    
    /**
     * Lis�� kohteen ty�ntekij�n kohdeluetteloon.
     */
    private void lisaaKohde() { 
        this.tyontekijaValittuna = this.chooserTyontekijat.getSelectedObject();
        if (this.tyontekijaValittuna == null) return;
        
        String kohdeNimi = UusiKohdeController.kysyNimi(null, "");
        this.rekisteri.lisaa(this.tyontekijaValittuna.getId(), kohdeNimi);
        this.haeTyontekijat(this.tyontekijaValittuna.getId());
        this.naytaKohteet();
    }
    
    
    /**
     * Poistaa kohteen ty�ntekij�n kohdeluettelosta.
     */
    private void poistaKohde() {
        this.tyontekijaValittuna = this.chooserTyontekijat.getSelectedObject();
        this.kohdeValittuna = this.chooserKohteet.getSelectedObject();
        if (this.tyontekijaValittuna == null || this.kohdeValittuna == null) return;
        
        // Selvitet��n, mik� ty�ntekij� ja mik� kohde on valittuna, ja poistetaan
        // niit� vastaava yhteys (kohteen tekij� -olio).
        int tyolainenId = this.tyontekijaValittuna.getId();
        int kohdeId = this.kohdeValittuna.getId();
        this.rekisteri.poista(tyolainenId, kohdeId);
        
        // P�ivitet��n n�kym�.
        this.naytaKohteet();
    }
    
    
    /**
     * N�ytt�� k�ytt�ohjeen.
     */
    private void naytaHelp() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2018k/ht/majasamo");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }        
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