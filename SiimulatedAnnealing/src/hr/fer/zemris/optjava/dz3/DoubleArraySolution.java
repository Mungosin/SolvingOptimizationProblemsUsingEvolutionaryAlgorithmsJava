package hr.fer.zemris.optjava.dz3;

import java.util.Random;
/**
 * Class that extends SingleObjectiveSolution and implements methods and attributes required to represent the solution as a double array
 *
 */
public class DoubleArraySolution extends SingleObjectiveSolution{
	double[] values;
	
	/**
	 * Constructor for DoubleArraySolution object
	 * @param size of the double solution array
	 */
	public DoubleArraySolution(int size){
		values = new double[size];
	}
	
	/**
	 * Method is used to create a object of type DoubleArraySolution with the same length of double array
	 * @return new object of this type and same values length
	 */
	public DoubleArraySolution newLikeThis(){
		return new DoubleArraySolution(values.length);
	}
	
	/**
	 * Method clones this object
	 * @return the cloned object
	 */
	public DoubleArraySolution duplicate(){
		DoubleArraySolution clone = new DoubleArraySolution(values.length);
		clone.values = this.values.clone();
		return clone;
	}
	
	/**
	 * Method is used to create a random value for every variable
	 * @param rand Random number generator
	 * @param lowerBounds for each variable
	 * @param upperBounds for each variable
	 */
	public void Randomize(Random rand, double[] lowerBounds, double[] upperBounds){
		int length = values.length;
		for(int i=0;i<length;i++){
			values[i] = rand.nextDouble() * (upperBounds[i] - lowerBounds[i]) + lowerBounds[i];
		}
	}
}
