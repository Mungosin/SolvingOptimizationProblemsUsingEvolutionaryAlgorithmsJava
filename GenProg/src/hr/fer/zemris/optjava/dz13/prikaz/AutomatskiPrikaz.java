package hr.fer.zemris.optjava.dz13.prikaz;

public class AutomatskiPrikaz implements Runnable{
	private Panel pan;
	
	public AutomatskiPrikaz(Panel pan){
		this.pan = pan;
	}
	@Override
	public void run() {
			pan.automatski();
	}

}
