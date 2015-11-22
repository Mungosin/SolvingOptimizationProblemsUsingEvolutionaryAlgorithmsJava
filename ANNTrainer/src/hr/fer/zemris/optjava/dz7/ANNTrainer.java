package hr.fer.zemris.optjava.dz7;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Class that implements main method which parses the user input and starts the appropriate neural network training algorithm
 *
 */
public class ANNTrainer {

	/**
	 * Main method
	 * @param args command line algorithms
	 */
	public static void main(String[] args) {
		String file;
		String alg;
		int n;
		double merr;
		int maxiter;
		Dataset data;

		if (args.length != 5) {
			System.out.println(
					"Program requries 5 arguments, file path to dataset, algorithm wanted, size of population, max acceptable error and maximum number of iterations");
			return;
		}
		try {
			file = args[0];
			File dataFile = new File(file);
			Scanner scan = new Scanner(dataFile);
			LinkedList<Data> myDataList = new LinkedList<>();
			while (scan.hasNextLine()) {
				String line = scan.nextLine();

				line = line.replace("(", "");
				line = line.replace(")", "");
				String tokens[] = line.split(":");
				String leftToken[] = tokens[0].split(",");
				String rightToken[] = tokens[1].split(",");
				LinkedList<Double> inputs = new LinkedList<Double>();
				for (String element : leftToken) {
					inputs.add(Double.parseDouble(element));
				}

				LinkedList<Double> outputs = new LinkedList<Double>();
				for (String element : rightToken) {
					outputs.add(Double.parseDouble(element));
				}

				Data myData = new Data(inputs, outputs);
				myDataList.add(myData);
			}
			data = new Dataset(myDataList);
		} catch (Exception e) {
			System.out.println("File incorrectly formatted");
			return;
		}
		
		
		
		try{
			n = Integer.parseInt(args[2]);
			if(n<10){
				System.out.println("size of population list can't be less than 10");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("Third parameter must be a number indicating the population size");
			return;
		}
		
		try{
			merr = Double.parseDouble(args[3]);
			if(merr<0 && merr >1){
				System.out.println("Max error must be between 0 and 1");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("Fourth parameter must be a number indicating the maximum error tolerated");
			return;
		}
		
		try{
			maxiter = Integer.parseInt(args[4]);
			if(maxiter<1){
				System.out.println("Maximum number of iterations can't be less than 1");
				return;
			}
		} catch(NumberFormatException e){
			System.out.println("Fifth parameter must be a number indicating the maximal number of iterations");
			return;
		}
		
		
		
		FFANN neural = new FFANN(new int[] {4, 3,5,7,3}, new IFunction[]{
				new SigmoidFunction(),new SigmoidFunction(),new SigmoidFunction(),new SigmoidFunction()
		}, data);
		

		EvaluateSolution evaluator = new EvaluateSolution(neural);
		try{
			String wantedAlg = args[1];
			int neighbourhood=0;
			if(wantedAlg.contains("pso")){
				if(wantedAlg.toLowerCase().equals("pso-a")){
					PSO pso = new PSO(n, true, merr, maxiter, neural.getWeightsCount(), evaluator, neighbourhood);
					pso.run();
				}else{
					String tokens[] = wantedAlg.split("-");
					neighbourhood = Integer.parseInt(tokens[tokens.length-1]);
					PSO pso = new PSO(n, true, merr, maxiter, neural.getWeightsCount(), evaluator, neighbourhood);
					pso.run();
				}
			}else{
				if(wantedAlg.toLowerCase().equals("clonalg")){
					ClonAlg clon = new ClonAlg (n, merr, maxiter, neural.getWeightsCount(),evaluator );
					clon.run();
				}
			}
		}catch (Exception e){
			System.out.println("You must choose between pso-a with global best, pso-b-d where there is d neighbours in consideration and clonalg");
		}
		
	
	}
}
