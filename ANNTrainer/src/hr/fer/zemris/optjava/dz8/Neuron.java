package hr.fer.zemris.optjava.dz8;

/**
 * Class implements methods and attributes of a neuron in the neural network
 *
 */
public class Neuron {
	int numberOfInputs;
	IFunction transferFunc;
	
	/**
	 * Constructor for neuron class
	 * @param numberOfInputs number of inputs for the neuron
	 * @param transferFunc transfer function used by the neuron
	 */
	public Neuron(int numberOfInputs, IFunction transferFunc){
		this.numberOfInputs = numberOfInputs;
		this.transferFunc = transferFunc;
	}
	
	/**
	 * Method is used to calculate output of the neuron
	 * @param input inputs of the neuron
	 * @param weight weights of the neuron
	 * @return calculated output
	 */
	public double calculateOutput(double input[], double[] weight){
		
		if(input.length != weight.length){
			throw new IllegalArgumentException("There must be same number of weights and inputs for a give neuron");
		}
		
		double temp=0;
		for(int i=0;i<input.length;i++){
			temp+=input[i] * weight[i];
		}
		return transferFunc.valueAt(temp);
	}
}
