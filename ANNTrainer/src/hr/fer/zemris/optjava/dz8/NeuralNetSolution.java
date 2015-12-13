package hr.fer.zemris.optjava.dz8;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz8.Evaluator;
/**
 * Class represents the solution of neural network
 *
 */
public class NeuralNetSolution {
	double[] position;
	int coordinateLength;
	Evaluator evaluator;
	
	/**
	 * Constructor for NeuralNetSolution
	 * @param coordinateLength coordinate length
	 * @param evaluator evaluator used to evaluate the solution
	 */
	public NeuralNetSolution(int coordinateLength, Evaluator evaluator){
		this.evaluator = evaluator;
		this.coordinateLength=coordinateLength;
		position = new double[coordinateLength];
	}
	
	/**
	 * Method is used to randomize the first solution vector
	 * @param rand Random number generator
	 */
	public void randomizeStarting(Random rand){
		for(int i=0;i<coordinateLength;i++){
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
