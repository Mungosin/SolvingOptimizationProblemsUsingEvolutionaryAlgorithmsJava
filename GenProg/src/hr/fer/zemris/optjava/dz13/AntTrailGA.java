package hr.fer.zemris.optjava.dz13;

import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import hr.fer.zemris.optjava.dz13.mapa.Mapa;
import hr.fer.zemris.optjava.dz13.prikaz.Frame;
import hr.fer.zemris.optjava.dz13.solution.Cvor;
import hr.fer.zemris.optjava.dz13.solution.Pozicija;
import hr.fer.zemris.optjava.dz13.solution.Smjer;
import hr.fer.zemris.optjava.dz13.solution.Solution;

public class AntTrailGA {
	
	
	public static void main(String[] args) {
		
		if(args.length != 5){
			System.out.println("Nedovoljan broj ulaznih argumenata");
			System.out.println("1. putanja do datoteke s mapom");
			System.out.println("2. maksimalni broj generacija koji je dozvoljen");
			System.out.println("3. velièina populacije s kojom se radi");
			System.out.println("4. minimalna dobrota koja, kada se dosegne, smije prekinuti uèenje (npr. 89)");
			System.out.println("5. putanja do datoteke u koju æe program zapisati najbolje pronaðeno rješenje");
			return;
		}
		String putanjaDoDatotekeSMapom, putanjaDoOutputDatoteke;
		int maxGen, maxPopSize, minDobrota;
		
		try{
			maxGen = Integer.parseInt(args[1]);
		} catch(NumberFormatException e){
			System.out.println("Greska u unosu 2. parametra");
			return;
		}
		try{
			maxPopSize = Integer.parseInt(args[2]);
		} catch(NumberFormatException e){
			System.out.println("Greska u unosu 3. parametra");
			return;
		}
		try{
			minDobrota = Integer.parseInt(args[3]);
		} catch(NumberFormatException e){
			System.out.println("Greska u unosu 4. parametra");
			return;
		}
		putanjaDoDatotekeSMapom = args[0];
		putanjaDoOutputDatoteke = args[4];
		
		Mapa map = new Mapa(putanjaDoDatotekeSMapom);

		GeneticAlg alg = new GeneticAlg(600, new Pozicija(0, 0, Smjer.Desno), maxGen, minDobrota, Constants.maxDubina, map , maxPopSize, putanjaDoOutputDatoteke);
		Solution poz = alg.run();
		LinkedList<Pozicija> pozicije = poz.dohvatiPokrete();
		System.out.println(pozicije.get(0).x);
		Frame novi = new Frame(pozicije, map);
		
	}
}
