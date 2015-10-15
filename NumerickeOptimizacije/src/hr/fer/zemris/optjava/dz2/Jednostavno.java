package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Jednostavno {

	public static void main(String[] args) {
		double[] bla = {-5,5};
		RealMatrix point = new Array2DRowRealMatrix(bla);
//		NumOptAlgorithms.GradientDescentMethod(new Zad2(), 15, point);
		
//		
//		double[] coef1 ={1.0,0.0,-5};
//		double[] coef2 ={0.0,1.0, -5};
//		RealMatrix coefM1 = new Array2DRowRealMatrix(coef1);
//		RealMatrix coefM2 = new Array2DRowRealMatrix(coef2);
//		RealMatrix[] coefM = {coefM1, coefM2};
//		Zad3 novi = new Zad3(coefM);
//		System.out.println(novi.GetHesseMatrix(point));
	}

}
