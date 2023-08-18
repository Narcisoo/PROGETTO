package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegoleController {
	
	
	@FXML
	Button toLogIn;
	
	private Scene scene;
	private Parent root;
	private Stage stage;

	public void toLogIn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaLogIn.fxml"));	
		root = loader.load();
		
		ScenaLogInController controllerLogIn = loader.getController();
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();	
	}
	
}
