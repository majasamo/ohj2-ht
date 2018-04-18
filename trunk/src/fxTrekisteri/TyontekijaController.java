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
 * @version 18.4.2018
 */
public class TyontekijaController implements ModalControllerInterface<Tyontekija>, Initializable {

    @FXML private TextField editNimi;
    @FXML private TextField editHlonumero;
    @FXML private TextField editAloitusvuosi;
    @FXML private TextField editKoulutus;
    @FXML private TextField editLisatietoja;
    @FXML private Label labelVirhe;
    
    private Tyontekija tyontekijaValittuna;
    private TextField[] tiedot;
    
    
    /**
     * Käsittelee Tallenna-napin painalluksen.
     */
    @FXML private void handleTallenna() {
        if (this.tyontekijaValittuna != null && this.tyontekijaValittuna.getNimi().trim().length() == 0) {
            this.naytaVirhe("Nimi ei saa olla tyhjä.");  // TODO: Miten tämä pitäisi tehdä? Tämähän on kopioitu
            return;                                      // Lappalaisen koodista.
        }
        ModalController.closeStage(this.editNimi);          
    }
    
    
    /**
     * Käsittelee Peruuta-napin painalluksen.
     */
    @FXML private void handlePeruuta() {
        this.tyontekijaValittuna = null;
        ModalController.closeStage(this.labelVirhe);  // Etsi labelVirhe-attribuutista, mikä
    }                                                 // dialogi suljetaan.
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        this.alusta();
    }

    
    @Override
    public Tyontekija getResult() {
        return this.tyontekijaValittuna;
    }

    
    @Override
    public void handleShown() {
        // Tähän ei tarvitse ainakaan vielä laittaa mitään.
    }

    
    @Override
    public void setDefault(Tyontekija oletus) {
        this.tyontekijaValittuna = oletus;
        naytaTyontekija(this.tyontekijaValittuna, this.tiedot);
    }
    
    
    // Suoraan käyttöliittymään liittyvät asiat ovat tämän yläpuolella.
    //********************************************************************************    
    
    /**
     * Tekee tarvittavat alustukset. TODO: tarkenna.
     */
    private void alusta() {
        this.tiedot = new TextField[] { this.editNimi, this.editHlonumero, this.editAloitusvuosi,
                                        this.editKoulutus, this.editLisatietoja };
        int i = 0;
        for (TextField tieto : this.tiedot) {
            int k = ++i;
            tieto.setOnKeyReleased(e -> kasitteleMuutos(k, (TextField) e.getSource()));            
        }
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
     * Käsittelee työntekijän tietoihin tulleen muutoksen.
     * @param k sen kentän numero, johon muutos tuli
     * @param tieto muuttunut kenttä
     */
    private void kasitteleMuutos(int k, TextField tieto) {
        if (this.tyontekijaValittuna == null) return;
        String teksti = tieto.getText();
        String virhe;;
        
        switch (k) {
            case 1 : virhe = this.tyontekijaValittuna.setNimi(teksti); break;
            case 2 : virhe = this.tyontekijaValittuna.setHlonumero(teksti); break;
            case 3 : virhe = this.tyontekijaValittuna.setAloitusvuosi(teksti); break;
            case 4 : virhe = this.tyontekijaValittuna.setKoulutus(teksti); break;
            case 5 : virhe = this.tyontekijaValittuna.setLisatietoja(teksti); break;
            default : virhe = null; break;
        }
        
        if (virhe == null) {  // Jos virhettä ei ole, niin 
            Dialogs.setToolTipText(tieto, "");
            tieto.getStyleClass().removeAll("virhe");
            this.naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(tieto, virhe);
            tieto.getStyleClass().add("virhe");
            this.naytaVirhe(virhe);
        }
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
        return ModalController.<Tyontekija, TyontekijaController>showModal(TyontekijaController.class.getResource("TyontekijaView.fxml"),
                "Työntekijärekisteri", modalityStage, oletus, null);
    }
}
