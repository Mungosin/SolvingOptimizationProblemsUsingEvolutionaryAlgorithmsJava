package hr.fer.zemris.optjava.dz2;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Sustav {

	public static void main(String[] args) {
		int maxIterations;
		String filePath;
		if(args.length != 3){
			System.out.println("Parameters are not correct, first is \"grad\" or \"newton\" for which algorithm to use, second is number of max iterations and third is the path "
					+ "to the text file containing the definition of system ");
			return;
		}else {
			try{
				maxIterations = Integer.parseInt(args[1]);
			}catch (NumberFormatException e){
				System.out.println("second parameter must be a number defining maximum iterations");	
			}
			Random rand = new Random(System.currentTimeMillis());
			double[] randomPoint = { rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 
									 rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), rand.nextDouble(),
									 rand.nextDouble(), rand.nextDouble()};
			RealMatrix point = new Array2DRowRealMatrix(randomPoint);
			
			filePath = args[2];
			
			switch(args[0]){
			case "grad":
				
			case "newton":
				
				default:
					System.out.println("Parameters are not correct");
			}
		}		
	}
	
	
	public static RealMatrix[] ReadUsefulRows(String filePath){
		File definition = new File(Paths.get(filePath).toAbsolutePath().toString());
		ArrayList<double[]> listOfCoefficients = new ArrayList<>();
		Scanner scan=null;
		String currentLine;
		String tokens[];
		double[] matrixData;
		RealMatrix[] matrices;
		boolean fileCorrect;
		try {
			scan = new Scanner(definition);
			fileCorrect = true;
		} catch (FileNotFoundException e) {
			fileCorrect = false;
		}
		if(!fileCorrect || scan==null){
			return null;
		}
		while(scan.hasNextLine()){
			currentLine = scan.nextLine();
			if(currentLine.contains("#")){
				continue;
			}else {
				currentLine = currentLine.replace("[", "");
				currentLine = currentLine.replace("]", "");
				tokens = currentLine.split(",");
				int numberOfDoubles= tokens.length;
				matrixData = new double[numberOfDoubles];
				for(int i=0;i<numberOfDoubles;i++){
					matrixData[i] = Double.parseDouble(tokens[i]);
				}	
				listOfCoefficients.add(matrixData);
			}
		}
		int coefficientsLength = listOfCoefficients.size();
		matrices = new RealMatrix[coefficientsLength];
		for(int i=0;i<coefficientsLength;i++){
			matrices[i] = new Array2DRowRealMatrix(listOfCoefficients.get(i));
		}
		return matrices;	
	}
	
	
}
