package hr.fer.zemris.optjava.dz5.part1;
/**
 * Class implements a function that the genetic algorithm maximises
 *
 */
public class Function1 implements IFunction{

	@Override
	public double valueAt(BitVectorSolution sol) {
		int numberOfOnes = 0;
		for(byte bit : sol.bits){
			if(bit==1){
				numberOfOnes++;
			}
		}
		if(sol.bits.length*0.8>= numberOfOnes){
			return (numberOfOnes * 1.)/sol.bits.length;
		}else if(0.8* sol.bits.length < numberOfOnes && 0.9*sol.bits.length> numberOfOnes){
			return 0.8;
		}else{
			return (2*numberOfOnes * 1.)/sol.bits.length -1;
		}
	}

}
