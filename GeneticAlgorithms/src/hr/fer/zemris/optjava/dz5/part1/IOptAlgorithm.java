package hr.fer.zemris.optjava.dz5.part1;
/**
 * Interface defines the required methods for optimization algorithms
 *
 * @param <T> has to extend SingleObjective solution
 */
public interface IOptAlgorithm<T> {
	/**
	 * Method runs the optimization algorithm
	 */
	void run();
}
