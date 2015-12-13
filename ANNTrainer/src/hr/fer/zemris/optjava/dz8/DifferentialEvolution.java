package hr.fer.zemris.optjava.dz8;

public class DifferentialEvolution {
	public int numberOfSolutions;
	public int numberOfCoordinates;
	public Evaluator evaluator;
	public double maxError;
	public int maxGenerations;
	public NeuralNetworkSolutionPopulation population;

	public DifferentialEvolution(int numberOfSolutions, int numberOfCoordinates, Evaluator evaluator, double maxError, int maxGenerations){
		this.numberOfCoordinates = numberOfCoordinates;
		this.numberOfSolutions = numberOfSolutions;
		this.evaluator = evaluator;
		this.maxError = maxError;
		this.maxGenerations = maxGenerations;
		
		this.population = new NeuralNetworkSolutionPopulation(numberOfSolutions, numberOfCoordinates, evaluator);
	}
	
	/**
	 * Method starts the algorithm
	 */
	public void run(){
		
		int iteration = 0;
		while(iteration++<maxGenerations && evaluator.evaluate(population.getBestSol())> maxError){
			
			population.calculateNextPosition();
			if(iteration%100==0 || iteration == 1){
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
