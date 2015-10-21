package hr.fer.zemris.optjava.dz3;

import java.util.Arrays;
import java.util.Random;
/**
 * 
 *Class implements simulated annealing algorithm method to optimize functions
 * @param <T> has to extend SingleObjective solution
 */

public  class SimulatedAnnealing<T extends SingleObjectiveSolution> implements IOptAlgorithm<T>{

	private IDecoder<T> decoder;
	private INeighbourhood<T> neighbourhood;
	private T startWith;
	private IFunction function;
	private boolean minimize;
	private ITempSchedule tempSchedule;
	
	/**
	 * Constructor for SimulatedAnnealing object
	 * @param decoder decodes the current representation of the solution to a double array and implements IDecoder
	 * @param neighbourhood class is used to generate the neighbourhood of the current solution and implements INeighbourhood
	 * @param startWith starting point
	 * @param function which is being optimized, implements IFunction
	 * @param minimize if true the function is being minimized, if false it is being maximized
	 * @param tempSchedule ITempschedule object that calculates next temperature and sets the inner and outer loop iterations
	 */
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
		current.fitness = calculateFitness(current);
		T bestFound = current;
		nextNeighbour.fitness = calculateFitness(nextNeighbour);
		int pointLength;
		double[] pointValues = null;
		double[] previousPointValues = null;
		double currentTemp = tempSchedule.getNextTemperature();
		int outerIterations = tempSchedule.getOuterLoopCounter();
		int innerIterations = tempSchedule.getInnerLoopCounter();
		Random rand = new Random(System.currentTimeMillis());
		double delta;

		
		for(int i=0;i<outerIterations;i++){ // different than value makes me continue if the solution is 
														  // equally good as the previous one and take better ones
			
			for(int j=0;j<innerIterations;j++){
				nextNeighbour = neighbourhood.randomNeighbour(current);
				nextNeighbour.fitness = calculateFitness(nextNeighbour);
				delta = nextNeighbour.fitness-current.fitness;
				if(!minimize){
					delta = delta*(-1);
				}
				if(delta <=0 || rand.nextDouble()<=Math.exp((delta*(-1))/currentTemp)){
					current = nextNeighbour;
					if(!minimize && bestFound.fitness < current.fitness){
						bestFound=current;
					}else if(minimize && bestFound.fitness > current.fitness){
						bestFound = current;
					}
				}
			}
			pointValues = decoder.decode(current);
			if(Arrays.equals(pointValues, previousPointValues)) continue;
			
			pointLength = pointValues.length;
			System.out.print("(");
			for(int z=0;z<pointLength;z++){
				if(z==pointLength-1){
					System.out.format("%10.10f", pointValues[z]);
					break;
				}
				System.out.format("%10.10f, ", pointValues[z]);
			}
			System.out.println(") -> function value = " + function.valueAt(pointValues));
			previousPointValues=pointValues;
			currentTemp=tempSchedule.getNextTemperature();
		}
		
		System.out.println("_________ Best found solution ___________");
		pointValues = decoder.decode(bestFound);
		System.out.print("(");
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
	
	/**
	 * Method is used to calculate fitness of the given solution
	 * @param nextNeighbour solution whose fitness is being calculated
	 * @return fitness value of the given object
	 */
	private double calculateFitness(T nextNeighbour){
		return function.valueAt(decoder.decode(nextNeighbour));
	}

}
