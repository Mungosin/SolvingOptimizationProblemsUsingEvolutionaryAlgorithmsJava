package hr.fer.zemris.optjava.dz3;

import java.util.Random;

/**
 * Class that extends SingleObjectiveSolution and implements methods and attributes required to represent the solution as a bit vector
 *
 */

public class BitVectorSolution extends SingleObjectiveSolution{
	byte[] bits;
	
	/**
	 * Constructor for BitVectorSolution object
	 * @param size of the byte solution array
	 */
	public BitVectorSolution(int size){
		this.bits = new byte[size];
	}
	
	/**
	 * Method is used to create a object of type BitVectorSolution with the same length of byte array
	 * @return new object of this type and same values length
	 */
	public BitVectorSolution newLikeThis(){
		return new BitVectorSolution(bits.length);
	}
	
	/**
	 * Method clones this object
	 * @return the cloned object
	 */
	public BitVectorSolution duplicate(){
		BitVectorSolution clone = new BitVectorSolution(bits.length);
		clone.bits = this.bits.clone();
		return clone;
	}
	
	/**
	 * 
	 * Method is used to create a random value for every variable	
	 * @param rand Random number generator
	 */
	public void Randomize(Random rand){
		int length = bits.length;
		for(int i=0;i<length;i++){
			if(rand.nextBoolean()){
				bits[i] = 1;
			}else {
				bits[i] = 0;
			}
		}
	}
}
