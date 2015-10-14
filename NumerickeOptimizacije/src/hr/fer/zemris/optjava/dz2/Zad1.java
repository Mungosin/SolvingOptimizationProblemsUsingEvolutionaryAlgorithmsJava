package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Zad1 implements IHFunction{
	// f(x1,x2) = x1^2 +(x2-1)^2
	@Override
	public int GetNumberOfVariables() {
		return 2;
	}

	@Override
	public double GetValueOfFunction(RealMatrix point) {
		double[] points = point.getColumn(0);	
		return ( Math.pow(points[0],2) + Math.pow(points[1]-1, 2));
	}

	@Override
	public RealMatrix GetGradient(RealMatrix point) {
		double[] points = point.getColumn(0);
		double[] matrixData = {2*(points[0]), 2*(points[1] -1)};
		return new Array2DRowRealMatrix(matrixData);
	}

	@Override
	public RealMatrix GetHesseMatrix(RealMatrix point) {
		double[][] matrixData = {{2 ,0 }, { 0,2}};
		return new Array2DRowRealMatrix(matrixData);
	}


}
