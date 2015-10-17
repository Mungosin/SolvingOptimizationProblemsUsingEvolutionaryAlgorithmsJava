package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class HelperIFunction {
	private IFunction myFunction;
	
	public HelperIFunction(IFunction myFunc){
		myFunction = myFunc;
	}
	public double DerivationOfFiByLamda(RealMatrix newPoint, RealMatrix currentPoint){
		RealMatrix gradientTransponed = myFunction.GetGradient(newPoint).transpose();
		RealMatrix dVector = myFunction.GetGradient(currentPoint).scalarMultiply(-1.);
		return gradientTransponed.multiply(dVector).getColumn(0)[0]; //gets the first element of the first collumn, which is the only element in the matrix
	} 
	
	public RealMatrix CalculateMyPoint(double lamda, RealMatrix x){
		RealMatrix dVector = myFunction.GetGradient(x).scalarMultiply(-1.);
		return x.add(dVector.scalarMultiply(lamda)); 
	}
	
	public double FindLamdaForCurrentPoint(RealMatrix x){
		double lamdaLower = 0;
		double lamdaUpper = 1;
		double lamdaOpt=0;
		double derivationValue;
		int iterations = Constants.lamdaIterations;
		RealMatrix currentPoint = CalculateMyPoint(lamdaUpper, x);
		while(DerivationOfFiByLamda(currentPoint, x)<0){
			lamdaUpper *=2;
			currentPoint = CalculateMyPoint(lamdaUpper, x);
		}
		lamdaOpt = (lamdaLower+lamdaUpper)/2;
		currentPoint = CalculateMyPoint(lamdaOpt, x);
		derivationValue = DerivationOfFiByLamda(currentPoint,x);
		while(!(-1*Constants.tolerance<=derivationValue && derivationValue<=Constants.tolerance) && iterations-->0){
			if(derivationValue>0){
				lamdaUpper = lamdaOpt;
			}else {
				lamdaLower = lamdaOpt;
			}
			lamdaOpt = (lamdaLower+lamdaUpper)/2;
			currentPoint = CalculateMyPoint(lamdaOpt, x);
			derivationValue = DerivationOfFiByLamda(currentPoint,x);
		}
		return lamdaOpt;
	}
	
	public boolean CheckIfPointIsOptimum(RealMatrix point){
		RealMatrix gradient = myFunction.GetGradient(point);
		double roundingFactor = Math.pow(10, Constants.roundDoublePrecision);
		double[] values = gradient.getColumn(0);
		for(double value : values){
			if ((Math.round( value * roundingFactor ) / roundingFactor) != 0){
				return false;
			}
		}
		return true;
	}
}
