package hr.fer.zemris.optjava.dz6;
/**
 * Class that implements Constants for the Algorithm implementations
 */
public class Constants {
	public static final double alfa = 1;
	public static final double beta = 5;
	public static final double a = 50; //Tmax/a = TMin
	public static final double sigma = 0.02;
	public static final int bestAntsThatUpdateFeromones = 6;
	public static final int resetTMax = 200; //if after this number of iterations distance hasnt decreased reinitialize feromones;
}
