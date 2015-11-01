package hr.fer.zemris.optjava.dz4.part1;

import java.util.ArrayList;
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
	public DoubleArraySolution findParent(ArrayList<DoubleArraySolution> currentSolutions) {
		int length =  currentSolutions.size();
		DoubleArraySolution currentContestant;
		DoubleArraySolution bestParent;
		bestParent = currentSolutions.get(rand.nextInt(length));
		for(int i=1;i<numberOfContestants;i++){
			currentContestant = currentSolutions.get(rand.nextInt(length));
			if(currentContestant.value -bestParent.value < 0){
				bestParent = currentContestant;
			}
		}
		return bestParent;
	}

}
