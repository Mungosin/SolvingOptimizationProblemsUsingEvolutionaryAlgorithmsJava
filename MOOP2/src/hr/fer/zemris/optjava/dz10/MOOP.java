package hr.fer.zemris.optjava.dz10;


import hr.fer.zemris.optjava.dz10.func.FitFun;
import hr.fer.zemris.optjava.dz10.interfaces.IMoopOptFun;
import hr.fer.zemris.optjava.dz10.nsga.GroupingSelection;
import hr.fer.zemris.optjava.dz10.nsga.NSGA;
import hr.fer.zemris.optjava.dz10.nsga.NSGASolution;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;



public class MOOP {

	public static void main(String[] args) {
		if(args.length!=4){
			System.out.println("there must be 4 paremeters: integer defining either problem 1 or 2, \npopulation size\n"
					+ "maximal number of iterations \nand sigma parameter for mutation");
			return;
		}
		
		int problemNumber;
		int sizeOfPop;
		String type;
		int maxIter;
		double sigma;
		try{
		problemNumber = Integer.parseInt(args[0]);
		sizeOfPop = Integer.parseInt(args[1]);
		type  = "objective-space";
		maxIter = Integer.parseInt(args[2]);
		sigma = Double.parseDouble(args[3]);
		}catch(Exception e){
			System.out.println("Invalid parameter format");
			return;
		}
		IMoopOptFun function = null;
		if(type.equals("decision-space")){
			function = new FitFun(problemType(problemNumber), false);

		}else if(type.equals("objective-space")){
			function = new FitFun(problemType(problemNumber), true);

		}else {
			System.out.println("must be either objective-space or decision-space for type");
			return;
		}
		
		NSGA algorithm = new NSGA(sizeOfPop, maxIter, new GroupingSelection(new Random(System.currentTimeMillis()), Constants.numberOfContenders, function.getMOOPProblem().getObjectiveMin(), function.getMOOPProblem().getObjectiveMax()), sigma, function);
		algorithm.run();
		savePointsToFile(algorithm);
		
	}
	
	private static void savePointsToFile(NSGA alg){
		BufferedWriter outDec = null;
		BufferedWriter outObj = null;

		try {
			FileWriter decStream = new FileWriter("izlaz-dec.txt", false);
			FileWriter objStream = new FileWriter("izlaz.obj.txt", false);

			outDec = new BufferedWriter(decStream);
			outObj = new BufferedWriter(objStream);

			LinkedList<LinkedList<NSGASolution>> paretoFronts = alg.getFronts();
			
			int frontNum = 1;
			for (LinkedList<NSGASolution> front : paretoFronts) {
				System.out.println(frontNum++ +". front contains: " + front.size() + " number of elements");
				for (NSGASolution solution : front) {
					outDec.write(solution.valToString() + "\n");
					outObj.write(solution.objToString() + "\n");
				}
				outDec.write("\n");
				outObj.write("\n");
			}
		} catch (IOException ignorable) {
		} finally {
			if (outDec != null) {
				try {
					outDec.close();
				} catch (IOException e) {
				}
			}
			if (outObj != null) {
				try {
					outObj.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private static MOOPProblem problemType(int label) {
		if (label == 1) {
			double[] prob1Min = new double[Constants.PROBLEM1_DOMAIN_DIM];
			double[] prob1Max = new double[Constants.PROBLEM1_DOMAIN_DIM];

			for (int i = 0; i < prob1Min.length; i++) {
				prob1Max[i] = Constants.UPPER_BOUNDARY_PROB1;
				prob1Min[i] = Constants.LOWER_BOUNDARY_PROB1;
			}
			return new Prob1(Constants.PROBLEM1_DOMAIN_DIM, prob1Min, prob1Max);

		} else if (label == 2) {

			double[] prob2Min = new double[] { Constants.LOWER_BOUNDARY_PROB2_X1, Constants.LOWER_BOUNDARY_PROB2_X2 };
			double[] prob2Max = new double[] { Constants.UPPER_BOUNDARY_PROB2_X1, Constants.UPPER_BOUNDARY_PROB2_X2 };

			return new Prob2(Constants.PROBLEM2_DOMAIN_DIM, prob2Min, prob2Max);
		} else
			throw new IllegalArgumentException("Label value must be 1 for the first problem or 2 for the second problem");
	}
}
