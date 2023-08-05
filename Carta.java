package application;

public class Carta {
	

	enum Colore{
		Rosso, Blu, Giallo, Verde;
		
		private static final Colore[] colori = Colore.values();
		public static Colore getColore(int i) {
			return Colore.colori[i];
		}
	}
		
		 enum Valore {
			Cittadino, Fante, Cavaliere, Prete, Ladro, Mercante, Ambasciatore, Principe, Re, Regina;
			private static final Valore[] valori = Valore.values();
			public static Valore getValore(int i) {
				return Valore.valori[i];
			}
		 }
	
	private final Colore colore;
	private final Valore valore;
	
	public Carta(final Colore colore, final Valore valore) {
		this.colore=colore;
		this.valore=valore;
	}
	
	
	public Colore getColore(){
		
		return this.colore;
	}
	
	public Valore getValore() {
		return this.valore;
	}
	
	public String toString() {
		return colore+" "+valore;
	}
	
}
