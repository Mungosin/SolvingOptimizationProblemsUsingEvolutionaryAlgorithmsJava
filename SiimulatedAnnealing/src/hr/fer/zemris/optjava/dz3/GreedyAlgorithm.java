package hr.fer.zemris.optjava.dz3;

public class GreedyAlgorithm<T extends SingleObjectiveSolution> implements IOptAlgorithm<T>{
	private IDecoder<T> decoder;
	private INeighbourhood<T> neighbourhood;
	private T startWith;
	private IFunction function;
	private boolean minimize;
	
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
		current.fitness = izracunajFitness(current);
		nextNeighbour.fitness = izracunajFitness(nextNeighbour);
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
			nextNeighbour.fitness = izracunajFitness(nextNeighbour);
			
		}
	}
	
	private double izracunajFitness(T nextNeighbour){
		return function.valueAt(decoder.decode(nextNeighbour));
	}

}
