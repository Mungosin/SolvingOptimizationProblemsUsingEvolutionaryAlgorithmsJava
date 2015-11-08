package hr.fer.zemris.optjava.dz5.part2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
/**
 * Class implements RAPG algorithm used to find the best solution for the object placement problem
 *
 */
public class RAPGAlgorithm implements IOptAlgorithm<LinkedList<ObjectPlacementSolution>>{
	protected LinkedList<ObjectPlacementSolution> population;
	protected int maxPopulation;
	protected int minPopulation;
	protected boolean changeCompFactorTroughIterations;
	protected double maxSelectionPressure;
	protected double ActSelPress;
	private double compFactor;
	private ISelection selection;
	private Random rand;
	private double successRatio;
	private double mutationChance;
	
	/**
	 * Constructor for RAPG algorithm
	 * @param mutationChance chance the new child will mutate
	 * @param minPopulation minimal number of solutions in a population
	 * @param maxPopulation maximal number of solutions in a population 
	 * @param changeCompFactorTroughIterations boolean that if true increases the comparison factor after a number of iterations
	 * @param maxSelectionPressure maximal selection pressure determines the number of iterations the algorithm has to create a new population
	 * @param selection is an object that determines how the parent for the next child will be selected 
	 * @param successRatio percentage of better children the next population is required to have
	 * @param population the initial population of solutions
	 */
	public RAPGAlgorithm(double mutationChance,int minPopulation, int maxPopulation, boolean changeCompFactorTroughIterations, double maxSelectionPressure, ISelection selection, double successRatio, LinkedList<ObjectPlacementSolution> population){
		this.rand = new Random(System.currentTimeMillis());
		this.minPopulation = minPopulation;
		this.maxPopulation = maxPopulation;
		this.changeCompFactorTroughIterations = changeCompFactorTroughIterations;
		this.maxSelectionPressure = maxSelectionPressure;
		this.ActSelPress = 0;
		this.compFactor = 0;
		this.successRatio = successRatio;
		this.selection = selection;
		this.mutationChance = mutationChance;
		this.population = population;
		
	}

	@Override
	public LinkedList<ObjectPlacementSolution> run() {
		int numberOfIterations = 0;
		ObjectPlacementSolution highestFound;
		LinkedList<ObjectPlacementSolution> lastPopulation = this.population;

		Collections.sort(population, Collections.reverseOrder());
		highestFound = population.get(0);
		while(true){
			Collections.sort(population, Collections.reverseOrder());
			population = generateNextPopulationBothParentsTournament(population);
			if(population == null){
				break;
			}
			lastPopulation = this.population;
			if(numberOfIterations>= Constants.changeCompFactorEveryNumberOfIterations){
				numberOfIterations=0;
				if(this.compFactor+Constants.compFactorIncrement<=1){
					this.compFactor +=Constants.compFactorIncrement;
				}
			}else{
				numberOfIterations++;
			}
		}
		return lastPopulation;
	}

