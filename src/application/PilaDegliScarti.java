package application;

import java.util.ArrayList;

public class PilaDegliScarti {

	private ArrayList<Carta> pilaDegliScarti;
	public PilaDegliScarti() {
		pilaDegliScarti = new ArrayList<Carta>();
	}

	public Carta cartaNellaPila(){
		if (pilaDegliScarti.size()>=1)
			return pilaDegliScarti.get(pilaDegliScarti.size()-1);
		else
		return null;
	}
	
	public ArrayList<Carta> getPila() {
		return pilaDegliScarti;
	}

	public int getSize() {
		return pilaDegliScarti.size();
	}
	
	public void add(Carta carta) {
		pilaDegliScarti.add(carta);
	}
	}

