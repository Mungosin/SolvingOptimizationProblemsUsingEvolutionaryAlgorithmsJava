package hr.fer.zemris.optjava.dz9;


import hr.fer.zemris.optjava.dz9.func.FitFun;
import hr.fer.zemris.optjava.dz9.interfaces.IMoopOptFun;
import hr.fer.zemris.optjava.dz9.nsga.NSGA;
import hr.fer.zemris.optjava.dz9.nsga.NSGASolution;
import hr.fer.zemris.optjava.dz9.nsga.ProportionalSelection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;



public class MOOP {

	public static void main(String[] args) {
		if(args.length!=5){
			System.out.println("there must be 5 paremeters: integer defining either problem 1 or 2 \n, population size\n domain type \"decision-space\" or \"objective-space\" \n"
					+ "maximal number of iterations \n and sigma parameter for mutation");
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
		type  = args[2];
		maxIter = Integer.parseInt(args[3]);
		sigma = Double.parseDouble(args[4]);
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
		
		NSGA algorithm = new NSGA(sizeOfPop, maxIter, new ProportionalSelection(new Random(System.currentTimeMillis())), sigma, function);
		algorithm.run();
		savePointsToFile(algorithm);
		
	}
	
	private static void savePointsToFile(NSGA alg){
		BufferedWriter outDec = null;
		BufferedWriter outObj = null;

		try {
			FileWriter decStream = new FileWriter("output-decisionSpace.txt", false);
			FileWriter objStream = new FileWriter("output-objectiveSpace.txt", false);

			outDec = new BufferedWriter(decStream);
			outObj = new BufferedWriter(objStream);

			LinkedList<LinkedList<NSGASolution>> paretoFronts = alg.getFronts();
			int frontNum = 1;
			for (LinkedList<NSGASolution> front : paretoFronts) {
				
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
