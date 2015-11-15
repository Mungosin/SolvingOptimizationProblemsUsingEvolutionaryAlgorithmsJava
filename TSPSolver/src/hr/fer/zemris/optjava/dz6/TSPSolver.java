package hr.fer.zemris.optjava.dz6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Class implements the main method and calls the AntSystem algorithm with parsed inputs
 *
 */
public class TSPSolver {
	//radi lakšeg parsiranja sam ulaznim datotekama izbrisao višak podataka
	
	/**
	 * Main method
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		int sizeOfCandidateList;
		int numberOfAnts;
		int maxIterations;
		int numberOfTowns;
		double a = Constants.a;
		double alfa = Constants.alfa;
		double beta = Constants.beta;
		Double[][] distanceBetweenTowns;
		LinkedList<City> cities = new LinkedList<>();
		
		if(args.length != 4){
			System.out.println("Program requires four parameters: ");
			System.out.println("First parameter - path to file describing the TSP");
			System.out.println("Second parameter - size of the candidate list");
			System.out.println("Third parameter - number of ants in colony");
			System.out.println("Fourth parameter - max iterations of algorithm");
		}
		
		try{
			File source = new File(args[0]);
			Scanner scan = new Scanner(source);
			numberOfTowns = scan.nextInt();
			for(int i=0;i<numberOfTowns;i++){
				cities.add(new City(scan.nextInt(),scan.nextDouble(),scan.nextDouble()));
			}
			
			
			distanceBetweenTowns = new Double[numberOfTowns][numberOfTowns];
			System.out.println("Loading distances");
			for(int i=0;i<numberOfTowns;i++){
				for(int j=0;j<numberOfTowns;j++){
					if(i==j) {
						distanceBetweenTowns[i][j] = 0.0;
					}
					double euclidDistance = Math.sqrt(Math.pow((cities.get(i).x - cities.get(j).x),2) + Math.pow((cities.get(i).y - cities.get(j).y),2));
					distanceBetweenTowns[i][j] = euclidDistance;
				}
			}
		}catch(Exception e){
			System.out.println("First parameter must be a path to the file defining TSP");
			return;
		}
		
		try{
			sizeOfCandidateList = Integer.parseInt(args[1]);
			if(sizeOfCandidateList<1){
				System.out.println("size of candidate list can't be less than 1");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("Second parameter must be a number indicating the candidate list size");
			return;
		}
		
		try{
			numberOfAnts = Integer.parseInt(args[2]);
			if(numberOfAnts<0){
				System.out.println("Number of ants can't be less than 1");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("Third parameter defines the number of ants in colony");
			return;
		}
		
		try{
			maxIterations = Integer.parseInt(args[3]);
			if(maxIterations<0){
				System.out.println("Max iterations can't be negative");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("Fourth parameter must is a number defining max iterations of the algorithm");
			return;
		}
		
		AntSystemAlgorithm alg = new AntSystemAlgorithm(distanceBetweenTowns, maxIterations, numberOfAnts, numberOfTowns, a, beta, alfa, cities, sizeOfCandidateList);
		alg.run();
	}
}
