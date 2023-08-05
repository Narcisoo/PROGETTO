package application;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.*;

public class Mazzo {
	
	private Carta[] carte;
	private int carteNelMazzo;
	
	
	public Mazzo() {
		
		carte = new Carta[104];
	}
	
	public void reset() {
		
		Carta.Colore[] colori = Carta.Colore.values();
		carteNelMazzo = 0;
		
		for(int i=0; i< colori.length-1; i++) {
			
			Carta.Colore colore = colori[i];
			
			for(int c=0; c<8; c++) {
			carte[carteNelMazzo++]=new Carta(colore, Carta.Valore.getValore(0));
			}
			
			for(int j=1; j<9;j++) {
				carte[carteNelMazzo++]=new Carta(colore, Carta.Valore.getValore(j));
				carte[carteNelMazzo++]=new Carta(colore, Carta.Valore.getValore(j));
			}
		}
	}


	public void sostituisciMazzo(ArrayList<Carta> carte) {
		this.carte = carte.toArray(new Carta[carte.size()]);
		this.carteNelMazzo = this.carte.length;
	}
	
	public boolean isEmpty() {
		return carteNelMazzo==0;
	}
	
	public void mescola() {
		
		int n = carte.length;
		Random random = new Random();
		
		for(int i =0; i<carte.length;i++) {
			int valoreRandom = random.nextInt(n-1);
			Carta cartaRandom = carte[valoreRandom];
			carte[valoreRandom] = carte[i];
			carte[i] = cartaRandom;
		}
		
	}
	
	public Carta pescaCarta() throws IllegalArgumentException{
		if(isEmpty()) {
			throw new IllegalArgumentException("Non puoi pescare carte, il mazzo è vuoto");
		}
		return carte[--carteNelMazzo];
	}

	
	public Image pescaCartaImmaginer() throws IllegalArgumentException{
		if(isEmpty()) {
			throw new IllegalArgumentException("Non puoi pescare carte, il mazzo è vuoto");
		}
		return new Image(carte[--carteNelMazzo].toString()+".png");
		
	}
	public Carta[] pescaCarte(int n) throws IllegalArgumentException {
		if(n<0) {
			throw new IllegalArgumentException(" Devi pescare un numero positivo di carte");
		}
		
		if(n>carteNelMazzo) {
			throw new IllegalArgumentException("Non puoi più pescare");
		}
		Carta[] drw = new Carta[n];
		
		for(int i =0; i<n;i++) {
			drw[i]=carte[--carteNelMazzo];
		}
		return drw;
	}

}