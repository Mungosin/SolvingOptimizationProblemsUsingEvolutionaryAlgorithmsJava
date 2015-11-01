package hr.fer.zemris.optjava.dz4.part2;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.plaf.synth.SynthSpinnerUI;
/**
 * Class implements the optimization algorithm used to find the best way to use space in the box
 *
 */
public class BoxSteadyStateAlgorithm implements IOptAlgorithm{
	private int populationSize;
	private int wantedBoxSize;
	private int maxIterations;
	private int numberOfContestantsBest;
	private int numberOfContestantsWorst;
	private boolean elitism;
	private LinkedList<Stick> givenSticks;
	private Random rand = new Random(System.currentTimeMillis());
	private int maxHeight = Constants.maxHeight;
	private ISelection selectionBest;
	private ISelection selectionWorst;
	/**
	 * Constructs the BoxSteadyAlgorithm object
	 * @param sizePop population size
	 * @param maxBoxSize maxim size of the box wanted to pack all the objects
	 * @param maxIterations maximum number of iterations
	 * @param n number of contestants for best parent selection
	 * @param m number of contestants for worst parent selection
	 * @param p boolean parameter if its true the algorithm will replace worst parent with the child only if its fitness is greater than the fitness of the worst parent
	 * @param sticks list of sticks that need to be positioned in boxes
	 */
	public BoxSteadyStateAlgorithm(int sizePop, int maxBoxSize, int maxIterations, int n, int m, boolean p,
			LinkedList<Stick> sticks) {
		this.populationSize = sizePop;
		this.wantedBoxSize = maxBoxSize;
		this.maxIterations = maxIterations;
		this.numberOfContestantsBest = n;
		this.numberOfContestantsWorst = m;
		this.elitism = p;
		this.givenSticks = sticks;
		this.selectionBest = new Tournament(numberOfContestantsBest, rand, true);
		this.selectionWorst = new Tournament(numberOfContestantsWorst, rand, false);
	}
	/**
	 * Method is used to initialize a population of BoxSolutions
	 * @return the list of BoxSolution objects after initialization
	 */
	private LinkedList<BoxSolution> initializePopulation() {
		LinkedList<BoxSolution> pop = new LinkedList<>();
		for(int i=0;i<populationSize;i++){
			Collections.shuffle(givenSticks,rand);
			BoxSolution newItem = new BoxSolution(new LinkedList<Stick>(givenSticks), maxHeight);
			newItem.calculateFitness();
			newItem.sortElements();
			pop.add(newItem);
		}
		return pop;
	}
	
	/**
	 * Mutates a child by randomly choosing a bin and redistributing its elements
	 * @param child to be mutated
	 */ 
	public void mutateChild (BoxSolution child){
		int childBins = child.arrangement.size();
		int mutateIndex = rand.nextInt(childBins);
		Bin mutate = child.arrangement.get(mutateIndex);
		if(child.removeBin(mutate)){
			childBins--;
		}else{
			System.out.println("Bin couldn't be removed");
			System.exit(0);
		}
		int binSize = mutate.sticks.size();
		for(int j=0;j<binSize;j++){
			Stick stick = mutate.sticks.get(j);
			boolean flagAdded = false;
			for(int i=0;i<child.arrangement.size();i++){
				if(child.arrangement.get(i).addToBin(stick)){
					flagAdded = true;
					break;
				}
			}
			if(!flagAdded){
				Bin newBin = new Bin(Constants.maxHeight);
				newBin.addToBin(stick);
				child.arrangement.add(newBin);
			}else{
				flagAdded=false;
			}
		}
	}
	
	
	/**
	 * Creates a new child by crossing the two parents, second parameter is being cloned and saved as the child solution and then a place of insertion is being chosen.
	 * Then a random number of bins is chosen from the first parent and inserted into the child. After that bins containing duplicate elements are being removed and the elements
	 * that weren't duplicate are redistributed in to the child. When the crossing is done the child is being mutated and returned
	 * @param firstParent first parent in the crossing operation
	 * @param secondParent second parent in the crossing operation
	 * @return newly created child
	 */
	public BoxSolution createChild(BoxSolution firstParent, BoxSolution secondParent){
		int firstBins = firstParent.arrangement.size();
		int firstParentFirstCrossover =  rand.nextInt(firstBins);
		int firstParentSecondCrossover =  rand.nextInt(firstBins);
		BoxSolution child = new BoxSolution();
		child.maxHeight = Constants.maxHeight;
		child.arrangement = new LinkedList<>();
		for(Bin bin : secondParent.arrangement){
			Bin newBin = new Bin(Constants.maxHeight);
			for(Stick stick: bin){
				newBin.addToBin(stick);
			}
			child.arrangement.add(newBin);
		}
		
		if(firstParentFirstCrossover > firstParentSecondCrossover){
			int p = firstParentFirstCrossover;
			firstParentFirstCrossover = firstParentSecondCrossover;
			firstParentSecondCrossover = p;
		}
		LinkedList<Bin> binsToAdd = getBinsToAdd(firstParent, firstParentFirstCrossover, firstParentSecondCrossover);
		if(binsToAdd.size() == 0) return child;
		
		LinkedList<Stick> sticksInBin = getSticksInBins(binsToAdd);
		
		
		LinkedList<Stick> sticksToReAdd = removeBinsWithDuplicatesAndGetRemainingSticks(child, sticksInBin);
		
		distributeSticks(child, sticksToReAdd);
		distributeSticks(child, sticksInBin);

		child.calculateFitness();
		return child;
	}

