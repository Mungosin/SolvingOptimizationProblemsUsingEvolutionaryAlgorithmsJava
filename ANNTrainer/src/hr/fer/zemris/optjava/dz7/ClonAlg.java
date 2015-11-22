package hr.fer.zemris.optjava.dz7;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class that implements Clon algorithm used to train neural network
 *
 */
public class ClonAlg {
	LinkedList<AntiBody> antybodies;
	double beta;
	int numberInPop;
	int coordinateLength;
	double merr;
	int maxIter;
	Evaluator evaluator;
	Random rand;
	

	/**
	 * Constructor for Clon algorithm
	 * @param numberOfAgents number of antibodies
	 * @param merr max error wanted 
	 * @param maxIter max number of iterations
	 * @param coordinateLength coordinates per antibody
	 * @param evaluator evaluator used to evaluate the quality of the solution
	 */
	public ClonAlg(int numberOfAgents, double merr, int maxIter, int coordinateLength, Evaluator evaluator ){
		this.numberInPop = numberOfAgents;
		this.merr = merr;
		this.maxIter = maxIter;
		this.coordinateLength = coordinateLength;
		this.evaluator = evaluator;
		this.antybodies = new LinkedList<>();
		this.rand = new Random(System.currentTimeMillis());
		for(int i=0;i<this.numberInPop;i++){
			AntiBody newBody = new AntiBody(coordinateLength, evaluator);
			newBody.randomize(rand);
			this.antybodies.add(newBody);
		}
	}
	
	/**
	 * Method partially sorts the antibodies, putting the max number of population best in front
	 */
	public void partialSort(){
		int length = this.antybodies.size();
        for(int i=0;i<this.numberInPop;i++){
            int best = i;
            for(int j=i+1;j<length;j++){
                    if(antybodies.get(best).affinity>antybodies.get(j).affinity){
                            best = j;
                    }
            }
            if(best != i){
                    AntiBody temp = antybodies.get(i);
                    this.antybodies.set(i, antybodies.get(best));
                    this.antybodies.set(best, temp);
            }
        }
	}
	
	/**
	 * Method is used to hipermutate the solutions, drastically changing the bad ones while barely touching the good ones
	 */
	public void hiperMutate(){
		int length = this.antybodies.size();
		for(int i=0;i<length;i++){
			AntiBody body = this.antybodies.get(i);
			double mutationChance = Math.exp((-Constants.ro)*body.affinity);
			body.mutate(mutationChance, rand);
		}
	}
	
	/**
	 * Method chooses best N of the population, where n is the max number in the pop defined in the constructor
	 */
	public void chooseBestN(){
		partialSort();
		this.antybodies.subList(this.numberInPop, this.antybodies.size()).clear();
	}
	
	/**
	 * Method creates new antibodies randomly to prevent stagnation and adds them to the population
	 */
	public void addNew(){
		LinkedList<AntiBody> newCreated = new LinkedList<>();
		int birthNew = this.numberInPop * Constants.newBirth / 100;
		for(int i=0;i<birthNew;i++){
			AntiBody newBody = new AntiBody(this.coordinateLength, this.evaluator);
			newBody.randomize(rand);
			newBody.evaluate();
			newCreated.add(newBody);
		}
		int length = this.antybodies.size();
		for(int i=length-1, j=birthNew-1;i>0 && j>0;i--,j--){
			this.antybodies.set(i, newCreated.get(j));
		}
		
	}
	
	/**
	 * Method evaluates every antibody
	 */
	public void evaluate(){
		for(AntiBody ant : antybodies){
			ant.evaluate();
		}
	}
	
	/**
	 * Method clones antibodies from the solution, the number of cloned antibodies varies based on the quality of the solution
	 */
	public void cloneBodies(){
		int length = this.antybodies.size();
		for(int i=0;i<length;i++){
			AntiBody original = this.antybodies.get(i);
			int numberOfClones = (int) ((Constants.beta * this.numberInPop)/(i+1));
			for(int j=0;j<numberOfClones;j++){
				AntiBody newBody = new AntiBody(this.coordinateLength, evaluator);
				newBody.affinity = original.affinity;
				newBody.position = Arrays.copyOf(original.position, original.position.length);
				this.antybodies.add(newBody);
			}
		}
	}
	
	/**
	 * Method starts the algorithm
	 */
	public void run(){
		int iteration=0;
		partialSort();
		while(iteration++<this.maxIter && evaluator.evaluate(this.antybodies.getFirst().position)>this.merr){
			evaluate();

			chooseBestN();

			cloneBodies();

			hiperMutate();

			evaluate();
			chooseBestN();

			addNew();
			if(iteration%10==0){

				System.out.println("Current iteration: " + iteration + " Current error: " + evaluator.evaluate(this.antybodies.getFirst().position));
			}
		}
		System.out.println("Current iteration: " + (iteration-1) + " Current error: " + evaluator.evaluate(this.antybodies.getFirst().position));
		
		partialSort();
		writeStatisticsOfTrainedModel();
	}
	
	/**
	 * Method is used to write the statistics of the trained neural network
	 */
	private void writeStatisticsOfTrainedModel() {
		double[] bestCoordinates = this.antybodies.getFirst().position;
		double[] statistics = evaluator.getStatistics(bestCoordinates); //0 - wrong 1-correct
		System.out.println(statistics[0] + " models were wrongly classified   " + statistics[1] + " models were correctly classified");
	}
}
