package hr.fer.zemris.optjava.dz13.solution;


import java.util.LinkedList;

import hr.fer.zemris.optjava.dz13.Operators;
import hr.fer.zemris.optjava.dz13.mapa.Mapa;
import hr.fer.zemris.optjava.dz13.mapa.VrijednostiMape;

public class Solution implements Comparable<Solution>{
	public double fitness;
	public int maxKoraka;
	public Pozicija poz;
	public Cvor root;
	public int maxDubina;
	public Mapa map;
	
	private int tempKorak;
	private Mapa tempMap;
	private Pozicija tempPoz;
	private int tempPozicijaCvora;
	public LinkedList<Pozicija> mojePozicije;

	public Solution(int maxKoraka, Pozicija poz, Mapa map, int maxDubina, boolean full){
		this.maxKoraka = maxKoraka;
		this.poz = poz.copyOf();
		Operators temp = new Operators();
		this.maxDubina = maxDubina;
		this.map = map;
		Operators op = new Operators();
		
		if(full){
			root = op.izgradiStablo(full, maxDubina);
		}else {
			root = op.izgradiStablo(full, maxDubina);
		}
		
	}
	
	public Solution(int maxKoraka, Pozicija poz, Mapa map, int maxDubina){
		this.maxKoraka = maxKoraka;
		this.poz = poz.copyOf();
		Operators temp = new Operators();
		this.maxDubina = maxDubina;
		this.map = map;
	}
	
	public Solution(Solution sol){
		this.fitness = sol.fitness;
		this.maxDubina = sol.maxDubina;
		this.maxKoraka = sol.maxKoraka;
		this.root = sol.root.copyOf();
		this.poz = sol.poz.copyOf();
		this.map = sol.map;
	}
	
	
	private void izvedi(Cvor cvor) {		
		if(tempKorak <= 0){
			return;
		}
		switch(cvor.vrijednost){
		case IfFoodAhead:

			if(tempMap.vrijednostiIspredPozicije(poz) == VrijednostiMape.Hrana){
				izvedi(cvor.listaDjece.get(0));
			}else {
				izvedi(cvor.listaDjece.get(1));
			}
			return;
			
		case Prog2:

			izvedi(cvor.listaDjece.get(0));
			izvedi(cvor.listaDjece.get(1));
			return;
			
		case Prog3:

			izvedi(cvor.listaDjece.get(0));
			izvedi(cvor.listaDjece.get(1));
			izvedi(cvor.listaDjece.get(2));
			return;
		case Move:
			tempKorak--;
			boolean flag=false;
			if(tempMap.vrijednostiIspredPozicije(poz) == VrijednostiMape.Hrana){
				this.fitness++;
				flag = true;
			}
			switch(poz.smjer){
			case Desno:				
				poz.x = (poz.x + 1) % this.tempMap.sirina;
				if(flag){
					tempMap.mapa[poz.x][poz.y] = VrijednostiMape.Prazno;
				}
				return;
			case Lijevo:
				poz.x = (poz.x -1+this.tempMap.sirina) % this.tempMap.sirina;
				if(flag){

					tempMap.mapa[poz.x][poz.y] = VrijednostiMape.Prazno;
				}
				return;
			case Gore:
				poz.y = (poz.y -1+ this.tempMap.visina) % this.tempMap.visina;
				if(flag){

					tempMap.mapa[poz.x][poz.y] = VrijednostiMape.Prazno;
				}
				return;
			case Dole:
				poz.y =(poz.y +1 ) % this.tempMap.visina; //koordinate rastu za visinu prema dole
				if(flag){

					tempMap.mapa[poz.x][poz.y] = VrijednostiMape.Prazno;
				}
				return; 
			}
			
		
		case Left:
			tempKorak--;
			
			switch(poz.smjer){
			case Dole:
				poz.smjer = Smjer.Desno;
				break;
			case Desno:
				poz.smjer = Smjer.Gore;
				break;
				
			case Gore:
				poz.smjer = Smjer.Lijevo;
				break;
				
			case Lijevo:
				poz.smjer = Smjer.Dole;
				break;				
			}
			return;
			
		case Right:
			tempKorak--;

			switch(poz.smjer){
			case Gore:
				poz.smjer = Smjer.Desno;
				break;
			case Lijevo:
				poz.smjer = Smjer.Gore;
				break;
				
			case Dole:
				poz.smjer = Smjer.Lijevo;
				break;
				
			case Desno:
				poz.smjer = Smjer.Dole;
				break;				
			}
			return;
			
		default :
				throw new IllegalArgumentException("Vrijednost u cvoru nije postojeca");
		}
	}
	
	public void evaluate(){
		this.fitness = 0;
		tempMap = map.copyOf();
		tempKorak = maxKoraka-1;
		tempPoz = this.poz.copyOf();
		while(tempKorak > 0){
			izvedi(root);
		}
		poz = tempPoz;		
	}	

