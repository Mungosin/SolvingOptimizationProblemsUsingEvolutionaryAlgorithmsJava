package hr.fer.zemris.optjava.dz10.func;

import hr.fer.zemris.optjava.dz10.interfaces.IFunc;

public class PowFunc implements IFunc {

	private int index;

	public PowFunc(int index) {
		this.index = index;
	}

	@Override
	public double[] f(double[] v) {
		if (v.length < index + 1)
			throw new IllegalArgumentException("Vector dimension too small.");
		return new double[] { 
				v[index] * v[index]
		};
	}

}
