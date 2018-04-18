package fxTrekisteri;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Kontrolleri uuden kohteen lis��mist� varten.
 * @author Marko Moilanen
 * @version 18.4.2018
 */
public class UusiKohdeController implements ModalControllerInterface<String> {

    @FXML private TextField textNimi;
    private String nimi;
    
    
    @Override
    public String getResult() {
        return this.nimi;
    }

    
    @Override
    public void handleShown() {
        // T�h�n ei tarvitse ainakaan viel� laittaa mit��n.
    }

    
    @Override
    public void setDefault(String oletus) {
        this.textNimi.setText(oletus);       
    }
    
    
    /**
     * K�sittelee Tallenna-napin painalluksen.
     */
    @FXML public void handleTallenna() {
        this.nimi = this.textNimi.getText();
        ModalController.closeStage(this.textNimi);
    }
    
    
    /**
     * K�sittelee Peruuta-napin painalluksen.
     */
    @FXML public void handlePeruuta() {
        this.nimi = null;
        ModalController.closeStage(this.textNimi);
    }
    
    
    /**
     * Luo dialogin, joka kysyy nime�, ja palauttaa siihen kirjoitetun nimen
     * tai null-viitteen.
     * @param modalityStage Stage, jolle ollaan modaalisia (jos null, niin sovellukselle)
     * @param oletus oletusnimi
     * @return kirjoitettu nimi tai null (jos painetaan Peruuta-nappia)
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(AvaaController.class.getResource("UusiKohdeView.fxml"),
                "Ty�ntekij�rekisteri", modalityStage, oletus);
    }

}
