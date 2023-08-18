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
	
	public Gioco(Giocatore giocatore1, Giocatore giocatore2){
		giocatore = new Giocatore [2];
		giocatore[0] = giocatore1;
		giocatore[1] = giocatore2;
		mazzo = new Mazzo();
		mazzo.popolaMazzo();
		mazzo.mischiaMazzo();
		pila = new PilaDegliScarti();
	
	/*	
		if(giocatore[1].isBot()) {
			giocatore[0] = new Giocatore(giocatore1);
			giocatore[1] = new Giocatore("BOT") ;
		} else {
			giocatore[0] = new Giocatore(giocatore1);
			giocatore[1] = new Giocatore(giocatore2) ;
		}
		*/
	}
	
	/*
	public void inizializzaGioco() {
		
		//crea la pila, prima carta
		pila.aggiungiAllaPila(mazzo.getCarta(0));
		mazzo.togliCartaDalMazzo(0);
		
		//dai carte ai giocatori
		for(int i = 0; i<2; i++) {
			for(int j=0; j<4;j++) {
			giocatore[i].aggiungiCarta(mazzo.getCarta(0));
			mazzo.togliCartaDalMazzo(0);
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
		giocatore.aggiungiCarta(mazzo.getCarta(0));
		mazzo.togliCartaDalMazzo(0);
		
	}
	*/
	
	public void inizioTurno() {
		//pescaCarta(giocatoreCorrente);
	}
	
	/*

	public void GiocaCarta(Carta cartaSelezionata){
		
		
		Boolean scelta = false;
		if(scelta == true) {
			giocatore.scartaCarta(n);
			pila.aggiungiAllaPila(cartaSelezionata);
			
			giocatore.aggiungiPunti(cartaSelezionata.assegnaPunti());
			
			if(pila.getUltimaCarta().getColore() == cartaSelezionata.getColore()){
				giocatore.aggiungiPunti(1);
			} else {
				giocatore.aggiungiPunti(0);
			}
			
			if(pila.getUltimaCarta().getCategoria()==cartaSelezionata.getCategoria()) {
				giocatore.aggiungiPunti(2);
			}else {
				giocatore.aggiungiPunti(0);
			}
			
			if(pila.getUltimaCarta().getCategoria()==cartaSelezionata.getCategoria()) {
				evento();
			}
			turnoGiocatore = !turnoGiocatore;
		} else {
			System.out.println("Scegli una carta da giocare");
		}	
	}
	*/
	
	public void isGameOver() {
		if(gameOver==true) {
			Highscore leadboard = new Highscore();
			leadboard.aggiungiAFile(giocatore);
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
	
	
	public void evento() {
		/*
		Random randomEvento = new Random();
		int evento = randomEvento.nextInt(25)+1;
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
			
		case 21:  System.out.println("Evento! Entrambi i giocatori pescano 1 carta!");
			pescaCarta(giocatoreCorrente);
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
		*/
	}
	
	
	
}
