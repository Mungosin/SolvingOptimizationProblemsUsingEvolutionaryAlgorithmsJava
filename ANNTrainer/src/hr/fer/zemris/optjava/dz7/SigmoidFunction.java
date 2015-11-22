package hr.fer.zemris.optjava.dz7;

/**
 * Class implements a sigmoid function
 * 
 */
public class SigmoidFunction implements IFunction{

	@Override
	public double valueAt(double point) {
		return 1/(1+ Math.exp(-point));
	}

}
