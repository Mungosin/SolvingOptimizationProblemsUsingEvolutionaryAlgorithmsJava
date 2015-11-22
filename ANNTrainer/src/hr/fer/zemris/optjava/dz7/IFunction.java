package hr.fer.zemris.optjava.dz7;

/**
 * Interface defines methods required for every function class
 * 
 */
public interface IFunction {
	
	/**
	 * Method requires value of the function at the given point
	 * @param point point where the value is wanted
	 * @return value of the function at the given point
	 */
	public double valueAt(double point);
}
