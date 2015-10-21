package hr.fer.zemris.optjava.dz3;

import java.util.Arrays;
import java.util.Random;

public class RegresijaSustava {

	public static void main(String[] args) {
		if(args.length != 3){
			System.out.println("Parameters are not correct, first is the parameter is the path "
					+ "to the text file containing the definition of system  the second is the way of solving, binary or decimal, and the third is the algorithm greedy or annealing");
			return;
		}else {
			
			
			Zad4 function = new Zad4(args[0]);
			if(args[1].contains("decimal")){
				

				IDecoder<DoubleArraySolution> decoder = new PassTroughDecoder();
				INeighbourhood<DoubleArraySolution> neighbourhood = new DoubleArrayNormNeighbourhood(Constants.deltas);
				DoubleArraySolution startWithDouble = new DoubleArraySolution(6);
				startWithDouble.Randomize(new Random(System.currentTimeMillis()), Constants.lowerBounds, Constants.upperBounds);
				
				switch(args[2]){
				case "greedy":
					GreedyAlgorithm<DoubleArraySolution> greedy= new GreedyAlgorithm<> (decoder, neighbourhood, startWithDouble, function, Constants.minimize);
					greedy.run();
					return;
				case "annaeling":
					ITempSchedule sched = new GeometricTempSchedule(Constants.alpha, Constants.beginingTemp, Constants.innerLimit, Constants.outerLimit);
					SimulatedAnnealing<DoubleArraySolution> annaeling = new SimulatedAnnealing<> (decoder, neighbourhood, startWithDouble, function, Constants.minimize, sched);
					annaeling.run();
					break;
					default:
						System.out.println("Parameters are not correct");
						return;
				}
				
			}else if(args[1].contains("binary:")){
				String token[] = args[1].split(":");
				if(token.length!=2){
					System.out.println("Wrong second parameter, it has to be decimal or binary:NumberOfBitSPerVariable");
					return;
				}else {
					int n;
					try{
						n = Integer.parseInt(token[1]);
					}catch (NumberFormatException e){
						System.out.println("Wrong second parameter, it has to be decimal or binary:NumberOfBitSPerVariable");
						return;
					}
					
					int[] bits = new int[6*n];
					double[] mins = new double[6*n];
					double[] maxs = new double[6*n];
					Arrays.fill(mins, Constants.mins);
					Arrays.fill(maxs, Constants.maxs);
					
					Random rand = new Random(System.currentTimeMillis());
					BitVectorSolution startWithBinary = new BitVectorSolution(6*n);
					startWithBinary.Randomize(rand);
					IDecoder<BitVectorSolution> decoder = new GreyBinaryDecoder(mins, maxs, bits, 6);
					INeighbourhood<BitVectorSolution> neighbourhood = new BitVectorNeighbourhood(n,bits.length);
					
					switch(args[2]){
					case "greedy":
						GreedyAlgorithm<BitVectorSolution> greedy= new GreedyAlgorithm<>(decoder, neighbourhood, startWithBinary, function, Constants.minimize);
						greedy.run();
						break;
					case "annaeling":
						ITempSchedule sched = new GeometricTempSchedule(Constants.alpha, Constants.beginingTemp, Constants.innerLimit, Constants.outerLimit);
						SimulatedAnnealing<BitVectorSolution> annaeling = new SimulatedAnnealing<> (decoder, neighbourhood, startWithBinary, function, Constants.minimize, sched);
						annaeling.run();
						break;
						default:
							System.out.println("Parameters are not correct");
							return;
					}
				}
			}else{
				System.out.println("Wrong second parameter, it has to be decimal or binary:NumberOfBitSPerVariable");
				
			}
		}
	}

}
