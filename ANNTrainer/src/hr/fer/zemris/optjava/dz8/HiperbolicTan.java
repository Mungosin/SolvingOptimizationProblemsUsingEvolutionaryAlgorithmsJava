package hr.fer.zemris.optjava.dz8;
/**
 * Class implements a hiperbolic tangens function
 * 
 */
public class HiperbolicTan implements IFunction{

	@Override
	public double valueAt(double point) {
		return 2/(1+ Math.exp(-point)) -1;
	}
}
