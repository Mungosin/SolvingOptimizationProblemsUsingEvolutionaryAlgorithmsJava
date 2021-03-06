package hr.fer.zemris.optjava.dz10.interfaces;


import hr.fer.zemris.optjava.dz10.MOOPProblem;
import hr.fer.zemris.optjava.dz10.nsga.NSGASolution;

import java.util.LinkedList;

public interface IMoopOptFun {

	public int getDecisionSpaceDim();

	public int getObjectiveSpaceDim();

	public double[] getMinDomainVals();

	
	public double[] getMaxDomainVals();

	
	public void evaluatePopulation(LinkedList<NSGASolution> population, double alpha, double sigma);

	public MOOPProblem getMOOPProblem();
	
	public LinkedList<LinkedList<NSGASolution>> getParetoFronts(LinkedList<NSGASolution> population);
}
