package hr.fer.zemris.optjava.dz13.solution;

public class Pozicija {
	public int x;
	public int y;
	public Smjer smjer;
	
	
	public Pozicija(int x, int y, Smjer pocetni){
		this.x = x;
		this.y = y;
		this.smjer = pocetni;
	}
	
	public Pozicija copyOf() {
		return new Pozicija(this.x, this.y, this.smjer);
	}
}
