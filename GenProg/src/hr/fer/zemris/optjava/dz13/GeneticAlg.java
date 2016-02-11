package hr.fer.zemris.optjava.dz13;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz13.mapa.Mapa;
import hr.fer.zemris.optjava.dz13.solution.Cvor;
import hr.fer.zemris.optjava.dz13.solution.Pozicija;
import hr.fer.zemris.optjava.dz13.solution.Solution;

public class GeneticAlg {
	public int maxKoraka;
	public Pozicija pocetna;
	public int maxPop;
	public int maxGeneracija;
	public int maxDubina;
	public Mapa map;
	public LinkedList<Solution> populacija;
	public Operators op;
	public double minFitness;
	public String putanjaDoOutputDatoteke;
	Random rand = new Random();
	
	public GeneticAlg(int maxKorak, Pozicija pocetna, int maxGeneracija, double minFitness, int maxDubina, Mapa mapa, int maxPop, String putanjaDoOutputDatoteke){
		this.maxDubina = maxDubina;
		this.maxKoraka = maxKorak;
		this.map = mapa;
		this.minFitness = minFitness;
		this.maxGeneracija = maxGeneracija;
		this.pocetna = pocetna;
		this.maxPop = maxPop;
		this.populacija = new LinkedList<Solution>();
		this.op = new Operators();
		this.putanjaDoOutputDatoteke = putanjaDoOutputDatoteke;
		
		
		initializaRamped();
	}
	
	private void initializaRamped() {

		int brojRazlicitihDubina = 1;
		if(this.maxDubina!=1){
			brojRazlicitihDubina = Constants.pocetnaDubina-1;
		}
		if(brojRazlicitihDubina !=1){
			int maxDubina = Constants.pocetnaDubina;
			int clanovaPoDubini = (this.maxPop/brojRazlicitihDubina);
			for(int j=0;j<brojRazlicitihDubina;j++){
				
				for(int i=0;i<clanovaPoDubini && this.maxPop>this.populacija.size();i++){
					Solution sol = new Solution(this.maxKoraka, pocetna, map, this.maxDubina);
					if(i<this.maxPop/2){
						sol.root = op.izgradiStablo(true, maxDubina);
					}else {
						sol.root = op.izgradiStablo(false, maxDubina);
					}
					sol.evaluate();
					this.populacija.add(sol);
				}
				maxDubina--;
				if(maxDubina <0){
					System.out.println("kaj");
					System.exit(0);
				}
			}
			
			for(int i=0;i<(this.maxPop-this.populacija.size());i++){
				Solution sol = new Solution(this.maxKoraka, pocetna, map, this.maxDubina);
				if(i<this.maxPop/2){
					sol.root = op.izgradiStablo(true, Constants.pocetnaDubina);
				}else {
					sol.root = op.izgradiStablo(false, Constants.pocetnaDubina);
				}
				sol.evaluate();
				this.populacija.add(sol);
			}
			
			
		}else {

			for(int i=0;i<this.maxPop;i++){
				Solution sol = new Solution(this.maxKoraka, pocetna, map, this.maxDubina);
				if(i<this.maxPop/2){
					sol.root = op.izgradiStablo(true, Constants.pocetnaDubina);
				}else {
					sol.root = op.izgradiStablo(false, Constants.pocetnaDubina);
				}
				sol.evaluate();
				this.populacija.add(sol);
			}
		}
	}

	public Solution run(){
		int brojIteracija = 0;
		
		while (brojIteracija < this.maxGeneracija){
			Collections.sort(populacija);
			if(populacija.getFirst().fitness>= this.minFitness){
				System.out.println("Najbolja jedinka pronašla " + populacija.getFirst().fitness + " komada hrane u iteraciji " + brojIteracija);
				break;
			}
			System.out.println("Najbolja jedinka pronašla " + populacija.getFirst().fitness + " komada hrane u iteraciji " + brojIteracija);
			this.populacija = kreirajNovuPopulaciju();
			
			
			brojIteracija++;
		}
		Collections.sort(populacija);
		Solution best = populacija.getFirst();
		ZapisiUDatoteku();
		return best;
	}

	private void ZapisiUDatoteku() {
		BufferedWriter fileOutputBuffered = null;
		
		try {
			FileWriter fileOutput = new FileWriter(this.putanjaDoOutputDatoteke, false);

			fileOutputBuffered = new BufferedWriter(fileOutput);
			String bestSolution = this.populacija.getFirst().toString();
			
			fileOutputBuffered.write(bestSolution);
			
		} catch (IOException ignorable) {
		} finally {
			if (fileOutputBuffered != null) {
				try {
					fileOutputBuffered.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private LinkedList<Solution> kreirajNovuPopulaciju() {
		LinkedList<Solution> novaPop = new LinkedList<Solution>();
		novaPop.add(new Solution(this.populacija.getFirst()));
		while(true){
			double vjerojatnost = rand.nextDouble();
			if(novaPop.size()==this.maxPop){
				break;
			}
			
			if(vjerojatnost <= Constants.vjerojatnostReprodukcije){				
				Solution newSol = op.tournament.findParent(this.populacija);
				newSol = op.reprodukcija(newSol);
				novaPop.add(newSol);
				
			}else if( vjerojatnost> Constants.vjerojatnostReprodukcije && vjerojatnost <= (Constants.vjerojatnostMutacije + Constants.vjerojatnostReprodukcije)){
				Solution newSol = op.tournament.findParent(this.populacija);
				newSol = op.mutate(newSol);
				novaPop.add(newSol);		
				
			}else if( vjerojatnost> Constants.vjerojatnostMutacije && vjerojatnost <= 1){
				Solution p1 = op.tournament.findParent(this.populacija);
				Solution p2 = op.tournament.findParent(this.populacija);
				Solution[] newSol = op.crossover(p1, p2);
				
				novaPop.add(newSol[0]);
				
				if(novaPop.size()==this.maxPop){
					break;
				}	
				novaPop.add(newSol[1]);
				
			}else {
				throw new IllegalArgumentException("Krivo definirane konstante za vjerojatnost");
			}
			
			//Testing
//			Solution newSol = op.tournament.findParent(this.populacija);
//			newSol = op.mutate(newSol);
//			novaPop.add(newSol);
//			if(novaPop.size() == this.maxPop){
//				break;
//			}
//			
//			Solution p1 = op.tournament.findParent(this.populacija);
//			Solution p2 = op.tournament.findParent(this.populacija);
//			Solution[] newSol = op.crossover(p1, p2);
//			
//			novaPop.add(newSol[0]);
//			
//			if(novaPop.size()==this.maxPop){
//				break;
//			}	
//			novaPop.add(newSol[1]);
			
		}
		return novaPop;
	}
}
