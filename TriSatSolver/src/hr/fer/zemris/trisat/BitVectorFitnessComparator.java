package hr.fer.zemris.trisat;

import java.util.Comparator;
/**
 * BitVectorFitnessComparator implementation
 *
 */
public class BitVectorFitnessComparator implements Comparator<BitVector>{
	private SATFormulaStats satFormStats;
	private HelperFunctions helper;
	
	/**
	 * Constructor for BitVectorFitnessComparator
	 * @param sat SATFormulaStats object containing information about the formula
	 * @param helper HelperFunctions object containing helper method implementations
	 */
	public BitVectorFitnessComparator(SATFormulaStats sat, HelperFunctions helper){
		satFormStats = sat;
		this.helper = helper;
	}

	@Override
	public int compare(BitVector o1, BitVector o2) {
		satFormStats.setAssignment(o1, false);
		double o1Fit = helper.fitnessFunctionThirdAlgorithm(satFormStats);
		
		satFormStats.setAssignment(o2, false);
		double o2Fit = helper.fitnessFunctionThirdAlgorithm(satFormStats);
		if(o1Fit - o2Fit > 0){
			return 1;
		}else if (o1Fit - o2Fit <0){
			return -1;
		}else {
			return 0;
		}
	}
}
