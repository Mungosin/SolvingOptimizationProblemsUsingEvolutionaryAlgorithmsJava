package hr.fer.zemris.optjava.dz3;

/**
 * This class implements IDecoder interface, and allows the object of type DoubleArraySolution to pass trough, returning his value without decoding
 *
 */
public class PassTroughDecoder implements IDecoder<DoubleArraySolution>{

	@Override
	public double[] decode(DoubleArraySolution object) {
		return object.values.clone();
	}

	@Override
	public void decode(DoubleArraySolution object, double[] result) {
		result = object.values.clone();
	}

}
