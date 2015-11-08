package hr.fer.zemris.optjava.dz5.part2;

import java.util.Collections;
import java.util.LinkedList;
/**
 * Class is used to represent a solution to the quadratic assignment problem
 * @author Toni
 *
 */
public class ObjectPlacementSolution implements Comparable<ObjectPlacementSolution>{
	protected double fitness;
	protected LinkedList<Integer> LocationOfObjects;
	protected double[][] distanceBetweenLocations;
	protected double[][] transportUnitsBetweenObjects;
	
	/**
	 * Constructor for ObjectPlacementSolution class
	 * @param LocationOfObjects List of all objects whose optimal position is wanted
	 * @param distanceBetweenLocations distance between every location where the objects can be placed
	 * @param transportUnitsBetweenObjects defines number of elements every object is sending to another object
	 */
	public ObjectPlacementSolution(LinkedList<Integer> LocationOfObjects,double[][] distanceBetweenLocations, double[][] transportUnitsBetweenObjects ){
		this.distanceBetweenLocations = distanceBetweenLocations;
		this.transportUnitsBetweenObjects = transportUnitsBetweenObjects;
		this.LocationOfObjects = new LinkedList<>();
		for(Integer integ : LocationOfObjects){
			this.LocationOfObjects.add(new Integer(integ.intValue()));
		}
	}
	
	/**
	 * Method is used to calculate the fitness of this solution
	 */
	public void calculateFitness(){
		this.fitness = 0;
		int numOfObjects = this.LocationOfObjects.size();
		for(int i=0;i<numOfObjects;i++){
			int IthLocation = this.LocationOfObjects.get(i);
			for(int j=0;j<numOfObjects;j++){
				int JthLocation = this.LocationOfObjects.get(j);
				this.fitness += distanceBetweenLocations[IthLocation][JthLocation] * transportUnitsBetweenObjects[i][j];
			}
		}
	}
	
	/**
	 * Permutates the object placement
	 */
	public void permutate(){
		Collections.shuffle(this.LocationOfObjects);
		calculateFitness();
	}
	
	/**
	 * Method is used to create a duplicate of this object
	 * @return newly created duplicate
	 */
	public ObjectPlacementSolution duplicate(){
		return new ObjectPlacementSolution(LocationOfObjects, distanceBetweenLocations, transportUnitsBetweenObjects);
	}

	@Override
	public int compareTo(ObjectPlacementSolution o) {
		return Double.compare(this.fitness, o.fitness);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof ObjectPlacementSolution){
			return this.LocationOfObjects.equals(((ObjectPlacementSolution) o).LocationOfObjects);
		}
		return false;
		
	}
	
	@Override
	public String toString(){
		return this.LocationOfObjects.toString();
	}
	
}
