package hr.fer.zemris.optjava.dz13.mapa;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import hr.fer.zemris.optjava.dz13.solution.Pozicija;

public class Mapa {
	public VrijednostiMape[][] mapa;
	public int sirina;
	public int visina;
	
	private Mapa(){
		
	}
	
	public Mapa(String putanjaDoTxtSMapom){
		BufferedReader fileInput = null;
		
		try {
			FileReader fileReader = new FileReader(new File(putanjaDoTxtSMapom));
			
			fileInput = new BufferedReader(fileReader);
			String dim = fileInput.readLine();
			String[] token = dim.split("x");
			this.sirina = Integer.parseInt(token[0]);
			this.visina = Integer.parseInt(token[1]);
			this.mapa = new VrijednostiMape[this.sirina][this.visina];
			
			for(int i=0;i<this.sirina;i++){
				String linija = fileInput.readLine();
				for(int j=0;j<this.visina;j++){
					mapa[j][i] = linija.charAt(j)=='1' ? VrijednostiMape.Hrana : VrijednostiMape.Prazno;
				}
			}
			
			
		} catch (IOException ignorable) {
		} finally {
			if (fileInput != null) {
				try {
					fileInput.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public Mapa (Mapa map){
		this.sirina = map.sirina;
		this.visina = map.visina;
		this.mapa = new VrijednostiMape[this.sirina][this.visina];
		for(int i=0;i<map.sirina;i++){
			for(int j=0;j<map.visina;j++){
				mapa[i][j] = map.mapa[i][j];
			}
		}
	}
	
	public VrijednostiMape vrijednostNaPoziciji(Pozicija poz){
		
		return mapa[poz.x][poz.y];
	}
	
	public VrijednostiMape vrijednostiIspredPozicije(Pozicija poz){ 
		
		switch(poz.smjer){
		case Desno:
			return mapa[(poz.x + 1) % sirina][poz.y];
		case Lijevo:
			return mapa[(poz.x -1+sirina) % sirina][poz.y];
		case Gore:
			return mapa[poz.x][(poz.y -1+ visina) % visina];
		case Dole:
			return  mapa[poz.x][(poz.y +1 ) % visina];//koordinate rastu za visinu prema dole
		default: 
				return null;
		}
	}
	
	public Mapa copyOf(){
		return new Mapa(this);
	}
	
	public void nacrtajSe(Graphics g, int sirina, int visina){
		for(int i=0;i<this.sirina;i++){
			for(int j=0;j<this.visina;j++){
				if(this.mapa[i][j] == VrijednostiMape.Prazno){
					g.setColor(Color.yellow);
					g.fillRect(i*sirina, j*visina, sirina, visina);
				}else{

					g.setColor(Color.blue);
					g.fillRect(i*sirina, j*visina, sirina, visina);
				}
				
				g.setColor(Color.black);
				g.drawRect(i*sirina, j*visina, sirina, visina);
				
			}
		}
	}
}
