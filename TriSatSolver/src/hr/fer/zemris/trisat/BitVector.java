package hr.fer.zemris.trisat;

import java.util.Random;
/**
 * Class that implements BitVectors and its methods
 *
 */
public class BitVector {
		protected boolean[] vector;
	 /**
	  * Constructor that creates a BitVector with given length and random values
	  * @param rand random number generator
	  * @param numberOfBits wanted length of the array
	  */
		public BitVector(Random rand, int numberOfBits){
			if(numberOfBits > 0){
				boolean[] initializator = new boolean[numberOfBits];
				for(int i=0; i<numberOfBits;i++){
					initializator[i] = rand.nextBoolean();					
				}
				vector = initializator;
			}else {
				throw new IndexOutOfBoundsException();
			}
		}
		
		/**
		 * Constructor that creates a BitVector from the given boolean array
		 * @param bits boolean array
		 */
		public BitVector(boolean ... bits){			
			this.vector = bits.clone();
		}
		
		/**
		 * Constructor that creates a BitVector with given length
		 * @param n wanted length of the array
		 */
		public BitVector(int n){
			this.vector = new boolean[n];
		}
		
		/**
		 * Returns the value of the element at the position index
		 * @param index position of the element in the array whose value is wanted
		 * @return boolean value at the given index
		 */
		public boolean get(int index){
			if(vector.length > index && index>=0){
				return vector[index];
			}else {
				throw new IndexOutOfBoundsException();  
			}
			
		}
		/**
		 * Getter for BitVector size
		 * @return size of the BitVector
		 */
		public int getSize(){
			return vector.length;
		}
		
		@Override
		public String toString(){
			StringBuilder returnString = new StringBuilder(this.vector.length);
			for (boolean temp : this.vector){
				if(temp){
					returnString.append("1");
				}else {
					returnString.append("0");			
				}
			}
			return returnString.toString();
		}
		
		/**
		 * Creates a new MutableBitVector from the current BitVector
		 * @return MutableBitVector
		 */
		public MutableBitVector copy(){
			return new MutableBitVector(vector);
		}
}
