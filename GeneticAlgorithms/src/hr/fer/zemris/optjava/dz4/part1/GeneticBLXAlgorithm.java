package hr.fer.zemris.optjava.dz4.part1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
/**
 * Class implements all algorithms used in BLX alpha selection to find the parameters that give the minimum value of the given function
 *
 */
public class GeneticBLXAlgorithm implements IOptAlgorithm{
	private int populationSize;
	private double minError;
	private int maxGenerations;
	private ISelection selection;
	private double sigma;
	private IFunction func;
	private Random rand;
	private ArrayList<DoubleArraySolution> currentPopulation = new ArrayList<>();

	/**
	 * Constructor for GeneticBLXAlgorithm
	 * @param populationSize size of the population
	 * @param minError maximal value  of the function  being minimized wanted
	 * @param maxGenerations number of generations within the algorithm needs to find the solution
	 * @param selection object used to select the best parents from the population
	 * @param sigma standard deviation used in gaussian distribution
	 * @param func function being minimized
	 */
	public GeneticBLXAlgorithm(int populationSize, double minError, int maxGenerations, ISelection selection, double sigma, IFunction func){
		this.populationSize = populationSize;
		this.minError = minError;
		this.maxGenerations = maxGenerations;
		this.selection = selection;
		this.sigma = sigma;
		this.func = func;
		
		DoubleArraySolution sol;
		this.rand = new Random(System.currentTimeMillis());
		
		for(int i=0; i<populationSize;i++){
			sol = new DoubleArraySolution(Constants.numberOfVariables);
			sol.Randomize(rand, Constants.lowerBounds, Constants.upperBounds);
			sol.fitness = sol.value = func.valueAt(sol.values);
			currentPopulation.add(sol);
		}
	}
	
	/**
	 * Method mutates the child by adding to each variable a random number generated with the gaussian distribution that has the standard deviation sigma given in the
	 * constructor
	 * @param child solution being mutated
	 */
	private void mutateSolution(DoubleArraySolution child){
		int length = child.values.length;
		for(int i=0;i<length;i++){
			child.values[i]+=rand.nextGaussian()*sigma;
		}
	}
	/**
	 * Method is used to create the next population from the current by repeatedly using the selection object to find parents and crossing two parents over to form
	 * a new child and then mutating it and adding it to the new pool.
	 * By modifying attribute maxAbortions the method will discard the number of children if their fitness is worse than the parents maximum number of times defined by the maxAbortions
	 * before accepting the next child
	 * @param currentPop population used in creation of the new population
	 * @return the newly created population of solutions
	 */
	private ArrayList<DoubleArraySolution> nextPopulation(ArrayList<DoubleArraySolution> currentPop){
		ArrayList<DoubleArraySolution> nextPop = new ArrayList<DoubleArraySolution>();
		int popSize =0;
		int currentPopSize = currentPop.size();
		int numberOfAbortions = Constants.maxAbortions;
		if(currentPopSize>Constants.saveBest){
			for(int i=0;i<Constants.saveBest;i++){
				nextPop.add(currentPop.get(i));
			}
			popSize = Constants.saveBest;
		}else{
			for(int i=0;i<currentPopSize;i++){
				nextPop.add(currentPop.get(i));
			}
			popSize = currentPopSize;
		}
		DoubleArraySolution firstParent;
		DoubleArraySolution secondParent;
		DoubleArraySolution child;
		while(popSize<populationSize){
			firstParent = selection.findParent(currentPop);
			secondParent = selection.findParent(currentPop);
			child = createChild(firstParent, secondParent);
			if(child.value > firstParent.value && child.value > secondParent.value && numberOfAbortions>1){
				numberOfAbortions--;
				continue;
			}else {

				nextPop.add(child);
				popSize++;
				numberOfAbortions = Constants.maxAbortions;
			}

		}
		return nextPop;
	}
	
	/**e
	 * Creates a new child and mutates it from the first and second parent by
	 * finding th minimal value of each variable between parents and maximal number, then creates a difference of every cmax and cmin for the ith variable.
	 * Then uniformly selects a value for the ith variable from interval [CMINi - CDIFFERENCEi * alpha, CMAXi+ CDIFFERENCEi * alpha], alpha is defined in the constants class
	 * @param firstParent
	 * @param secondParent
	 * @return
	 */
	private DoubleArraySolution createChild(DoubleArraySolution firstParent, DoubleArraySolution secondParent){
		DoubleArraySolution child = new DoubleArraySolution(Constants.numberOfVariables);
		double[] CMin = new double[Constants.numberOfVariables];
		double[] CMax = new double[Constants.numberOfVariables];
		double[] CDifference = new double[Constants.numberOfVariables];
		double upperB, lowerB;
		for(int i=0; i<Constants.numberOfVariables;i++){
			CMin[i] = Math.min(firstParent.values[i], secondParent.values[i]);
			CMax[i] = Math.max(firstParent.values[i], secondParent.values[i]);
			CDifference[i] = CMax[i] - CMin[i];
		}
		for(int i=0;i<Constants.numberOfVariables;i++){
			upperB = CMax[i] + Constants.alpha * CDifference[i];
			lowerB = CMin[i] - Constants.alpha * CDifference[i];
			child.values[i] = rand.nextDouble() * (upperB-lowerB) + lowerB;
		}
		mutateSolution(child);
		child.fitness = child.value = func.valueAt(child.values);
		return child;
	}
	
	@Override
	public void run() {
		int numberOfGenerations = maxGenerations;
		DoubleArraySolution previous = null;
		
		while(numberOfGenerations-->0){

			currentPopulation =nextPopulation(currentPopulation);
			currentPopulation.sort(new Comparator<DoubleArraySolution>(){

				@Override
				public int compare(DoubleArraySolution o1, DoubleArraySolution o2) {
					if(o1.value -o2.value>0){
						return 1;
					}else if(o1.value - o2.value<0){
						return -1;
					}else{
						return 0;
					}
				}
				
			});
			
			if (this.minError - currentPopulation.get(0).value>0){
				System.out.println("Best found solution");
				double pointValues[] = currentPopulation.get(0).values;
				int pointLength = pointValues.length;
				System.out.print("(");
				for(int z=0;z<pointLength;z++){
					if(z==pointLength-1){
						System.out.format("%10.10f", pointValues[z]);
						break;
					}
					System.out.format("%10.10f, ", pointValues[z]);
				}
				System.out.println(") -> function value = " + func.valueAt(pointValues));
				return;
			}
			
			if(currentPopulation.get(0) == previous){
				continue;
			}
			previous = currentPopulation.get(0);
			
			double pointValues[] = currentPopulation.get(0).values;
			int pointLength = pointValues.length;
			for(int z=0;z<pointLength;z++){
				if(z==pointLength-1){
					System.out.format("%10.10f", pointValues[z]);
					break;
				}
				System.out.format("%10.10f, ", pointValues[z]);
			}
			System.out.println(") -> function value = " + func.valueAt(pointValues));

		}
	}

}