	/**
	 * Method is used to generate new population from the given population, new population can't contain duplicate elements
	 * @param population collection of elements from which the new population will be created
	 * @return the collection containing elements of the new population
	 */
	private LinkedList<ObjectPlacementSolution> generateNextPopulationBothParentsTournament(LinkedList<ObjectPlacementSolution> population) {
		LinkedList<ObjectPlacementSolution> newPopulation = new LinkedList<ObjectPlacementSolution>();
		LinkedList<ObjectPlacementSolution> geneticPool = new LinkedList<ObjectPlacementSolution>();
		while((1.*(newPopulation.size()+geneticPool.size())/population.size())<maxSelectionPressure){
			ObjectPlacementSolution firstParent = selection.findParent(population).duplicate();
			ObjectPlacementSolution secondParent = selection.findParent(population).duplicate();
			ObjectPlacementSolution child = createChild(firstParent,secondParent);
			if(rand.nextDouble()<=mutationChance){
				mutateChild(child);
			}
			child.calculateFitness();
			if(newPopulation.size()==this.maxPopulation){
				break;
			}
			if(evaluateChild(child, firstParent, secondParent) && !newPopulation.contains(child) && newPopulation.size()<=this.maxPopulation){
				newPopulation.add(child);
			}else {
				geneticPool.add(child);
				if(newPopulation.size() >= population.size()*this.successRatio){
					if(!newPopulation.contains(child)){
						newPopulation.add(child);
					}
					boolean testedAll=false;
					while(newPopulation.size()<this.maxPopulation && testedAll){
						for(int i=geneticPool.size()-1;i>=0;i--){
							if(newPopulation.size()==this.maxPopulation){
								return newPopulation;
							}
							if(!newPopulation.contains(geneticPool.get(i))){
								newPopulation.add(geneticPool.get(i));
							}
						}
						testedAll = true;
					}
				}
			}
		}
		if(newPopulation.size() <=this.maxPopulation && newPopulation.size()>=this.minPopulation){
			return newPopulation;
		}
		return null;
	}


	/**
	 * Method is used to compare the fitness of the created child with its parents and return true if it is better using a formula that includes comparison factor
	 * @param child child being evaluated
	 * @param firstParent first parent of the child
	 * @param secondParent second parent of the child
	 * @return true if the child is evaluated as better
	 */
	private boolean evaluateChild(ObjectPlacementSolution child, ObjectPlacementSolution firstParent,
			ObjectPlacementSolution secondParent) {
		double betterParentFit;
		double worseParentFit;
		if(firstParent.fitness < secondParent.fitness){
			betterParentFit = secondParent.fitness;
			worseParentFit = firstParent.fitness;
		}else {
			worseParentFit = secondParent.fitness;
			betterParentFit = firstParent.fitness;
		}
		if(child.fitness > (worseParentFit + this.compFactor*(betterParentFit-worseParentFit))){
			return true;
		}
		return false;
		
	}
	
	/**
	 * Method is used to mutate the child
	 * @param child child being mutated
	 */
	private void mutateChild(ObjectPlacementSolution child) {
		int length = child.LocationOfObjects.size();
		int change = rand.nextInt(length);
		int changeWith = rand.nextInt(length);
		Integer p = child.LocationOfObjects.get(change);
		child.LocationOfObjects.set(change, child.LocationOfObjects.get(changeWith));
		child.LocationOfObjects.set(changeWith, p);
	}

	/**
	 * Method is being used to create a new child from the given parents 
	 * @param firstParent first parent of the child
	 * @param secondParent second parent of the child
	 * @return newly created child
	 */
	private ObjectPlacementSolution createChild(ObjectPlacementSolution firstParent, ObjectPlacementSolution secondParent) {
		ObjectPlacementSolution child = firstParent.duplicate();
		LinkedList<Integer> LocationsToAdd = new LinkedList<Integer>();
		LinkedList<Integer> LocationsToReplace = new LinkedList<Integer>();
		int secondParentSize = secondParent.LocationOfObjects.size();
		int firstCrossoverPoint = rand.nextInt(secondParentSize);
		int secondCrossoverPoint = rand.nextInt(secondParentSize);
		
		if(firstCrossoverPoint>secondCrossoverPoint){
			int p = firstCrossoverPoint;
			firstCrossoverPoint = secondCrossoverPoint;
			secondCrossoverPoint = p;
		}
		
		for(int i=firstCrossoverPoint;i<secondCrossoverPoint;i++){
			int p = child.LocationOfObjects.get(i).intValue();
			int secondParentValue = secondParent.LocationOfObjects.get(i).intValue();
			if(p == secondParentValue){
				continue;
			}else {
				int index = child.LocationOfObjects.indexOf(new Integer(secondParentValue));
				child.LocationOfObjects.set(i, secondParentValue);
				child.LocationOfObjects.set(index, p);
			}
		}
		
		return child;
	}
}