	/**
	 * Distributes the given sticks in the first available bin of the solution, if no bins are available creates new
	 * @param child solution where the sticks are being distributed
	 * @param sticksToReAdd sticks that need to be redistributed
	 */
	private void distributeSticks(BoxSolution child, LinkedList<Stick> sticksToReAdd) {
	
		for(Stick stick : sticksToReAdd){
			boolean flag=false;
			for(int i=0;i<child.arrangement.size();i++){
				if(child.arrangement.get(i).addToBin(stick)){
					flag=true;
					break;
				}
			}
			if(flag==false){
				Bin newBin = new Bin(Constants.maxHeight);
				newBin.addToBin(stick);
				child.arrangement.add(newBin);
			}
		}
		
	}

	/**
	 * Method is used to remove bins containing duplicate sticks, and returns the sticks that were removed as collateral from the bins and need to be re-added
	 * @param child solution whose duplicates are being removed
	 * @param sticksInBin sticks that are duplicate
	 * @return Sticks that were contained in the bins removed but weren't duplicated and need to be re-added
	 */
	private LinkedList<Stick> removeBinsWithDuplicatesAndGetRemainingSticks(BoxSolution child, LinkedList<Stick> sticksInBin) {
		LinkedList<Stick> sticksToReAdd = new LinkedList<Stick>();
		LinkedList<Bin> removeBin = new LinkedList<Bin>();
		int arrangementLength = child.arrangement.size();
		for(int i=arrangementLength-1;i>=0;i--){
			Bin currentBin = child.arrangement.get(i);
			if(binContainsAny(currentBin, sticksInBin)){
				addRestOfSticks(sticksToReAdd, currentBin, sticksInBin);
				removeBin.add(child.arrangement.get(i));
			}
		}
		for(Bin in : removeBin){
			child.arrangement.remove(in);
		}
		return sticksToReAdd;
	}

	/**
	 * Method is used to add sticks that are are not in the duplicate list but are in the currentBin
	 * @param notDuplicate sticks that are in the bin but are not in the duplicate list
	 * @param currentBin the bin being searched for sticks that aren't in the duplicate list
	 * @param duplicate list of duplicate sticks
	 */
	private void addRestOfSticks(LinkedList<Stick> notDuplicate, Bin currentBin,LinkedList<Stick> duplicate) {
	for(Stick stick : currentBin){
		boolean flag = false;
		for(Stick sticksBin : duplicate){
			if(stick.stickNumber == sticksBin.stickNumber){
				flag = true;
				break;
			}
		}
		if(!flag){
			notDuplicate.add(stick);
		}
	}
	
}
	/**
	 * Method is used to check if the current bin has any sticks in the sticksInBin list
	 * @param currentBin bin being searched if it contains any of the sticks in sticksInBin list
	 * @param sticksInBin list of sticks that are being checked
	 * @return true if the bin contains any of the sticks in the sticksInBin list
	 */
	private boolean binContainsAny(Bin currentBin, LinkedList<Stick> sticksInBin) {
		for(Stick stick: sticksInBin){
			for(Stick currentBinStick : currentBin){
				if(stick.stickNumber == currentBinStick.stickNumber){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method gets the list of sticks in the list of bins
	 * @param binsToAdd list of bins whose sticks are being requested
	 * @return the sticks from all the bins in a list
	 */
	private LinkedList<Stick> getSticksInBins(LinkedList<Bin> binsToAdd) {
		LinkedList<Stick> sticks = new LinkedList<Stick>();
		for(Bin bin : binsToAdd){
			for(Stick stick : bin){
				sticks.add(stick);
			}
		}
		
		return sticks;

	}

	/**
	 * Method is used to get list of bins from the interval of the given box solution first number inclusive second exclusive.
	 * For example if the solution has 5 bins and the interval is 2 - 4 it would return the third and fourth bin since bins start from 0
	 * @param firstParent solution whose bin interval is requested
	 * @param firstParentFirstCrossover starting point of the bin interval
	 * @param firstParentSecondCrossover ending point of the bin interval
	 * @return List of Bins 
	 */
	private LinkedList<Bin> getBinsToAdd(BoxSolution firstParent, int firstParentFirstCrossover,
			int firstParentSecondCrossover) {
		
		LinkedList<Bin> binsToAdd = new LinkedList<>();
		for(int i=firstParentFirstCrossover;i<firstParentSecondCrossover;i++){
			binsToAdd.add(new Bin(firstParent.arrangement.get(i)));
		}

		return binsToAdd;
	}

	@Override
	public void run() {
		LinkedList<BoxSolution> currentPop = initializePopulation();
		int maxIter = this.maxIterations;
		BoxSolution firstParent;
		BoxSolution secondParent;
		BoxSolution parentToReplace;
		BoxSolution child;

		Collections.sort(currentPop, Collections.reverseOrder());
		
		
		while(maxIter-->0){
			
			firstParent = selectionBest.findParent(currentPop);
			secondParent = selectionBest.findParent(currentPop);
			parentToReplace = selectionWorst.findParent(currentPop);
			child = createChild(firstParent, secondParent);
			if(child.fitness>parentToReplace.fitness || !this.elitism){

				currentPop.remove(parentToReplace);
				currentPop.add(child);
				Collections.sort(currentPop, Collections.reverseOrder());
				System.out.println(child.fitness);
			}
			
			if(currentPop.get(0).arrangement.size() <= this.wantedBoxSize){
				System.out.println("____WantedMinimum____");
				System.out.println(currentPop.get(0));
				return;
			}
		}
		System.out.println("____Best Found_____");
		System.out.println(currentPop.get(0));
	}

	

}
