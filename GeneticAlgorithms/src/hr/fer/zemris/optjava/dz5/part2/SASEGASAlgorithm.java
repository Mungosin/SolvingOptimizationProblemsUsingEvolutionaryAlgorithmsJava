package hr.fer.zemris.optjava.dz5.part2;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Class implements SASEGAS algorithm used to find optimal placement of objects so they utilize their resources most efficiently.
 * RAPG algorithm is used to find the optimal solution for every population
 */
public class SASEGASAlgorithm implements IOptAlgorithm<LinkedList<ObjectPlacementSolution>>{
	protected double mutationChance;
	protected int minPopulation;
	protected int maxPopulation;
	protected int numberOfObjects;
	protected int numberOfPopulations;
	protected boolean changeCompFactorTroughIterations;
	protected double maxSelectionPressure;
	protected ISelection selection;
	protected double successRatio;
	protected double[][] distanceBetweenLocations;
	protected double[][] transportUnitsBetweenObjects;
	protected LinkedList<LinkedList<ObjectPlacementSolution>> populations;
	
	/**
	 * Constructor for SASEGAS algorithm
	 * @param mutationChance chance that a child will mutate
	 * @param minPopulation minimal number of solutions in a population
	 * @param maxPopulation maximal number of elements in a population
	 * @param changeCompFactorTroughIterations boolean that if true increases the comparison factor after a number of iterations
	 * @param maxSelectionPressure maximal selection pressure determines the number of iterations the algorithm has to create a new population
	 * @param selection is an object that determines how the parent for the next child will be selected 
	 * @param successRatio percentage of better children the next population is required to have
	 * @param numberOfPopulations starting number of populations
	 * @param distanceBetweenLocations defines the distance between all locations
	 * @param transportUnitsBetweenObjects defines how much elements are being transported between every object
	 * @param numberOfObjects total number of objects
	 */
	public SASEGASAlgorithm (double mutationChance,int minPopulation, int maxPopulation, boolean changeCompFactorTroughIterations, double maxSelectionPressure, ISelection selection, double successRatio, int numberOfPopulations, double[][] distanceBetweenLocations, double[][] transportUnitsBetweenObjects, int numberOfObjects){
		this.mutationChance = mutationChance;
		this.minPopulation = minPopulation;
		this.maxPopulation = maxPopulation;
		this.numberOfObjects = numberOfObjects;
		this.numberOfPopulations = numberOfPopulations;
		this.changeCompFactorTroughIterations = changeCompFactorTroughIterations;
		this.maxSelectionPressure = maxSelectionPressure;
		this.selection = selection;
		this.successRatio = successRatio;
		this.distanceBetweenLocations = distanceBetweenLocations;
		this.transportUnitsBetweenObjects = transportUnitsBetweenObjects;
		generateStartingPopulations();
		
	}

	/**
	 * Randomly initializes the starting number of populations
	 */
	private void generateStartingPopulations() {
		populations = new LinkedList<>();
		LinkedList<Integer> objects = new LinkedList<>();
		for(int j=0;j<numberOfObjects;j++){
			objects.add(j);
		}
		
		for(int i=0;i<numberOfPopulations;i++){
			LinkedList<ObjectPlacementSolution> current = new LinkedList<>();
			for(int j=0;j<minPopulation;j++){
				ObjectPlacementSolution object = new ObjectPlacementSolution(objects, distanceBetweenLocations, transportUnitsBetweenObjects);
				object.permutate();
				current.add(object);
			}
			populations.add(current);
		}
	}

	@Override
	public LinkedList<ObjectPlacementSolution> run() {
		while(populations.size()>1){

			System.out.println("Population size: " + populations.size());
			for(int i=0;i<populations.size();i++){
				System.out.println("Currently running genetic algorithm for: " + (i+1) + ". population");
				RAPGAlgorithm newAlg = new RAPGAlgorithm(mutationChance, minPopulation, maxPopulation, changeCompFactorTroughIterations, maxSelectionPressure, selection, successRatio, populations.get(i));
				populations.set(i, newAlg.run());
			}
			LinkedList<ObjectPlacementSolution> populationToRemove = populations.getLast();
			int addToPopulation=0;
			int numberOfPopulations = populations.size();
			for(int i=populationToRemove.size()-1;i>=0;i--){
				populations.get(addToPopulation%numberOfPopulations).add(populationToRemove.get(i));
				populationToRemove.removeLast();
				addToPopulation++;
			}
			populations.removeLast();
		}
		System.out.println("Population size: 1");
		System.out.println("Running algorithm for last population");
		LinkedList<ObjectPlacementSolution> lastPopulation = populations.getFirst();
		
		RAPGAlgorithm newAlg = new RAPGAlgorithm(mutationChance, minPopulation, maxPopulation, changeCompFactorTroughIterations, maxSelectionPressure, selection, successRatio, lastPopulation);
		lastPopulation = newAlg.run();
		Collections.sort(lastPopulation);
		System.out.println("Fitness: "+lastPopulation.get(0).fitness + " object placement: " + lastPopulation.get(0).LocationOfObjects);
		return lastPopulation;
	}
}
