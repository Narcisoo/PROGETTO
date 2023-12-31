package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScenaLogInController {
	
	@FXML
	TextField codiceNuovaPartita;
	@FXML
	TextField codiceCaricaPartita;
	@FXML
	Button logInToClassificaButton;
	@FXML
	TextField adminUsername;
	@FXML
	PasswordField adminPassword;
	@FXML
	Button amichevole1v1Button;
	@FXML
	Button torneo8giocatoriButton;
	@FXML
	Button nuovaPartitaButton;
	@FXML
	Button caricaPartitaButton;
	@FXML
	Button elimina;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private static String adminUser = "AdminNP";
	private String adminPass = "spacchi";
	private String codiceNuova;
	private String codiceCarica;
	
	public void nuovaPartita(ActionEvent event) throws IOException {

		codiceNuova = codiceNuovaPartita.getText();
		File partitaAmichevole = new File("src/partite/"+codiceNuova+"/"+codiceNuova+"_new.txt");
		File partitaTorneo = new File("src/tornei/"+codiceNuova+"/"+codiceNuova+"_new.txt");
		File partitaInCorso = new File("src/partite/"+codiceNuova+"/"+codiceNuova+"_info.txt");
		File torneoInCorso = new File("src/tornei/"+codiceNuova+"/"+codiceNuova+"_continua.txt");
		File partitaConclusa = new File("src/partite/"+codiceNuova+"/"+codiceNuova+"_concluso.txt");
		File torneoConcluso = new File("src/tornei/"+codiceNuova+"/"+codiceNuova+"_concluso.txt");
		
		if(partitaAmichevole.exists()&&!partitaInCorso.exists()&&!partitaConclusa.exists()) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaGioco.fxml"));	
		root = loader.load();
		ControllerScenaGioco controllerScenaGioco = loader.getController();
		controllerScenaGioco.setCodice(codiceNuova);
		controllerScenaGioco.startGame();
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();	
		stage.setOnCloseRequest(evv -> {
		evv.consume();
		try {
			controllerScenaGioco.pausaEsci(stage);
		} catch (IOException e) {
			e.printStackTrace();
		}});
		} else if(partitaTorneo.getAbsoluteFile().exists()&&!torneoInCorso.getAbsoluteFile().exists()&&!torneoConcluso.getAbsoluteFile().exists()){
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaTorneo.fxml"));	
			root = loader.load();
			ScenaTorneoController torneoController = loader.getController();
			torneoController.inizializzaTorneo(codiceNuova);
			stage =(Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();	
			stage.setOnCloseRequest(evv -> {
				evv.consume();
					try {
						torneoController.sospendi(stage);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
		} else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("CODICE NON VALIDO");
				alert.setHeaderText("Non esiste nessuna partita o torneo da giocare con tale codice oppure sono già conclusi.");
				alert.setContentText("Se la parita o il torneo sono stati salvati, inserisci il codice nella sezione 'Carica Partita'.\nSe il problema persiste allora la partita o il torneo sono già conclusi.\nSe non"
						+ "è questo il caso, rivolgersi all'amministratore.");
				if(alert.showAndWait().get()==ButtonType.OK){
					alert.close();
				} else {

					alert.close();
				}
			}					
}
	
	public void exit() {
		System.exit(0);
	}
	
	public void regole(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaRegole.fxml"));	
		root = loader.load();
		RegoleController controllerRegole = loader.getController();
		controllerRegole.scriviTesto();
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();	
	}
	
	public void classifica(ActionEvent event) throws IOException {
		File high = new File("src/Highscore.csv");
		File folder = new File("src");
		folder.mkdir();
		if(!high.exists())
			high.createNewFile();
		List<String> classificaOrdinata = leggiEdOrdinaClassifica();
		if(classificaOrdinata.size()>20) {
		List<String> classificaRidotta = classificaOrdinata.subList(0, 20);
		String classificaTesto = classificaRidotta.stream().collect(Collectors.joining("\n"));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("CLASSIFICA");
		alert.initStyle(StageStyle.TRANSPARENT);
		alert.setHeaderText("Classifica attuale dei migliori top 20");
		alert.setContentText(classificaTesto);
		if(alert.showAndWait().get()==ButtonType.OK) {
			alert.close();
		}	
		} else {
			String classificaTesto = classificaOrdinata.stream().collect(Collectors.joining("\n"));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("CLASSIFICA");
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setHeaderText("Classifica attuale dei migliori top 20");
			alert.setContentText(classificaTesto);
			if(alert.showAndWait().get()==ButtonType.OK) {
				alert.close();
			}	
		}
		
	}
	
	public void caricaPartita(ActionEvent event) throws IOException{
		
		codiceCarica = codiceCaricaPartita.getText();
		if(codiceCarica.length()<7) {
		File file = new File("src/partite/"+codiceCarica+"/"+codiceCarica+"_info.txt");
		File partitaConclusa = new File("src/partite/"+codiceCarica+"/"+codiceCarica+"_concluso.txt");
		 if (!file.getAbsoluteFile().exists()||partitaConclusa.getAbsoluteFile().exists()) {
			 Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("CODICE NON VALIDO");
				alert.setHeaderText("Non esiste nessuna partita o torneo in corso con tale codice");
				alert.setContentText("Se la partita è nuova, inserisci il codice nella sezione 'Nuova Partita'.\nSe il problema persiste allora la partita o il torneo sono già conclusi."
						+"\nSe non è questo il caso, rivolgersi all'amministratore");
				if(alert.showAndWait().get()==ButtonType.OK){
					alert.close();
				} else {

					alert.close();
				}
           } else {
        	   FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaGioco.fxml"));	
       			root = loader.load();
       			ControllerScenaGioco controllerScenaGioco = loader.getController();
       			controllerScenaGioco.loadGame(codiceCarica);
       			stage =(Stage)((Node)event.getSource()).getScene().getWindow();
       			scene = new Scene(root);
       			stage.setScene(scene);
       			stage.centerOnScreen();
       			stage.show(); 
       			stage.setOnCloseRequest(evv -> {
       				evv.consume();
       				try {
       					controllerScenaGioco.pausaEsci(stage);
       				} catch (IOException e) {
       					e.printStackTrace();
       				}});
           }
		} else {
			File file = new File("src/tornei/"+codiceCarica+"/"+codiceCarica+"_continua.txt");
			File torneoConcluso = new File("src/tornei/"+codiceCarica+"/"+codiceCarica+"_concluso.txt");
			if (!file.getAbsoluteFile().exists()||torneoConcluso.getAbsoluteFile().exists()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("CODICE NON VALIDO");
				alert.setHeaderText("Non esiste nessuna partita o torneo in corso con tale codice");
				alert.setContentText("Se la partita è nuova, inserisci il codice nella sezione 'Nuova Partita'.\nSe il problema persiste allora la partita o il torneo sono già conclusi."
						+"\nSe non è questo il caso, rivolgersi all'amministratore");
				if(alert.showAndWait().get()==ButtonType.OK){
					alert.close();
				} else {

					alert.close();
				}
			} else {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaTorneo.fxml"));	
				root = loader.load();
				ScenaTorneoController torneoController = loader.getController();
				torneoController.continuaTorneo(codiceCarica);
				stage =(Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.show();	
				stage.setOnCloseRequest(evv -> {
					evv.consume();
						try {
							torneoController.sospendi(stage);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
			}
		}
	}
	
	public void amichevole(ActionEvent event) throws IOException{
		
		if(adminUsername.getText().equals(adminUser)&&adminPassword.getText().equals(adminPass)){
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Amichevole1v1.fxml"));	
			root = loader.load();
			stage =(Stage)((Node)event.getSource()).getScene().getWindow();
   			scene = new Scene(root);
   			stage.setScene(scene);
   			stage.centerOnScreen();
   			stage.show();
   			} else {
   				Alert alert = new Alert(AlertType.CONFIRMATION);
   				alert.setTitle("Credenziali Errate");
   				alert.setHeaderText("Username o Password errati!");
   				alert.setContentText("Se non sei l'admin ma un giocatore, non devi inserire credenziali, ma solamente il Codice Partita / Torneo nella sezione 'Giocatori'.");
   				if(alert.showAndWait().get()==ButtonType.OK){
   					alert.close();
   				} else {
  
   					alert.close();
   				}
   			}
	}
	
	public void torneo(ActionEvent event) throws IOException{
		if(adminUsername.getText().equals(adminUser)&&adminPassword.getText().equals(adminPass)){
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TorneoAdmin.fxml"));	
			root = loader.load();
			stage =(Stage)((Node)event.getSource()).getScene().getWindow();
   			scene = new Scene(root);
   			stage.setScene(scene);
   			stage.centerOnScreen();
   			stage.show();
   			} else {
   				Alert alert = new Alert(AlertType.CONFIRMATION);
   				alert.setTitle("Credenziali Errate");
   				alert.setHeaderText("Username o Password errati!");
   				alert.setContentText("Se non sei l'admin ma un giocatore, non devi inserire credenziali, ma solamente il Codice Partita / Torneo nella sezione 'Giocatori'.");
   				if(alert.showAndWait().get()==ButtonType.OK){
   					alert.close();
   				} else {
  
   					alert.close();
   				}
   			}
	}
	
	 private List<String> leggiEdOrdinaClassifica() throws IOException {
	        List<String> scores = new ArrayList<>();
	        
	        try {
	        	//InputStream ins = Classifica.class.getResourceAsStream("src/Highscore.csv");
				BufferedReader reader = new BufferedReader(new FileReader("src/Highscore.csv"));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                scores.add(line);
	            }
	            reader.close();
	        }catch (Exception e) {
	        	e.printStackTrace();
	        
	        }
	        scores.sort((s1, s2) -> {
	            int score1 = Integer.parseInt(s1.split(",")[1]);
	            int score2 = Integer.parseInt(s2.split(",")[1]);
	            return Integer.compare(score2, score1); 
	        });
	        return scores;
	    }
	
	 
	 public void eliminaSalvataggi() throws IOException {
		 if(adminUsername.getText().equals(adminUser)&&adminPassword.getText().equals(adminPass)){
			 Alert alert = new Alert(AlertType.CONFIRMATION);
			 alert.setTitle("Eliminazione dati salvati");
			 alert.setHeaderText("Stai per eliminare tutte le partite ed i tornei in corso!");
			 alert.setContentText("Sei sicuro di voler continuare?");
				if(alert.showAndWait().get()==ButtonType.OK){
					File folder = new File("src");
					folder.mkdir();
				File dirPartite = new File("src/partite");
				File dirTorneo = new File("src/tornei");
				dirPartite.mkdir();
				dirTorneo.mkdir();
				FileUtils.cleanDirectory(dirPartite);
				FileUtils.cleanDirectory(dirTorneo); 
				} else {
					alert.close();
				}
			
		 }else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Credenziali Errate");
				alert.setHeaderText("Username o Password errati!");
				alert.setContentText("Solo l'admin può cancellare le partite in corso o concluse");
				if(alert.showAndWait().get()==ButtonType.OK){
					alert.close();
				} else {

					alert.close();
				}
			}
	 }
	 
	 public static String getAdmin() {
		 return adminUser;
	 }
	 
	 
	 
}
		

	

