package hr.fer.zemris.optjava.dz3;

/**
 * Interface defines the methods that all decoders are required implement  to enable simulated annealing algorithm to work properly
 *
 * @param <T> has to extend SingleObjective solution
 */
public interface IDecoder<T extends SingleObjectiveSolution> {

	/**
	 * Method decodes the object and returns the variable values as a double array
	 * @param object to be decoded
	 * @return values of variables in a double array
	 */
	double[] decode(T object);
	/**
	 * Method decodes the object and puts the decoded variable values in the result parameter
	 * @param object to be decoded
	 * @param result decoded value of the variables
	 */
	void decode(T object, double[] result);
}
