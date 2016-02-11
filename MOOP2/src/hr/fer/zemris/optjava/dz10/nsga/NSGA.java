package hr.fer.zemris.optjava.dz10.nsga;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import hr.fer.zemris.optjava.dz10.Constants;
import hr.fer.zemris.optjava.dz10.interfaces.IMoopOptFun;
import hr.fer.zemris.optjava.dz10.operations.BLXCross;
import hr.fer.zemris.optjava.dz10.operations.Mutation;



public class NSGA {

	public int populationSize;
	public int maxIterations;
	public double sigma;
	public IMoopOptFun function;
	public NSGAPop population;
	public GroupingSelection groupingpSel;
	public BLXCross crossoverOp;
	public Mutation mutationOp;
	
	
	public NSGA(int sizeOfPop, int maxIter,
			GroupingSelection groupingSelection, double sigma,
			IMoopOptFun function) {
		this.populationSize = sizeOfPop;
		this.maxIterations = maxIter;
		this.groupingpSel = groupingSelection;
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
			nextPopulation = new NSGASolution[this.populationSize*2];
			
			
			//dodaj pareto fronte
			groupingpSel.setPop(population, function.getParetoFronts(population.population));
			
			
			while(nextElement<populationSize*2){
				firstParent = groupingpSel.selectNext();
				secondParent = groupingpSel.selectNext();
				child = crossoverOp.cross(firstParent, secondParent, Constants.BLX_Constant);
				child = mutationOp.mutation(child, Constants.MUTATION_SIGMA);
				double[] objectives = new double[function.getMOOPProblem().getObjectiveSpaceDim()];
				function.getMOOPProblem().evaluate(child.values, objectives);
				child.objSolutionValues = objectives;
				
				nextPopulation[nextElement] = child.duplicate();
				nextElement++;
			}
			
			NSGAPop tempPop = new NSGAPop(nextPopulation);
			function.evaluatePopulation(tempPop.population, Constants.ALPHA_SHARING, sigma);
			LinkedList<LinkedList<NSGASolution>> paretoFronts = function.getParetoFronts(population.population);
			
			population = new NSGAPop(this.populationSize, population.domainDim, population.objectDim);
			
			int currentFront=0;
			for(LinkedList<NSGASolution> front : paretoFronts){
				if(population.population.size() < this.populationSize){
					if(population.population.size()+ front.size()<this.populationSize){
						for(NSGASolution nsgaSol : front){
							population.population.add(nsgaSol);
						}

					}else {
						LinkedList<NSGASolution> candidates = this.groupingpSel.getBestFromParetoFront(this.populationSize-population.population.size(), currentFront, front);
						for(NSGASolution nsgaSol : candidates){
							if(population.population.size()<this.populationSize){
								population.population.add(nsgaSol.duplicate());
								
							}else {
								break;
							}
						}
					}
				}else {

					break;
				}
				currentFront++;
			}

			
			
			function.evaluatePopulation(population.population, Constants.ALPHA_SHARING, sigma);
			Collections.sort(population.population, Collections.reverseOrder());
			
			
		}

		
		System.out.println("FINISHED");
		
	}
	
	public LinkedList<LinkedList<NSGASolution>> getFronts(){
		return this.function.getParetoFronts(population.population);
	}
}
