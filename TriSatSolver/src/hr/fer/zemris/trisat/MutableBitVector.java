package hr.fer.zemris.trisat;

/**
 * Class that extends the Bitvector class and implements a set method allowing it to be mutable
 *
 */
public class MutableBitVector extends BitVector{

	/**
	 * Constructs a MutableBitVector object from the given boolean array
	 * @param bits boolean array of 0 to n elements
	 */
	public MutableBitVector(boolean ... bits){
		super(bits);		
	}
	
	/**
	 * Constructs a MutableBitvector with length of 0 and all false values
	 * @param n length of the wanted array
	 */
	public MutableBitVector(int n){
		super(n);		
	}
	
	/**
	 * Setter method for MutableBitVector object
	 * @param index of the element whose value is being changed
	 * @param value new value of the element at position index
	 */
	public void set(int index, boolean value){
		if(this.vector.length > index && index>=0){
			this.vector[index] = value;
		}else {
			 throw new IndexOutOfBoundsException();
		}		
		
	}
}
