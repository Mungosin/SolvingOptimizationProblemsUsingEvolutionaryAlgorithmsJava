package hr.fer.zemris.optjava.dz10.operations;


import hr.fer.zemris.optjava.dz10.nsga.NSGASolution;

import java.util.Random;

public class Mutation {
	private  Random rand;
	private double[] minDomainVal;
	private double[] maxDomainVal;

	public Mutation(double[] minDomainVal, double[] maxDomainVal, Random rand) {
		this.minDomainVal = minDomainVal;
		this.maxDomainVal = maxDomainVal;
		this.rand = rand;
	}
	
	public NSGASolution mutation(NSGASolution subject, double sigma) {
		NSGASolution mutatedSolution = subject.duplicate();
		int solutionDim = mutatedSolution.values.length;
		double[] values = mutatedSolution.values;
		for (int i = 0; i < solutionDim; i++) {
			values[i] += rand.nextGaussian() * sigma;
			
			if (values[i] < minDomainVal[i])
				values[i] = minDomainVal[i];
			if (values[i] > maxDomainVal[i])
				values[i] = maxDomainVal[i];
		}
		
		return mutatedSolution;
	}
}
