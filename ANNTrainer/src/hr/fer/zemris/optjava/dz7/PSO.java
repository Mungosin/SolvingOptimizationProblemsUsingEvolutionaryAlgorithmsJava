package hr.fer.zemris.optjava.dz7;

/**
 * Class implements particle swarm optimization algorithm used to train a neural network
 *
 */
public class PSO {
	boolean global;
	int numberOfAgents;
	double merr;
	int maxIter;
	PSOPop population;
	Evaluator evaluator;
	
	/**
	 * Constructor for particle swarm optimization algorithm
	 * @param numberOfAgents number of antibodies
	 * @param global flag that if true lets the algorithm know to use global neighbourhood, if false then uses the neighbourhood size defined in the constructor
	 * @param merr max error wanted 
	 * @param maxIter max number of iterations
	 * @param coordinateLength coordinates per antibody
	 * @param evaluator evaluator used to evaluate the quality of the solution
	 * @param neighbourhood neighbourhood size, if 1 the particle will look for best solution between him, his left and right neighbour
	 */
	public PSO(int numberOfAgents, boolean global, double merr, int maxIter, int coordinateLength, Evaluator evaluator, int neighbourhood){
		this.global = global;
		this.numberOfAgents = numberOfAgents;
		this.merr = merr;
		this.maxIter = maxIter;
		this.population = new PSOPop(numberOfAgents, global, coordinateLength, evaluator, neighbourhood);
		this.evaluator = evaluator;
	}
	
	/**
	 * Method starts the algorithm
	 */
	public void run(){
		
		int iteration = 0;
		while(iteration++<maxIter && evaluator.evaluate(population.getBestSol())> merr){
			population.calculateNextPosition();
			population.updateInertia((1.0 *1)/maxIter);
			if(iteration%10==0){

				System.out.println("Current iteration: " + iteration + " Current error: " + evaluator.evaluate(population.getBestSol()));
			}
			
		}
		System.out.println("Current iteration: " + (iteration-1) + " Current error: " + evaluator.evaluate(population.getBestSol()));
		
		writeStatisticsOfTrainedModel();
	}

	/**
	 * Method is used to write the statistics of the trained neural network
	 */
	private void writeStatisticsOfTrainedModel() {
		double[] bestCoordinates = population.getBestSol();
		double[] statistics = evaluator.getStatistics(bestCoordinates); //0 - wrong 1-correct
		System.out.println(statistics[0] + " models were wrongly classified   " + statistics[1] + " models were correctly classified");
	}
	
	
}
