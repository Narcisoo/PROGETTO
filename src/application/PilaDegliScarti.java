package application;

import java.util.ArrayList;

public class PilaDegliScarti {

	private ArrayList<Carta> pilaDegliScarti;
	private int carteNellaPila;
	
	public PilaDegliScarti() {
		pilaDegliScarti = new ArrayList<Carta>();
	}
	
	public void aggiungiAllaPila(Carta carta) {
		pilaDegliScarti.add(carta);
		carteNellaPila++;
	}
	
	public Carta getUltimaCarta(){
		return pilaDegliScarti.get(carteNellaPila-2);
	}
	
	
}
