package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.RealMatrix;

public class NumOptAlgorithms {

	public static RealMatrix GradientDescentMethod(IHFunction object, int maxIterations, RealMatrix point){
		HelperFunctions myFunc = new HelperFunctions(object);
		RealMatrix currentPoint = point;
		double[] pointValues;
		double functionValue;
		double lamda = 0;
		for(int i=0;i<maxIterations; i++){
			if(myFunc.CheckIfPointIsOptimum(currentPoint)){
				return currentPoint;
			}
			lamda = myFunc.FindLamdaForCurrentPoint(currentPoint);
			currentPoint = myFunc.CalculateMyPoint(lamda, currentPoint);
			pointValues = currentPoint.getColumn(0);
			functionValue = object.GetValueOfFunction(currentPoint);
			System.out.println("("+pointValues[0]+", "+ pointValues[1]+")"+ "-> function value = " + functionValue);
		}
		return currentPoint;
	}
	
	public static void NewtonMerthod(IHFunction object, int maxIterations, RealMatrix point){
		
	}
}
