package hr.fer.zemris.optjava.dz10.func;

import hr.fer.zemris.optjava.dz10.interfaces.IFunc;

public class RatioFunc implements IFunc{

	@Override
	public double[] f(double[] v) {
		if (v.length < 2)
			throw new IllegalArgumentException("Vector dimension too small.");
		return new double[] { (v[1] + 1) / v[0] };
	}

}
