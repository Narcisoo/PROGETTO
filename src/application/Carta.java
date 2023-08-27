package application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
enum Colore{
	
	Bianco, Nero, Evento1, Evento2;
	
}


enum Categoria{
	
	Pedone, Alfiere, Cavallo, Torre, Regina, Re;
	
}


public class Carta {

	private Colore coloreCarta;
	private Categoria categoriaCarta;
	private int puntiCarta;
	
	public Carta(Colore colore, Categoria categoria){	
		this.coloreCarta = colore;
		this.categoriaCarta = categoria;
		assegnaPunti();
	}
	
	
	
	public Colore getColore() {
		return this.coloreCarta;
	}
	
	public Categoria getCategoria(){	
		return this.categoriaCarta;
	}
	
	public void assegnaPunti() {
		switch(getCategoria()) {
		case Pedone: this.puntiCarta =1 ; break;
		case Alfiere: this.puntiCarta = 3; break;
		case Cavallo: this.puntiCarta = 3; break;
		case Torre: this.puntiCarta = 5; break;
		case Regina: this.puntiCarta = 7; break;
		case Re: this.puntiCarta = 10; break;
		}
	}
	
	
	
	public String infoCarta() {
		if(this.coloreCarta!=Colore.Bianco&&this.coloreCarta!=Colore.Nero) {
			return "Evento, "+this.categoriaCarta;
		}else {
		return ""+this.categoriaCarta+" "+this.coloreCarta;
		}
	}
	
	public String infoCartaFile() {
		return ""+this.categoriaCarta+" "+this.coloreCarta;
		
	}
	
	
	public String immagineCarta() {
		return "/immagini/"+this.categoriaCarta+""+this.coloreCarta+".png";
	}
	
	public String retro() {
		return "/immagini/Retro.png";
	}
	
	public Image getImageCarte() {
		String pathName = immagineCarta();
		return new Image(Carta.class.getResourceAsStream(pathName));
	}
	public Image getImageRetro() {
		String pathName = retro();
		return new Image(Carta.class.getResourceAsStream(pathName));
	}
	public Image getImageCartaGiocata() {
		String pathName = retro();
		return new Image(Carta.class.getResourceAsStream(null));
	}
	
	public int getPuntiCarta() {
		return this.puntiCarta;
	}
	
	public void setColore(Colore colore){
		this.coloreCarta = colore;
	}
	public void setCategoria(Categoria categoria) {
		this.categoriaCarta = categoria;
	}
}
