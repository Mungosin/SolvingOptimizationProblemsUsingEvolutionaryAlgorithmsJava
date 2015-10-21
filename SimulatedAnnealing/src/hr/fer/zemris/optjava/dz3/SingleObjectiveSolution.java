package hr.fer.zemris.optjava.dz3;
/**
 * Class implements a simple objective solution to a problem, and basic attributes and methods
 *
 */
public class SingleObjectiveSolution {
	double fitness;
	double value;
	
	/**
	 * Constructor for single objective solution
	 */
	public SingleObjectiveSolution(){
		
	}
	
	/**
	 * Compares the given object to this object
	 * @param object to which this object is being compared
	 * @return 1 if this object is greater, -1 if it isnt and 0 if they're equal
	 */
	int compareTo(SingleObjectiveSolution object){
		if(object.fitness<this.fitness){
			return 1;
		}else if(object.fitness>this.fitness){
			return -1;
		}
		return 0;
	}
}
