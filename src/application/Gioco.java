package application;

import java.util.ArrayList;
import java.util.Random;

public class Gioco {

	private Mazzo mazzo;
	private PilaDegliScarti pila;
	private Giocatore[] giocatore;
	private boolean turnoGiocatore;
	private Giocatore giocatoreCorrente;
	private Giocatore avversario;
	private boolean gameOver = false;
	
	public Gioco(String giocatore1, String giocatore2){
		giocatore = new Giocatore [2];
		mazzo = new Mazzo();
		mazzo.popolaMazzo();
		mazzo.mischiaMazzo();
		pila = new PilaDegliScarti();
	
		
		if(giocatore[1].isBot()) {
			giocatore[0] = new Giocatore(giocatore1);
			giocatore[1] = new Giocatore("BOT") ;
		} else {
			giocatore[0] = new Giocatore(giocatore1);
			giocatore[1] = new Giocatore(giocatore2) ;
		}
	}
	
	
	public void inizializzaGioco() {
		
		//crea la pila, prima carta
		pila.aggiungiAllaPila(mazzo.getCarta(mazzo.getSize()-1));
		mazzo.togliCartaDalMazzo();
		
		//dai carte ai giocatori
		for(int i = 0; i<2; i++) {
			for(int j=0; j<5;j++) {
			giocatore[i].aggiungiCarta(mazzo.getCarta(mazzo.getSize()-1));
			mazzo.togliCartaDalMazzo();
			}
		}
		Random random = new Random();
		turnoGiocatore = random.nextBoolean();
		if (turnoGiocatore==true) {
			giocatoreCorrente = giocatore[0];
			avversario = giocatore[1];
		} else {
			giocatoreCorrente = giocatore[1];
			avversario = giocatore[0];
		}
		
	}
	
	public void pescaCarta(Giocatore giocatore) {
		giocatore.aggiungiCarta(mazzo.getCarta(mazzo.getSize()-1));
		mazzo.togliCartaDalMazzo();
		
	}
	
	public void inizioTurno() {
		pescaCarta(giocatoreCorrente);
	}
	
	public void GiocaCarta(Giocatore giocatore, int n){
		
		Carta cartaDaGiocare = giocatore.getCarta(n);
		System.out.println("Vuoi giocare questa carta?");
		//possibile bottone per fare confermare la carta selezionata;
		Boolean scelta = false;
		if(scelta == true) {
			giocatore.scartaCarta(n);
			pila.aggiungiAllaPila(cartaDaGiocare);
			
			giocatore.aggiungiPunti(cartaDaGiocare.assegnaPunti());
			
			if(pila.getUltimaCarta().getColore() == cartaDaGiocare.getColore()){
				giocatore.aggiungiPunti(1);
			} else {
				giocatore.aggiungiPunti(0);
			}
			
			if(pila.getUltimaCarta().getCategoria()==cartaDaGiocare.getCategoria()) {
				giocatore.aggiungiPunti(2);
				evento();
			}else {
				giocatore.aggiungiPunti(0);
			}
			
			turnoGiocatore = !turnoGiocatore;
		} else {
			System.out.println("Scegli una carta da giocare");
		}	
	}
	
	public void isGameOver() {
		if(gameOver==true) {
			System.exit(0);
		}
		
		if(giocatoreCorrente.getPuntiGiocatore()==30) {
			System.out.println(giocatoreCorrente.getNome()+" ha vinto la partita!");
			gameOver = true;
			isGameOver();
		} else if(avversario.getPuntiGiocatore()==30){
			System.out.println(avversario.getNome()+" ha vinto la partita!");
			gameOver = true;
			isGameOver();
		}
		
		if(mazzo.getSize()==0) {
			if(giocatoreCorrente.getPuntiGiocatore()>avversario.getPuntiGiocatore()) {
				System.out.println(giocatoreCorrente.getNome()+" ha vinto la partita!");
				gameOver = true;
				isGameOver();
			} else {
				System.out.println(avversario.getNome()+" ha vinto la partita!");
				gameOver = true;
				isGameOver();
			}
		}
		
	}
	
