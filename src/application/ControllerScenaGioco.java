package application;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ControllerScenaGioco{
	@FXML
	private Button terminaSessioneButton;
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
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	//mostra nome giocatore, preso dal log in
	public void displayNomeGiocatore(String username) {
		nomeGiocatore.setText(username);
		nomeGiocatore.setTextFill(Color.GREEN);
		giocatore = new Giocatore(username);
		punteggioGiocatore.setText(""+giocatore.getPuntiGiocatore()+" Punti");
	}
	
	//mostra nome avversario, preso dal log in
	public void displayNomeAvversario(String username) {
		nomeAvversario.setText(username);
		nomeAvversario.setTextFill(Color.RED);
		avversario = new Giocatore(username);
		punteggioAvversario.setText(""+avversario.getPuntiGiocatore()+" Punti");
	}

	//inizia la partita/gioco. Da le carte ai giocatori ed richiama i metodi per impostare le immagini
	public void startGame() {
		areaTesto.setEditable(false);
		mazzoEventi = new Mazzo();
		mazzoEventi.popolaMazzoEventi();
		mazzoEventi.mischiaMazzo();
		mazzoCarte = new Mazzo();
		mazzoCarte.popolaMazzo();
		mazzoCarte.mischiaMazzo();
		pilaCarte = new PilaDegliScarti();
		carteGiocatore = new ArrayList<>(5);
		carteAvversario = new ArrayList<>();
		numeroTurno = 1;
		//Dai carte al giocatore
		for(int i=0; i<manoGiocatore.getChildren().size()-3;i++) {
	
			Carta carta = mazzoCarte.daiCartaInCima();
			carteGiocatore.add(new Carta(carta.getColore(),carta.getCategoria()));
		
		}
		//dai carte all'avversario
		for(int i=0; i<manoAvversario.getChildren().size()-4;i++) {
			Carta carta = mazzoCarte.daiCartaInCima();
			carteAvversario.add(new Carta(carta.getColore(),carta.getCategoria()));
		}
		inizializzaImmaginiCarte();
		immaginePila();
		immagineMazzo();
		areaTesto.setFont(Font.font("Georgia", 12));
		areaTesto.appendText("Turno ("+numeroTurno+") di: "+giocatore.getNome()+"\n");
	}
	
	//imposta le immagini delle carte nella mano del giocatore, e il retro in quelle dell'avversario
	public void inizializzaImmaginiCarte() {
		//giocatore
		if(turnoGiocatore) {
		for(int i=0; i<carteGiocatore.size();i++) {
			ImageView imageView = (ImageView) manoGiocatore.getChildren().get(i);
			imageView.setImage(carteGiocatore.get(i).getImageCarte());
			imageView.setUserData(i);
			imageView.setOnMouseClicked(event ->{
				giocaCarta((int)imageView.getUserData());
			});
			
		}
		} else {
			for(int i=0; i<carteGiocatore.size();i++) {
				ImageView imageView = (ImageView) manoGiocatore.getChildren().get(i);
				imageView.setImage(new Image(Carta.class.getResourceAsStream("/immagini/Retro.png")));
				imageView.setUserData(i);
				imageView.setOnMouseClicked(event ->{
					nullEvent();
				});	
				
		}
		}	
		// i<carteAvversario.size()	
		//avversario	
		if(turnoGiocatore){
		for(int i=0;i<carteAvversario.size();i++) {
			ImageView imageView = (ImageView) manoAvversario.getChildren().get(i);
			imageView.setImage(new Image(Carta.class.getResourceAsStream("/immagini/Retro.png")));
			imageView.setUserData(i);	
			imageView.setOnMouseClicked(event ->{
				nullEvent();
			});
		}
		} else {
			for(int i=0;i<carteAvversario.size();i++) {
				ImageView imageView = (ImageView) manoAvversario.getChildren().get(i);
				imageView.setImage(carteAvversario.get(i).getImageCarte());
				imageView.setUserData(i);
				imageView.setOnMouseClicked(event ->{
					giocaCarta((int)imageView.getUserData());
				});}
				
		
			}
		}
	
	//imposta l'immagine (e la carta) nella pila degli scarti quando una carta viene giocata
	public void immaginePila() {
		pilaCarte.add(mazzoCarte.getCarta());
		pila.setImage(mazzoCarte.daiCartaInCima().getImageCarte());
		pila.setOnMouseClicked(event ->{
			areaTesto.appendText(">>Ultima carta nella pila: "+pilaCarte.cartaNellaPila().infoCarta()+"<<\n");
			areaTesto.appendText(">>Numero di carte nella pila: "+pilaCarte.getSize()+"<<\n");
			
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
			pescaCarta();
			areaTesto.appendText(">>Carte rimaste nel mazzo: "+mazzoCarte.getSize()+"<<\n");
		});
	}
	
	//metodo per giocare una carta
	public void giocaCarta(int i) {
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
				nullEvent();
			});	
			iv.setOnMouseClicked(event ->{
				nullEvent();
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
						nullEvent();
					});	
					iv.setOnMouseClicked(event ->{
						nullEvent();
					});	
				inizializzaImmaginiCarte();
				checkVittoria(avversario);
				//cambiaTurno();
					}
		}}
		azioniConcesse = false;}
	}
	
	//gioca evento 
	public void giocaEvento(int i) {
		if(turnoGiocatore) {
			ImageView imageView =(ImageView) manoGiocatore.getChildren().get(i);
			setImmaginePila(carteGiocatore.get(i));
			carteGiocatore.remove(i);
			imageView.setImage(null);
			ImageView iv =(ImageView) manoGiocatore.getChildren().get(carteGiocatore.size());
			iv.setImage(null);
			imageView.setOnMouseClicked(event ->{
				nullEvent();
			});	
			iv.setOnMouseClicked(event ->{
				nullEvent();
			});		
			inizializzaImmaginiCarte();
			attivaEvento();
			checkVittoria(giocatore);
			
		} else {
			ImageView imageView =(ImageView) manoAvversario.getChildren().get(i);
			setImmaginePila(carteAvversario.get(i));
			carteAvversario.remove(i);
			imageView.setImage(null);
			ImageView iv =(ImageView) manoAvversario.getChildren().get(carteAvversario.size());
			iv.setImage(null);
			imageView.setOnMouseClicked(event ->{
				nullEvent();
			});	
			iv.setOnMouseClicked(event ->{
				nullEvent();
			});		
			inizializzaImmaginiCarte();
			attivaEvento();
			checkVittoria(avversario);
		}
		azioniConcesse = false;
	}
	
	
	//metodo per pescare la carta
	public void pescaCarta() {
		if(mazzoCarte.getSize()==1) {
			mazzo.setImage(null);
			mazzo.setOnMouseClicked(event ->{
				nullEvent();
			});
		}
		if(mazzoCarte.getSize()==0) {
			areaTesto.appendText("!! IL mazzo è terminato, il vincitore è colui che ha più punti !!\n");
			mazzoTerminato = true;
			checkVittoria(giocatore);
		}
		if(puoiPescare) {
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
		if(pescaDaEvento) {
			pescaDaEvento = false;
		}else {
			puoiPescare=false;
		}
	}

	public void pescaCartaEvento() {
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
	
	public void pausaEsci(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenaLogIn.fxml"));	
		root = loader.load();
		ScenaLogInController controllerLogIn = loader.getController();
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
		//javafx.application.Platform.exit();
	}
	
	public void nullEvent() {
		
	}
	
	public void cambiaTurno() {
		puoiPescare=true;
		numeroTurno++;
		if(azioniConcesse) {
			areaTesto.appendText("-------------------------------------\n");
			areaTesto.appendText("DEVI ANCORA GIOCARE UNA CARTA, NON PUOI PASSARE!\n");
			areaTesto.appendText("-  -  -  -  -  -  -  -  -  -  -  -  -  -\n");
		} else {
		turnoGiocatore = !turnoGiocatore;
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
		}
		inizializzaImmaginiCarte();
		//pescaCarta();
		azioniConcesse=true;
		}
		areaTesto.appendText("-------------------------------------\n");
	}
	
	public void assegnaPunti(int i) {
		if(turnoGiocatore) {
		giocatore.aggiungiPunti(carteGiocatore.get(i).getPuntiCarta()+checkCarte(carteGiocatore.get(i),pilaCarte.cartaNellaPila()));
		punteggioGiocatore.setText(""+giocatore.getPuntiGiocatore());
	
		} else {
			
			avversario.aggiungiPunti(carteAvversario.get(i).getPuntiCarta()+checkCarte(carteAvversario.get(i),pilaCarte.cartaNellaPila()));
			punteggioAvversario.setText(""+avversario.getPuntiGiocatore());
			
		}
	}
	
	//Controllo carta giocata e carta nella pila per assgnare i punti
	public int checkCarte(Carta carta, Carta pila) {
		int x = 0;
			if((carta.getColore()==pila.getColore())&&(carta.getCategoria()==pila.getCategoria())) {
				areaTesto.appendText("Carta nella pila: "+pila.infoCarta()+"\n");
				areaTesto.appendText("Carta giocata: "+carta.infoCarta()+", valore carta: "+carta.getPuntiCarta()+"\n");
				areaTesto.appendText("Le carte corrisopondo perfettamente!\nPeschi un evento!\n");
				areaTesto.appendText("-  -  -  -  -  -  -  -  -  -  -  -  -  -\n");
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
	
	
	public void attivaEvento() {
		
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
					puoiPescare=true;
					pescaDaEvento = true;
					areaTesto.appendText("Peschi una carta\n");
					pescaCarta();
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
					puoiPescare=true;
					pescaDaEvento = true;
					if(giocatore.getPuntiGiocatore()<35) {
					giocatore.aggiungiPunti(10);}
					else if(giocatore.getPuntiGiocatore()>=45){
					giocatore.aggiungiPunti(0);} else {
						giocatore.setPunti(45);
						pescaCarta();
					}
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
					puoiPescare=true;
					pescaDaEvento = true;
					avversario.aggiungiPunti(-10);
					pescaCarta();
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
						puoiPescare=true;
						areaTesto.appendText("Peschi una carta\n");
						pescaCarta();
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
						puoiPescare=true;
						pescaDaEvento = true;
						if(avversario.getPuntiGiocatore()<35) {
						avversario.aggiungiPunti(10);}
						else if(avversario.getPuntiGiocatore()>=45){
							avversario.aggiungiPunti(0);} else {
								avversario.setPunti(45);
						}
						pescaCarta();
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
						puoiPescare=true;
						giocatore.aggiungiPunti(-10);
						pescaCarta();
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
	
	
	public void scartaCarta() {
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
				nullEvent();
			});	
			iv.setOnMouseClicked(event ->{
				nullEvent();
			});		
			inizializzaImmaginiCarte();
			}} else {
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
			nullEvent();
		});	
		iv.setOnMouseClicked(event ->{
			nullEvent();
		});		
		inizializzaImmaginiCarte();
				}}
}
	
	
	public void classifica() {
		//System.out.println("classifica");
	}
	
	//controlla se il giocatore ha vinto dopo che ha giocato una carta, si è attivato un evento, o è finito il mazzo
	
public void checkVittoria(Giocatore g) {
		if(mazzoTerminato) {
			if(g.getPuntiGiocatore()>avversario.getPuntiGiocatore()) {
				areaTesto.appendText(giocatore.getNome()+" ha totalizzato piu' punti e si aggiudica la partita\n");
				System.exit(0);
			}else {
				areaTesto.appendText(avversario.getNome()+" ha totalizzato piu' punti e si aggiudica la partita\n");
				System.exit(0);
			}
		}
		
		if(g.getPuntiGiocatore()>=50) {
			areaTesto.appendText(g.getNome()+" e' arrivato a 50 punti e si aggiudica la partiata!\n");
			javafx.application.Platform.exit();
		}
	}
}

