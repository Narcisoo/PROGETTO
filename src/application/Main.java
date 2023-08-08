package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;



public class Main extends Application {
	public static void main(String[] args) {	
		launch(args);
		/*
		Mazzo mazzo = new Mazzo();
		mazzo.popolaMazzo();
		
		System.out.println("Mazzo Ordinato");
		for(int i=0; i<mazzo.getSize();i++) {
		System.out.println("Carta "+(i+1)+" "+mazzo.getCarta(i));
		}
		System.out.println("Dimensioni mazzo: "+mazzo.getSize());
		System.out.println("----------------");
		mazzo.mischiaMazzo();
		System.out.println("Mazzo Disordinato");
		for(int i=0; i<mazzo.getSize();i++) {
			System.out.println("Carta "+(i+1)+" "+mazzo.getCarta(i));
			}
			System.out.println("Dimensioni mazzo: "+mazzo.getSize());
			*/
		
	}

	public void start (Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ScenaGioco.fxml")); 
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		Group g1 = new Group();
	}
	
	
}
