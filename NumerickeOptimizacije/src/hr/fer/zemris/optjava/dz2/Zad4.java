package hr.fer.zemris.optjava.dz2;

import java.util.ArrayList;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Zad4 implements IHFunction{
	private RealMatrix[] coefficients;
	private double[] solution;
	private int numberOfVariables;
	
	public Zad4(RealMatrix[] coefficients){
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
		double[] points = point.getColumn(0);
		double value=0;
		double currentValue=0;
		double[] coeff;
		for(int i=0;i<coefficients.length;i++){
			coeff = coefficients[i].getColumn(0);
			currentValue =  points[0] * coeff[0] +
					points[1] * Math.pow(coeff[0], 3) * coeff[1] +
					points[2] * Math.exp(points[3]*coeff[2]) * (1+Math.cos( points[4]* coeff[3])) + 
					points[5] * coeff[3]*Math.pow(coeff[4], 2);
			currentValue -= solution[i];
			currentValue = Math.pow(currentValue, 2);
			value += currentValue;
		}
		return value;
	}

	@Override
	public RealMatrix GetGradient(RealMatrix point) {
		double[] gradient = new double[point.getColumn(0).length];
		int lengthCoefficients = coefficients.length;
		double[] points = point.getColumn(0);
		double[] coeff;
		double currentValue = 0;
		for(int i=0;i<lengthCoefficients;i++){
			coeff = coefficients[i].getColumn(0);
			currentValue =  points[0] * coeff[0] +
					points[1] * Math.pow(coeff[0], 3) * coeff[1] +
					points[2] * Math.exp(points[3]*coeff[2]) * (1+Math.cos( points[4]* coeff[3])) + 
					points[5] * coeff[3]*Math.pow(coeff[4], 2);
			currentValue -= solution[i];
 
			// 0-a, 1-b, 2-c, 3-d,4-g, 5-f
			// 0-x1, 1-x2, 2-x3, 3-x4, 4-x5
			
			gradient[0]+=2* coeff[0] * currentValue;
			gradient[1]+=2* Math.pow(coeff[0], 3) * coeff[1] * currentValue;
			gradient[2]+=2* Math.exp(points[3]*coeff[2])*(1+Math.cos(points[4] * coeff[3])) * currentValue;
			gradient[3]+=2* points[2]* coeff[2]	* Math.exp(points[3]*coeff[2]) * (1+Math.cos(points[4] * coeff[3]))* currentValue;
			gradient[4]+=-2*(points[2] * coeff[3] * Math.exp(points[3]*coeff[2]) * Math.sin(points[4]*coeff[3])) * currentValue;
			gradient[5]+=2* coeff[3]* Math.pow(coeff[4], 2) * currentValue;
			
		}
		return new Array2DRowRealMatrix(gradient);
	}

	@Override
	public RealMatrix GetHesseMatrix(RealMatrix point) {
		int lengthOfCoefficients = coefficients.length;
		int numberOfVariables = GetNumberOfVariables();
		double[][] hessian = new double[6][6];
		double[] coeff;
		double[] gradient;
		double currentValue = 0;
		for(int i=0;i<lengthOfCoefficients;i++){
			coeff=coefficients[i].getColumn(0);
			double[] points = point.getColumn(0);
			currentValue =  points[0] * coeff[0] +
					points[1] * Math.pow(coeff[0], 3) * coeff[1] +
					points[2] * Math.exp(points[3]*coeff[2]) * (1+Math.cos( Math.exp(1)* coeff[3])) + //neznam da li je e nepoznanica ili euler
					points[4] * coeff[3]*coeff[4];
			currentValue -= solution[i];
			// 0-a, 1-b, 2-c, 3-d,4-g, 5-f
			// 0-x1, 1-x2, 2-x3, 3-x4, 4-x5
			
			hessian[0][0] += 2* coeff[0] * coeff[0];
			hessian[0][1] += 2 * Math.pow(coeff[0],4) * coeff[1];
			hessian[0][2] += functionCoefficientsDerivedFirstTimeByA * functionDerivedSecondTimeByC;
			hessian[0][3] += functionCoefficientsDerivedFirstTimeByA * functionDerivedSecondTimeByD;
			hessian[0][4] += functionCoefficientsDerivedFirstTimeByA * functionDerivedSecondTimeByF;
			hessian[0][5]
			
			hessian[1][0] += functionCoefficientsDerivedFirstTimeByB * functionDerivedSecondTimeByA;
			hessian[1][1] += functionCoefficientsDerivedFirstTimeByB * functionDerivedSecondTimeByB;
			hessian[1][2] += functionCoefficientsDerivedFirstTimeByB * functionDerivedSecondTimeByC;
			hessian[1][3] += functionCoefficientsDerivedFirstTimeByB * functionDerivedSecondTimeByD;
			hessian[1][4] += functionCoefficientsDerivedFirstTimeByB * functionDerivedSecondTimeByF;
			hessian[1][5]
			
			hessian[2][0] += functionCoefficientsDerivedFirstTimeByC * functionDerivedSecondTimeByA;
			hessian[2][1] += functionCoefficientsDerivedFirstTimeByC * functionDerivedSecondTimeByB;
			hessian[2][2] += functionCoefficientsDerivedFirstTimeByC * functionDerivedSecondTimeByC;
			hessian[2][3] += functionCoefficientsDerivedFirstTimeByC * functionDerivedSecondTimeByD;
			hessian[2][4] += functionCoefficientsDerivedFirstTimeByC * functionDerivedSecondTimeByF;
			hessian[2][5]
			
			hessian[3][0] += functionCoefficientsDerivedFirstTimeByD * functionDerivedSecondTimeByA;
			hessian[3][1] += functionCoefficientsDerivedFirstTimeByD * functionDerivedSecondTimeByB;
			hessian[3][2] += functionCoefficientsDerivedFirstTimeByD * functionDerivedSecondTimeByC;
			hessian[3][3] += functionCoefficientsDerivedFirstTimeByD * functionDerivedSecondTimeByD;
			hessian[3][4] += functionCoefficientsDerivedFirstTimeByD * functionDerivedSecondTimeByF;
			hessian[3][5]
			
			hessian[4][0] += functionCoefficientsDerivedFirstTimeByF * functionDerivedSecondTimeByA;
			hessian[4][1] += functionCoefficientsDerivedFirstTimeByF * functionDerivedSecondTimeByB;
			hessian[4][2] += functionCoefficientsDerivedFirstTimeByF * functionDerivedSecondTimeByC;
			hessian[4][3] += functionCoefficientsDerivedFirstTimeByF * functionDerivedSecondTimeByD;
			hessian[4][4] += functionCoefficientsDerivedFirstTimeByF * functionDerivedSecondTimeByF;
			hessian[4][5]
					
			hessian[5][5]
		}
		return new Array2DRowRealMatrix(hessian);
	}

}
