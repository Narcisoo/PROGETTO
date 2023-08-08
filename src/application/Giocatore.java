package application;

import java.util.ArrayList;

public class Giocatore {

	String nomeGiocatore;
	private int puntiGiocatore;
	private ArrayList<Carta> mano;
	private int carteInMano;
	public boolean bot = false;
	

	
	public Giocatore(String nome) {
		this.nomeGiocatore = nome;
		this.puntiGiocatore = 0;
		mano = new ArrayList<Carta>(5);
		carteInMano = mano.size();
	
	}
	
	public void aggiungiCarta(Carta c) {
		mano.add(c);
	}
	
	
	public int getPuntiGiocatore() {
		
		return this.puntiGiocatore;
	}
	
	public void aggiungiPunti(int n) {
		
		puntiGiocatore += n;
	}
	
	public void setPunti(int n) {
		puntiGiocatore = n;
	}
	
	public Carta getCarta(int n) {
		return mano.get(n);
	}

	public void setBot() {
		bot = true;
	}
	
	public boolean isBot() {
		return bot;
	}
	
	public void scartaCarta(int n) {
		mano.remove(getCarta(n));
		carteInMano = mano.size();
	}
	
	public int getCarteInMano() {
		return carteInMano;
	}
	
	public String getNome() {
		return this.nomeGiocatore;
	}
	
	
	
}
