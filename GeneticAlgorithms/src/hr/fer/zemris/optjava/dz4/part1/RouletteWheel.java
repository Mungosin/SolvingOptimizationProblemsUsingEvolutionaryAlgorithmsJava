package hr.fer.zemris.optjava.dz4.part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 * Class implements methods used for RouletteWheel selection of parent in the current population pool
 *
 */
public class RouletteWheel implements ISelection{
	private Random rand;
	
	/**
	 * Constructor for RouletteWheel class
	 * @param rand random number generator used in the selection algorithm
	 */
	public RouletteWheel(Random rand){
		this.rand = rand;
	}
	
	@Override
	public DoubleArraySolution findParent(ArrayList<DoubleArraySolution> currentSolutions) {
		DoubleArraySolution bestParent = null;
		DoubleArraySolution currentSol;
		double rouletteFitness =0;
		double currentFitnessSum=0;
		double highestFitness=0;
		int length = currentSolutions.size();
		
		highestFitness = currentSolutions.get(0).value;
		
		for(int i=0;i<length;i++){
			currentSol = currentSolutions.get(i);
			currentSol.fitness = Math.abs(highestFitness - currentSol.value);
			currentSol.fitness = (1.0/(currentSol.fitness+1));
			currentFitnessSum += currentSol.fitness;
		}
		
		rouletteFitness = rand.nextDouble() * currentFitnessSum;
		
		currentFitnessSum=0;
		for(int i=0;i<length;i++){
			currentSol = currentSolutions.get(i);
			if(rouletteFitness < currentFitnessSum + currentSol.fitness  && rouletteFitness >= currentFitnessSum){
				bestParent = currentSol;
			}
			currentFitnessSum += currentSol.fitness;
		}
		return bestParent;
	}

}
