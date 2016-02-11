package hr.fer.zemris.optjava.dz13.prikaz;

import hr.fer.zemris.optjava.dz13.mapa.Mapa;
import hr.fer.zemris.optjava.dz13.mapa.VrijednostiMape;
import hr.fer.zemris.optjava.dz13.solution.Pozicija;
import hr.fer.zemris.optjava.dz13.solution.Smjer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Panel extends JPanel{
	public Mapa map;
	public LinkedList<Pozicija> pozicije;
	public int pozicijePrikaza;
	public volatile boolean zaustavi = false;
	public boolean crtajNormalno = false;

	public Panel(Mapa map, LinkedList<Pozicija> pozicije, int pozicijaPrikaza) {
		this.map = map;
		this.pozicije = pozicije;
		this.pozicijePrikaza = 0;
	}
	
	public void automatski(){
		this.crtajNormalno = true;
		while(pozicijePrikaza<pozicije.size() && !zaustavi){
			this.repaint();
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void sljedeciKorak(){
		this.crtajNormalno = true;
		if(pozicijePrikaza<pozicije.size()){
			this.repaint();;
		}
	}
	

	public void paintComponent(Graphics g){
		if(!crtajNormalno){

			g.clearRect(0, 0, 1000, 1000);
			map.nacrtajSe(g, 16, 16);

			g.setColor(Color.red);
			g.fillRect(0, 0, 16, 16);
			g.setColor(Color.BLACK);
			g.fillRect(0+12, 0*16+6, 4, 4);
			if(map.vrijednostNaPoziciji(new Pozicija(0,0,Smjer.Desno)) == VrijednostiMape.Hrana){
				map.mapa[0][0] = VrijednostiMape.Prazno;
			}
		}else {
			g.clearRect(0, 0, 1000, 1000);
			map.nacrtajSe(g, 16, 16);

			g.setColor(Color.red);
			Pozicija mojaPoz = pozicije.get(pozicijePrikaza);
			pozicijePrikaza++;
			g.fillRect(mojaPoz.x*16, mojaPoz.y*16, 16, 16);
			g.setColor(Color.BLACK);
			switch(mojaPoz.smjer){
			case Desno:

				g.fillRect(mojaPoz.x*16+12, mojaPoz.y*16+6, 4, 4);
				break;
			case Lijevo:
				
				g.fillRect(mojaPoz.x*16, mojaPoz.y*16+6, 4, 4);
				break;
			case Gore:

				g.fillRect(mojaPoz.x*16+6, mojaPoz.y*16, 4, 4);
				break;
			case Dole:

				g.fillRect(mojaPoz.x*16+6, mojaPoz.y*16+12, 4, 4);
				break;
			}
			if(map.vrijednostNaPoziciji(mojaPoz) == VrijednostiMape.Hrana){
				map.mapa[mojaPoz.x][mojaPoz.y] = VrijednostiMape.Prazno;
			}
		}
	}
}
