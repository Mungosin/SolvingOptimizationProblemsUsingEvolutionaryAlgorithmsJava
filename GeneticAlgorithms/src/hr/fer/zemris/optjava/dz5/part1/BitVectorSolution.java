package hr.fer.zemris.optjava.dz5.part1;

import java.util.Arrays;
import java.util.Random;

/**
 * Class that extends SingleObjectiveSolution and implements methods and attributes required to represent the solution as a bit vector
 *
 */

public class BitVectorSolution extends SingleObjectiveSolution implements Comparable<BitVectorSolution>{
	byte[] bits;
	protected double fitness;
	protected IFunction func;
	
	/**
	 * Constructor for BitVectorSolution object
	 * @param size of the byte solution array
	 * @param func is the function whose solution is being stored
	 */
	public BitVectorSolution(int size, IFunction func){
		if(size<0){
			System.out.println("Vector size cant be negative");
			return;
		}
		this.bits = new byte[size];
		this.func = func;
	}
	
	/**
	 * Method is used to create a object of type BitVectorSolution with the same length of byte array
	 * @return new object of this type and same values length
	 */
	public BitVectorSolution newLikeThis(){
		return new BitVectorSolution(bits.length, this.func);
	}
	
	/**
	 * Method clones this object
	 * @return the cloned object
	 */
	public BitVectorSolution duplicate(){
		BitVectorSolution clone = new BitVectorSolution(bits.length, this.func);
		clone.bits = this.bits.clone();
		clone.fitness = this.fitness;
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
	/**
	 * Method is used to calculate the fitness of current solution
	 */
	public void calculateFitness(){
		fitness = func.valueAt(this);
	}
	
	@Override
	public int hashCode(){
		return this.bits.hashCode();
	}
	
	@Override
	public boolean equals(Object object){
		if(object instanceof BitVectorSolution){
			if(this == object || Arrays.equals(this.bits, ((BitVectorSolution)object).bits)){
				return true;
			}else {
				return false;
			}
			
		}else {
			return false;
		}
		
	}

	@Override
	public int compareTo(BitVectorSolution o) {
		if(this.fitness ==o.fitness){
			return 0;
		}else if(this.fitness > o.fitness){
			return 1;
		}else {
			return -1;
		}
	}
	
	@Override
	public String toString(){
		int length = this.bits.length;
		StringBuilder stb = new StringBuilder(length);
		for(int i=0;i<length;i++){
			stb.append(this.bits[i]);
		}
		return stb.toString();
	}

	
}
