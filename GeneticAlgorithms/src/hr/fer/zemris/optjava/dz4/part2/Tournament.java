package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
/**
 * Class implements methods used for tournament selection of parent in the current population pool
 *
 */
public class Tournament implements ISelection {
	private int numberOfContestants;
	private Random rand;
	private boolean best;
	
	/**
	 * Constructor for Tournament class
	 * @param n number of contestants
	 * @param rand is the random number generator used in the algorithm
	 * @param best if this parameter is true it will find parameters with better fitness otherwise it will find the parameter with the lowest fitness
	 * between the contestants
	 */
	public Tournament(int n, Random rand, boolean best){
		if(n<1){
			throw new IllegalArgumentException("n must be > 1");
		}
		numberOfContestants = n;
		this.rand = rand;
		this.best = best;
	}

	@Override
	public BoxSolution findParent(LinkedList<BoxSolution> currentSolutions) {
		int length =  currentSolutions.size();
		BoxSolution currentContestant;
		BoxSolution bestParent;
		bestParent = currentSolutions.get(rand.nextInt(length));
		for(int i=1;i<numberOfContestants;i++){
			currentContestant = currentSolutions.get(rand.nextInt(length));
			if((currentContestant.fitness > bestParent.fitness) == best){
				bestParent = currentContestant;
			}
		}
		return bestParent;
	}
	

}
