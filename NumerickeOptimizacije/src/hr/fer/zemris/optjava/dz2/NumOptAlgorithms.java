package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.RealMatrix;

public class NumOptAlgorithms {

	public static RealMatrix GradientDescentMethod(IHFunction object, int maxIterations, RealMatrix point){
		HelperIFunction myFunc = new HelperIFunction(object);
		RealMatrix currentPoint = point;
		double[] pointValues;
		double functionValue;
		double pointLength;
		double roundingFactor = Math.pow(10, Constants.roundDoublePrecision);
		double lamda = 0;
		for(int i=0;i<maxIterations; i++){
			if(myFunc.CheckIfPointIsOptimum(currentPoint)){
				return currentPoint;
			}
			lamda = myFunc.FindLamdaForCurrentPoint(currentPoint);
			currentPoint = myFunc.CalculateMyPoint(lamda, currentPoint);
			pointValues = currentPoint.getColumn(0);
			functionValue = object.GetValueOfFunction(currentPoint);
			pointLength = pointValues.length;
			System.out.print("(");
			for(int j=0;j<pointLength;j++){
				if(j==pointLength-1){
					System.out.format("%10.10f", pointValues[j]);
					break;
				}
				System.out.format("%10.10f, ", pointValues[j]);
			}
			System.out.println(") -> function value = " + (Math.round( functionValue * roundingFactor ) / roundingFactor));
		}
		return currentPoint;
	}
	
	public static RealMatrix NewtonMethod(IHFunction object, int maxIterations, RealMatrix point){
		HelperIHFunction myHFunc = new HelperIHFunction(object);
		RealMatrix currentPoint = point;
		double[] pointValues;
		double functionValue;
		double pointLength;
		double roundingFactor = Math.pow(10, Constants.roundDoublePrecision);
		double lamda = 0;
		for(int i=0;i<maxIterations; i++){
			if(myHFunc.CheckIfPointIsOptimum(currentPoint)){
				return currentPoint;
			}
			lamda = myHFunc.FindLamdaForCurrentPoint(currentPoint);
			currentPoint = myHFunc.CalculateMyPoint(lamda, currentPoint);
			pointValues = currentPoint.getColumn(0);
			functionValue = object.GetValueOfFunction(currentPoint);
			pointLength = pointValues.length;
			System.out.print("(");
			for(int j=0;j<pointLength;j++){
				if(j==pointLength-1){
					System.out.format("%10.10f", pointValues[j]);
					break;
				}
				System.out.format("%10.10f, ", pointValues[j]);
			}
			System.out.println(") -> function value = " + (Math.round( functionValue * roundingFactor ) / roundingFactor));
		}
		return currentPoint;
	}
}
