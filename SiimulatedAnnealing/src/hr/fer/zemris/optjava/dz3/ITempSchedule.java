package hr.fer.zemris.optjava.dz3;
/**
 * ITempSchedule interface defines all methods required for variations of temperature schedules used in simulated annealing algorithm
 *
 */
public interface ITempSchedule {
	/**
	 * Method gets the value of next temperature
	 * @return the next temperature
	 */
	double getNextTemperature();
	
	/**
	 * Method gets the inner loop counter
	 * @return inner loop counter
	 */
	int getInnerLoopCounter();
	
	/**
	 * Method gets the outer loop counter
	 * @return outer loop counter
	 */
	int getOuterLoopCounter();
}
