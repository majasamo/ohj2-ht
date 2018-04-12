package fxTrekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trekisteri.Tyontekija;


/**
 * Kontrolleri ty�ntekij�n tietojen muokkaamista varten.
 * @author Marko Moilanen
 * @version 12.4.2018
 */
public class MuokkaaController implements ModalControllerInterface<Tyontekija>, Initializable {

    @FXML private TextField editNimi;
    @FXML private TextField editHlonumero;
    @FXML private TextField editAloitusvuosi;
    @FXML private TextField editKoulutus;
    @FXML private TextField editLisatietoja;
    private Tyontekija tyontekijaValittuna;
    
    
    /**
     * K�sittelee OK-napin painalluksen.
     */
    @FXML private void handleOK() {
        ModalController.closeStage(this.editNimi);          
    }
    
    
    /**
     * K�sittelee Peruuta-napin painalluksen.
     */
    @FXML private void handlePeruuta() {
        ModalController.closeStage(this.editNimi);  // Etsi editNimi-attribuutista, mik�
    }                                               // dialogi suljetaan.
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //
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
        this.naytaTyontekija(this.tyontekijaValittuna);
    }
    
    
    /**
     * N�ytt�� valittuna olevan ty�ntekij�n tiedot. 
     * @param tyontekija ty�ntekij�, jonka tiedot n�ytet��n
     */
    public void naytaTyontekija(Tyontekija tyontekija) {
        if (tyontekija == null) return;
       
        // Haetaan valittuna olevalta ty�ntekij�lt� tarvittavat tiedot ja n�ytet��n ne.
        this.editNimi.setText(tyontekija.getNimi());
        this.editHlonumero.setText("" + tyontekija.getHlonumero());
        this.editAloitusvuosi.setText("" + tyontekija.getAloitusvuosi());
        this.editKoulutus.setText(tyontekija.getKoulutus());
        this.editLisatietoja.setText(tyontekija.getLisatietoja());
    }

    
    /**
     * Luo dialogin ja palauttaa ty�ntekij�n muokattuna tai null-viitteen.
     * @param modalityStage Stage, jolle ollaan modaalisia (jos null, niin sovellukselle)
     * @param oletus oletusty�ntekij� 
     * @return ty�ntekij� muokattuna tai null (jos painetaan Peruuta)
     */
    public static Tyontekija kysyTyontekija(Stage modalityStage, Tyontekija oletus) {
        return ModalController.<Tyontekija, MuokkaaController>showModal(MuokkaaController.class.getResource("MuokkaaView.fxml"),
                "Ty�ntekij�rekisteri", modalityStage, oletus, null);
    }
}
