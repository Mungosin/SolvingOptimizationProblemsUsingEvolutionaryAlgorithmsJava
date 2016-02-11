package hr.fer.zemris.optjava.dz9.nsga;



import java.util.LinkedList;
import java.util.Random;

public class NSGAPop {
	public static Random rand = new Random(System.currentTimeMillis());;
	public LinkedList<NSGASolution> population = new LinkedList<>();
	public int domainDim;
	public int objectDim;
	
	public NSGAPop(int populationSize, int domainDim, int objDim, double[] minComponentVals,
			double[] maxComponentVals) {
		this.domainDim = domainDim;
		this.objectDim = objDim;
		randomize(populationSize, minComponentVals, maxComponentVals);
	}

	
	public NSGAPop(NSGASolution[] initPopulation) {
		this.domainDim = initPopulation[0].values.length;
		for (int i = 0; i < initPopulation.length; i++){
			this.population.add(initPopulation[i].duplicate());
		}
		
	}
	
	private void randomize(int populationSize, double[] minComponentVals, double[] maxComponentVals) {
		for (int i = 0; i < populationSize; i++) {
			NSGASolution s = new NSGASolution(domainDim, objectDim);
			s.randomize(rand, minComponentVals, maxComponentVals);
			population.add(s);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (NSGASolution s : population) {
			sb.append(s + "\n");
		}
		return sb.toString();
	}
}
