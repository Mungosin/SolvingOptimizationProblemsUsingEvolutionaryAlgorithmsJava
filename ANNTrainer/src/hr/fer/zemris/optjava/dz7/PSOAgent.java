package hr.fer.zemris.optjava.dz7;

import java.util.Arrays;
import java.util.Random;

/**
 * Class implements methods and attributes for a PSO agent
 *
 */
public class PSOAgent {
	double[] velocity;
	double[] position;
	double[] personalBest;
	int coordinateLength;
	Evaluator evaluator;
	
	/**
	 * Constructor for PSOAgent
	 * @param coordinateLength coordinate length
	 * @param evaluator evaluator used to evaluate the solution
	 */
	public PSOAgent(int coordinateLength, Evaluator evaluator){
		this.evaluator = evaluator;
		this.coordinateLength=coordinateLength;
		this.personalBest = new double[coordinateLength];
		velocity = new double[coordinateLength];
		position = new double[coordinateLength];
		personalBest = Arrays.copyOf(position, position.length);
	}
	
	/** 
	 * Method is used to calculate the next position
	 * @param bestPosition best known position
	 * @param rand Random number generator
	 * @param inertiaFactor factor of inertia
	 */
	public void calculateNext(double[] bestPosition, Random rand, double inertiaFactor){
		for(int i=0;i<coordinateLength;i++){
			velocity[i] = inertiaFactor * velocity[i] + Constants.c1 *rand.nextDouble()*(bestPosition[i] - position[i]) + Constants.c2 * rand.nextDouble()*(this.personalBest[i] - position[i]);
			if(velocity[i]<-1){
				velocity[i] = -1;
			}else if(velocity[i]>1){
				velocity[i] = 1;
			}
			position[i] += velocity[i];
		}
		if(evaluator.evaluate(this.position)< evaluator.evaluate(this.personalBest)){
			this.personalBest = Arrays.copyOf(position, position.length);
		}
	}
	
	/**
	 * Method is used to randomize the first position and velocity of the agent
	 * @param rand Random number generator
	 */
	public void randomize(Random rand){
		for(int i=0;i<coordinateLength;i++){
			velocity[i] = rand.nextDouble() * (2) -1;
			position[i] = rand.nextDouble() * (2) -1;
		}
	}
	
	/**
	 * Method returns the copy of the agents position
	 * @return copied position array
	 */
	public double[] getCopyOfPosition(){
		double[] pos = new double[position.length];
		for(int i=0;i<position.length;i++){
			pos[i] = position[i];
		}
		return pos;
	}
}
