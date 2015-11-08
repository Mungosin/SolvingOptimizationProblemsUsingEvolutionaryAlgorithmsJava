package hr.fer.zemris.optjava.dz5.part1;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class RAPGAlgorithm implements IOptAlgorithm{
	protected LinkedList<BitVectorSolution> population;
	protected int maxPopulation;
	protected int minPopulation;
	protected boolean changeCompFactorTroughIterations;
	protected double maxSelectionPressure;
	protected int vectorLength;
	protected IFunction func;
	protected double ActSelPress;
	private double compFactor;
	private ISelection selection;
	private Random rand;
	private double successRatio;
	private double mutationChance;
	
	/**
	 * Constructor for RAPG algorithm class
	 * @param mutationChance chance the new child will mutate
	 * @param minPopulation minimal number of solutions in a population
	 * @param maxPopulation maximal number of solutions in a population 
	 * @param changeCompFactorTroughIterations boolean that if true increases the comparison factor after a number of iterations
	 * @param maxSelectionPressure maximal selection pressure determines the number of iterations the algorithm has to create a new population
	 * @param vectorLength length of the solution vector
	 * @param func function being maximised
	 * @param selection is an object that determines how the parent for the next child will be selected 
	 * @param successRatio percentage of better children the next population is required to have
	 */
	public RAPGAlgorithm(double mutationChance,int minPopulation, int maxPopulation, boolean changeCompFactorTroughIterations, double maxSelectionPressure, int vectorLength, IFunction func, ISelection selection, double successRatio){
		this.rand = new Random(System.currentTimeMillis());
		this.vectorLength = vectorLength;
		this.func = func;
		this.minPopulation = minPopulation;
		this.maxPopulation = maxPopulation;
		this.changeCompFactorTroughIterations = changeCompFactorTroughIterations;
		this.maxSelectionPressure = maxSelectionPressure;
		this.ActSelPress = 0;
		this.compFactor = 0;
		this.successRatio = successRatio;
		this.selection = selection;
		this.mutationChance = mutationChance;
		
		population = new LinkedList<BitVectorSolution>();
		int numberInPop=0;
		while(numberInPop<minPopulation){
			BitVectorSolution newSol = new BitVectorSolution(vectorLength,func);
			newSol.Randomize(rand);
			newSol.calculateFitness();
			if(population.add(newSol)){
				numberInPop++;
			}
		}
		
	}

	@Override
	public void run() {
		int numberOfIterations = 0;
		BitVectorSolution highestFound = new BitVectorSolution(vectorLength,func);
		highestFound.calculateFitness();
		while(true){
			Collections.sort(population);

			population = generateNextPopulationBothParentsTournament(population);
			if(population == null){
				break;
			}

			if(numberOfIterations>= Constants.changeCompFactorEveryNumberOfIterations){
				numberOfIterations=0;
				if(this.compFactor+Constants.compFactorIncrement<=1){
					this.compFactor +=Constants.compFactorIncrement;
				}
			}else{
				numberOfIterations++;
			}
			if(highestFound.fitness<population.getLast().fitness){
				highestFound = population.getLast();
				System.out.println("_____New better solution_____");
				System.out.println("fitness: " + highestFound.fitness+ " solution: " + highestFound );
			}
		}
	}

	/**
	 * Method is used to generate new population from the given population, new population can't contain duplicate elements
	 * @param population collection of elements from which the new population will be created
	 * @return the collection containing elements of the new population
	 */
	private LinkedList<BitVectorSolution> generateNextPopulationBothParentsTournament(LinkedList<BitVectorSolution> population) {
		LinkedList<BitVectorSolution> newPopulation = new LinkedList<BitVectorSolution>();
		LinkedList<BitVectorSolution> geneticPool = new LinkedList<BitVectorSolution>();
		while((1.*(newPopulation.size()+geneticPool.size())/population.size())<maxSelectionPressure){
			BitVectorSolution firstParent = selection.findParent(population).duplicate();
			BitVectorSolution secondParent = selection.findParent(population).duplicate();
			BitVectorSolution child = createChild(firstParent,secondParent);
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
	private boolean evaluateChild(BitVectorSolution child, BitVectorSolution firstParent,
			BitVectorSolution secondParent) {
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
	private void mutateChild(BitVectorSolution child) {
		int length = child.bits.length;
		int change = rand.nextInt(length);
		if(child.bits[change] == 1){
			child.bits[change] = 0;
		}else{
			child.bits[change] = 1;
		}
		
	}

	/**
	 * Method is being used to create a new child from the given parents 
	 * @param firstParent first parent of the child
	 * @param secondParent second parent of the child
	 * @return newly created child
	 */
	private BitVectorSolution createChild(BitVectorSolution firstParent, BitVectorSolution secondParent) {
		BitVectorSolution child = firstParent.newLikeThis();
		int bitLength = firstParent.bits.length;
		int firstPoint = rand.nextInt(bitLength);
		int secondPoint = rand.nextInt(bitLength);
		for(int i=0;i<bitLength;i++){
			if(i>= firstPoint && i<secondPoint){
				child.bits[i] = firstParent.bits[i];
			}else{
				child.bits[i] = secondParent.bits[i];
			}
		}
		return child;
	}
}
