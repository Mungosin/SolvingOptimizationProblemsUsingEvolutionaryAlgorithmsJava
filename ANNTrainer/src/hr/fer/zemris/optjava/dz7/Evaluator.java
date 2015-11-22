package hr.fer.zemris.optjava.dz7;

/**
 * Interface defines methods required for every evaluator
 *
 */
public interface Evaluator {
	/**
	 * Method evaluates the function output for the given coordinates
	 * @param coordinates array of coordinates for the function
	 * @return value of the function in the given coordinates
	 */
	public double evaluate(double[] coordinates);
	
	/**
	 * Method is used to get the statistics of the function 
	 * @param coordinates coordinates used to calculate the statistics
	 * @return double array of statistics
	 */
	public double[] getStatistics(double[] coordinates);
}
