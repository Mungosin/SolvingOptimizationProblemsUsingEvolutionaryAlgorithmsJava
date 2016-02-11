package hr.fer.zemris.optjava.dz11.genetic.operators;

import hr.fer.zemris.optjava.dz11.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.generic.ga.Solution;

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
	public Solution findParent(LinkedList<Solution> currentSolutions);
}
