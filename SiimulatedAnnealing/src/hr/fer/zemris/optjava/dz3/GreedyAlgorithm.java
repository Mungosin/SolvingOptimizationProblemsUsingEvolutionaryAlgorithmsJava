package hr.fer.zemris.optjava.dz3;
/**
 * 
 *Class implements greedy algorithm method to optimize functions
 * @param <T> has to extend SingleObjective solution
 */
public class GreedyAlgorithm<T extends SingleObjectiveSolution> implements IOptAlgorithm<T>{
	private IDecoder<T> decoder;
	private INeighbourhood<T> neighbourhood;
	private T startWith;
	private IFunction function;
	private boolean minimize;
	
	
	/**
	 * 
	 * @param decoder decodes the current representation of the solution to a double array and implements IDecoder
	 * @param neighbourhood class is used to generate the neighbourhood of the current solution and implements INeighbourhood
	 * @param startWith starting point
	 * @param function which is being optimized, implements IFunction
	 * @param minimize if true the function is being minimized, if false it is being maximized
	 */
	public GreedyAlgorithm (IDecoder<T> decoder, INeighbourhood<T> neighbourhood, T startWith, IFunction function, boolean minimize){
		this.decoder = decoder;
		this.neighbourhood = neighbourhood;
		this.startWith = startWith;
		this.function = function;
		this.minimize = minimize;
	}
	
	@Override
	public void run() {
		T current = startWith;
		T nextNeighbour = neighbourhood.randomNeighbour(current);
		current.fitness = calculateFitness(current);
		nextNeighbour.fitness = calculateFitness(nextNeighbour);
		int value;
		int pointLength;
		double[] pointValues;
		int iterations = Constants.greedyIterations;
		
		if(minimize){
			value =1;
		}else {
			value = -1; 
		}
		while(iterations-- > 0){ 
			
			if(nextNeighbour.compareTo(current) != value){ // different than value makes me continue if the solution is 
				  // equally good as the previous one and take better ones
				pointValues = decoder.decode(current);
				pointLength = pointValues.length;
				for(int j=0;j<pointLength;j++){
					if(j==pointLength-1){
						System.out.format("%10.10f", pointValues[j]);
						break;
					}
					System.out.format("%10.10f, ", pointValues[j]);
				}
				System.out.println(") -> function value = " + function.valueAt(pointValues));
				
				
				current = nextNeighbour;
			}
			nextNeighbour = neighbourhood.randomNeighbour(current);
			nextNeighbour.fitness = calculateFitness(nextNeighbour);
			
		}
	}
	
	/**
	 * Method is used to calculate fitness of the given solution
	 * @param nextNeighbour solution whose fitness is being calculated
	 * @return fitness value of the given object
	 */
	private double calculateFitness(T nextNeighbour){
		return function.valueAt(decoder.decode(nextNeighbour));
	}

}
