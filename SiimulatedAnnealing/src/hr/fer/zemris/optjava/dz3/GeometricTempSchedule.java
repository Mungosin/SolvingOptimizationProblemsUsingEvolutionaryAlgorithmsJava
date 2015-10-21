package hr.fer.zemris.optjava.dz3;
/**
 * Class implements ITempSchedule and methods required to enable geometric progression of the temperature
 *
 */
public class GeometricTempSchedule implements ITempSchedule{
	private double alpha;
	private double tInitial;
	private double tCurrent;
	private int innerLimit;
	private int outerLimit;
	
	/**
	 * Constructor for geometric temp schedule
	 * @param alpha coefficient of the geometric series
	 * @param tInitial initial value of the temperature
	 * @param innerLimit inner loop limit
	 * @param outerLimit outer loop limit
	 */
	public GeometricTempSchedule(double alpha, double tInitial, int innerLimit, int outerLimit){
		this.alpha = alpha;
		this.tInitial = tInitial;
		this.tCurrent = tInitial;
		this.innerLimit = innerLimit;
		this.outerLimit = outerLimit;
	}
	
	@Override
	public double getNextTemperature() {
		double tNext = tCurrent;
		tCurrent = tCurrent * alpha;
		return tNext;
	}

	@Override
	public int getInnerLoopCounter() {
		return innerLimit;
	}

	@Override
	public int getOuterLoopCounter() {
		return outerLimit;
	}

}
