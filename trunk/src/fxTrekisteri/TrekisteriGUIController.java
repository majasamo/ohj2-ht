package fxTrekisteri;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import trekisteri.Kohde;
import trekisteri.Rekisteri;
import trekisteri.SailoException;
import trekisteri.Tyontekija;


/**
 * Käsittelee käyttöliittymän tapahtumat.
 * @author Marko Moilanen
 * @version 15.4.2018
 */
public class TrekisteriGUIController implements Initializable {
    
    @FXML private ListChooser<Tyontekija> chooserTyontekijat;
    @FXML private ListChooser<Kohde> chooserKohteet;
    @FXML private ScrollPane panelTyontekija; 
    
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
    
    private String nimi = "putsaus";
    private Rekisteri rekisteri;
    private Tyontekija tyontekijaValittuna;
    private Kohde kohdeValittuna;
    private TextField[] tiedot;
    
    
    /**
     * Näyttää valittuna olevan työntekijän tiedot.
     */
    private void naytaTyontekija() {
        this.tyontekijaValittuna = chooserTyontekijat.getSelectedObject();
        if (this.tyontekijaValittuna == null) return;
        
        MuokkaaController.naytaTyontekija(this.tyontekijaValittuna, this.tiedot);
        this.naytaKohteet();
    }
    
    
    /**
     * Näyttää valittuna olevan työntekijän kohteet.
     */
    private void naytaKohteet() {
        if (this.tyontekijaValittuna == null) return;
        
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
     * @return mahdollinen virheilmoitus. Jos virhettä ei ole, palautetaan null.
     */
    public String lueHakemisto(String hakemistonNimi) {
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
     * Hakee työntekijät listaan.
     * @param tyontekijaId sen työntekijän id-numero, joka haun jälkeen aktivoidaan
     */
    private void haeTyontekijat(int tyontekijaId) {
        this.chooserTyontekijat.clear();
        int aktivoitava = 0;
        for (int i = 0; i < this.rekisteri.getTyolaisetLkm(); i++) {
            Tyontekija tyontekija = this.rekisteri.anna(i);
            if (tyontekija.getId() == tyontekijaId) aktivoitava = i;
            this.chooserTyontekijat.add(tyontekija.getNimi(), tyontekija);
        }
        
        this.chooserTyontekijat.setSelectedIndex(aktivoitava);
    }
    
    
    /**
     * Lisää uuden työntekijän.
     */
    private void lisaaTyontekija() {
        Tyontekija uusi = new Tyontekija();
        uusi = MuokkaaController.kysyTyontekija(null, uusi);
        if (uusi == null) return;
        
        uusi.rekisteroi();
        this.rekisteri.lisaa(uusi);
        this.haeTyontekijat(uusi.getId());
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
    private void muokkaaTyontekija() {        
        try {
            Tyontekija tyontekija;
            tyontekija = MuokkaaController.kysyTyontekija(null, this.tyontekijaValittuna.clone());
            if (tyontekija == null) return;  // Tässä tapauksessa painettiin Peruuta-nappia.
            this.rekisteri.korvaa(tyontekija);
            this.haeTyontekijat(tyontekija.getId());
        } catch (CloneNotSupportedException e) {
            // Tämän ei pitäisi koskaan tapahtua:
            System.out.println("Ongelma kloonin luomisessa: " + e.getMessage());
        }
    }
        
    
    /**
     * Lisää kohteen työntekijän kohdeluetteloon.
     */
    private void lisaaKohde() { // Oletuskäsittelijä.
        //ModalController.showModal(TrekisteriGUIController.class.getResource("UusiKohdeView.fxml"), "Lisää kohde",
                //null, "");
        // Väliaikainen ratkaisu: tehdään uusi kohde, täytetään se höpöhöpötiedoilla ja lisätään luetteloon.
        Kohde lisattavaKohde = new Kohde();
        lisattavaKohde.rekisteroi();
        lisattavaKohde.taytaTiedot();
        this.rekisteri.lisaa(lisattavaKohde);  // Tämä on siis hyvin tilapäinen ratkaisu! (Tässähän kohde ja
                                               // kohteentekijä lisätään manuaalisesti erikseen.)
        
        this.rekisteri.lisaaKohteenTekija(this.tyontekijaValittuna.getId(), lisattavaKohde.getId());
        this.haeTyontekijat(this.tyontekijaValittuna.getId());
    }
    
    
    /**
     * Poistaa kohteen työntekijän kohdeluettelosta.
     */
    private void poistaKohde() {
        this.kohdeValittuna = this.chooserKohteet.getSelectedObject();
        if (this.kohdeValittuna == null) return;
        
        // Selvitetään, mikä työntekijä ja mikä kohde on valittuna, ja poistetaan
        // niitä vastaava yhteys (kohtee tekijä -olio).
        int tyolainenId = this.tyontekijaValittuna.getId();
        int kohdeId = this.kohdeValittuna.getId();
        this.rekisteri.poista(tyolainenId, kohdeId);
        
        // Päivitetään näkymä.
        this.naytaKohteet();
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