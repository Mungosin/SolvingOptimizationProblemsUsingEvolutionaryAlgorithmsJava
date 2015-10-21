package hr.fer.zemris.optjava.dz3;
/**
 * Class that implements Constants for the Algorithm implementations
 */
public class Constants {
	public static final double maxs = -10;
	public static final double mins = 10;
	
	public static final double[] deltas ={0.3,0.3,0.3,0.3,0.3,0.3};
	public static final double[] lowerBounds ={-5,-5,-5,-5,-5,-5};
	public static final double[] upperBounds ={5,5,5,5,5,5};
	
	
	public static final boolean minimize = true;
	
	public static final double alpha = 0.975;
	public static final double beginingTemp = 1E7;
	public static final int innerLimit = 10000;
	public static final int outerLimit = 1000;
	
	public static final int greedyIterations = 10000;
}
