package hr.fer.zemris.optjava.dz5.part1;

import java.util.LinkedList;
import java.util.Random;
/**
 * Class implements methods used for tournament selection of parent in the current population pool
 *
 */
public class Tournament implements ISelection {
	private int numberOfContestants;
	private Random rand;
	private boolean onlyTournament;
	private int foundParents;
	/**
	 * Constructor for Tournament class
	 * @param n number of contestants
	 * @param rand is the random number generator used in the algorithm
	 * @param onlyTournament if it is true it will choose all parents as tournament, if false it will choose every second as a random from the collection
	 */
	public Tournament(int n, Random rand,boolean onlyTournament){
		if(n<1){
			throw new IllegalArgumentException("n must be > 1");
		}
		this.onlyTournament = onlyTournament;
		numberOfContestants = n;
		this.foundParents = 0;
		this.rand = rand;
	}
	
	
	@Override
	public BitVectorSolution findParent(LinkedList<BitVectorSolution> currentSolutions) {
		int length =  currentSolutions.size();
		if(foundParents%2 == 1 && !onlyTournament){
			foundParents++;
			return currentSolutions.get(rand.nextInt(length));
		}else {
			BitVectorSolution currentContestant;
			BitVectorSolution bestParent;
			bestParent = currentSolutions.get(rand.nextInt(length));
			for(int i=1;i<numberOfContestants;i++){
				currentContestant = currentSolutions.get(rand.nextInt(length));
				if(bestParent.value < currentContestant.value){
					bestParent = currentContestant;
				}
			}
			foundParents++;
			return bestParent;
		}
	}

}
