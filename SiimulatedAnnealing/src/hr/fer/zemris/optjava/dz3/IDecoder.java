package hr.fer.zemris.optjava.dz3;

public interface IDecoder<T> {

	double[] decode(T object);
	void decode(T object, double[] result);
}
