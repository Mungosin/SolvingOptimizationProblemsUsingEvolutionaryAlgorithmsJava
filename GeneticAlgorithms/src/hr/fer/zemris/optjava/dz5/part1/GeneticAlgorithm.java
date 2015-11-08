package hr.fer.zemris.optjava.dz5.part1;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Class implements RAPG algorithm used to find the points where the given function has maximal value
 *
 */
public class GeneticAlgorithm {
// working parameters 10 105 100 true 10 0.5 4 0.2 true
	public static void main(String[] args) {
		int minPopulation, maxPopulation, vectorLength, tournament;
		boolean changeCompFactorTroughIterations;
		double maxSelectionPressure,successRatio,mutationChance;
		ISelection selection;
		boolean onlyTournament;
		
		
		
		IFunction func = new Function1();

		if(args.length!=9){
			System.out.println("First parameter - min population");
			System.out.println("Second parameter - max population");
			System.out.println("Third parameter - vector length");
			System.out.println("Fourth parameter - boolean change compFactor after a number of iterations");
			System.out.println("Fifth parameter - max selection pressure");
			System.out.println("Sixth parameter - success ratio");
			System.out.println("Seventh parameter - number of contestants in tournament");
			System.out.println("Eighth parameter - mutation rate");
			System.out.println("Ninth parameter -boolean if only tournament selection is used or 1 tournament and 1 random");
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
			vectorLength = Integer.parseInt(args[2]);
		} catch(NumberFormatException e){
			System.out.println("Third parameter must be a number indicating the max length of solution vector");
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
		
			onlyTournament = Boolean.parseBoolean(args[8]);


		Random rand = new Random(System.currentTimeMillis());
		selection = new Tournament(tournament, rand, onlyTournament);
		RAPGAlgorithm alg = new RAPGAlgorithm(mutationChance, minPopulation, maxPopulation, changeCompFactorTroughIterations, maxSelectionPressure, vectorLength, func, selection, successRatio);
		alg.run();
	}
}
