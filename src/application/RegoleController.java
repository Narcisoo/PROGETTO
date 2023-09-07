package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RegoleController {
	
	
	@FXML
	Button toLogIn;
	@FXML
	Label areaTesto;
	
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	
	public void scriviTesto() {
	        areaTesto.setWrapText(true); 
	        try {
	        	InputStream ins = getClass().getResourceAsStream("/Regole.txt");
	            StringBuilder testo = new StringBuilder();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
	            String line;

	            while ((line = reader.readLine()) != null) {
	            	testo.append(line).append("\n");
	            }
	            reader.close();
	            areaTesto.setText(testo.toString());
	        } catch (IOException e) {
	            e.printStackTrace();
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
