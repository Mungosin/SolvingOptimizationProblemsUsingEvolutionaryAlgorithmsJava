package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Jednostavno {

	public static void main(String[] args) {
		double[] bla = {-5,-5};
		RealMatrix point = new Array2DRowRealMatrix(bla);
		NumOptAlgorithms.GradientDescentMethod(new Zad1(), 100, point);
	}

}
