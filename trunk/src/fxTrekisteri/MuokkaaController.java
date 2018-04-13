package fxTrekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trekisteri.Tyontekija;


/**
 * Kontrolleri työntekijän tietojen muokkaamista varten.
 * @author Marko Moilanen
 * @version 13.4.2018
 */
public class MuokkaaController implements ModalControllerInterface<Tyontekija>, Initializable {

    @FXML private TextField editNimi;
    @FXML private TextField editHlonumero;
    @FXML private TextField editAloitusvuosi;
    @FXML private TextField editKoulutus;
    @FXML private TextField editLisatietoja;
    @FXML private Label labelVirhe;
    
    private Tyontekija tyontekijaValittuna;
    private TextField[] tiedot;

    
    
    /**
     * Käsittelee OK-napin painalluksen.
     */
    @FXML private void handleOK() {
        ModalController.closeStage(this.editNimi);          
    }
    
    
    /**
     * Käsittelee Peruuta-napin painalluksen.
     */
    @FXML private void handlePeruuta() {
        ModalController.closeStage(this.editNimi);  // Etsi editNimi-attribuutista, mikä
    }                                               // dialogi suljetaan.
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        this.alusta();
    }

    
    @Override
    public Tyontekija getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    
    @Override
    public void setDefault(Tyontekija oletus) {
        this.tyontekijaValittuna = oletus;
        naytaTyontekija(this.tyontekijaValittuna, this.tiedot);
    }
    
    
    /**
     * Tekee tarvittavat alustukset. TODO: tarkenna.
     */
    private void alusta() {
        this.tiedot = new TextField[] { this.editNimi, this.editHlonumero, this.editAloitusvuosi,
                                       this.editKoulutus, this.editLisatietoja };
        editNimi.setOnKeyReleased(e -> this.nimiMuuttui());
        editHlonumero.setOnKeyReleased(e -> this.hlonumeroMuuttui());
        editAloitusvuosi.setOnKeyReleased(e -> this.aloitusvuosiMuuttui());
        editKoulutus.setOnKeyReleased(e -> this.koulutusMuuttui());
        editLisatietoja.setOnKeyReleased(e -> this.lisatietojaMuuttui());
    }                                                
    
    
    /**
     * Näyttää virheilmituksen.
     * @param virhe virheilmoitus
     */
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.equals("")) {
            this.labelVirhe.setText("");
            this.labelVirhe.getStyleClass().removeAll("virhe");
        }
        
        this.labelVirhe.setText(virhe);
        this.labelVirhe.getStyleClass().add("virhe");
    }
    
    
    /**
     * Asettaa nimi-kenttään uuden tekstin.
     */
    private void nimiMuuttui() {
        String uusi = this.editNimi.getText();
        String virhe = this.tyontekijaValittuna.setNimi(uusi);
        if (virhe == null) return;
    }
    
    
    /**
     * Asettaa henkilönumero-kenttään  uuden numeron.
     */
    private void hlonumeroMuuttui() {
        String uusi = this.editHlonumero.getText();
        String virhe = this.tyontekijaValittuna.setHlonumero(uusi);

        if (virhe == null) {
            Dialogs.setToolTipText(this.editHlonumero, "");
            this.editHlonumero.getStyleClass().removeAll("virhe");
            this.naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(this.editHlonumero, virhe);
            this.editHlonumero.getStyleClass().add("virhe");
            this.naytaVirhe(virhe);   
        }               
    }
    
    
    /**
     * Asettaa aloitusvuosi-kenttään uuden numeron.
     */
    private void aloitusvuosiMuuttui() {
        String uusi = this.editAloitusvuosi.getText();
        this.tyontekijaValittuna.setAloitusvuosi(uusi);
    }
    
    
    /**
     * Asettaa koulutus-kenttään uuden tekstin.
     */
    private void koulutusMuuttui() {
        String uusi = this.editKoulutus.getText();
        this.tyontekijaValittuna.setKoulutus(uusi);
    }
    
    
    private void lisatietojaMuuttui() {
        String uusi = this.editLisatietoja.getText();
        this.tyontekijaValittuna.setLisatietoja(uusi);
    }
    
    
    /**
     * Näyttää työntekijän tiedot. 
     * @param tyontekija työntekijä, jonka tiedot näytetään
     * @param tiedot taulukko, johon näytettävät tiedot tallennetaan
     */
    public static void naytaTyontekija(Tyontekija tyontekija, TextField[] tiedot) {
        if (tyontekija == null) return;
       
        // Haetaan valittuna olevalta työntekijältä tarvittavat tiedot ja näytetään ne.
        tiedot[0].setText(tyontekija.getNimi());
        tiedot[1].setText("" + tyontekija.getHlonumero());
        tiedot[2].setText("" + tyontekija.getAloitusvuosi());
        tiedot[3].setText(tyontekija.getKoulutus());
        tiedot[4].setText(tyontekija.getLisatietoja());
    }

    
    /**
     * Luo dialogin ja palauttaa työntekijän muokattuna tai null-viitteen.
     * @param modalityStage Stage, jolle ollaan modaalisia (jos null, niin sovellukselle)
     * @param oletus oletustyöntekijä 
     * @return työntekijä muokattuna tai null (jos painetaan Peruuta)
     */
    public static Tyontekija kysyTyontekija(Stage modalityStage, Tyontekija oletus) {
        return ModalController.<Tyontekija, MuokkaaController>showModal(MuokkaaController.class.getResource("MuokkaaView.fxml"),
                "Työntekijärekisteri", modalityStage, oletus, null);
    }
}
