package hr.fer.zemris.trisat;

import java.util.Iterator;
/**
 * BitVectorNGenerator class implementation used to generate neighbourhood of a BitVector
 *
 */
public class BitVectorNGenerator implements Iterable<MutableBitVector>{
	
	private BitVector assignment;
	
	/**
	 * Constructor for BitVectorNGenerator that sets the assignment equal to the recieved BitVector
	 * @param assignment BitVector object
	 */
	public BitVectorNGenerator(BitVector assignment){
		this.assignment = assignment;		
	}
	
	@Override
	public Iterator<MutableBitVector> iterator() {
		
		return new MutableBitVectorIterator(assignment);
	
	}

	/**
	 * Creates a neighbourhood from current BitVector assignment object and returns it as a MutableBitVector array
	 * @return new MutableBitVector array containing the neighbourhood 
	 */
	public MutableBitVector[] createNeighbourhood(){
		int sizeOfField = assignment.getSize();
		MutableBitVector[] returnMutableBitVector = new MutableBitVector[sizeOfField];
		int i=0;
		Iterator<MutableBitVector> it = iterator();
		while(it.hasNext()){
			returnMutableBitVector[i++] = it.next();
		}
		return returnMutableBitVector;
	}
}

