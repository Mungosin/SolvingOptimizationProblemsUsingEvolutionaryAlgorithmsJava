package hr.fer.zemris.optjava.dz4.part2;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * Class is used to implement methods and attributes that represent a single bin in the Box where sticks are being stored
 *
 */
public class Bin implements Iterable<Stick>{
	protected LinkedList<Stick> sticks;
	protected int maxHeight;
	protected int currentHeight;
	
	/**
	 * Constructor for Bin class, sets the max height of the bin to the given parameter
	 * @param maxHeight of the bin
	 */
	public Bin(int maxHeight){
		currentHeight = 0;
		this.maxHeight = maxHeight;
		sticks = new LinkedList<Stick>();
	}
	/**
	 * Creates a new bin from the given bin
	 * @param bin copied bin
	 */
	public Bin(Bin bin) {
		this.maxHeight = bin.maxHeight;
		this.sticks = new LinkedList<>(bin.sticks);
		this.calculateHeight();
	}

	/**
	 * Method is used to add objects to the bin
	 * @param object to be added
	 * @return true if the object has been added false if it wasn't possible due to height restrictions
	 */
	public boolean addToBin(Stick object){
		if(object == null) return false;
		if(object.getHeight() + currentHeight <= maxHeight){
			currentHeight +=object.getHeight();
			sticks.add(object);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Calculates height of the bin
	 */
	public void calculateHeight(){
		currentHeight=0;
		for(Stick stick : sticks){
			currentHeight+=stick.getHeight();
		}
	}
	
	

	/**
	 * Method is used to sort the bin
	 */
	public void SortFilled() {
		Collections.sort(sticks);
	}

	@Override
	public String toString(){
		return sticks.toString();
	}
	@Override
	public Iterator<Stick> iterator() {
		return sticks.iterator();
	}
	
	@Override
	public boolean equals(Object o1){
		if(o1 instanceof Bin){
			return this.sticks.equals(((Bin)o1).sticks);
		}else {
			return false;
		}
	}
	
}
