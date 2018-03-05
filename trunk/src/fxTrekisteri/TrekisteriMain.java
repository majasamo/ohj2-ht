package fxTrekisteri;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Pääohjelma, joka käynnistää työntekijärekisterin.
 * @author Marko Moilanen
 * @version 13.1.2018
 */
public class TrekisteriMain extends Application {
    
	@Override
	public void start(Stage primaryStage) {
		try {
		FXMLLoader ldr = new FXMLLoader(getClass().getResource("TrekisteriGUIView.fxml"));
        final Pane root = (Pane)ldr.load();
        final TrekisteriGUIController trekisteriCtrl = (TrekisteriGUIController)ldr.getController();
        
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("trekisteri.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Työntekijärekisteri");
        
        // Platform.setImplicitExit(false); // jos tämän laittaa, pitää itse sulkea
       
        primaryStage.setOnCloseRequest((event) -> {
            // Kutsutaan voikoSulkea-metodia
            if ( !trekisteriCtrl.voikoSulkea() ) event.consume(); // korjaa nimi
        });
        
        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Käynnistää käyttöliittymän.
	 * @param args komentoriviparametrit
	 * 
	 */
	public static void main(String[] args) {
	    launch(args);
	}
}
