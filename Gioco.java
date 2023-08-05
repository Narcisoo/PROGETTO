package application;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.control.DialogPane;
public class Gioco {
	
	private int giocatoreCorrente;
	private String[] idGiocatori;
	private Mazzo mazzo;
	private MazzoEventi mazzoEventi;
	private ArrayList<ArrayList<Carta>> manoGiocatore;
	private ArrayList<Carta> carteGiocate;
	private Carta.Colore coloreCorrente;
	private Carta.Valore valoreCorrente;
	
	boolean ordineTurni;
	
	public Gioco(String[] idGiocatori) {
		
		mazzo = new Mazzo();
		mazzo.mescola();
		carteGiocate= new ArrayList<Carta>();
		this.idGiocatori = idGiocatori;
		giocatoreCorrente = 0;
		ordineTurni = false;
		
		manoGiocatore = new ArrayList<ArrayList<Carta>>();
		
		for(int i = 0; i<this.idGiocatori.length;i++) {
			ArrayList<Carta> mano = new ArrayList<Carta>(Arrays.asList(mazzo.pescaCarte(7)));
			manoGiocatore.add(mano);
		}
	}
	
	public void start(Gioco gioco) {
		Carta carta = mazzo.pescaCarta();
		coloreCorrente = carta.getColore();
		valoreCorrente = carta.getValore();
		
		carteGiocate.add(carta);
	}
	
	public Carta getUltimaCarta() {
		
		return new Carta(coloreCorrente, valoreCorrente);
	}
	
	public Image getImmagineUltimaCarta() {
		
		return new Image(coloreCorrente+"-"+valoreCorrente+".png");
	}
	
	public boolean isGiocoConcluso(){
		
		for(String giocatore: this.idGiocatori) {
			if(manoVuota(giocatore)) {
				return true;
			}	
		}
		return false;
	}
	
	public String getGiocatoreCorrente() {
		return this.idGiocatori[this.giocatoreCorrente];
	}
	
	public String getGiocatorePrecedente(int i) {
		int index = this.giocatoreCorrente-i;
		if(index == -1) {
			index = idGiocatori.length-1;
		
		}
		return this.idGiocatori[index];
	}
	
	public String[] getGiocatori() {
		return idGiocatori;
	}
	
	public ArrayList<Carta> getManoGiocatore(String idGiocatore){
		int index = Arrays.asList(idGiocatori).indexOf(idGiocatore);
		return manoGiocatore.get(index);
	}
	
	public int getNumeroCarteManoGiocatore(String idGiocatore) {
		return getManoGiocatore(idGiocatore).size();
	}
	
	public Carta getCarta(String idGiocatore, int scelta) {
		ArrayList<Carta> manoGiocatore = getManoGiocatore(idGiocatore);
		return manoGiocatore.get(scelta);
	}
	
	public boolean manoVuota(String idGiocatore) {
		return getManoGiocatore(idGiocatore).isEmpty();
	}
	
	public boolean cartaValida(Carta carta) {
		return carta.getColore() == coloreCorrente || carta.getValore() == valoreCorrente;
	}
	
	public void controllaTurnoGiocatore(String idGiocatore) throws InvalidPlayerTurnException {
		if(this.idGiocatori[this.giocatoreCorrente]!=idGiocatore) {
			throw new InvalidPlayerTurnException("non e' il turno di"+idGiocatore, idGiocatore);
		}
	}
	
	public void daiCarte(String idGiocatore) throws InvalidPlayerTurnException{
		controllaTurnoGiocatore(idGiocatore);
		
		if(mazzo.isEmpty()) {
			mazzo.sostituisciMazzo(carteGiocate);
			mazzo.mescola();
		}
		
		getManoGiocatore(idGiocatore).add(mazzo.pescaCarta());
		if(ordineTurni == false) {
			giocatoreCorrente = (giocatoreCorrente+1)% idGiocatori.length;
		} else {
			if(ordineTurni == true) {
				giocatoreCorrente = (giocatoreCorrente-1)% idGiocatori.length;
				if(giocatoreCorrente == -1) {
					giocatoreCorrente = idGiocatori.length-1;
				}
			}
		}
	}
	
	
	public void setColoreCarta(Carta.Colore colore) {
		coloreCorrente = colore;
	}
	
	
	public void giocaCarta(String idGiocatore, Carta carta, Carta.Colore colore) throws InvalidColorSubmissionException,InvalidSubmissionException,InvalidPlayerTurnException{
		controllaTurnoGiocatore(idGiocatore);
		
		ArrayList<Carta> manoG = getManoGiocatore(idGiocatore);
		if(!cartaValida(carta)) {
			if(carta.getColore()!=coloreCorrente) {
				Label messaggio = new Label("Carta non giusta");
				messaggio.setFont(new Font("Arial", 48));
				
			}
		}
		
		coloreCorrente = carta.getColore();
		valoreCorrente = carta.getValore();
		carteGiocate.add(carta);
		
		if(ordineTurni == false) {
			giocatoreCorrente = (giocatoreCorrente+1)% idGiocatori.length;
		} else {
			if(ordineTurni == true) {
				giocatoreCorrente = (giocatoreCorrente-1)% idGiocatori.length;
				if(giocatoreCorrente == -1) {
					giocatoreCorrente = idGiocatori.length-1;
				}
			}
	
		}
		
		
		
		
}

}
class InvalidPlayerTurnException extends Exception{
	
	String idGiocatore;
	public InvalidPlayerTurnException(String messaggio, String idGiocatore) {
		super(messaggio);
		this.idGiocatore = idGiocatore;
		
	}
	public String getIdGiocatore() {
		return this.idGiocatore;
	}
}

class InvalidColorSubmissionException extends Exception{
	
	private Carta.Colore giusto;
	private Carta.Colore sbagliato;
	
	public InvalidColorSubmissionException(String messaggio, Carta.Colore giusto, Carta.Colore sbagliato) {
		
		this.giusto = giusto;
		this.sbagliato = sbagliato;
		
	}
}

class InvalidSubmissionException extends Exception{
	
	private Carta.Valore giusto;
	private Carta.Valore sbagliato;
	
	public InvalidSubmissionException(String messaggio, Carta.Valore giusto, Carta.Valore sbagliato) {
		
		this.giusto = giusto;
		this.sbagliato = sbagliato;
	
}
}

