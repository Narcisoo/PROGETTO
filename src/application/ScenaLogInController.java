package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ScenaLogInController {
	
	@FXML
	Label messaggiSchermo;
	@FXML
	TextField username1;
	@FXML
	TextField username2;
	
	@FXML
	Button leaderboardButton;
	
	@FXML
	TextField admin;
	@FXML
	PasswordField password;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private String adminUsername = "a";
	private String adminPassword = "a";
	private String usernameGiocatore1;
	private String usernameGiocatore2;
	
	
	
	public void login(ActionEvent event) throws IOException {
		
		usernameGiocatore1 = username1.getText();
		usernameGiocatore2 = username2.getText();

		if(admin.getText().equals(adminUsername)&&password.getText().equals(adminPassword)){
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaGioco.fxml"));	
		root = loader.load();
		
		ControllerScenaGioco controllerScenaGioco = loader.getController();
		controllerScenaGioco.displayNomeGiocatore(usernameGiocatore1);
		controllerScenaGioco.displayNomeAvversario(usernameGiocatore2);
		controllerScenaGioco.startGame();
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();	
		} else {
			
			messaggiSchermo.setText("Username o Password errati !");

			}
		
}
	
	public void exit() {
		System.exit(0);
	}
	
	public void regole(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaRegole.fxml"));	
		root = loader.load();
		
		RegoleController controllerRegole = loader.getController();
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();	
	}
	
	public void toLeaderboard(ActionEvent event) throws IOException {
		
	}


}
		

	

