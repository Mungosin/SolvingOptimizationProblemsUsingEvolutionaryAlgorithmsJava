package hr.fer.zemris.optjava.dz3;

import java.lang.reflect.Constructor;
import java.util.Random;

public class DoubleArraySolution extends SingleObjectiveSolution{
	double[] values;
	
	public DoubleArraySolution(int size){
		values = new double[size];
	}
	
	public DoubleArraySolution newLikeThis(){
		return new DoubleArraySolution(values.length);
	}
	
	public DoubleArraySolution duplicate(){
		DoubleArraySolution clone = new DoubleArraySolution(values.length);
		clone.values = this.values.clone();
		return clone;
	}
	
	public void Randomize(Random rand, double[] lowerBounds, double[] upperBounds){
		int length = values.length;
		for(int i=0;i<length;i++){
			values[i] = rand.nextDouble() * (upperBounds[i] - lowerBounds[i]) + lowerBounds[i];
		}
	}
}
