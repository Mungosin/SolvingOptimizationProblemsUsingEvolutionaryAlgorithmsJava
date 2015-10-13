package hr.fer.zemris.trisat;

import java.util.Iterator;
/**
 * 
 *
 */
public class MutableBitVectorIterator implements Iterator<MutableBitVector>{
	private BitVector current;
	private int numberOfIterations;
	
	/**
	 * Constructor for MutableBitVectorIterator
	 * @param assignment BitVector that will be set
	 */
	public MutableBitVectorIterator(BitVector assignment){
		this.current = assignment;
		numberOfIterations = assignment.getSize();
	}
	
	/**
	 * Returns true if the current BitVector has more neighbours
	 */
	@Override
	public boolean hasNext() {
		if(numberOfIterations >0){
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * returns the next MutableBitVector neighbour of the current BitVector
	 */
	@Override
	public MutableBitVector next() {
		MutableBitVector newVector = current.copy();
		boolean currentIterationValue = current.get(numberOfIterations-1);
		newVector.set(numberOfIterations-1, !currentIterationValue);
		numberOfIterations--;
		return newVector;
	}
	
}

