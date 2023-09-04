
package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScenaTorneoController {

    @FXML
    private Button bottoneFinale;
    @FXML
    private Button bottoneSemi1;
    @FXML
    private Button bottoneMatch1;
    @FXML
    private Button bottoneMatch4;
    @FXML
    private Button bottoneSemi2;
    @FXML
    private Button bottoneMatch2;
    @FXML
    private Button bottoneMatch3;
    @FXML
    private Label codice;
    @FXML
    private Label giocatore1T;
    @FXML
    private Label giocatore2T;
    @FXML
    private Label giocatore3T;
    @FXML
    private Label giocatore4T;
    @FXML
    private Label giocatore5T;
    @FXML
    private Label giocatore6T;
    @FXML
    private Label giocatore7T;
    @FXML
    private Label giocatore8T;
    @FXML
    private Label primoFinalista;
    @FXML
    private Label primoSemifinalista;
    @FXML
    private Label quartoSemifinalista;
    @FXML
    private Label secondoFinalista;
    @FXML
    private Label secondoSemifinalista;
    @FXML
    private Label terzoSemifinalista;
    @FXML
    private Label vincitore;
    @FXML
    private Group giocatori;
    @FXML
    private Button toClassificaButton;
    @FXML
    private Button sospendiTorneoButton;
    @FXML
    private Button terminaTorneoButton;
    @FXML
    private AnchorPane paneTorneo;
    @FXML
    private Group partite;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String codiceTorneo="";
    private int numeroMatch;
    private static final String nomeFile = "src/Highscore.csv";
    
    public void inizializzaTorneo(String codiceTorneo) throws IOException {
    	codice.setText(codiceTorneo);
    	this.codiceTorneo = codiceTorneo;
    	Scanner scf = new Scanner(new File("src/tornei/"+codiceTorneo+"/"+codiceTorneo+"_new.txt"));
		while(scf.hasNextLine()) {
			giocatore1T.setText(scf.nextLine());
			giocatore2T.setText(scf.nextLine());
			giocatore3T.setText(scf.nextLine());
			giocatore4T.setText(scf.nextLine());
			giocatore5T.setText(scf.nextLine());
			giocatore6T.setText(scf.nextLine());
			giocatore7T.setText(scf.nextLine());
			giocatore8T.setText(scf.nextLine());
		}
		scf.close();
		
		bottoneFinale.setVisible(false);
		bottoneSemi1.setVisible(false);
		bottoneSemi2.setVisible(false);
		salvaMatch(codiceTorneo);
    }
    
    
    public void giocaMatch1(ActionEvent event) throws IOException {
    	numeroMatch = 1;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaGioco.fxml"));	
		root = loader.load();
		ControllerScenaGioco controllerScenaGioco = loader.getController();
		controllerScenaGioco.displayNomeGiocatore(giocatore1T.getText());
		controllerScenaGioco.displayNomeAvversario(giocatore2T.getText());
		controllerScenaGioco.startGameTorneo(numeroMatch);
		controllerScenaGioco.setCodice(codice.getText());
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

    

    
    public void giocaMatch2(ActionEvent event) throws IOException {
    	numeroMatch = 2;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaGioco.fxml"));	
		root = loader.load();
		ControllerScenaGioco controllerScenaGioco = loader.getController();
		controllerScenaGioco.displayNomeGiocatore(giocatore3T.getText());
		controllerScenaGioco.displayNomeAvversario(giocatore4T.getText());
		controllerScenaGioco.startGameTorneo(numeroMatch);
		controllerScenaGioco.setCodice(codice.getText());
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
    
    
   public void giocaMatch3(ActionEvent event) throws IOException {
	   numeroMatch = 3;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaGioco.fxml"));	
		root = loader.load();
		ControllerScenaGioco controllerScenaGioco = loader.getController();
		controllerScenaGioco.displayNomeGiocatore(giocatore5T.getText());
		controllerScenaGioco.displayNomeAvversario(giocatore6T.getText());
		controllerScenaGioco.startGameTorneo(numeroMatch);
		controllerScenaGioco.setCodice(codice.getText());
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

    
    public void giocaMatch4(ActionEvent event) throws IOException {
    	numeroMatch = 4;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaGioco.fxml"));	
		root = loader.load();
		ControllerScenaGioco controllerScenaGioco = loader.getController();
		controllerScenaGioco.displayNomeGiocatore(giocatore7T.getText());
		controllerScenaGioco.displayNomeAvversario(giocatore8T.getText());
		controllerScenaGioco.startGameTorneo(numeroMatch);
		controllerScenaGioco.setCodice(codice.getText());
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
    
    
    public void giocaSemi1(ActionEvent event) throws IOException {
    	numeroMatch = 5;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaGioco.fxml"));	
		root = loader.load();
		ControllerScenaGioco controllerScenaGioco = loader.getController();
		controllerScenaGioco.displayNomeGiocatore(primoSemifinalista.getText());
		controllerScenaGioco.displayNomeAvversario(secondoSemifinalista.getText());
		controllerScenaGioco.startGameTorneo(numeroMatch);
		controllerScenaGioco.setCodice(codice.getText());
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

    
    public void giocaSemi2(ActionEvent event) throws IOException {
    	numeroMatch = 6;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaGioco.fxml"));	
		root = loader.load();
		ControllerScenaGioco controllerScenaGioco = loader.getController();
		controllerScenaGioco.displayNomeGiocatore(terzoSemifinalista.getText());
		controllerScenaGioco.displayNomeAvversario(quartoSemifinalista.getText());
		controllerScenaGioco.startGameTorneo(numeroMatch);
		controllerScenaGioco.setCodice(codice.getText());
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

    
    public void giocaFinale(ActionEvent event) throws IOException {
    	numeroMatch = 7;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaGioco.fxml"));	
		root = loader.load();
		ControllerScenaGioco controllerScenaGioco = loader.getController();
		controllerScenaGioco.displayNomeGiocatore(primoFinalista.getText());
		controllerScenaGioco.displayNomeAvversario(secondoFinalista.getText());
		controllerScenaGioco.startGameTorneo(numeroMatch);
		controllerScenaGioco.setCodice(codice.getText());
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
    
    public void continuaTorneo(String codice) throws IOException {
    	this.codice.setText(codice);
    	codiceTorneo = codice;
    	Scanner scf = new Scanner(new File("src/tornei/"+codice+"/"+codice+"_continua.txt"));
		while(scf.hasNextLine()) {
			for(int i=0; i<giocatori.getChildren().size(); i++) {
				((Label) giocatori.getChildren().get(i)).setText(scf.nextLine());
			}
		}
		scf.close();
		setMatch(codice);
		segnaColoreVincitori();
    }
    
    public void sospendi(Stage stage) throws IOException {
    	if(!checkConcluso()){
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sospendi torneo");
		alert.setHeaderText("Il torneo non è ancora concluso");
		alert.setContentText("Vuoi sospendere il torneo? Potrai continuare in un secondo momento con il codice torneo, dalla schermata del login.");
		if(alert.showAndWait().get()==ButtonType.OK) {
			File file = new File("src/tornei/"+codiceTorneo+"/"+codiceTorneo+"_continua.txt");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i=0; i<giocatori.getChildren().size(); i++) {
				bw.write(((Label) giocatori.getChildren().get(i)).getText()+"\n");
			}
			bw.close();
			fw.close();
			stage.close();
		} else {
			alert.close();
		}}
    	else {
    		stage=(Stage)paneTorneo.getScene().getWindow();
			stage.close();
    	}
    }
    
    public void sospendi(ActionEvent event) throws IOException {
    	if(!checkConcluso()){
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sospendi torneo");
		alert.setHeaderText("Il torneo non è ancora concluso");
		alert.setContentText("Vuoi sospendere il torneo? Potrai continuare in un secondo momento con il codice torneo, dalla schermata del login.");
		if(alert.showAndWait().get()==ButtonType.OK) {
			File file = new File("src/tornei/"+codiceTorneo+"/"+codiceTorneo+"_continua.txt");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i=0; i<giocatori.getChildren().size(); i++) {
				bw.write(((Label) giocatori.getChildren().get(i)).getText()+"\n");
			}
			bw.close();
			fw.close();
			stage =(Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
		} else {
			alert.close();
		}} else {
			stage =(Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
		}
    }
    
    public void salvaInfo(String codiceTorneo) throws IOException {
    	this.codiceTorneo = codiceTorneo;
    	Scanner scf = new Scanner(new File("src/tornei/"+codiceTorneo+"/"+codiceTorneo+"_new.txt"));
		while(scf.hasNextLine()) {
			giocatore1T.setText(scf.nextLine());
			giocatore2T.setText(scf.nextLine());
			giocatore3T.setText(scf.nextLine());
			giocatore4T.setText(scf.nextLine());
			giocatore5T.setText(scf.nextLine());
			giocatore6T.setText(scf.nextLine());
			giocatore7T.setText(scf.nextLine());
			giocatore8T.setText(scf.nextLine());
		}
		scf.close();
    	File file = new File("src/tornei/"+codiceTorneo+"/"+codiceTorneo+"_continua.txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for(int i=0; i<giocatori.getChildren().size(); i++) {
			bw.write(((Label) giocatori.getChildren().get(i)).getText()+"\n");
		}
		bw.close();
		fw.close();
		
		
    }
    
    public void salvaMatch(String codiceTorneo) throws IOException {
		File file = new File("src/tornei/"+codiceTorneo+"/"+codiceTorneo+"_match.txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for(int i=0; i<partite.getChildren().size(); i++) {
			bw.write(((Button) partite.getChildren().get(i)).isVisible()+"\n");
		}
		bw.close();
		fw.close();
    }
    
    public void setMatch(String codiceTorneo) throws IOException {
    	Scanner scf = new Scanner(new File("src/tornei/"+codiceTorneo+"/"+codiceTorneo+"_match.txt"));
    	for(int i =0; i<partite.getChildren().size();i++) {
			 partite.getChildren().get(i).setVisible(Boolean.parseBoolean(scf.nextLine()));
			 if(!scf.hasNextLine()) {
				 break;
			 }
			}
			scf.close();
			salvaMatch(codiceTorneo);
    }
    
    public void vincitore(String codiceTorneo,String vincitore, int match) throws IOException {
    	this.codiceTorneo=codiceTorneo;
    	codice.setText(this.codiceTorneo);
    	 setMatch(codiceTorneo);
   switch(match){
   case 1: primoSemifinalista.setText(vincitore);
   			bottoneMatch1.setVisible(false);
   			if(!bottoneMatch2.isVisible()) {
   				bottoneSemi1.setVisible(true);
   			} break;
   case 2:secondoSemifinalista.setText(vincitore);
   			bottoneMatch2.setVisible(false);
   			if(!bottoneMatch1.isVisible()) {
   				bottoneSemi1.setVisible(true);
   			} break;
   case 3:terzoSemifinalista.setText(vincitore);
   			bottoneMatch3.setVisible(false);
   			if(!bottoneMatch4.isVisible()) {
   				bottoneSemi2.setVisible(true);
   			} break;
   case 4:quartoSemifinalista.setText(vincitore); 
   			bottoneMatch4.setVisible(false);
   			if(!bottoneMatch3.isVisible()) {
   				bottoneSemi2.setVisible(true);
   			} break;
   case 5:primoFinalista.setText(vincitore); 
   			bottoneSemi1.setVisible(false);
   			if(!bottoneSemi2.isVisible()&&!bottoneMatch3.isVisible()&&!bottoneMatch4.isVisible()) {
   				bottoneFinale.setVisible(true);
   			} break;
   case 6:secondoFinalista.setText(vincitore);
   			bottoneSemi2.setVisible(false);
   			if(!bottoneSemi1.isVisible()&&!bottoneMatch1.isVisible()&&!bottoneMatch1.isVisible()) {
   				bottoneFinale.setVisible(true);
   			} break;
   case 7:this.vincitore.setText(vincitore);
   			File file = new File("src/tornei/"+codiceTorneo+"/"+codiceTorneo+"_concluso.txt");
   				if (!file.exists()) {
   					file.createNewFile();
   				}
   				FileWriter fw = new FileWriter(file.getAbsoluteFile());
   				BufferedWriter bw = new BufferedWriter(fw);
   				bw.write("Torneo #"+codiceTorneo+" CONCLUSO.\nVincitore: "+this.vincitore.getText());
   				bw.close();
   				fw.close();
   		  bottoneFinale.setVisible(false);
   		  sospendiTorneoButton.setVisible(false);
   		  terminaTorneoButton.setVisible(true);
   		  	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("CONGRATULAZIONI");
			alert.initStyle(StageStyle.TRANSPARENT);
			alert.setHeaderText("Il torneo è concluso. Contratulazioni e grazie a tutti i partecipanti!");
			alert.setContentText(this.vincitore.getText()+" è il vincitore di questo torneo ["+codiceTorneo+"].");
			if(alert.showAndWait().get()==ButtonType.OK) {
				alert.close();
			}	;break;
   		  
   }
  
   salvaMatch(codiceTorneo);
   salvaInfo(codiceTorneo);
   segnaColoreVincitori();
	 }
    
    public boolean casellaVuota(int n) {
    	boolean isVuoto = false;	
			if(((Label) giocatori.getChildren().get(n)).getText().equals("Vincitore Match 1")||((Label) giocatori.getChildren().get(n)).getText().equals("Vincitore Match 2")
					||((Label) giocatori.getChildren().get(n)).getText().equals("Vincitore Match 3")||((Label) giocatori.getChildren().get(n)).getText().equals("Vincitore Match 4")
					||((Label) giocatori.getChildren().get(n)).getText().equals("Vincitore Semi 1")||((Label) giocatori.getChildren().get(n)).getText().equals("Vincitore Semi 2")
					||((Label) giocatori.getChildren().get(n)).getText().equals("Vincitore Finale")) {
				isVuoto = true;			
			}
    	return isVuoto;
    }
    
    public void segnaColoreVincitori() {
    	if(primoSemifinalista.getText().equals(giocatore1T.getText())) {
    		giocatore1T.setTextFill(Color.web("#79bf1d"));
    		giocatore2T.setTextFill(Color.web("#a60c20"));
    	} else if(primoSemifinalista.getText().equals(giocatore2T.getText())){
    		giocatore2T.setTextFill(Color.web("#79bf1d"));
    		giocatore1T.setTextFill(Color.web("#a60c20"));
    	} else {
    		giocatore1T.setTextFill(Color.WHITE);
    		giocatore2T.setTextFill(Color.WHITE);
    	}
    	
    	if(secondoSemifinalista.getText().equals(giocatore3T.getText())) {
    		giocatore3T.setTextFill(Color.web("#79bf1d"));
    		giocatore4T.setTextFill(Color.web("#a60c20"));
    	} else if(secondoSemifinalista.getText().equals(giocatore4T.getText())){
    		giocatore4T.setTextFill(Color.web("#79bf1d"));
    		giocatore3T.setTextFill(Color.web("#a60c20"));
    	} else {
    		giocatore3T.setTextFill(Color.WHITE);
    		giocatore4T.setTextFill(Color.WHITE);
    	}
    	
    	if(terzoSemifinalista.getText().equals(giocatore5T.getText())) {
    		giocatore5T.setTextFill(Color.web("#79bf1d"));
    		giocatore6T.setTextFill(Color.web("#a60c20"));
    	} else if(terzoSemifinalista.getText().equals(giocatore6T.getText())){
    		giocatore6T.setTextFill(Color.web("#79bf1d"));
    		giocatore5T.setTextFill(Color.web("#a60c20"));
    	} else {
    		giocatore5T.setTextFill(Color.WHITE);
    		giocatore6T.setTextFill(Color.WHITE);
    	}
    	
    	if(quartoSemifinalista.getText().equals(giocatore7T.getText())) {
    		giocatore7T.setTextFill(Color.web("#79bf1d"));
    		giocatore8T.setTextFill(Color.web("#a60c20"));
    	} else if(quartoSemifinalista.getText().equals(giocatore8T.getText())){
    		giocatore8T.setTextFill(Color.web("#79bf1d"));
    		giocatore7T.setTextFill(Color.web("#a60c20"));
    	} else {
    		giocatore7T.setTextFill(Color.WHITE);
    		giocatore8T.setTextFill(Color.WHITE);
    	}
    	
    	if(primoFinalista.getText().equals(primoSemifinalista.getText())){
    		primoSemifinalista.setTextFill(Color.web("#79bf1d"));
    		secondoSemifinalista.setTextFill(Color.web("#a60c20"));
    	} else if(primoFinalista.getText().equals(secondoSemifinalista.getText())){
    		secondoSemifinalista.setTextFill(Color.web("#79bf1d"));
    		primoSemifinalista.setTextFill(Color.web("#a60c20"));
    	} else {
    		primoSemifinalista.setTextFill(Color.WHITE);
    		secondoSemifinalista.setTextFill(Color.WHITE);
    	}
    	
    	if(secondoFinalista.getText().equals(terzoSemifinalista.getText())) {
    		terzoSemifinalista.setTextFill(Color.web("#79bf1d"));
    		quartoSemifinalista.setTextFill(Color.web("#a60c20"));
    	} else if(secondoFinalista.getText().equals(quartoSemifinalista.getText())){
    		quartoSemifinalista.setTextFill(Color.web("#79bf1d"));
    		terzoSemifinalista.setTextFill(Color.web("#a60c20"));
    	} else {
    		terzoSemifinalista.setTextFill(Color.WHITE);
    		quartoSemifinalista.setTextFill(Color.WHITE);
    	}
    	
    	if(this.vincitore.getText().equals(primoFinalista.getText())) {
    		primoFinalista.setTextFill(Color.web("#79bf1d"));
    		this.vincitore.setTextFill(Color.web("#79bf1d"));
    		secondoFinalista.setTextFill(Color.web("#a60c20"));
    	} else if(this.vincitore.getText().equals(secondoFinalista.getText())){
    		this.vincitore.setTextFill(Color.web("#79bf1d"));
    		secondoFinalista.setTextFill(Color.web("#79bf1d"));
    		primoFinalista.setTextFill(Color.web("#a60c20"));
    	} else {
    		primoFinalista.setTextFill(Color.WHITE);
    		secondoFinalista.setTextFill(Color.WHITE);
    	}
    	
    }

    public boolean checkConcluso() {
    	File file = new File("src/tornei/"+codiceTorneo+"/"+codiceTorneo+"_concluso.txt");
    	if(file.exists()) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public void classifica() throws IOException {
    	
			List<String> classificaOrdinata = leggiEdOrdinaClassifica();
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
    }

    private List<String> leggiEdOrdinaClassifica() throws IOException {
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        }
        scores.sort((s1, s2) -> {
            int score1 = Integer.parseInt(s1.split(",")[1]);
            int score2 = Integer.parseInt(s2.split(",")[1]);
            return Integer.compare(score2, score1); 
        });
        return scores;
    }


}

