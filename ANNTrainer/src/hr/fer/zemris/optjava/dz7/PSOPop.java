package hr.fer.zemris.optjava.dz7;

import java.util.LinkedList;
import java.util.Random;
/**
 * Class implements population of a PSO algorithm
 *
 */
public class PSOPop {
	LinkedList<PSOAgent> agents;
	boolean global;
	double[] bestPosition;
	Random rand;
	Evaluator evaluator;
	int neighbourhood;
	int numberOfAgents;
	double inertiaFactor;
	
	/**
	 * Constructor for PSO poopulation
	 * @param numberOfAgents number of agents in the population
	 * @param global flag that tells the population to use global best if true or local best if false
	 * @param coordinateLength coordinate length
	 * @param evaluator evaluator class to evaluate the quality of the solution
	 * @param neighbourhood number of local solutions to use in consideration when calculating best known for a particle
	 */
	public PSOPop(int numberOfAgents, boolean global, int coordinateLength, Evaluator evaluator, int neighbourhood){
		this.agents = new LinkedList<PSOAgent>();
		this.rand = new Random(System.currentTimeMillis());
		this.evaluator = evaluator;
		this.bestPosition = null;
		this.neighbourhood = neighbourhood;
		this.numberOfAgents = numberOfAgents;
		this.inertiaFactor = Constants.startingInertiaFactor;
		
		if(!global && neighbourhood <1){
			throw new IllegalArgumentException("neighbourhood can't be less than one");
		}
		for(int i=0;i<numberOfAgents; i++){;
			PSOAgent agent = new PSOAgent(coordinateLength, evaluator);
			agent.randomize(rand);
			if(this.bestPosition == null  || evaluator.evaluate(agent.position) < evaluator.evaluate(this.bestPosition)){
				this.bestPosition = agent.getCopyOfPosition();
			}
			agents.add(agent);
		}
		this.global = global;
	}
	

	/**
	 * Method is used to update the value of inertia by substracting the value from the inertia value
	 * @param value value that decreases inertia
	 */
	public void updateInertia(double value){
		this.inertiaFactor -= value;
	}
	
	/**
	 * Method is used to calculate the next position of the particles
	 */
	public void calculateNextPosition(){
		if(global){
			calculateNextGlobalEdition();
		}else {
			calculateNextNeighbourhoodEdition();
		}
	}
	
	/**
	 * Method calculates next position of agents using global best solution
	 */
	private void calculateNextGlobalEdition(){
		
		for(PSOAgent agent : agents){
			agent.calculateNext(bestPosition, rand, inertiaFactor);
			if(evaluator.evaluate(bestPosition) > evaluator.evaluate(agent.position)){
				bestPosition = agent.getCopyOfPosition();
			}
		}
	}
	/**
	 * Method calculates next position of agents using local best solution
	 */
	private void calculateNextNeighbourhoodEdition(){
		for(int i=0;i<this.numberOfAgents;i++){
			PSOAgent agent = agents.get(i);
			double[] bestPos = null;
			int size=0;
			while(size<(2*this.neighbourhood+1)){
				PSOAgent current = agents.get((this.numberOfAgents+ i-this.neighbourhood+size)%this.numberOfAgents);
				if(bestPos == null || evaluator.evaluate(bestPos) > evaluator.evaluate(current.personalBest)){
					bestPos = current.personalBest;
				}
				size++;
			}
			agent.calculateNext(bestPos,rand,inertiaFactor);
			if(evaluator.evaluate(bestPosition) > evaluator.evaluate(agent.position)){
				bestPosition = agent.getCopyOfPosition();
			}
		}
	}
	
	/**
	 * Method returns best known solution of the population
	 * @return best solution
	 */
	public double[] getBestSol(){
		return this.bestPosition;
	}
}
