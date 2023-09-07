package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ControllerScenaGioco{
	@FXML
	private Button resaButton;
	@FXML
	private HBox manoGiocatore;
	@FXML
	private HBox manoAvversario;
    @FXML
    private Label nomeGiocatore;
    @FXML
    private Label nomeAvversario;
    @FXML
    private Label punteggioGiocatore;
    @FXML
    private Label punteggioAvversario;
    @FXML
    private ImageView mazzo;
    @FXML
    private ImageView pila;
    @FXML
    private Button cambiaTurnoButton;
    @FXML
    private Button classificaButton;
    @FXML
    private TextArea areaTesto;
    @FXML
    private AnchorPane paneGioco;
    @FXML 
    private Button scarta;
    @FXML
    private Label codicePartita;
    @FXML
    private Button loadGameButton;
    @FXML
    private Label numeroMatch;
    @FXML
    private Label matchLabel;

	
	private Mazzo mazzoCarte;
	private Mazzo mazzoEventi;
	private PilaDegliScarti pilaCarte;
	private Giocatore giocatore;
	private Giocatore avversario;
	private ArrayList<Carta> carteGiocatore;
	private ArrayList<Carta> carteAvversario;
	private boolean turnoGiocatore = true;
	private boolean azioniConcesse = true;
	private boolean mazzoTerminato = false;
	private int numeroTurno;
	private boolean puoiPescare=true;
	private boolean pescaDaEvento = false;
	private String codice;
	private boolean isTorneo = false;
	private int nMatch;
	private boolean isBot = false;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	//mostra nome giocatore, preso dal log in
	public void displayNomeGiocatore(String username) {
		nomeGiocatore.setText(username);
		nomeGiocatore.setTextFill(Color.web("#36b36c"));
		giocatore = new Giocatore(username);
		punteggioGiocatore.setText(""+giocatore.getPuntiGiocatore()+" Punti");
	}
	
	//mostra nome avversario, preso dal log in
	public void displayNomeAvversario(String username) {
		nomeAvversario.setText(username);
		nomeAvversario.setTextFill(Color.web("#408fe3"));
		avversario = new Giocatore(username);
		punteggioAvversario.setText(""+avversario.getPuntiGiocatore()+" Punti");
		if(avversario.getNome().equals("BOT"))
			isBot = true;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
		codicePartita.setText(codice);
	}

	//inizia la partita/gioco. Da le carte ai giocatori ed richiama i metodi per impostare le immagini
	public void startGame()throws IOException {
		isTorneo = false;
		Scanner scf = new Scanner(new File("src/partite/"+codice+"/"+codice+"_new.txt"));
		while(scf.hasNextLine()) {
			displayNomeGiocatore(scf.nextLine());
			displayNomeAvversario(scf.nextLine());
		}
		scf.close();
		areaTesto.setEditable(false);
		mazzoEventi = new Mazzo();
		mazzoEventi.popolaMazzoEventi();
		mazzoEventi.mischiaMazzo();
		mazzoCarte = new Mazzo();
		mazzoCarte.popolaMazzo();
		mazzoCarte.mischiaMazzo();
		pilaCarte = new PilaDegliScarti();
		carteGiocatore = new ArrayList<>();
		carteAvversario = new ArrayList<>();
		numeroTurno = 1;
		if(!isBot) {
		Random rnd = new Random();
		turnoGiocatore = rnd.nextBoolean();
		} else {
			turnoGiocatore = true;
		}
		//Dai carte al giocatore
		for(int i=0; i<manoGiocatore.getChildren().size()-4;i++) {
	
			Carta carta = mazzoCarte.daiCartaInCima();
			carteGiocatore.add(new Carta(carta.getColore(),carta.getCategoria()));
		
		}
		//dai carte all'avversario
		for(int i=0; i<manoAvversario.getChildren().size()-4;i++) {
			Carta carta = mazzoCarte.daiCartaInCima();
			carteAvversario.add(new Carta(carta.getColore(),carta.getCategoria()));
		}
		inizializzaImmaginiCarte();
		pilaCarte.add(mazzoCarte.getCarta());
		immaginePila();
		immagineMazzo();
		areaTesto.setFont(Font.font("Georgia", 12));
		if(turnoGiocatore) {
		areaTesto.appendText("Turno ("+numeroTurno+") di: "+giocatore.getNome()+"\n");}
		else {
			areaTesto.appendText("Turno ("+numeroTurno+") di: "+avversario.getNome()+"\n");
		}
	}
	
	
	public void startGameTorneo(int match)throws IOException {
		isTorneo = true;
		matchLabel.setVisible(true);
		numeroMatch.setVisible(true);
		nMatch = match;
		numeroMatch.setText(match+"");
		resaButton.setVisible(true);
		areaTesto.setEditable(false);
		mazzoEventi = new Mazzo();
		mazzoEventi.popolaMazzoEventi();
		mazzoEventi.mischiaMazzo();
		mazzoCarte = new Mazzo();
		mazzoCarte.popolaMazzo();
		mazzoCarte.mischiaMazzo();
		pilaCarte = new PilaDegliScarti();
		carteGiocatore = new ArrayList<>();
		carteAvversario = new ArrayList<>();
		numeroTurno = 1;
		Random rnd = new Random();
		turnoGiocatore = rnd.nextBoolean();
		for(int i=0; i<manoGiocatore.getChildren().size()-4;i++) {
	
			Carta carta = mazzoCarte.daiCartaInCima();
			carteGiocatore.add(new Carta(carta.getColore(),carta.getCategoria()));
		
		}
		//dai carte all'avversario
		for(int i=0; i<manoAvversario.getChildren().size()-4;i++) {
			Carta carta = mazzoCarte.daiCartaInCima();
			carteAvversario.add(new Carta(carta.getColore(),carta.getCategoria()));
		}
		inizializzaImmaginiCarte();
		pilaCarte.add(mazzoCarte.getCarta());
		immaginePila();
		immagineMazzo();
		areaTesto.setFont(Font.font("Georgia", 12));
		if(turnoGiocatore) {
			areaTesto.appendText("Turno ("+numeroTurno+") di: "+giocatore.getNome()+"\n");}
			else {
				areaTesto.appendText("Turno ("+numeroTurno+") di: "+avversario.getNome()+"\n");
			}
	}
	
	//imposta le immagini delle carte nella mano del giocatore, e il retro in quelle dell'avversario
	public void inizializzaImmaginiCarte()throws IOException {
		//giocatore
		if(turnoGiocatore) {
		for(int i=0; i<carteGiocatore.size();i++) {
			ImageView imageView = (ImageView) manoGiocatore.getChildren().get(i);
			imageView.setImage(carteGiocatore.get(i).getImageCarte());
			imageView.setUserData(i);
			imageView.setOnMouseClicked(event ->{
				try {
					giocaCarta((int)imageView.getUserData());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
		}
		} else {
			for(int i=0; i<carteGiocatore.size();i++) {
				ImageView imageView = (ImageView) manoGiocatore.getChildren().get(i);
				imageView.setImage(new Image(Carta.class.getResourceAsStream("/immagini/Retro.png")));
				imageView.setUserData(i);
				imageView.setOnMouseClicked(event ->{
					try {
						nullEvent();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});	
				
		}
		}	
		//avversario	
		if(!isBot) {
		if(turnoGiocatore){
		for(int i=0;i<carteAvversario.size();i++) {
			ImageView imageView = (ImageView) manoAvversario.getChildren().get(i);
			imageView.setImage(new Image(Carta.class.getResourceAsStream("/immagini/Retro.png")));
			imageView.setUserData(i);	
			imageView.setOnMouseClicked(event ->{
				try {
					nullEvent();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		} else {
			for(int i=0;i<carteAvversario.size();i++) {
				ImageView imageView = (ImageView) manoAvversario.getChildren().get(i);
				imageView.setImage(carteAvversario.get(i).getImageCarte());
				imageView.setUserData(i);
				imageView.setOnMouseClicked(event ->{
					try {
						giocaCarta((int)imageView.getUserData());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});}
				
		
			}
		} else {
			for(int i=0;i<carteAvversario.size();i++) {
				ImageView imageView = (ImageView) manoAvversario.getChildren().get(i);
				imageView.setImage(new Image(Carta.class.getResourceAsStream("/immagini/Retro.png")));
				imageView.setUserData(i);	
				imageView.setOnMouseClicked(event ->{
					try {
						nullEvent();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		}
		}
	
	//imposta l'immagine (e la carta) nella pila degli scarti quando una carta viene giocata
	public void immaginePila() {
		
		pila.setImage(pilaCarte.cartaNellaPila().getImageCarte());
		pila.setOnMouseClicked(event ->{
			areaTesto.appendText(">>Ultima carta nella pila: "+pilaCarte.cartaNellaPila().infoCarta()+"<<\n");
			//areaTesto.appendText(">>Numero di carte nella pila: "+pilaCarte.getSize()+"<<\n");
			
		});
	}
	
	//imposta la prima carta della pila degli scarti
	public void setImmaginePila(Carta carta) {
		pila.setImage(carta.getImageCarte());
		pilaCarte.add(carta);
	}
	
	//imposta l'immagine del mazzo (retro)
	public void immagineMazzo() {
		mazzo.setImage(new Image(Carta.class.getResourceAsStream("/immagini/Retro.png")));
		
		mazzo.setOnMouseClicked(event ->{
			try {
				pescaCarta();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			areaTesto.appendText(">>Carte rimaste nel mazzo: "+mazzoCarte.getSize()+"<<\n");
		});
	}
	
	//metodo per giocare una carta
	public void giocaCarta(int i) throws IOException {
		if(azioniConcesse) {
		if(turnoGiocatore) {
			ImageView imageView =(ImageView) manoGiocatore.getChildren().get(i);
			if((carteGiocatore.get(i).getColore()==Colore.Evento1&&pilaCarte.cartaNellaPila().getColore()==Colore.Bianco)&&(carteGiocatore.get(i).getCategoria()==pilaCarte.cartaNellaPila().getCategoria())){
				giocaEvento(i);
			}else if((carteGiocatore.get(i).getColore()==Colore.Evento2&&pilaCarte.cartaNellaPila().getColore()==Colore.Nero)&&(carteGiocatore.get(i).getCategoria()==pilaCarte.cartaNellaPila().getCategoria())){
				giocaEvento(i);
			} else if((carteGiocatore.get(i).getColore()==Colore.Evento1)||(carteGiocatore.get(i).getColore()==Colore.Evento2)){
				imageView.setOnMouseClicked(event ->{
					areaTesto.appendText(">>Non puoi giocare questo evento<<\n");
					azioniConcesse = true;
				});	
			} else {
			assegnaPunti(i);
			setImmaginePila(carteGiocatore.get(i));	
			carteGiocatore.remove(i);
			imageView.setImage(null);
			ImageView iv =(ImageView) manoGiocatore.getChildren().get(carteGiocatore.size());
			iv.setImage(null);
			imageView.setOnMouseClicked(event ->{
				try {
					nullEvent();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});	
			iv.setOnMouseClicked(event ->{
				try {
					nullEvent();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});		
			inizializzaImmaginiCarte();
			checkVittoria(giocatore);
			}
			//cambiaTurno();	
		} else {if(!turnoGiocatore) {
					
					ImageView imageView =(ImageView) manoAvversario.getChildren().get(i);
					if((carteAvversario.get(i).getColore()==Colore.Evento1&&pilaCarte.cartaNellaPila().getColore()==Colore.Bianco)&&(carteAvversario.get(i).getCategoria()==pilaCarte.cartaNellaPila().getCategoria())){
						giocaEvento(i);
					}else if(((carteAvversario.get(i).getColore()==Colore.Evento2&&pilaCarte.cartaNellaPila().getColore()==Colore.Nero)&&carteAvversario.get(i).getCategoria()==pilaCarte.cartaNellaPila().getCategoria())){
						giocaEvento(i);
					} else if((carteAvversario.get(i).getColore()==Colore.Evento1)||(carteAvversario.get(i).getColore()==Colore.Evento2)){
						imageView.setOnMouseClicked(event ->{
							areaTesto.appendText(">>Non puoi giocare questo evento<<\n");
							azioniConcesse = true;
						});	
					} else {
					assegnaPunti(i);
					setImmaginePila(carteAvversario.get(i));
					
					carteAvversario.remove(i);
					imageView.setImage(null);
					ImageView iv =(ImageView) manoAvversario.getChildren().get(carteAvversario.size());
					iv.setImage(null);
					imageView.setOnMouseClicked(event ->{
						try {
							nullEvent();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});	
					iv.setOnMouseClicked(event ->{
						try {
							nullEvent();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});	
				inizializzaImmaginiCarte();
				checkVittoria(avversario);
				//cambiaTurno();
					}
		}}
		azioniConcesse = false;}
	}
	
	//gioca evento 
	public void giocaEvento(int i) throws IOException {
		if(turnoGiocatore) {
			ImageView imageView =(ImageView) manoGiocatore.getChildren().get(i);
			setImmaginePila(carteGiocatore.get(i));
			carteGiocatore.remove(i);
			imageView.setImage(null);
			ImageView iv =(ImageView) manoGiocatore.getChildren().get(carteGiocatore.size());
			iv.setImage(null);
			imageView.setOnMouseClicked(event ->{
				try {
					nullEvent();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});	
			iv.setOnMouseClicked(event ->{
				try {
					nullEvent();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});		
			inizializzaImmaginiCarte();
			attivaEvento();
			checkVittoria(giocatore);
			azioniConcesse = false;
		} else {
			ImageView imageView =(ImageView) manoAvversario.getChildren().get(i);
			setImmaginePila(carteAvversario.get(i));
			carteAvversario.remove(i);
			imageView.setImage(null);
			ImageView iv =(ImageView) manoAvversario.getChildren().get(carteAvversario.size());
			iv.setImage(null);
			imageView.setOnMouseClicked(event ->{
				try {
					nullEvent();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});	
			iv.setOnMouseClicked(event ->{
				try {
					nullEvent();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});		
			inizializzaImmaginiCarte();
			attivaEvento();
			checkVittoria(avversario);
			azioniConcesse = false;
		}
		
	}
	
	
	//metodo per pescare la carta
	public void pescaCarta() throws IOException {
		if(mazzoCarte.getSize()==1) {
			mazzo.setImage(null);
			mazzo.setOnMouseClicked(event ->{
				try {
					nullEvent();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		if(mazzoCarte.getSize()==1) {
			areaTesto.appendText("!! IL mazzo è terminato, il vincitore è colui che ha più punti !!\n");
			mazzoTerminato = true;
			checkVittoria(giocatore);
		}
		
		if(puoiPescare||pescaDaEvento) {
		if(turnoGiocatore) {
		if(carteGiocatore.size()>=7) {
			areaTesto.appendText(giocatore.getNome()+", hai raggiunto il numero massimo di carte in mano!\n");
		} else {
		areaTesto.appendText(giocatore.getNome()+" ha pescato una carta.\n");	
		Carta carta = mazzoCarte.daiCartaInCima();
		carteGiocatore.add(new Carta(carta.getColore(),carta.getCategoria()));
		inizializzaImmaginiCarte();
		}
		}
		else {
			if(carteAvversario.size()>=7) {
				areaTesto.appendText(avversario.getNome()+", hai raggiunto il numero massimo di carte in mano!\n");
			} else {
			areaTesto.appendText(avversario.getNome()+" ha pescato una carta.\n");	
			Carta carta = mazzoCarte.daiCartaInCima();
			carteAvversario.add(new Carta(carta.getColore(),carta.getCategoria()));
			inizializzaImmaginiCarte();
			}
		}}else {
			areaTesto.appendText(">>Hai gia' pescato questo turno<<\n");
		}
			if(pescaDaEvento==false) {
			puoiPescare=false;
			}
			pescaDaEvento=false;
	
	}

	public void pescaCartaEvento()throws IOException {
		if(turnoGiocatore) {
			if(carteGiocatore.size()>=7) {
				areaTesto.appendText(giocatore.getNome()+", hai raggiunto il numero massimo di carte in mano!\n");
			} else {
			areaTesto.appendText(giocatore.getNome()+" ha pescato una carta Evento.\n");	
			areaTesto.appendText("-  -  -  -  -  -  -  -  -  -  -  -  -  -\n");
			Carta carta = mazzoEventi.daiCartaInCima();
			carteGiocatore.add(new Carta(carta.getColore(),carta.getCategoria()));
			inizializzaImmaginiCarte();
			}
			}
			else {
				if(carteAvversario.size()>=7) {
					areaTesto.appendText(avversario.getNome()+", hai raggiunto il numero massimo di carte in mano!\n");
				} else {
				areaTesto.appendText(avversario.getNome()+" ha pescato una carta Evento.\n");	
				areaTesto.appendText("-  -  -  -  -  -  -  -  -  -  -  -  -  -\n");
				Carta carta = mazzoEventi.daiCartaInCima();
				carteAvversario.add(new Carta(carta.getColore(),carta.getCategoria()));
				inizializzaImmaginiCarte();
				}
			}
	}
	

	
	public void nullEvent()throws IOException {
		
	}
	
	public void cambiaTurno()throws IOException {
		
		numeroTurno++;
		if(azioniConcesse) {
			areaTesto.appendText("-------------------------------------\n");
			areaTesto.appendText("DEVI ANCORA GIOCARE UNA CARTA, NON PUOI PASSARE!\n");
			areaTesto.appendText("-  -  -  -  -  -  -  -  -  -  -  -  -  -\n");
		} else {
		puoiPescare=true;
		turnoGiocatore = !turnoGiocatore;
		inizializzaImmaginiCarte();
		azioniConcesse=true;
		if(turnoGiocatore) {
			areaTesto.appendText("-------------------------------------\n");
			areaTesto.appendText("Turno ("+numeroTurno+") di "+giocatore.getNome()+"\n");
			areaTesto.appendText("--Punti Attuali--\n");
			areaTesto.appendText(giocatore.getNome()+": "+giocatore.getPuntiGiocatore()+" \t"+avversario.getNome()+": "+avversario.getPuntiGiocatore()+" \n");
			areaTesto.appendText("Carta attuale nella Pila: "+pilaCarte.cartaNellaPila().infoCarta()+"\n");
			areaTesto.appendText("-  -  -  -  -  -  -  -  -  -  -  -  -  -\n");
		} else {
			areaTesto.appendText("-------------------------------------\n");
			areaTesto.appendText("Turno ("+numeroTurno+") di "+avversario.getNome()+"\n");
			areaTesto.appendText("--Punti Attuali--\n");
			areaTesto.appendText(avversario.getNome()+": "+avversario.getPuntiGiocatore()+" \t"+giocatore.getNome()+": "+giocatore.getPuntiGiocatore()+" \n");
			areaTesto.appendText("Carta attuale nella Pila: "+pilaCarte.cartaNellaPila().infoCarta()+"\n");
			areaTesto.appendText("-  -  -  -  -  -  -  -  -  -  -  -  -  -\n");
			if(isBot) {
				pescaCarta();
				for(int i=0; i<carteAvversario.size();i++) {
					if((carteAvversario.get(i).getCategoria()!=pilaCarte.cartaNellaPila().getCategoria()||carteAvversario.get(i).getColore()!=pilaCarte.cartaNellaPila().getColore())&&(carteAvversario.get(i).getColore()!=Colore.Evento1&&carteAvversario.get(i).getColore()!=Colore.Evento2)) {
						giocaCarta(i);
						break;
					} else {
						continue;
					}
					
				}
				cambiaTurno();
			}
		}
		}
		areaTesto.appendText("-------------------------------------\n");
	}
	
	public void assegnaPunti(int i) throws IOException{
		if(turnoGiocatore) {
		giocatore.aggiungiPunti(carteGiocatore.get(i).getPuntiCarta()+checkCarte(carteGiocatore.get(i),pilaCarte.cartaNellaPila()));
		punteggioGiocatore.setText(""+giocatore.getPuntiGiocatore());
	
		} else {
			
			avversario.aggiungiPunti(carteAvversario.get(i).getPuntiCarta()+checkCarte(carteAvversario.get(i),pilaCarte.cartaNellaPila()));
			punteggioAvversario.setText(""+avversario.getPuntiGiocatore());
			
		}
	}
	
	//Controllo carta giocata e carta nella pila per assgnare i punti
	public int checkCarte(Carta carta, Carta pila) throws IOException{
		int x = 0;
			if((carta.getColore()==pila.getColore())&&(carta.getCategoria()==pila.getCategoria())) {
				areaTesto.appendText("Carta nella pila: "+pila.infoCarta()+"\n");
				areaTesto.appendText("Carta giocata: "+carta.infoCarta()+", valore carta: "+carta.getPuntiCarta()+"\n");
				areaTesto.appendText("Le carte corrisopondo perfettamente!\nPeschi un evento!\n");
				areaTesto.appendText("-  -  -  -  -  -  -  -  -  -  -  -  -  -\n");
				x+=3;
				pescaCartaEvento();

			} else if(carta.getColore()==pila.getColore()) {
			
				
				areaTesto.appendText("Carta nella pila: "+pila.infoCarta()+"\n");
				areaTesto.appendText("Carta giocata: "+carta.infoCarta()+", valore carta: "+carta.getPuntiCarta()+"\n");
				areaTesto.appendText("I colori delle carte combaciano. +1 Punto extra\n");
				areaTesto.appendText("-  -  -  -  -  -  -  -  -  -  -  -  -  -\n");
				x+=1;
			} else if(carta.getCategoria()==pila.getCategoria()) {
				
				areaTesto.appendText("Carta nella pila: "+pila.infoCarta()+"\n");
				areaTesto.appendText("Carta giocata: "+carta.infoCarta()+", valore carta: "+carta.getPuntiCarta()+"\n");
				areaTesto.appendText("La categoria delle carte combacia. +2 Punti extra\n");
				areaTesto.appendText("-  -  -  -  -  -  -  -  -  -  -  -  -  -\n");
				x+=2;
			} else {
				areaTesto.appendText("Carta nella pila: "+pila.infoCarta()+"\n");
				areaTesto.appendText("Carta giocata: "+carta.infoCarta()+", valore carta: "+carta.getPuntiCarta()+"\n");
				areaTesto.appendText("Le carte non hanno ne' lo stesso colore ne' la stessa categoria oppure\n");
				areaTesto.appendText("hai giocato sopra un evento\n");
				areaTesto.appendText("Non hai guadagnato punti extra\n");
				areaTesto.appendText("-  -  -  -  -  -  -  -  -  -  -  -  -  -\n");
				
				x = 0;
			}	
		return x;
	}
	
	
	public void attivaEvento() throws IOException {
		
			areaTesto.appendText("E V E N T O\n");
			if(turnoGiocatore) {
			switch(pilaCarte.cartaNellaPila().getCategoria()) {
			case Pedone :
				if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
					areaTesto.appendText("L'unione fa la forza\n");
					areaTesto.appendText("Guadagni 5 punti\n");
					giocatore.aggiungiPunti(5);
			}else {
					areaTesto.appendText("L'unione fa la forza\n");
					areaTesto.appendText("L'avversario perde 5\n");
					avversario.aggiungiPunti(-5);
			}; break;
			case Cavallo :
				if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
					areaTesto.appendText("Prescelto\n");
					pescaDaEvento = true;
					areaTesto.appendText("Peschi una carta\n");
					pescaCarta();
					pescaDaEvento = false;
				}else {
					areaTesto.appendText("Trascelto\n");
					scartaCarta();
					areaTesto.appendText("L'avversario scarta una carta\n");
				}; break;
				
			case Torre :
				if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
					areaTesto.appendText("Fuoco sulla capitale\n");
					areaTesto.appendText("L'avversario scarta 2 carte\n");
					scartaCarta();
					scartaCarta();
				}else {
					areaTesto.appendText("Fuoco sulla reggia\n");
					areaTesto.appendText("L'avversario perde 10 punti e scarta una carta\n");
					avversario.aggiungiPunti(-10);
					scartaCarta();
				}; break;
				
			case Alfiere :
				if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
					areaTesto.appendText("Terra Promessa!\n");
					areaTesto.appendText("Scambia i tuoi punti con l'avversario\n");
					int puntiTmp = avversario.getPuntiGiocatore();
					avversario.setPunti(giocatore.getPuntiGiocatore());
					giocatore.setPunti(puntiTmp);
				}else {
					areaTesto.appendText("Incoronazione dell'Imperatore\n");
					areaTesto.appendText("Guadagni 10 punti (non piu' di 45) e peschi una carta\n");
					pescaDaEvento = true;
					if(giocatore.getPuntiGiocatore()<35) {
					giocatore.aggiungiPunti(10);}
					else if(giocatore.getPuntiGiocatore()>=45){
					giocatore.aggiungiPunti(0);} else {
						giocatore.setPunti(45);
						
					}
					pescaCarta();
					pescaDaEvento = false;
				}; break;
				
			case Regina :
				if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
					areaTesto.appendText("Seduzione\n");
					areaTesto.appendText("Ottieni punti fino ad avere gli stessi dell'avversario, poi lui perde 10 punti\n");
					giocatore.setPunti(avversario.getPuntiGiocatore());
					avversario.aggiungiPunti(-10);
				}else {
					areaTesto.appendText("Trono Vacante\n");
					areaTesto.appendText("Entrambi i giocatori tornano a 0 punti, poi tu ne ottieni 5\n");
					giocatore.setPunti(0);
					avversario.setPunti(0);
					giocatore.aggiungiPunti(5);
				}; break;
				
			case Re :
				if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
					areaTesto.appendText("Assassinio del Re\n");
					areaTesto.appendText("Il tuo avversario perde 10 punti e tu peschi una carta\n");
					pescaDaEvento = true;
					avversario.aggiungiPunti(-10);
					pescaCarta();
					pescaDaEvento = false;
				}else {
					areaTesto.appendText("Morte del Re\n");
					areaTesto.appendText("Il tuo avversario perde 20 punti\n");
					avversario.aggiungiPunti(-20);
				}; break;
				
				
			}
			} else {
				switch(pilaCarte.cartaNellaPila().getCategoria()) {
				case Pedone :
					if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
						areaTesto.appendText("L'unione fa la forza\n");
						areaTesto.appendText("Guadagni 5 punti\n");
						avversario.aggiungiPunti(5);
				}else {
						areaTesto.appendText("L'unione fa la forza\n");
						areaTesto.appendText("L'avversario perde 5\n");
						giocatore.aggiungiPunti(-5);
				}; break;
				case Cavallo :
					if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
						areaTesto.appendText("Prescelto\n");
						pescaDaEvento = true;
						areaTesto.appendText("Peschi una carta\n");
						pescaCarta();
						pescaDaEvento = false;
					}else {
						areaTesto.appendText("Trascelto\n");
						areaTesto.appendText("L'avversario scarta una carta\n");
						scartaCarta();
					}; break;
					
				case Torre :
					if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
						areaTesto.appendText("Fuoco sulla capitale\n");
						areaTesto.appendText("L'avversario scarta 2 carte\n");
						scartaCarta();
						scartaCarta();
					}else {
						areaTesto.appendText("Fuoco sulla reggia\n");
						areaTesto.appendText("L'avversario perde 10 punti e scarta una carta\n");
						giocatore.aggiungiPunti(-10);
						scartaCarta();
					}; break;
					
				case Alfiere :
					if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
						areaTesto.appendText("Terra Promessa!\n");
						areaTesto.appendText("Scambia i tuoi punti con l'avversario\n");
						int puntiTmp = giocatore.getPuntiGiocatore();
						giocatore.setPunti(avversario.getPuntiGiocatore());
						avversario.setPunti(puntiTmp);
					}else {
						areaTesto.appendText("Incoronazione dell'Imperatore\n");
						areaTesto.appendText("Guadagni 10 punti (non piu' di 45) e peschi una carta\n");
						pescaDaEvento = true;
						if(avversario.getPuntiGiocatore()<35) {
						avversario.aggiungiPunti(10);}
						else if(avversario.getPuntiGiocatore()>=45){
							avversario.aggiungiPunti(0);} else {
								avversario.setPunti(45);
						}
						pescaCarta();
						pescaDaEvento = false;
					}; break;
					
				case Regina :
					if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
						areaTesto.appendText("Seduzione\n");
						areaTesto.appendText("Ottieni punti fino ad avere gli stessi dell'avversario, poi lui perde 10 punti\n");
						avversario.setPunti(giocatore.getPuntiGiocatore());
						giocatore.aggiungiPunti(-10);
					}else {
						areaTesto.appendText("Trono Vacante\n");
						areaTesto.appendText("Entrambi i giocatori tornano a 0 punti, poi tu ne ottieni 5\n");
						avversario.setPunti(0);
						giocatore.setPunti(0);
						avversario.aggiungiPunti(5);
					}; break;
					
				case Re :
					if(pilaCarte.cartaNellaPila().getColore()==Colore.Evento1) {
						areaTesto.appendText("Assassinio del Re\n");
						areaTesto.appendText("Il tuo avversario perde 10 punti e tu peschi una carta\n");
						pescaDaEvento = true;
						giocatore.aggiungiPunti(-10);
						pescaCarta();
						pescaDaEvento = false;
					}else {
						areaTesto.appendText("Morte del Re\n");
						areaTesto.appendText("Il tuo avversario perde 20 punti\n");
						giocatore.aggiungiPunti(-20);
					}; break;
					
			}
			}
			
			punteggioGiocatore.setText(""+giocatore.getPuntiGiocatore());
			punteggioAvversario.setText(""+avversario.getPuntiGiocatore());
			checkVittoria(giocatore);
			checkVittoria(avversario);
	
	
	}
	
	
	public void scartaCarta()throws IOException {
		if(turnoGiocatore) {
			if(carteAvversario.size()>=1) {
			Random rnd = new Random();
			int i = rnd.nextInt(carteAvversario.size());
			ImageView imageView =(ImageView) manoAvversario.getChildren().get(i);
			setImmaginePila(carteAvversario.get(i));	
			areaTesto.appendText("- - - - - - - - - - - - - -\n");
			areaTesto.appendText(avversario.getNome()+" ha scartato una carta:\n");
			areaTesto.appendText(carteAvversario.get(i).infoCarta()+"\n");
			areaTesto.appendText("- - - - - - - - - - - - - -\n");
			carteAvversario.remove(i);
			imageView.setImage(null);
			ImageView iv =(ImageView) manoAvversario.getChildren().get(carteAvversario.size());
			iv.setImage(null);
			imageView.setOnMouseClicked(event ->{
				try {
					nullEvent();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});	
			iv.setOnMouseClicked(event ->{
				try {
					nullEvent();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});		
			inizializzaImmaginiCarte();
			}else {
				areaTesto.appendText(avversario.getNome()+"non può scartare altre carte\n");
			}
			} else {
				if(carteGiocatore.size()>=1) {
		Random rnd = new Random();
		int i = rnd.nextInt(carteGiocatore.size());
		ImageView imageView =(ImageView) manoGiocatore.getChildren().get(i);
		setImmaginePila(carteGiocatore.get(i));	
		areaTesto.appendText("- - - - - - - - - - - - - -\n");
		areaTesto.appendText(giocatore.getNome()+" ha scartato una carta:\n");
		areaTesto.appendText(carteGiocatore.get(i).infoCarta()+"\n");
		areaTesto.appendText("- - - - - - - - - - - - - -\n");
		carteGiocatore.remove(i);
		imageView.setImage(null);
		ImageView iv =(ImageView) manoGiocatore.getChildren().get(carteGiocatore.size());
		iv.setImage(null);
		imageView.setOnMouseClicked(event ->{
			try {
				nullEvent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});	
		iv.setOnMouseClicked(event ->{
			try {
				nullEvent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});		
		inizializzaImmaginiCarte();
				}else {
					areaTesto.appendText(giocatore.getNome()+"non può scartare altre carte\n");
				}
				}
}
	
	
	public void classifica() throws IOException{
		try{
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
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private List<String> leggiEdOrdinaClassifica() throws IOException {
		 List<String> scores = new ArrayList<>();
	        
	        try {
	        	InputStream ins = Classifica.class.getResourceAsStream("/Highscore.csv");
				BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                scores.add(line);
	            }
	            reader.close();
				ins.close();
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
	
	//controlla se il giocatore ha vinto dopo che ha giocato una carta, si è attivato un evento, o è finito il mazzo
	
	public void checkVittoria(Giocatore g) throws IOException {
		if(!isTorneo) {
		if(mazzoTerminato) {
			if(giocatore.getPuntiGiocatore()>avversario.getPuntiGiocatore()) {
				areaTesto.appendText(giocatore.getNome()+" ha totalizzato piu' punti e si aggiudica la partita\n");
				aggiungiAllaClassifica();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("MAZZO TERMINATO!");
				alert.setHeaderText("Il Vincitore è: "+giocatore.getNome());
				alert.setContentText("totalizzando "+giocatore.getPuntiGiocatore()+"Punti!");
				if(alert.showAndWait().get()==ButtonType.OK) {
					stage = (Stage)paneGioco.getScene().getWindow();
					stage.close();
					File file = new File("src/partite/"+codice+"/"+codice+"_concluso.txt");
	   				if (!file.exists()) {
	   					file.createNewFile();
	   				}
	   				FileWriter fw = new FileWriter(file.getAbsoluteFile());
	   				BufferedWriter bw = new BufferedWriter(fw);
	   				bw.write("Partita #"+codice+" CONCLUSA.\nVincitore: "+giocatore.getNome());
	   				bw.close();
	   				fw.close();
				}
			}else {
				areaTesto.appendText(avversario.getNome()+" ha totalizzato piu' punti e si aggiudica la partita\n");
				aggiungiAllaClassifica();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("MAZZO TERMINATO!");
				alert.setHeaderText("Il Vincitore è: "+avversario.getNome());
				alert.setContentText("totalizzando "+avversario.getPuntiGiocatore()+"Punti!");
				if(alert.showAndWait().get()==ButtonType.OK) {
					stage = (Stage)paneGioco.getScene().getWindow();
					stage.close();
					File file = new File("src/partite/"+codice+"/"+codice+"_concluso.txt");
	   				if (!file.exists()) {
	   					file.createNewFile();
	   				}
	   				FileWriter fw = new FileWriter(file.getAbsoluteFile());
	   				BufferedWriter bw = new BufferedWriter(fw);
	   				bw.write("Partita #"+codice+" CONCLUSA.\nVincitore: "+avversario.getNome());
	   				bw.close();
	   				fw.close();
				}
			}
		} 
		
		if(g.getPuntiGiocatore()>=50) {
			areaTesto.appendText(g.getNome()+" e' arrivato a 50 punti e si aggiudica la partiata!\n");
			aggiungiAllaClassifica();
			Alert alert = new Alert(AlertType.INFORMATION);			
			alert.setTitle("VITTORIA!");
			alert.setHeaderText("Il Vincitore è: "+g.getNome());
			alert.setContentText("totalizzando "+g.getPuntiGiocatore()+"Punti!");
			if(alert.showAndWait().get()==ButtonType.OK) {
				stage = (Stage)paneGioco.getScene().getWindow();
				stage.close();
				File file = new File("src/partite/"+codice+"/"+codice+"_concluso.txt");
   				if (!file.exists()) {
   					file.createNewFile();
   				}
   				FileWriter fw = new FileWriter(file.getAbsoluteFile());
   				BufferedWriter bw = new BufferedWriter(fw);
   				bw.write("Partita #"+codice+" CONCLUSA.\nVincitore: "+g.getNome());
   				bw.close();
   				fw.close();
			}	
		}
		
		}else {
			File file = new File("src/tornei/"+codice+"/"+codice+"_continua.txt");
			 if (!file.exists()) {	
	               file.createNewFile();
	           }
			if(mazzoTerminato) {
				if(giocatore.getPuntiGiocatore()>avversario.getPuntiGiocatore()) {
					areaTesto.appendText(giocatore.getNome()+" ha totalizzato piu' punti e si aggiudica la partita\n");
					aggiungiAllaClassifica();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("MAZZO TERMINATO!");
					alert.setHeaderText("Il Vincitore è: "+giocatore.getNome());
					alert.setContentText("totalizzando "+giocatore.getPuntiGiocatore()+"Punti!");
					if(alert.showAndWait().get()==ButtonType.OK) {
						
						FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaTorneo.fxml"));	
						root = loader.load();
						ScenaTorneoController torneoController = loader.getController();
						torneoController.continuaTorneo(codice);
						torneoController.vincitore(codice, giocatore.getNome(), nMatch);
						torneoController.salvaInfo(codice);
						stage = (Stage)paneGioco.getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.centerOnScreen();
						stage.show();
						stage.setOnCloseRequest(evv -> {
							evv.consume();
								try {
									torneoController.sospendi(stage);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							});
					}
				}else {
					areaTesto.appendText(avversario.getNome()+" ha totalizzato piu' punti e si aggiudica la partita\n");
					aggiungiAllaClassifica();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("MAZZO TERMINATO!");
					alert.setHeaderText("Il Vincitore è: "+avversario.getNome());
					alert.setContentText("totalizzando "+avversario.getPuntiGiocatore()+"Punti!");
					if(alert.showAndWait().get()==ButtonType.OK) {
						
						FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaTorneo.fxml"));	
						root = loader.load();
						ScenaTorneoController torneoController = loader.getController();
						torneoController.continuaTorneo(codice);
						torneoController.vincitore(codice, avversario.getNome(), nMatch);
						torneoController.salvaInfo(codice);
						stage = (Stage)paneGioco.getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.centerOnScreen();
						stage.show();
						stage.setOnCloseRequest(evv -> {
							evv.consume();
								try {
									torneoController.sospendi(stage);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							});
					}
				}
			} 
			
			if(g.getPuntiGiocatore()>=50) {
				areaTesto.appendText(g.getNome()+" e' arrivato a 50 punti e si aggiudica la partiata!\n");
				aggiungiAllaClassifica();
				Alert alert = new Alert(AlertType.INFORMATION);			
				alert.setTitle("VITTORIA!");
				alert.setHeaderText("Il Vincitore è: "+g.getNome());
				alert.setContentText("totalizzando "+g.getPuntiGiocatore()+"Punti!");
				if(alert.showAndWait().get()==ButtonType.OK) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaTorneo.fxml"));	
					root = loader.load();
					ScenaTorneoController torneoController = loader.getController();
					torneoController.continuaTorneo(codice);
					torneoController.vincitore(codice, g.getNome(), nMatch);
					torneoController.salvaInfo(codice);
					stage = (Stage)paneGioco.getScene().getWindow();
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
	}

	public void aggiungiAllaClassifica() throws IOException {
		Classifica classifica = new Classifica();
		if(giocatore.getPuntiGiocatore()>0)
			classifica.aggiornaClassifica(giocatore.getNome(),giocatore.getPuntiGiocatore());
		if(avversario.getPuntiGiocatore()>0)
		classifica.aggiornaClassifica(avversario.getNome(), avversario.getPuntiGiocatore());
	}
	
	public void resa(ActionEvent event) throws IOException {
		String vincitore = "";
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("RESA");
		alert.setHeaderText("Attento! Hai premuto il pulsante resa!");
		alert.setContentText("Sei sicuro di voler arrenderti?");
		File file = new File("src/tornei/"+codice+"/"+codice+"_continua.txt");
		 if (!file.exists()) {	
               file.createNewFile();
           }
		if(alert.showAndWait().get()==ButtonType.OK) {
			if(turnoGiocatore) {	
				vincitore = avversario.getNome();
			} else {
				vincitore = giocatore.getNome();
				}
			aggiungiAllaClassifica();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaTorneo.fxml"));	
			root = loader.load();
			ScenaTorneoController torneoController = loader.getController();
			torneoController.continuaTorneo(codice);
			torneoController.vincitore(codice, vincitore, nMatch);
			torneoController.salvaInfo(codice);
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			
		}else {
			
		}
	}
	
	public void pausaEsci(Stage stage) throws IOException {
		if(isTorneo==false) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CONCLUDI PARTITA");
		alert.setHeaderText("Stai per chiudere l'applicazione e la partita in corso");
		alert.setContentText("Vuoi salvare ed uscire? Potrai continuare in un secondo momento con il codice partita, dalla schermata del login.");
		if(alert.showAndWait().get()==ButtonType.OK) {
			try {
				String folder = "src/partite/"+codice;
				File f = new File(folder);
				f.mkdirs();
				File file = new File("src/partite/"+codice+"/"+codice+"_info.txt");
				 if (!file.exists()) {	
		                file.createNewFile();
		            }
				 FileWriter fw = new FileWriter(file.getAbsoluteFile());
				 BufferedWriter bw = new BufferedWriter(fw);
				 bw.write(giocatore.getNome()+"\n");
				 bw.write(giocatore.getPuntiGiocatore()+"\n");
				 bw.write(avversario.getNome()+"\n");
				 bw.write(avversario.getPuntiGiocatore()+"\n");
				 bw.write(numeroTurno+"\n");
				 bw.write(azioniConcesse+"\n");
				 bw.write(puoiPescare+"\n");
				 bw.write(isTorneo+"\n");
				 bw.close();
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			//----------------------------------------------
			try {
				File file = new File("src/partite/"+codice+"/"+codice+"_mazzo.txt");
				 if (!file.exists()) {	
		                file.createNewFile();
		            }
				 FileWriter fw = new FileWriter(file.getAbsoluteFile());
				 BufferedWriter bw = new BufferedWriter(fw);
				 for(Carta carta : mazzoCarte.getMazzo()) {
					 bw.write(carta.getColore()+","+carta.getCategoria()+"\n");
					
				 }
				 bw.close();
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			//----------------------------------------------
			try {
				File file = new File("src/partite/"+codice+"/"+codice+"_mazzoEventi.txt");
				 if (!file.exists()) {	
		                file.createNewFile();
		            }
				 FileWriter fw = new FileWriter(file.getAbsoluteFile());
				 BufferedWriter bw = new BufferedWriter(fw);
				 for(Carta carta : mazzoEventi.getMazzo()) {
					 bw.write(carta.getColore()+","+carta.getCategoria()+"\n");
					
				 }
				 bw.close();
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			//----------------------------------------------
			try {
				File file = new File("src/partite/"+codice+"/"+codice+"_pila.txt");
				 if (!file.exists()) {	
		                file.createNewFile();
		            }
				 FileWriter fw = new FileWriter(file.getAbsoluteFile());
				 BufferedWriter bw = new BufferedWriter(fw);
				 for(Carta carta : pilaCarte.getPila()) {
					 bw.write(carta.getColore()+","+carta.getCategoria()+"\n");
					 
				 }
				 bw.close();
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			//----------------------------------------------
			try {
				File file = new File("src/partite/"+codice+"/"+codice+"_manoGiocatore.txt");
				 if (!file.exists()) {	
		                file.createNewFile();
		            }
				 FileWriter fw = new FileWriter(file.getAbsoluteFile());
				 BufferedWriter bw = new BufferedWriter(fw);
				 for(Carta carta : carteGiocatore) {
					 bw.write(carta.getColore()+","+carta.getCategoria()+"\n");
					 
				 }
				 bw.close();
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			//----------------------------------------------
			try {
				File file = new File("src/partite/"+codice+"/"+codice+"_manoAvversario.txt");
				 if (!file.exists()) {	
		                file.createNewFile();
		            }
				 FileWriter fw = new FileWriter(file.getAbsoluteFile());
				 BufferedWriter bw = new BufferedWriter(fw);
				 for(Carta carta : carteAvversario) {
					 bw.write(carta.getColore()+","+carta.getCategoria()+"\n");
					 
				 }
				 bw.close();
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			//----------------------------------------------
			stage = (Stage)paneGioco.getScene().getWindow();
			stage.close();
			
		} else {
			stage.close();
		}
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("ATTENZIONE");
			alert.setHeaderText("Questa è una partita da torneo!");
			alert.setContentText("Le partite di un torneo non possono essere messe in pausa!\nPuoi concludere la partita solo tramite il pulsante RESA.");
			if(alert.showAndWait().get()==ButtonType.OK){
				alert.close();
			} else {

				alert.close();
			}
		}
	}
	
	
	


	public void loadGame(String codiceFile)throws IOException, InputMismatchException {
		try {
			Scanner scf = new Scanner(new File("src/partite/"+codiceFile+"/"+codiceFile+"_info.txt"));
			while(scf.hasNextLine()) {
				setCodice(codiceFile);
				displayNomeGiocatore(scf.nextLine());
				giocatore.setPunti(Integer.parseInt(scf.nextLine()));	
				punteggioGiocatore.setText(""+giocatore.getPuntiGiocatore());
				displayNomeAvversario(scf.nextLine());	
				avversario.setPunti(Integer.parseInt(scf.nextLine()));	
				punteggioAvversario.setText(""+avversario.getPuntiGiocatore());
				numeroTurno = Integer.parseInt(scf.nextLine());
				azioniConcesse = Boolean.parseBoolean(scf.nextLine());
				puoiPescare =Boolean.parseBoolean(scf.nextLine());		
				isTorneo =Boolean.parseBoolean(scf.nextLine());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		//----------------------------------------------
		try {
			Scanner scf = new Scanner(new File("src/partite/"+codiceFile+"/"+codiceFile+"_mazzo.txt"));
			mazzoCarte = new Mazzo();
			while(scf.hasNextLine()) {
				caricaMazzoDaFile(scf.nextLine());
			}
			scf.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//----------------------------------------------
				try {
					Scanner scf = new Scanner(new File("src/partite/"+codiceFile+"/"+codiceFile+"_mazzoEventi.txt"));
					mazzoEventi = new Mazzo();
					while(scf.hasNextLine()) {
						caricaMazzoEventiDaFile(scf.nextLine());
					}
					scf.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
		//----------------------------------------------
		try {
			Scanner scf = new Scanner(new File("src/partite/"+codiceFile+"/"+codiceFile+"_pila.txt"));
			pilaCarte = new PilaDegliScarti();
			while(scf.hasNextLine()) {
				caricaPilaDaFile(scf.nextLine());
			}
			scf.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//----------------------------------------------
		try {
			Scanner scf = new Scanner(new File("src/partite/"+codiceFile+"/"+codiceFile+"_manoGiocatore.txt"));
			carteGiocatore = new ArrayList<Carta>();
			while(scf.hasNextLine()) {
			caricaManoGiocatoreDaFile(scf.nextLine());
			}
			scf.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//----------------------------------------------
		try {
			Scanner scf = new Scanner(new File("src/partite/"+codiceFile+"/"+codiceFile+"_manoAvversario.txt"));
			carteAvversario = new ArrayList<Carta>();
			while(scf.hasNextLine()) {
			caricaManoAvversarioDaFile(scf.nextLine());
			}
			scf.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//----------------------------------------------
		riprendiPartita();
		
	}
	
	public void caricaMazzoDaFile(String line) {
		 String[] info = line.split(",");
		 Carta carta = new Carta(stringToColore(info[0]),stringToCategoria(info[1]));
		 mazzoCarte.add(carta);
	}

	public void caricaMazzoEventiDaFile(String line) {
		 String[] info = line.split(",");
		 Carta carta = new Carta(stringToColore(info[0]),stringToCategoria(info[1]));
		 mazzoEventi.add(carta);
	}
	
	public void caricaPilaDaFile(String line) {
		 String[] info = line.split(",");
		 Carta carta = new Carta(stringToColore(info[0]),stringToCategoria(info[1]));
		 pilaCarte.add(carta);
	}
	
	public void caricaManoGiocatoreDaFile(String line) {
		 String[] info = line.split(",");
		 Carta carta = new Carta(stringToColore(info[0]),stringToCategoria(info[1]));
		 carteGiocatore.add(carta);
		 
	}
	public void caricaManoAvversarioDaFile(String line) {
		 String[] info = line.split(",");
		 Carta carta = new Carta(stringToColore(info[0]),stringToCategoria(info[1]));
		 carteAvversario.add(carta);
		 
	}
	
	public Colore stringToColore(String line) {
		switch(line) {
		case "Bianco" : return Colore.Bianco; 
		case "Nero" : return Colore.Nero; 
		case "Evento1" : return Colore.Evento1;
		case "Evento2" : return Colore.Evento2; 
		}
		return null;
	}
	
	public Categoria stringToCategoria(String line) {
		switch(line) {
		case "Pedone" : return Categoria.Pedone; 
		case "Alfiere" : return Categoria.Alfiere; 
		case "Cavallo" : return Categoria.Cavallo; 
		case "Torre" : return Categoria.Torre; 
		case "Regina" : return Categoria.Regina; 
		case "Re" : return Categoria.Re; 
		}
		return null;
	}
	
	
	public void riprendiPartita() throws IOException {
		if(turnoGiocatore) {
			areaTesto.appendText("Turno ("+numeroTurno+") di: "+giocatore.getNome()+"\n");
			} else { 
				areaTesto.appendText("Turno ("+numeroTurno+") di: "+avversario.getNome()+"\n");
			}
		inizializzaImmaginiCarte();
		immaginePila();
		immagineMazzo();
		mazzoCarte.mischiaMazzo();
		mazzoEventi.mischiaMazzo();
		areaTesto.setEditable(false);
		areaTesto.setFont(Font.font("Georgia", 12));
		
	}
	
}