	public void zamijeniCvor(int brojCvora, Cvor noviCvor){
		Cvor trazeniCvor = vratiCvorSBrojem(this.root, brojCvora, 0);
		if(trazeniCvor == null){
			throw new IllegalArgumentException("Ne postoji cvor kojeg treba zamijeniti");
		}
		trazeniCvor.vrijednost = noviCvor.vrijednost;
		trazeniCvor.maxDjece = noviCvor.maxDjece;
		trazeniCvor.djeceIspodMene = noviCvor.djeceIspodMene;
		LinkedList<Cvor> djeca = new LinkedList<>();
		if(noviCvor.listaDjece == null || noviCvor.listaDjece.isEmpty()){

			trazeniCvor.listaDjece = djeca;
			return;
		}
		for(Cvor temp : noviCvor.listaDjece){
			Cvor kopijaTemp = temp.copyOf();
			kopijaTemp.azurirajDubinu(trazeniCvor.dubina+1);
			djeca.add(kopijaTemp);
		}
		trazeniCvor.listaDjece = djeca;
		
	}
	public void zamijeniCvor(int brojCvora, Cvor noviCvor, Cvor zaZamijenu){
		Cvor trazeniCvor = zaZamijenu;
		if(trazeniCvor == null){
			throw new IllegalArgumentException("Ne postoji cvor kojeg treba zamijeniti");
		}
		trazeniCvor.vrijednost = noviCvor.vrijednost;
		trazeniCvor.maxDjece = noviCvor.maxDjece;
		trazeniCvor.djeceIspodMene = noviCvor.djeceIspodMene;
		LinkedList<Cvor> djeca = new LinkedList<>();
		if(noviCvor.listaDjece == null || noviCvor.listaDjece.isEmpty()){

			trazeniCvor.listaDjece = djeca;
			return;
		}
		for(Cvor temp : noviCvor.listaDjece){
			Cvor kopijaTemp = temp.copyOf();
			kopijaTemp.azurirajDubinu(trazeniCvor.dubina+1);
			djeca.add(kopijaTemp);
		}
		trazeniCvor.listaDjece = djeca;
		
	}	
	
	public Cvor vratiCvorSBrojem(Cvor trenutni, int brojCvora, int pocetniBrojCvora){
		tempPozicijaCvora = pocetniBrojCvora;
		
			
		
		if(pocetniBrojCvora==brojCvora){
			return trenutni;			
		}else if(trenutni.listaDjece.isEmpty()){
			return null;
		}
		
		for(Cvor temp : trenutni.listaDjece){
			Cvor rijesenje = vratiCvorSBrojem(temp, brojCvora, tempPozicijaCvora+1);
			if(rijesenje != null){
				return rijesenje;
			}
		}		
		return null;		
	}
	
	public void izbrojiVrijednostiDjeceUCvorovima(){
		root.azurirajBrojDjece();
	}

	@Override
	public int compareTo(Solution o) {
		return -Double.compare(this.fitness, o.fitness);
	}

	public void izvediIZapisuj(Cvor cvor){
		if(tempKorak <= 0){
			return;
		}

		switch(cvor.vrijednost){
		case IfFoodAhead:

			if(tempMap.vrijednostiIspredPozicije(poz) == VrijednostiMape.Hrana){
				izvediIZapisuj(cvor.listaDjece.get(0));
			}else {
				izvediIZapisuj(cvor.listaDjece.get(1));
			}
			return;
			
		case Prog2:
			izvediIZapisuj(cvor.listaDjece.get(0));
			izvediIZapisuj(cvor.listaDjece.get(1));
			return;
			
		case Prog3:

			izvediIZapisuj(cvor.listaDjece.get(0));
			izvediIZapisuj(cvor.listaDjece.get(1));
			izvediIZapisuj(cvor.listaDjece.get(2));
			return;
		case Move:
			tempKorak--;
			boolean flag=false;
			if(tempMap.vrijednostiIspredPozicije(poz) == VrijednostiMape.Hrana){
				this.fitness++;
				flag = true;
			}
			switch(poz.smjer){
			case Desno:				
				poz.x = (poz.x + 1) % this.tempMap.sirina;
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				if(flag){
					tempMap.mapa[poz.x][poz.y] = VrijednostiMape.Prazno;
				}
				return;
			case Lijevo:
				poz.x = (poz.x -1+this.tempMap.sirina) % this.tempMap.sirina;
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				if(flag){

					tempMap.mapa[poz.x][poz.y] = VrijednostiMape.Prazno;
				}
				return;
			case Gore:
				poz.y = (poz.y -1+ this.tempMap.visina) % this.tempMap.visina;
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				if(flag){

					tempMap.mapa[poz.x][poz.y] = VrijednostiMape.Prazno;
				}
				return;
			case Dole:
				poz.y =(poz.y +1 ) % this.tempMap.visina; //koordinate rastu za visinu prema dole
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				if(flag){

					tempMap.mapa[poz.x][poz.y] = VrijednostiMape.Prazno;
				}
				return; 
			}
			
		
		case Left:
			tempKorak--;
			
			switch(poz.smjer){
			case Dole:
				poz.smjer = Smjer.Desno;
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				break;
			case Desno:
				poz.smjer = Smjer.Gore;
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				break;
				
			case Gore:
				poz.smjer = Smjer.Lijevo;
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				break;
				
			case Lijevo:
				poz.smjer = Smjer.Dole;
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				break;				
			}
			return;
			
		case Right:
			tempKorak--;
			
			switch(poz.smjer){
			case Gore:
				poz.smjer = Smjer.Desno;
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				break;
			case Lijevo:
				poz.smjer = Smjer.Gore;
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				break;
				
			case Dole:
				poz.smjer = Smjer.Lijevo;
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				break;
				
			case Desno:
				poz.smjer = Smjer.Dole;
				this.mojePozicije.add(new Pozicija(poz.x, poz.y, poz.smjer));
				break;				
			}
			return;
			
		default :
				throw new IllegalArgumentException("Vrijednost u cvoru nije postojeca");
		}
	}
	
	public LinkedList<Pozicija> dohvatiPokrete() {

		this.mojePozicije = new LinkedList<>();
		this.fitness = 0;
		tempMap = map.copyOf();
		tempKorak = maxKoraka-1;
		tempPoz = this.poz.copyOf();
		while(tempKorak > 0){
			this.izvediIZapisuj(root);
		}
		return mojePozicije;
	}
	
	@Override
	public String toString(){
		return this.root.toString();
	}
}
