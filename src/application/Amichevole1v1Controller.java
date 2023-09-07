package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
public class Amichevole1v1Controller {

		@FXML
	    private Button bottoneCrea;
		@FXML
	    private Button logIn;
	    @FXML
	    private Label codice;
	    @FXML
	    private TextField giocatore1;
	    @FXML
	    private TextField giocatore2;
	    
	    private Stage stage;
	    private Scene scene;
	    private Parent root;
	    private String usernameGiocatore1;
		private String usernameGiocatore2;
		private String codicePartita = "";
		
   public void creaAmichevole(ActionEvent event) throws IOException {

    	if((giocatore1.getText().isBlank()||giocatore1.getText().equals("BOT"))|| giocatore2.getText().isBlank()) {
    			Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Username mancante");
				alert.setHeaderText("Inserisci i nomi di entrambi i giocatori. Se vuoi giocare contro un bot, inserisci 'BOT' come giocatore2. Il giocatore 1 non può essere un bot");
				alert.setContentText("A fine partita i nomi verranno aggiunti alla classifica");
				if(alert.showAndWait().get()==ButtonType.OK){
					alert.close();
				} else {

					alert.close();
				}
    		} else {
    			usernameGiocatore1 = giocatore1.getText();
    			usernameGiocatore2 = giocatore2.getText();
    			Random random = new Random();
        		for(int i=0; i<6; i++)
        			 codicePartita += random.nextInt(10)+"";
        		codice.setText(codicePartita);
    			String folder = "src/partite/"+codicePartita;
				File f = new File(folder);
				f.mkdirs();
    			File file = new File("src/partite/"+codicePartita+"/"+codicePartita+"_new.txt");
    			FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(usernameGiocatore1+"\n");
				bw.write(usernameGiocatore2+"\n");
				bw.close();
				fw.close();
    		
        		bottoneCrea.setDisable(true);
        		
    			
    		}
    		
    	}
    
 
    

    
    public void toLogIn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaLogIn.fxml"));	
		root = loader.load();
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}
    
}




