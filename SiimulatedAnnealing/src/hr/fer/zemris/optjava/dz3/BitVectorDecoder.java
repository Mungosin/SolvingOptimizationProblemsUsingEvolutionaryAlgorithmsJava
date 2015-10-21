package hr.fer.zemris.optjava.dz3;

import java.security.InvalidParameterException;
import java.util.Arrays;
/**
 * Abstract class that implements IDecoder for bit vector solutions and basic attributes and methods that every bit vector decoder needs to have
 *
 */
public abstract class BitVectorDecoder implements IDecoder<BitVectorSolution>{
	protected double[] mins;
	protected double[] maxs;
	protected int[] bits;
	protected int n;
	protected int totalBits;
	
	/**
	 * Constructor for BitVectorDecoder object
	 * @param mins minimal value of the binary code per variable
	 * @param maxs maximal value of the binary code per variable
	 * @param bits starting bits to be decoded
	 * @param n number of bits per variable
	 */
	public BitVectorDecoder (double[] mins, double [] maxs, int[] bits, int n){
		if(bits.length/n != (1.*bits.length)/n){
			throw new InvalidParameterException("Length of bits and number n must be dividable without remainders");
		}
		
		this.mins = mins.clone();
		this.maxs = maxs.clone();
		this.bits = bits.clone();
		this.n = n;
		this.totalBits = bits.length;
	}
	/**
	 * Constructor for BitVectorDecoder object
	 * @param min minimal value of the binary code
	 * @param max maximal value of the binary code
	 * @param n number of bits per variable
	 * @param totalBits total bits
	 */
	public BitVectorDecoder(double min, double max, int n, int totalBits){
		if(totalBits/n != (1.*totalBits)/n){
			throw new InvalidParameterException("Length of bits and number n must be dividable without remainders");
		}
		
		mins = new double[totalBits];
		Arrays.fill(mins, min);
		maxs = new double[totalBits];
		Arrays.fill(maxs, max);
		int[] bits = new int[totalBits];
		this.n = n;
	}
	
	/**
	 * Gets the total bits of the code
	 * @return total bits
	 */
	public int getTotalBits(){
		return totalBits;
	}
	
	/**
	 * Gets the number of bits per variable
	 * @return the number of bits per variable
	 */
	public int getDimensions(){
		return n;
	}
	
	@Override
	public abstract double[] decode(BitVectorSolution object);

	@Override
	public abstract void decode(BitVectorSolution object, double[] result);

}
