package hr.fer.zemris.optjava.dz5.part2;

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
	 * @param onlyTournament if it is true it will choose all parents as tournament, if false it will choose every second as a random from the collection
	 */
	public Tournament(int n, Random rand){
		if(n<1){
			throw new IllegalArgumentException("n must be > 1");
		}
		numberOfContestants = n;
		this.rand = rand;
	}
	
	
	@Override
	public ObjectPlacementSolution findParent(LinkedList<ObjectPlacementSolution> currentSolutions) {
		int length =  currentSolutions.size();
		ObjectPlacementSolution currentContestant;
		ObjectPlacementSolution bestParent;
		bestParent = currentSolutions.get(rand.nextInt(length));
		for(int i=1;i<numberOfContestants;i++){
			currentContestant = currentSolutions.get(rand.nextInt(length));
			if(bestParent.fitness > currentContestant.fitness){
				bestParent = currentContestant;
			}
		}
		return bestParent;
	}

}
