package hr.fer.zemris.optjava.dz11;

import java.util.Random;

import hr.fer.zemris.optjava.dz11.algorithms.Zad1Alg;
import hr.fer.zemris.optjava.dz11.genetic.operators.ISelection;
import hr.fer.zemris.optjava.dz11.genetic.operators.Tournament;

public class Pokretac1 {

	public static void main(String[] args) {
		if(args.length != 7){
			System.out.println("Nedovoljan broj ulaznih argumenata");
			System.out.println("1. putanja do png slike");
			System.out.println("2. broj kvadrata kojim se aproksimira");
			System.out.println("3. velicina populacije s kojom se radi");
			System.out.println("4. max broj generacija");
			System.out.println("5. trazeni fitness");
			System.out.println("6. staza do txt datoteke gdje ce se ispisati parametri");
			System.out.println("7. staza do slike koju ce program generirati");
		}
		String picPath, picOutputPath, filePath;
		int brojKvadrata, maxPop, maxGen;
		double fitness;
		
		picPath = args[0];
		picOutputPath = args[6];
		filePath = args[5];
		
		
		try{
			brojKvadrata = Integer.parseInt(args[1]);
		} catch(NumberFormatException e){
			System.out.println("Greska u unosu 2. parametra");
			return;
		}
		
		try{
			maxPop = Integer.parseInt(args[2]);
		} catch(NumberFormatException e){
			System.out.println("Greska u unosu 3. parametra");
			return;
		}
		
		try{
			maxGen = Integer.parseInt(args[3]);
		} catch(NumberFormatException e){
			System.out.println("Greska u unosu 4. parametra");
			return;
		}
		
		try{
			fitness = Double.parseDouble(args[4]);
		} catch(NumberFormatException e){
			System.out.println("Greska u unosu 5. parametra");
			return;
		}
		//"C:\\Java\\Paralelizacija\\kuca.png"
		//"C:\\Java\\Paralelizacija\\parametri.txt"
		//"C:\\Java\\Paralelizacija\\kucaAprox.png"
		
		Zad1Alg alg = new Zad1Alg(brojKvadrata, maxPop, maxGen, fitness, picPath, filePath, picOutputPath, new Tournament(3, new Random()), false);
	}

}
