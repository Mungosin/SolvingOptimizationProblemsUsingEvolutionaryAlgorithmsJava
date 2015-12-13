package hr.fer.zemris.optjava.dz8;

import java.util.LinkedList;

/**
 * Class is used to save data, separating inputs from their outputs
 *
 */
public class Data {
	public LinkedList<Double> input;
	public LinkedList<Double> output;
	
	/**
	 * Constructor for Data class
	 * @param inputs inputs for the data
	 * @param outputs outputs for the given inputs
	 */
	public Data(LinkedList<Double> inputs, LinkedList<Double> outputs){
		this.input = new LinkedList<>(inputs);
		this.output = new LinkedList<>(outputs);
	}
	
}
