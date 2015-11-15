package hr.fer.zemris.optjava.dz6;
/**
 * Class that implements Constants for the Algorithm implementations
 */
public class Constants {
	public static final double alfa = 0.98;
	public static final double beta = 3.5;
	public static final double a = 50; //Tmax/a = TMin
	public static final double sigma = 0.01;
	public static final int bestAntsThatUpdateFeromones = 20;
	public static final int resetTMax = 1500; //if after 10 iterations distance hasnt decreased reinitialize feromones;
}
