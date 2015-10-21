package hr.fer.zemris.optjava.dz3;
/**
 * Interface defines all methods required to generate the neighbourhood of a parameter T that extends SingleObjectiveSolution 
 *
 * @param <T> has to extend SingleObjective solution
 */
public interface INeighbourhood<T extends SingleObjectiveSolution> {
	
	/**
	 * Method calculates a random neighbour of the T object
	 * @param object whose neighbour is being calculated
	 * @return the neighbour of the given object
	 */
	T randomNeighbour(T object);
}
