package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Defines methods that every selection algorithm needs to have
 *
 */
public interface ISelection {
	
	/**
	 * Method is used to find the next parent from the given list of solutions
	 * @param currentSolutions list of solutions to choose from
	 * @return the found parent
	 */
	public BoxSolution findParent(LinkedList<BoxSolution> currentSolutions);
}
