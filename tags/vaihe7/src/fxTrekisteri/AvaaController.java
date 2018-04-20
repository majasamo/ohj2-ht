package fxTrekisteri;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Kontrolleri työnantajan nimen kysymiseksi.
 * @author Marko Moilanen
 * @version 3.4.2018
 */
public class AvaaController implements ModalControllerInterface<String> {

    @FXML private TextField textVastaus;
    private String vastaus;
    
    
    /**
     * Käsittelee OK-napin painamisen.
     */
    @FXML public void handleOK() {
        this.vastaus = this.textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }
    
    
    /**
     * Käsittelee Peruuta-napin painamisen.
     */
    @FXML public void handlePeruuta() {
        ModalController.closeStage(textVastaus);
    }
    
    
    @Override
    public void handleShown() {
        this.textVastaus.requestFocus();        
    }
    
        
    @Override
    public String getResult() {
        return this.vastaus;
    }

    
    @Override
    public void setDefault(String oletus) {
        this.textVastaus.setText(oletus);        
    }
    
    
    /**
     * Luo dialogin, joka kysyy nimeä, ja palauttaa siihen kirjoitetun nimen
     * tai null-viitteen.
     * @param modalityStage Stage, jolle ollaan modaalisia (jos null, niin sovellukselle)
     * @param oletus oletusnimi
     * @return kirjoitettu nimi tai null (jos painetaan Peruuta-nappia)
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(AvaaController.class.getResource("AvaaView.fxml"),
                "Työntekijärekisteri", modalityStage, oletus);
    }
}