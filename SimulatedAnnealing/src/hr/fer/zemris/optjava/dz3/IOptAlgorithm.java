package hr.fer.zemris.optjava.dz3;
/**
 * Interface implements the required methods for optimization algorithms
 *
 * @param <T> has to extend SingleObjective solution
 */
public interface IOptAlgorithm<T extends SingleObjectiveSolution> {
	/**
	 * Method runs the optimization algorithm
	 */
	void run();
}
