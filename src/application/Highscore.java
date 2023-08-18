package application;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.Scanner;
import java.util.TreeMap;



public class Highscore {
	
	private  String nomeFile = "src\\\\Highscore.csv";
	private String nomeGiocatore;
	private int punteggio;
	SortedMap<String, Integer> leaderboard;
	
	public Highscore() {
		
	}
	
	public  void openLeaderboard() {
		try {
			Scanner scf = new Scanner(new File(nomeFile));
			while (scf.hasNextLine()) {
				parseLine(scf.nextLine());
				sortLeaderboard();
			}
			scf.close();
		} catch (FileNotFoundException e) {
			System.out.println("file inesistente");
			e.printStackTrace();
		}
	}
	
	public void checkHighscore(Giocatore[] giocatori) {
		boolean check = false;
		int tmp;
		for(int i=0;i<giocatori.length;i++) {
			if(leaderboard.containsKey(giocatori[i].getNome())) {
				tmp = leaderboard.get(giocatori[i].getNome())+(giocatori[i].getPuntiGiocatore());
				leaderboard.put(giocatori[i].getNome(), tmp);
			} else {
				leaderboard.put(giocatori[i].getNome(), giocatori[i].getPuntiGiocatore());
			}
		}
		
	}
	
	public void aggiungiAFile(Giocatore[] giocatori) {
		try {
			Scanner scf = new Scanner(new File(nomeFile));
			while (scf.hasNextLine()) {
				parseLine(scf.nextLine());
			}
			checkHighscore(giocatori);
			scf.close();
		} catch (FileNotFoundException e) {
			System.out.println("file inesistente");
			e.printStackTrace();
		}
		
		try {
			PrintWriter prf = new PrintWriter(nomeFile);
			prf.println(leaderboard.toString());
			prf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sortLeaderboard(){
		for (Integer punti : leaderboard.values()) {
			System.out.println(leaderboard.toString());
		}
	}
	
	public  void parseLine(String line) {
		leaderboard = new TreeMap<String, Integer>();
		Scanner scl = new Scanner(line);
		scl.useDelimiter("\\s*,\\s*");
		nomeGiocatore = scl.next();
		punteggio = scl.nextInt();
		leaderboard.put(nomeGiocatore, punteggio);
		
	}
	
	public String getNomeGiocatore() {
		return this.nomeGiocatore;
	}
	
	public int getPunteggioGiocatore() {
		return this.punteggio;
	}

	
}
