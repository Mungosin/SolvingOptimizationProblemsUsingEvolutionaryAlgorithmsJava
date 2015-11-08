package hr.fer.zemris.optjava.dz5.part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class GeneticAlgorithm {

// if max selection pressure or max population is too high in some examples the program might run for too long since the population generation will have a very long time to try to find a better population
//works for 10 20 30 true 2 0.8 4 0.3 path 
	public static void main(String[] args) {
		int minPopulation, maxPopulation, tournament;
		boolean changeCompFactorTroughIterations;
		double maxSelectionPressure,successRatio,mutationChance;
		ISelection selection;
		int numberOfPopulations;
		String path;
		double[][] distanceBetweenLocations = null;
		double[][] transportUnitsBetweenObjects = null;
		int numberOfObjects;
			

		if(args.length!=9){
			System.out.println("First parameter - min population");
			System.out.println("Second parameter - max population");
			System.out.println("Third parameter - numberOfPopulations");
			System.out.println("Fourth parameter - boolean change compFactor after a number of iterations");
			System.out.println("Fifth parameter - max selection pressure");
			System.out.println("Sixth parameter - success ratio");
			System.out.println("Seventh parameter - number of contestants in tournament");
			System.out.println("Eighth parameter - mutation rate");
			System.out.println("Ninth parameter - path to the file that defines the problem");
			return;
		}
		
		try{
			minPopulation = Integer.parseInt(args[0]);
			if(minPopulation<0){
				System.out.println("population number cant be negative");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("First parameter must be a number indicating the min size of population");
			return;
		}
		try{
			maxPopulation = Integer.parseInt(args[1]);
			if(maxPopulation<0){
				System.out.println("population number cant be negative");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("Second parameter must be a number indicating the max size of population");
			return;
		}
		if(minPopulation>maxPopulation){
			System.out.println("Min population cant be higher than max population");
			return;
		}
		try{
			numberOfPopulations = Integer.parseInt(args[2]);
		} catch(NumberFormatException e){
			System.out.println("Third parameter must be a number indicating the number of populations");
			return;
		}
		
			changeCompFactorTroughIterations = Boolean.parseBoolean(args[3]);
		
		try{
			maxSelectionPressure = Double.parseDouble(args[4]);
			if(maxSelectionPressure<1){
				System.out.println("Max selection pressure cant be less than one");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("Fifth parameter must be a number indicating the max selection pressure");
			return;
		}
		try{
			successRatio = Double.parseDouble(args[5]);
			if(successRatio<0 || successRatio>1 ){
				System.out.println("SuccessRatio cant be negative or higher than one");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("Sixth parameter must be a parameter indicating the percentage of better childs required in the new population");
			return;
		}
		try{
			tournament = Integer.parseInt(args[6]);
			if(tournament<2){
				System.out.println("Number of contestants cant be less than 2");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("Third parameter must be a number indicating the max length of solution vector");
			return;
		}
		try{
			mutationChance = Double.parseDouble(args[7]);
			if(mutationChance<0 || mutationChance>1 ){
				System.out.println("MutationChance cant be negative or higher than one");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("Eith parameter must be a parameter indicating the mutation chance of a child");
			return;
		}
		
		try{
			path = args[8];
//			numberOfObjects = loadDefinition(path, distanceBetweenLocations,transportUnitsBetweenObjects);
//			if(distanceBetweenLocations == null || transportUnitsBetweenObjects==null || numberOfObjects==0){
//				System.out.println("File didnt define matrices properly");
//				return;
//			}
			File source = new File(path);
			Scanner scan = new Scanner(source);
			numberOfObjects = scan.nextInt();
			distanceBetweenLocations = new double[numberOfObjects][numberOfObjects];
			transportUnitsBetweenObjects = new double[numberOfObjects][numberOfObjects];
			
			for(int i=0;i<numberOfObjects;i++){
				for(int j=0;j<numberOfObjects;j++){
					distanceBetweenLocations[i][j] = scan.nextDouble();
				}
			}
			
			for(int i=0;i<numberOfObjects;i++){
				for(int j=0;j<numberOfObjects;j++){
					transportUnitsBetweenObjects[i][j] = scan.nextDouble();
				}
			}
		} catch(Exception e){
			System.out.println("Ninth parameter needs to be a valid path to the file containing properly formatted definition of the problem");
			return;
		}
		
		Random rand = new Random(System.currentTimeMillis());
		selection = new Tournament(tournament, rand);
		SASEGASAlgorithm saseg = new SASEGASAlgorithm(mutationChance, minPopulation, maxPopulation, changeCompFactorTroughIterations, maxSelectionPressure, selection, successRatio, numberOfPopulations, distanceBetweenLocations, transportUnitsBetweenObjects, numberOfObjects);
		saseg.run();
		
	}

//	private static int loadDefinition(String path, double[][] distanceBetweenLocations, double[][] transportUnitsBetweenObjects) throws FileNotFoundException {
//		File source = new File(path);
//		Scanner scan = new Scanner(source);
//		int numberOfObjects = scan.nextInt();
//		distanceBetweenLocations = new double[numberOfObjects][numberOfObjects];
//		transportUnitsBetweenObjects = new double[numberOfObjects][numberOfObjects];
//		
//		for(int i=0;i<numberOfObjects;i++){
//			for(int j=0;j<numberOfObjects;j++){
//				distanceBetweenLocations[i][j] = scan.nextDouble();
//			}
//		}
//		
//		for(int i=0;i<numberOfObjects;i++){
//			for(int j=0;j<numberOfObjects;j++){
//				transportUnitsBetweenObjects[i][j] = scan.nextDouble();
//			}
//		}
//		
//		return numberOfObjects;
//	}
}
