package application;
public class Giocatore {

	String nomeGiocatore;
	private int puntiGiocatore;
	
	public Giocatore(String nome) {
		this.nomeGiocatore = nome;
		this.puntiGiocatore = 0;
	
	}

	
	public int getPuntiGiocatore() {
		
		return this.puntiGiocatore;
	}
	
	public void aggiungiPunti(int n) {
		
		puntiGiocatore = puntiGiocatore+n;
		if(puntiGiocatore<0) {
			puntiGiocatore = 0;
		}
	}
	
	public void setPunti(int n) {
		puntiGiocatore = n;
	}
	
	public String getNome() {
		return this.nomeGiocatore;
	}
	
	public void setNome(String nome) {
		this.nomeGiocatore = nome;
	}
	
	
}
