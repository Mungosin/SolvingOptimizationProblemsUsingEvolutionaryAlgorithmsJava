package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class DoubleArrayNormNeighbourhood implements INeighbourhood<DoubleArraySolution>{
	private double[] deltas;
	Random rand;
	
	public DoubleArrayNormNeighbourhood(double[] deltas){
		this.deltas = deltas;
		rand = new Random(System.currentTimeMillis());
	}
	
	@Override
	public DoubleArraySolution randomNeighbour(DoubleArraySolution object) {
		
		DoubleArraySolution newSolution = object.duplicate();
		int length = newSolution.values.length;
		for(int i=0;i<length;i++){
			newSolution.values[i] = rand.nextGaussian() * deltas[i] + newSolution.values[i];
		}
		return newSolution;
	}

}
