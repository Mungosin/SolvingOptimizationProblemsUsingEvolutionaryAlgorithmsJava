package hr.fer.zemris.optjava.dz7;

import java.util.Arrays;
import java.util.Random;

/**
 * Class implements methods used for the antibody in the clon algorithm
 *
 */
public class AntiBody implements Comparable<AntiBody>{
	int coordinateLength;
	Evaluator evaluator;
	double affinity;
	double[] position;
	
	/**
	 * Constructor for the antibody
	 * @param coordinateLength number of coordinates
	 * @param evaluator evaluator function used to evaluate the quality of the solution
	 */
	public AntiBody(int coordinateLength, Evaluator evaluator){
		this.evaluator = evaluator;
		this.coordinateLength=coordinateLength;
		this.position = new double[coordinateLength];
	}
	
	/**
	 * Method randomizes the position of the antibody
	 * @param rand Random number generator
	 */
	public void randomize(Random rand){
		for(int i=0;i<coordinateLength;i++){
			position[i] = rand.nextDouble() * (2) -1;
		}
	}
	
	/**
	 * Method evaluates the current solution of the antibody
	 */
	public void evaluate(){
		affinity = evaluator.evaluate(position);
	}
	
	/**
	 * Method is used to mutate the antibody
	 * @param mutationChance chance to mutate every coordinate
	 * @param rand Random number generator
	 */
	public void mutate(double mutationChance, Random rand){
		for(int i=0;i<this.coordinateLength;i++){
			if(rand.nextDouble()>mutationChance){
				position[i] += rand.nextGaussian() * Constants.sigma;
			}
		}
	}

	@Override
	public int compareTo(AntiBody o) {
		return Double.compare(this.affinity, o.affinity);
	}

	
}
