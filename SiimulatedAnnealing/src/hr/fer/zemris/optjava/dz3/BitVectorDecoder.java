package hr.fer.zemris.optjava.dz3;

import java.security.InvalidParameterException;
import java.util.Arrays;

public abstract class BitVectorDecoder implements IDecoder<BitVectorSolution>{
	protected double[] mins;
	protected double[] maxs;
	protected int[] bits;
	protected int n;
	protected int totalBits;
	
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
	
	public int getTotalBits(){
		return totalBits;
	}
	
	public int getDimensions(){
		return n;
	}
	
	@Override
	public abstract double[] decode(BitVectorSolution object);

	@Override
	public abstract void decode(BitVectorSolution object, double[] result);

}
