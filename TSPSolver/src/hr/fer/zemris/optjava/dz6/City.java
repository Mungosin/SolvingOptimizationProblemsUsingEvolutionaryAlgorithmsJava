package hr.fer.zemris.optjava.dz6;
/**
 * Class defines attributes and methods required to represent a city in the path of traveling salesman problem
 */
public class City {
	
	public double x;
	public double y;
	public int index;
	
	/**
	 * Constructor for City class
	 * @param index index of the city
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public City(int index, double x, double y){
		this.x = x;
		this.y = y;
		this.index = index;
	}

	@Override
	public String toString(){
		return index + ". City " + "x: " + x + "  y: " + y + "\n";
	}
}
