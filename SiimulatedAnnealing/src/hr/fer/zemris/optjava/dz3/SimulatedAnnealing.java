package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public  class SimulatedAnnealing<T extends SingleObjectiveSolution> implements IOptAlgorithm<T>{

	private IDecoder<T> decoder;
	private INeighbourhood<T> neighbourhood;
	private T startWith;
	private IFunction function;
	private boolean minimize;
	private ITempSchedule tempSchedule;
	
	public SimulatedAnnealing (IDecoder<T> decoder, INeighbourhood<T> neighbourhood, T startWith, IFunction function, boolean minimize, ITempSchedule tempSchedule){
		this.decoder = decoder;
		this.neighbourhood = neighbourhood;
		this.startWith = startWith;
		this.function = function;
		this.minimize = minimize;
		this.tempSchedule = tempSchedule;
	}

	@Override
	public void run() {
		T current = startWith;
		T nextNeighbour = neighbourhood.randomNeighbour(current);
		current.fitness = izracunajFitness(current);
		nextNeighbour.fitness = izracunajFitness(nextNeighbour);
		int pointLength;
		double[] pointValues;
		double currentTemp = tempSchedule.getNextTemperature();
		int outerIterations = tempSchedule.getOuterLoopCounter();
		int innerIterations = tempSchedule.getInnerLoopCounter();
		Random rand = new Random(System.currentTimeMillis());
		double delta;

		
		for(int i=0;i<outerIterations;i++){ // different than value makes me continue if the solution is 
														  // equally good as the previous one and take better ones
			
			for(int j=0;j<innerIterations;j++){
				nextNeighbour = neighbourhood.randomNeighbour(current);
				nextNeighbour.fitness = izracunajFitness(nextNeighbour);
				delta = nextNeighbour.fitness-current.fitness;
				if(!minimize){
					delta = delta*(-1);
				}
				if(delta <=0 || rand.nextDouble()<=Math.exp((delta*(-1))/currentTemp)){
					current = nextNeighbour;
					pointValues = decoder.decode(current);
					pointLength = pointValues.length;
					for(int z=0;z<pointLength;z++){
						if(z==pointLength-1){
							System.out.format("%10.10f", pointValues[z]);
							break;
						}
						System.out.format("%10.10f, ", pointValues[z]);
					}
					System.out.println(") -> function value = " + function.valueAt(pointValues));
				}
			}
			currentTemp=tempSchedule.getNextTemperature();
			
			
		}
	}
	
	private double izracunajFitness(T nextNeighbour){
		return function.valueAt(decoder.decode(nextNeighbour));
	}

}
