package application;

import java.util.ArrayList;
import java.util.Collections;;

public class Mazzo {

	private ArrayList<Carta> mazzo;
	private int carteNelMazzo;
	
	
	public Mazzo() {
		mazzo =new ArrayList<Carta>();		
	}
	
	public void popolaMazzo() {
		//popola di pedoni
		for(int i = 0; i<12; i++) {
		if (i%2==0) {
			mazzo.add(new Carta(Colore.Bianco, Categoria.Pedone));
		} else {
			mazzo.add(new Carta(Colore.Nero, Categoria.Pedone));
		}
		}
		
		//popola alfieri
		for(int i = 12; i<20; i++) {
			if (i%2==0) {
				mazzo.add(new Carta(Colore.Bianco, Categoria.Alfiere));
			} else {
				mazzo.add(new Carta(Colore.Nero, Categoria.Alfiere));
			}
			}
		
		//popola cavalli
		for(int i = 20; i<28; i++) {
			if (i%2==0) {
				mazzo.add(new Carta(Colore.Bianco, Categoria.Cavallo));
			} else {
				mazzo.add(new Carta(Colore.Nero, Categoria.Cavallo));
			}
			}
		
		//popola torri
		for(int i = 28; i<34; i++) {
			if (i%2==0) {
				mazzo.add(new Carta(Colore.Bianco, Categoria.Torre));
			} else {
				mazzo.add(new Carta(Colore.Nero, Categoria.Torre));
			}
			}
		
		//popola regine
			for(int i = 34; i<38; i++) {
				if (i%2==0) {
					mazzo.add(new Carta(Colore.Bianco, Categoria.Regina));
				} else {				
					mazzo.add(new Carta(Colore.Nero, Categoria.Regina));
				}
				}
		
				
			//popola re
			for(int i = 38; i<40; i++) {
				if (i%2==0) {
					mazzo.add(new Carta(Colore.Bianco, Categoria.Re));
				} else {
					mazzo.add(new Carta(Colore.Nero, Categoria.Re));
				}
				}
			carteNelMazzo = mazzo.size();
	}
	
	//aggiungerlo nel metodo di Gioco??
	public void togliCartaDalMazzo() {
		 mazzo.remove(carteNelMazzo-1);

	}
	
	public void mischiaMazzo() {
		Collections.shuffle(mazzo);
	}
	
	public Carta getCarta(int n){
		return mazzo.get(n);
	}
	
	public int getSize() {
		return mazzo.size();
	}
}
