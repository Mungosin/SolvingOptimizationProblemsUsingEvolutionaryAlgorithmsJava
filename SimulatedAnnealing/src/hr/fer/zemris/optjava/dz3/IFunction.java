package hr.fer.zemris.optjava.dz3;

/**
 * Interface defines methods required for simulated annealing algorithm to optimize functions
 *
 */
public interface IFunction {
	
	/**
	 * Method calculates value of the function at the given point
	 * @param point at which the value is being calculated
	 * @return calculated value of the function in the given point
	 */
	double valueAt(double[] point);
}
