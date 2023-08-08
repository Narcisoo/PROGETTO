package application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
enum Colore{
	
	Bianco, Nero;
	
}


enum Categoria{
	
	Pedone, Alfiere, Cavallo, Torre, Regina, Re;
	
}


public class Carta {

	private Colore coloreCarta;
	private Categoria categoriCarta;
	protected String testoCarta;

	
	public Carta(Colore colore, Categoria categoria){	
		this.coloreCarta = colore;
		this.categoriCarta = categoria;
	}
	
	
	
	public Colore getColore() {
		return this.coloreCarta;
	}
	
	public Categoria getCategoria(){	
		return this.categoriCarta;
	}
	
	public int assegnaPunti() {
		int n = 0;
		switch(getCategoria()) {
		case Pedone: n =1 ; break;
		case Alfiere: n = 3; break;
		case Cavallo: n = 3; break;
		case Torre: n = 5; break;
		case Regina: n = 7; break;
		case Re: n = 10; break;
		}
		return n;
		}
	
	//nome immagine carta
	public String toString() {
		return this.categoriCarta+""+this.coloreCarta+".png";
	}
	
	public void setImmagine() {
		String nomeImmagine = toString();
		Image immagine = new Image(nomeImmagine);
		ImageView immView = new ImageView(immagine);
	}
}
