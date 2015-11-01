package hr.fer.zemris.optjava.dz4.part2;
/**
 * Interface defines the required methods for optimization algorithms
 *
 * @param <T> has to extend SingleObjective solution
 */
public interface IOptAlgorithm {
	/**
	 * Method runs the optimization algorithm
	 */
	void run();
}
