package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Zad3 implements IHFunction{
	private RealMatrix[] coefficients;
	private double[] solution;
	private int numberOfVariables;
	
	public Zad3(RealMatrix[] coefficients){
		this.coefficients = coefficients;
		int rowLength = coefficients.length;
		this.numberOfVariables = coefficients[0].getColumn(0).length-1;
		double[] solutions = new double[rowLength];
		for(int i=0;i<rowLength;i++){
			solutions[i] = coefficients[i].getColumn(0)[numberOfVariables];
		}
		this.solution = solutions;
	}
	@Override
	public int GetNumberOfVariables() {
		return numberOfVariables;
	}

	@Override
	public double GetValueOfFunction(RealMatrix point) {
		return numberOfVariables;
	}

	@Override
	public RealMatrix GetGradient(RealMatrix point) {
		return point;
		
	}

	@Override
	public RealMatrix GetHesseMatrix(RealMatrix point) {
		int lengthOfCoefficients = coefficients.length;
		int numberOfVariables = GetNumberOfVariables();
		double[][] hessian = new double[numberOfVariables][numberOfVariables];
		double[] currentCoefficients;
		for(int i=0;i<lengthOfCoefficients;i++){
			currentCoefficients=coefficients[i].getColumn(0);
			for(int j=0;j<numberOfVariables;j++){
				for(int z=0;z<numberOfVariables;z++){
					hessian[j][z] += currentCoefficients[j] * currentCoefficients[z] * 2;
				}
			}
		}
		return new Array2DRowRealMatrix(hessian);
	}
}
