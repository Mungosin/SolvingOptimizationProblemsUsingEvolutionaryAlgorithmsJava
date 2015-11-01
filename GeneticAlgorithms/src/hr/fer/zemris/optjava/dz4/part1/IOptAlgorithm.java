package hr.fer.zemris.optjava.dz4.part1;
/**
 * Interface implements the required methods for optimization algorithms
 *
 * @param <T> has to extend SingleObjective solution
 */
public interface IOptAlgorithm {
	/**
	 * Method runs the optimization algorithm
	 */
	void run();
}
