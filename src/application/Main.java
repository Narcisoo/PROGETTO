package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;



public class Main extends Application {
	
	public static void main(String[] args) {	
		
		
		launch(args);
		
		
	}

	public void start (Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ScenaLogIn.fxml")); 
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.setTitle("SPACCHI");
			Image image = new Image("Icona.png");
			stage.getIcons().add(image);
			stage.show();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
}