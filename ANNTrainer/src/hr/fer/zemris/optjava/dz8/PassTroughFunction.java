package hr.fer.zemris.optjava.dz8;

/**
 * Class implements a function that returns the given input
 *
 */
public class PassTroughFunction implements IFunction{

	@Override
	public double valueAt(double point) {
		return point;
	}
	
}
