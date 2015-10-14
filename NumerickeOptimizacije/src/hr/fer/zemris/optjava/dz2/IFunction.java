package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.RealMatrix;

public interface IFunction {

	public int GetNumberOfVariables();
	
	public double GetValueOfFunction(RealMatrix point);
	
	public RealMatrix GetGradient(RealMatrix point);
}
