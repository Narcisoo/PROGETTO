
package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;



public class TorneoAdminController {


	 	@FXML
	 	private FlowPane nomiGiocatori;
		@FXML
	    private Button bottoneCreaTorneo;
	  	@FXML
	    private Button toLogInButton;
	  	@FXML
	    private Label codice;
	  	@FXML
	  	private TextField T0;
		@FXML
	  	private TextField T1;
		@FXML
	  	private TextField T2;
		@FXML
	  	private TextField T3;
		@FXML
	  	private TextField T4;
		@FXML
	  	private TextField T5;
		@FXML
	  	private TextField T6;
		@FXML
	  	private TextField T7;

	  
	    private Stage stage;
	    private Scene scene;
	    private Parent root;
	    private ArrayList<String> listaNomiTorneo;
		private String codiceTorneo = "";
    	private boolean giocatoreAssente;

		    public void creaTorneo(ActionEvent event) throws IOException {
		    	listaNomiTorneo = new ArrayList<>();
		    	Random rnd = new Random();
		    	
		    	listaNomiTorneo.add(T0.getText());
		    	listaNomiTorneo.add(T1.getText());
		    	listaNomiTorneo.add(T2.getText());
		    	listaNomiTorneo.add(T3.getText());
		    	listaNomiTorneo.add(T4.getText());
		    	listaNomiTorneo.add(T5.getText());
		    	listaNomiTorneo.add(T6.getText());
		    	listaNomiTorneo.add(T7.getText());

		    	
		    	if(checkNull()) {
		    			Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Giocatore Mancante");
						alert.setHeaderText("Mancano uno o più nomi o sono presnti dei bot");
						alert.setContentText("Assicurati che tutti gli spazi a disposizione siano compilati e che non ci siano bot");
						if(alert.showAndWait().get()==ButtonType.OK){
							alert.close();
						} else {

							alert.close();
						}
		    		} else {
		    			for(int i=0; i<8; i++)
		        			 codiceTorneo += rnd.nextInt(10)+"";
		        		codice.setText(codiceTorneo);
		        		Collections.shuffle(listaNomiTorneo);
		    			String folder = "src/tornei/"+codiceTorneo;
						File f = new File(folder);
						f.mkdirs();
		    			File file = new File("src/tornei/"+codiceTorneo+"/"+codiceTorneo+"_new.txt");
		    			FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
							for(int i = 0; i<listaNomiTorneo.size(); i++) {
								bw.write(listaNomiTorneo.get(i)+"\n");
							}
						bw.close();
						fw.close();
						bottoneCreaTorneo.setDisable(true);
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
			
			public boolean checkNull() {
				giocatoreAssente = false;
				
				if(T0.getText().isBlank()||T0.getText().equals("BOT"))
					giocatoreAssente = true;
				if(T1.getText().isBlank()||T1.getText().equals("BOT"))
					giocatoreAssente = true;
				if(T2.getText().isBlank()||T2.getText().equals("BOT"))
					giocatoreAssente = true;
				if(T3.getText().isBlank()||T3.getText().equals("BOT"))
					giocatoreAssente = true;
				if(T4.getText().isBlank()||T4.getText().equals("BOT"))
					giocatoreAssente = true;
				if(T5.getText().isBlank()||T5.getText().equals("BOT"))
					giocatoreAssente = true;
				if(T6.getText().isBlank()||T6.getText().equals("BOT"))
					giocatoreAssente = true;
				if(T7.getText().isBlank()||T7.getText().equals("BOT"))
					giocatoreAssente = true;
				
				return giocatoreAssente;
			}
}
   

