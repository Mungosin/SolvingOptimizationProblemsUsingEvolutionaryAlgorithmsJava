package hr.fer.zemris.optjava.dz9;

public interface MOOPProblem {

	void evaluate(double[] solution, double[] objectives);
	
	public int getDecisionSpaceDim();
	
	int getObjectiveSpaceDim();

	public double[] getMinDomainVals();
	
	public double[] getMaxDomainVals();
}
