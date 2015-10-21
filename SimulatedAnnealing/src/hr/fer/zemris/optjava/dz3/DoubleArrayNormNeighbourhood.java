package hr.fer.zemris.optjava.dz3;

import java.util.Random;
/**
 * Class implements INeighbourhood for double array solution objects and calculates it as a normal distribution
 *
 */

public class DoubleArrayNormNeighbourhood implements INeighbourhood<DoubleArraySolution>{
	private double[] deltas;
	Random rand;
	/**
	 * Constructor for DoubleArrayNormNeighbourhood object
	 * @param deltas the interval for each variable
	 */
	public DoubleArrayNormNeighbourhood(double[] deltas){
		this.deltas = deltas;
		rand = new Random(System.currentTimeMillis());
	}
	
	@Override
	public DoubleArraySolution randomNeighbour(DoubleArraySolution object) {
		
		DoubleArraySolution newSolution = object.duplicate();
		int length = newSolution.values.length;
		for(int i=0;i<length;i++){
			newSolution.values[i] += rand.nextGaussian() * deltas[i];
		}
		return newSolution;
	}

}
