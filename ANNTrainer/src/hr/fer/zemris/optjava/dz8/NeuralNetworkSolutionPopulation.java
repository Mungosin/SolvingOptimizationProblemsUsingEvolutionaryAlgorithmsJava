package hr.fer.zemris.optjava.dz8;

import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.PSOAgent;

/**
 * Class implements population of neural network solutions
 *
 */
public class NeuralNetworkSolutionPopulation {
	public LinkedList<NeuralNetSolution> solutions;
	public int numberOfSolutions;
	public int numberOfCoordinates;
	public Evaluator evaluator;
	public Random rand;
	public double[] bestPosition;
	public int bestPositionIndex;
	
	/**
	 * Constructor for NeuralNetworkSolutionPopulation
	 * @param numberOfSolutions number of solutions in the population
	 * @param numberOfCoordinates coordinate length
	 * @param evaluator evaluator class to evaluate the quality of the solution
	 */
	public NeuralNetworkSolutionPopulation(int numberOfSolutions, int numberOfCoordinates, Evaluator evaluator){
		this.evaluator = evaluator;
		this.numberOfCoordinates = numberOfCoordinates;
		this.numberOfSolutions = numberOfSolutions;
		this.solutions = new LinkedList<>();
		
		rand = new Random(System.currentTimeMillis());
		if(numberOfCoordinates <0 || numberOfSolutions <0){
			System.out.println("number of coordinates and solutions need to be positive");
			System.exit(0);
		}
		for(int i=0;i<numberOfSolutions; i++){;
			NeuralNetSolution solution = new NeuralNetSolution(numberOfCoordinates, evaluator);
			solution.randomizeStarting(rand);
			if(this.bestPosition == null  || evaluator.evaluate(solution.position) < evaluator.evaluate(this.bestPosition)){
				this.bestPosition = solution.getCopyOfPosition();
				this.bestPositionIndex = i;
			}
			solutions.add(solution);
		}
	}
	
	public void calculateNextPosition(){
		LinkedList<NeuralNetSolution> nextPopulation = new LinkedList<NeuralNetSolution>();
		int length = solutions.size();
		int firstHelper=0;
		int secondHelper=0;
		boolean condition = true;
		for(int i=0;i<length;i++){
			NeuralNetSolution current = solutions.get(i);
			
			
			condition = false;
			while(!condition){
				if(!(firstHelper != secondHelper && firstHelper!= i && firstHelper != this.bestPositionIndex)){
					firstHelper = rand.nextInt(length);
				}
				if(!(firstHelper != secondHelper && secondHelper!= i && secondHelper != this.bestPositionIndex)){
					secondHelper = rand.nextInt(length);
				}
				if(firstHelper != secondHelper && secondHelper!= i && i!= firstHelper && firstHelper != this.bestPositionIndex && secondHelper != this.bestPositionIndex){
					condition=true;
				}
			}
			NeuralNetSolution newVector = VectorOperations.mutateVector(current, this.solutions.get(firstHelper), this.solutions.get(secondHelper), this.solutions.get(this.bestPositionIndex));
			newVector = VectorOperations.Crossover(newVector, current, rand);
			
			
			if(evaluator.evaluate(newVector.position)>evaluator.evaluate(current.position)){
				newVector = current;
				nextPopulation.add(newVector);
			}else {
				nextPopulation.add(newVector);
			}
			
			if(evaluator.evaluate(bestPosition) >= evaluator.evaluate(newVector.position)){
				
				this.bestPosition = newVector.getCopyOfPosition();
				this.bestPositionIndex = i;
			}
		}
		
		this.solutions = nextPopulation;
		
	}

	public double[] getBestSol() {
		return this.bestPosition;
	}
}