	//i System.out.println() sono un indicazione per aggiungere possibili pannelli di testo (o popUp)
	public void evento() {
		Random randomEvento = new Random();
		int evento = randomEvento.nextInt(20)+1;
		switch(evento) {
		case 1: System.out.println("Evento! Pesca una carta!");
		pescaCarta(giocatoreCorrente); break;
		
		case 2: System.out.println("Evento! Pesca una carta!");
		pescaCarta(giocatoreCorrente); break;
		
		case 3: System.out.println("Evento! L' avversario pesca una carta!");
		pescaCarta(avversario); break;
		
		case 4: System.out.println("Evento! L' avversario pesca una carta!");
			pescaCarta(avversario); break;
			
		case 5: System.out.println("Evento! Guadagni 1 Punto!");
			giocatoreCorrente.aggiungiPunti(1); break;
			
		case 6: System.out.println("Evento! Guadagni 1 Punto!");
			giocatoreCorrente.aggiungiPunti(1); break;
		
		case 7: System.out.println("Evento! L'avversario Guadagna 1 Punto!");
			avversario.aggiungiPunti(1); break;
			
		case 8: System.out.println("Evento! L'avversario Guadagna 1 Punto!");
			avversario.aggiungiPunti(1); break;
		
		case 9: System.out.println("Evento! Guadagni 2 Punti!");
			giocatoreCorrente.aggiungiPunti(2); break;
			
		case 10: System.out.println("Evento! Guadagni 2 Punti!");
			giocatoreCorrente.aggiungiPunti(2); break;
		
		case 11: System.out.println("Evento! L'avversario guadagna 2 Punti!");
			avversario.aggiungiPunti(2); break;
			
		case 12: System.out.println("Evento! L'avversario guadagna 2 Punti!");
			avversario.aggiungiPunti(2); break;
			
		case 13: System.out.println("Evento! Perdi 2 Punti!");
			if(giocatoreCorrente.getPuntiGiocatore()>=2) {
				giocatoreCorrente.aggiungiPunti(-2); break;
			} else {
				giocatoreCorrente.setPunti(0); break;
			}
		
		case 14: System.out.println("Evento! Perdi 2 Punti!");
			if(giocatoreCorrente.getPuntiGiocatore()>=2) {
				giocatoreCorrente.aggiungiPunti(-2); break;
			} else {
				giocatoreCorrente.setPunti(0); break;
			}
	
		case 15: System.out.println("Evento! L'avversario perde 2 Punti!");
			if(avversario.getPuntiGiocatore()>=2) {
				avversario.aggiungiPunti(-2); break;
			} else {
				avversario.setPunti(0); break;
			}
	
		case 16: System.out.println("Evento! L'avversario perde 2 Punti!");
			if(avversario.getPuntiGiocatore()>=2) {
				avversario.aggiungiPunti(-2); break;
			}else {
				avversario.setPunti(0); break;
			}
	
		case 17: System.out.println("Evento! Guadagni 5 Punti!");
			giocatoreCorrente.aggiungiPunti(5); break;
			
		case 18: System.out.println("Evento! L'avversario guadagna 5 Punti!");
			avversario.aggiungiPunti(5); break;
		
		case 19: System.out.println("Evento! Perdi 5 Punti!");
			if(giocatoreCorrente.getPuntiGiocatore()>=5) {
				giocatoreCorrente.aggiungiPunti(-5); break;
			} else {
				giocatoreCorrente.setPunti(0); break;
			}
			
		case 20: System.out.println("Evento! L'avversario perde 5 Punti!");
			if(avversario.getPuntiGiocatore()>=5) {
				avversario.aggiungiPunti(-5); break;
			}else {
				avversario.setPunti(0); break;
			}
			
		case 21:  System.out.println("MEGA EVENTO! Entrambi i giocatori pescano 2 carte!");
			pescaCarta(giocatoreCorrente);
			pescaCarta(giocatoreCorrente);
			pescaCarta(avversario);
			pescaCarta(avversario); break;
		
		case 22: System.out.println("MEGA EVENTO! PERDI TUTTI I TUOI PUNTI!");
			giocatoreCorrente.setPunti(0);
			
		case 23: System.out.println("MEGA EVENTO! L'AVVERSARIO DIMEZZA I SUOI PUNTI!");
			avversario.setPunti((int)(avversario.getPuntiGiocatore()/2));
			
		case 24: System.out.println("MEGA EVENTO! VITTORIA AUTOMATICA!");
			giocatoreCorrente.setPunti(30);
			
		case 25: System.out.println("MEGA EVENTO! IL TUO AVVERSARIO E' AD UN PASSO DALLA VITTORIA!");
			avversario.setPunti(25);
			
		}
	}
	
}
