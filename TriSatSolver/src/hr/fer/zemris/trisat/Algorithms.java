package hr.fer.zemris.trisat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class that implements three algorithms
 *
 */
public class Algorithms {
	private File file;
	private BufferedReader reader;
	private boolean fileCorrect;
	private HelperFunctions helper;
	
	
	
	/**
	 * Constructor for Algorithms class
	 * @param myFile file where information about the assignment is stored
	 */
	public Algorithms(File myFile){
		this.file = myFile;
		try {
			reader = new BufferedReader(new FileReader(file));
			fileCorrect = true;
			helper = new HelperFunctions(reader);
		} catch (FileNotFoundException e) {
			fileCorrect = false;
		}
	}
	
	/**
	 * First algorithm that tests all combinations and finds all solutions but returns one of them
	 * @return BitVector of the first found solution to the formula
	 */
	public BitVector FirstAlgorithm(){
		if(fileCorrect){
			ArrayList<BitVector> solutions = new ArrayList<BitVector>();
			SATFormula satForm = helper.initializeSATForms();
			if(satForm == null){
				return null;
			}
			int numberOfVariables = satForm.getNumberOfVariables();
			int numberOfCombinations = (int) Math.pow(2, numberOfVariables);
			BitVector current = new BitVector(numberOfVariables);
			
			while(helper.hasGreater(current)){
				if(satForm.isSatisfied(current)){
					solutions.add(current);
				}
				current = helper.nextGreater(current);
			}
			
			if(solutions.isEmpty()){
				return null;
			}else {
				for(BitVector currentSolution : solutions){
					System.out.println(currentSolution);
				}
				return solutions.get(0);
			}
		}else {
			System.out.println("File not found");
			return null;
		}
	}
	
	/**
	 * Second algorithm uses a simple fitness function to try and find the global optimum, but stops if it finds a local optimum.
	 * Each iteration is scored based on a fitness function that directs the search towards an optimum and the candidate for the 
	 * next iteration is randomly choosen between all neighbours with a better fitness score.
	 * @return true if a solution was found, else returns false
	 */
	public boolean SecondAlgorithm(){
		if(fileCorrect){
			SATFormula satForm = helper.initializeSATForms();
			if(satForm == null){
				return false;
			}
			SATFormulaStats satFormStats = new SATFormulaStats(satForm);
			int numberOfVariables = satForm.getNumberOfVariables();
			int maxIterations = Constants.maxIterations;
			Random randomGenerator = new Random(System.currentTimeMillis());
			BitVector currentBestSolution = new BitVector(randomGenerator, numberOfVariables);
			BitVectorNGenerator solutionGenerator = new BitVectorNGenerator(currentBestSolution);
			MutableBitVector[] solutions;
			satFormStats.setAssignment(currentBestSolution, false);
			int currentBestFitness = helper.fitnessFunctionSecondAlgorithm(satFormStats);
			boolean changedBest=false;
			while(maxIterations-- > 0){
				changedBest = false;
				solutions = solutionGenerator.createNeighbourhood();
				ArrayList<BitVector> subsetSolutions = new ArrayList<>();
				
				if(satFormStats.isSatisfied()){
					System.out.println(currentBestSolution);
					return true;
				}		
				
				for(MutableBitVector temp : solutions){
					satFormStats.setAssignment(temp, false);
					int currentSolution = helper.fitnessFunctionSecondAlgorithm(satFormStats);
					if(currentBestFitness < currentSolution){
						currentBestFitness=currentSolution;
						subsetSolutions.clear(); // if maxfit changed i empty my solution array and add the new solution with highest fitness
						subsetSolutions.add(temp);						
					}else if(currentBestFitness == currentSolution){
						subsetSolutions.add(temp); //if max fitness didnt change just add the solution
					}
				}
				if(!subsetSolutions.isEmpty()){
					int size = subsetSolutions.size();
					int index = Math.abs(randomGenerator.nextInt())%size;
					currentBestSolution = subsetSolutions.get(index);
					solutionGenerator = new BitVectorNGenerator(currentBestSolution);
					satFormStats.setAssignment(currentBestSolution, false);
					currentBestFitness = helper.fitnessFunctionSecondAlgorithm(satFormStats);
					changedBest = true;
				}
				if(!changedBest){
					return false; 
				}
				subsetSolutions.clear();
			}
			return false;
			
		}else {
			System.out.println("File not found");
			return false;
		}
	}
	
	/**
	 * Third algorithm an improved fitness function which scores every iteration and chooses between all neighbours with the highest score one candidate for the next iteration
	 * to better direct the search towards a global optimum.
	 * 
	 * @return true if a solution was found, else returns false 
	 */
	public boolean ThirdAlgorithm(){
		if(fileCorrect){
			SATFormula satForm = helper.initializeSATForms();
			if(satForm == null){
				return false;
			}
			SATFormulaStats satFormStats = new SATFormulaStats(satForm);
			int numberOfVariables = satForm.getNumberOfVariables();
			int maxIterations = Constants.maxIterations;
			Random randomGenerator = new Random(System.currentTimeMillis());
			BitVector currentBestSolution = new BitVector(randomGenerator, numberOfVariables);
			BitVectorNGenerator solutionGenerator  = new BitVectorNGenerator(currentBestSolution);
			MutableBitVector[] solutions;
			satFormStats.setAssignment(currentBestSolution, true);				
			
			while(maxIterations-- > 0){
				solutions = solutionGenerator.createNeighbourhood();
				LinkedList<BitVector> subsetSolutions = new LinkedList<>();
				
				if(satFormStats.isSatisfied()){
					System.out.println(currentBestSolution);
					return true;
				}				
				
				for(MutableBitVector temp : solutions){
					satFormStats.setAssignment(temp, false);
					subsetSolutions.add(temp);	
				}
				
				
				Collections.sort(subsetSolutions, Collections.reverseOrder(new BitVectorFitnessComparator(satFormStats,helper)));
				if(!subsetSolutions.isEmpty()){
					int size = subsetSolutions.size();
					int index;
					if(Constants.numberOfBest<size){
						index = Math.abs(randomGenerator.nextInt())%Constants.numberOfBest;
					}else {
						index = Math.abs(randomGenerator.nextInt())%size;
					}
					currentBestSolution = subsetSolutions.get(index);
					solutionGenerator = new BitVectorNGenerator(currentBestSolution);
					satFormStats.setAssignment(currentBestSolution, true);
				}
				subsetSolutions.clear();
			}
			return false;
			
			
		}else {
			System.out.println("File not found");
			return false;
		}
	}	
}
