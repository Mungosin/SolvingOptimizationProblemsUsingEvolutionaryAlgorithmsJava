package hr.fer.zemris.optjava.dz4.part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
/**
 * Class is implementing the function from numeric optimisations problem 4
 *
 */

public class Zad4 implements IFunction{
	private RealMatrix[] coefficients;
	private double[] solution;
	private int numberOfVariables;
	
	/**
	 * Constructor for Zad4 that initializes coefficients and solutions, aswell as number of variables
	 * @param filePath to the file that contains definition of the problem
	 */
	public Zad4(String filePath){
		RealMatrix[] coefficients = ReadUsefulRows(filePath);
		if(coefficients == null){
			throw new InvalidParameterException("Invalid file path or file format");
		}
		this.coefficients = coefficients;
		int rowLength = coefficients.length;
		this.numberOfVariables = coefficients[0].getColumn(0).length-1;
		double[] solutions = new double[rowLength];
		for(int i=0;i<rowLength;i++){
			solutions[i] = coefficients[i].getColumn(0)[numberOfVariables];
		}
		this.solution = solutions;
	}

	/**
	 * Method is used to read the useful values from a text file whose path is given as a parameter
	 * @param filePath path to the text file with informations about the problem
	 * @return coefficients of the problem sorted in a matrix and the solution as at the last index of the column
	 */
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
	
	
	@Override
	public double valueAt(double[] point) {
		double value=0;
		double currentValue=0;
		double[] coeff;
		for(int i=0;i<coefficients.length;i++){
			coeff = coefficients[i].getColumn(0);
			currentValue =  point[0] * coeff[0] +
					point[1] * Math.pow(coeff[0], 3) * coeff[1] +
					point[2] * Math.exp(point[3]*coeff[2]) * (1+Math.cos( point[4]* coeff[3])) + 
					point[5] * coeff[3]*Math.pow(coeff[4], 2);
			currentValue -= solution[i];
			currentValue = Math.pow(currentValue, 2);
			value += currentValue;
		}
		return value;
	}

}
