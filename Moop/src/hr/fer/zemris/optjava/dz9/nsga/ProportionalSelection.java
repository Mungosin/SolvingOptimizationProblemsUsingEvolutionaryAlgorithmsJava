package hr.fer.zemris.optjava.dz9.nsga;


import java.util.Collections;
import java.util.Random;

public class ProportionalSelection {
	private Random rand;
	private double[] scaled;
	private double[] probability;
	private NSGAPop population;
	
	public ProportionalSelection(Random rand){
		this.rand = rand;
	}
	
	public void setPop(NSGAPop pop){
		this.population = pop;
		scaled = scale();
		probability = calculateProbabilities();
	}
	
	public NSGASolution selectNext(){
		
		double randomSelected = rand.nextDouble();
		double upperBound=0;
		double lowerBound=0;
		int size = population.population.size();
		for (int i = 0; i < size; i++) {
			upperBound += probability[i];
			if (lowerBound < randomSelected && randomSelected < upperBound) {
				return population.population.get(i).duplicate();
			} else {
				lowerBound = upperBound;
			}
		}
		return null;
	}

	private double[] scale() {
		Collections.sort(population.population, Collections.reverseOrder());
		int size = population.population.size();
		NSGASolution worstSolution = population.population.get(size-1);

		double fWorst = worstSolution.fitness;

		double[] scaled = new double[size];

		for (int i = 0; i < size; i++) {
			NSGASolution subj = population.population.get(i);
			if (subj.fitness > 1)
				scaled[i] = 1 / subj.fitness;
			else
				scaled[i] = Math.abs(fWorst - subj.fitness);
		}
		return scaled;
	}

	private double[] calculateProbabilities() {
		
		int size = population.population.size();
		double[] probability = new double[size];
		
		double totalFitness = 0;
		for (int i = 0; i < size - 1; i++) {
			totalFitness += scaled[i];
		}
		for (int i = 0; i < size; i++) {
			probability[i] = scaled[i] / totalFitness;
		}
		return probability;
		
	}
}
