package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class BitVectorNeighbourhood implements INeighbourhood<BitVectorSolution>{
	Random rand;
	
	public BitVectorNeighbourhood(){
		rand = new Random(System.currentTimeMillis());
	}
	
	@Override
	public BitVectorSolution randomNeighbour(BitVectorSolution object) {
		int length = object.bits.length;
		BitVectorSolution newSolution = object.duplicate();
		int position = rand.nextInt(length);
		if(newSolution.bits[position] != 1){
			newSolution.bits[position] = 1;
		}else {
			newSolution.bits[position] = 0;
		}
		return newSolution;
	}

}
