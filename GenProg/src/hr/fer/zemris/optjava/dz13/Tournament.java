package hr.fer.zemris.optjava.dz13;


import hr.fer.zemris.optjava.dz13.solution.Solution;

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
	/**
	 * Constructor for Tournament class
	 * @param n number of contestants
	 * @param rand is the random number generator used in the algorithm
	 */
	public Tournament(int n, Random rand){
		if(n<1){
			throw new IllegalArgumentException("n must be > 1");
		}
		numberOfContestants = n;
		this.rand = rand;
	}
	@Override
	public Solution findParent(
			LinkedList<Solution> currentSolutions) {
		int length =  currentSolutions.size();
		Solution currentContestant;
		Solution bestParent;
		bestParent = currentSolutions.get(rand.nextInt(length));
		for(int i=1;i<numberOfContestants;i++){
			currentContestant = currentSolutions.get(rand.nextInt(length));
			if(bestParent.fitness < currentContestant.fitness){
				bestParent = currentContestant;
			}
		}
		return bestParent;
	}
	
	


}
