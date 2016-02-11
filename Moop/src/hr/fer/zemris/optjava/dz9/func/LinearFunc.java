package hr.fer.zemris.optjava.dz9.func;

import hr.fer.zemris.optjava.dz9.interfaces.IFunc;

public class LinearFunc implements IFunc{

	@Override
	public double[] f(double[] v) {
		if (v.length < 1)
			throw new IllegalArgumentException("Vector dimension too small.");
		return new double[] { v[0] };
	}

}
