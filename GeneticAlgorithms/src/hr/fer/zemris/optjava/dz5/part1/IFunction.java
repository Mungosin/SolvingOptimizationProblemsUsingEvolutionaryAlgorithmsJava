package hr.fer.zemris.optjava.dz5.part1;

/**
 * Interface defines methods required for genetic algorithm RAPGA to optimize functions
 *
 */
public interface IFunction {
	
	/**
	 * Method calculates value of the function at the given point
	 * @param point represented by a bit vector at which the value is being calculated
	 * @return calculated value of the function in the given point
	 */
	double valueAt(BitVectorSolution sol);
}
