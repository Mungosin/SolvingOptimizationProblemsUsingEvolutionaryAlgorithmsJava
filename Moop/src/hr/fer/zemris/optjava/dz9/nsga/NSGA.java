package hr.fer.zemris.optjava.dz9.nsga;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz9.Constants;
import hr.fer.zemris.optjava.dz9.interfaces.IMoopOptFun;
import hr.fer.zemris.optjava.dz9.operations.BLXCross;
import hr.fer.zemris.optjava.dz9.operations.Mutation;



public class NSGA {

	public int populationSize;
	public int maxIterations;
	public double sigma;
	public IMoopOptFun function;
	public NSGAPop population;
	public ProportionalSelection propSel;
	public BLXCross crossoverOp;
	public Mutation mutationOp;
	
	
	public NSGA(int sizeOfPop, int maxIter,
			ProportionalSelection proportionalSelection, double sigma,
			IMoopOptFun function) {
		this.populationSize = sizeOfPop;
		this.maxIterations = maxIter;
		this.propSel = proportionalSelection;
		this.sigma = sigma;
		this.function = function;
		Random rand = new Random(System.currentTimeMillis());
		this.crossoverOp = new BLXCross(function.getMinDomainVals(),function.getMaxDomainVals(), rand);
		this.mutationOp = new Mutation(function.getMinDomainVals(), function.getMaxDomainVals(), rand);
		
		population = new NSGAPop(populationSize, function.getDecisionSpaceDim(), function.getObjectiveSpaceDim(),
				function.getMinDomainVals(), function.getMaxDomainVals());
		

		function.evaluatePopulation(population.population, Constants.ALPHA_SHARING, sigma);
		Collections.sort(population.population, Collections.reverseOrder());
	}


	public void run() {
		

		
		NSGASolution firstParent;
		NSGASolution secondParent;
		NSGASolution child;
		
		NSGASolution[] nextPopulation;
		int nextElement;
		int currentIter = 0;
		while(currentIter <this.maxIterations){

			if(currentIter%10==0){
				System.out.println("Current iteration: "+ currentIter);
			}
			currentIter++;
			nextElement=0;
			nextPopulation = new NSGASolution[this.population.population.size()];
			propSel.setPop(population);
			
			while(nextElement<populationSize){
				firstParent = propSel.selectNext();
				secondParent = propSel.selectNext();
				child = crossoverOp.cross(firstParent, secondParent, Constants.BLX_Constant);
				child = mutationOp.mutation(child, Constants.MUTATION_SIGMA);
				double[] objectives = new double[function.getMOOPProblem().getObjectiveSpaceDim()];
				function.getMOOPProblem().evaluate(child.values, objectives);
				child.objSolutionValues = objectives;
				
				
				// treba slozit ovdje sortanje i grouping i onda dodavat
				if(child.checkDomination(firstParent) || child.checkDomination(secondParent)){
					nextPopulation[nextElement] = child.duplicate();
				}else if(firstParent.checkDomination(secondParent)){
					nextPopulation[nextElement] = firstParent.duplicate();
				}else if(secondParent.checkDomination(firstParent)){
					nextPopulation[nextElement] = secondParent.duplicate();
				} else {
					nextPopulation[nextElement] = child.duplicate();
				}
				nextElement++;
			}
			population = new NSGAPop(nextPopulation);
			function.evaluatePopulation(population.population, Constants.ALPHA_SHARING, sigma);
			Collections.sort(population.population, Collections.reverseOrder());
			
			
		}

		
		System.out.println("FINISHED");
		
	}
	
	public LinkedList<LinkedList<NSGASolution>> getFronts(){
		return this.function.getParetoFronts(population.population);
	}
}
