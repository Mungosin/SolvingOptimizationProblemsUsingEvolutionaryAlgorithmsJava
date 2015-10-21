package hr.fer.zemris.optjava.dz3;

import java.security.InvalidParameterException;
import java.util.Random;

public class BitVectorNeighbourhood implements INeighbourhood<BitVectorSolution>{
	Random rand;
	int n;
	int totalBits;
	
	public BitVectorNeighbourhood(int n, int totalBits){

		if((1.*totalBits)/n != totalBits/n){
			throw new InvalidParameterException("Length of bits and number n must be dividable without remainders");
			
		}
		rand = new Random(System.currentTimeMillis());
		this.n = n;
		this.totalBits = totalBits;
	}
	
	@Override
	public BitVectorSolution randomNeighbour(BitVectorSolution object) {
		BitVectorSolution newSolution = object.duplicate();
		int numOfVariables = totalBits/n;
		for(int i=0;i<numOfVariables;i++){
			int position = rand.nextInt(n)+i*n;
			if(newSolution.bits[position] != 1){
				newSolution.bits[position] = 1;
			}else {
				newSolution.bits[position] = 0;
			}
		}
		return newSolution;
	}

}
