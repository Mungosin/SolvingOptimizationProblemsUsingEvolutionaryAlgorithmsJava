package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import java.util.Comparator;
/**
 * Class implements attributes and methods used to represent a Box with Bins with maximal height used to store objects
 *
 */
public class BoxSolution implements Comparable<BoxSolution>{
	protected double fitness;
	protected int maxHeight;
	protected LinkedList<Bin> arrangement;
	
	/**
	 * Constructor for Box solution
	 * @param sticks used to initialize the solution
	 * @param maxHeight max height of every bin
	 */
	public BoxSolution(LinkedList<Stick> sticks, int maxHeight){
		this.maxHeight = maxHeight;
		arrangement = new LinkedList<Bin>();
		initialize(sticks);
	}
	/**
	 * Empty constructor used for initialization
	 */
	public BoxSolution(){
		
	}

	/**
	 * Calculates the fitness of the solution with the given formula
	 * Fitness = sum of every bin divided by its capacity to the power of K, and then divided by the number of bins, 
	 * if the parameter K is higher it will favor equally sized bins
	 */
	public void calculateFitness(){
		fitness = 0;
		int numberOfBins = arrangement.size();
		removeEmptyBins();
		for(int i=0;i<arrangement.size();i++){
			arrangement.get(i).calculateHeight();
		}
		for(Bin bins : arrangement){
			fitness += Math.pow(((1.0*bins.currentHeight)/bins.maxHeight), Constants.K);
		}
		fitness  /=(1.0*numberOfBins);
	}
	
	/**
	 * Removes empty bins from the solution
	 */
	public void removeEmptyBins(){
		int numberOfBins = arrangement.size();
		for(int i=numberOfBins-1;i>=0;i--){
			Bin bin = arrangement.get(i);
			if(bin.currentHeight == 0) arrangement.remove(bin);
		}
	}
	
	/**
	 * Sorts the elements of the solution, more filled bins come first and elements in bins that are of higher height come first in the bin
	 */
	public void sortElements(){
		Collections.sort(arrangement, new Comparator<Bin>(){

			@Override
			public int compare(Bin o1, Bin o2) {
				if(o1.currentHeight < o2.currentHeight){
					return 1;
				}else if(o1.currentHeight > o2.currentHeight){
					return -1;
				}else{
					return 0;
				}
			}
			
		});
		for(Bin current : arrangement){
			current.SortFilled();
		}
	}
	

	/**
	 * Initializes the solution putting every stick in its bin
	 * @param sticks to be distributed in the box
	 */
	private void initialize(LinkedList<Stick> sticks) {
		int numStick = sticks.size();
		for(int i=0;i<numStick;i++){
			Bin newList = new Bin(Constants.maxHeight);
			newList.addToBin(sticks.get(i));
			arrangement.add(newList);
		}
	}
	
	
	@Override
	public String toString(){
		int row = 1;
		for(Bin red : arrangement){
			System.out.print("row " + row + "  ");
			for(Stick bla : red){
				System.out.print(bla +" ");
			}
			System.out.println();
			row++;
		}
		System.out.println();
		return "";
	}
	
	@Override
	public int compareTo(BoxSolution o) {
	
			if(this.fitness<o.fitness){
				return -1;
			}else if(this.fitness>o.fitness){
				return 1;
			}else{
				return 0;
			}
		
	}
	
	/**
	 * Method is used to remove the given bin from the boxSolution
	 * @param Bin to be removed from the box
	 * @return true if it was successful, false if it doesn't contain the given bin
	 */
	public boolean removeBin(Bin Bin) {
		return arrangement.remove(Bin);
	}
	
}
