package hr.fer.zemris.optjava.dz5.part2;

import java.util.LinkedList;

/**
 * Interface defines the required methods for optimization algorithms
 *
 * @param <T> has to extend SingleObjective solution
 */
public interface IOptAlgorithm<T> {
	/**
	 * Method runs the optimization algorithm
	 */
	T run();
}
