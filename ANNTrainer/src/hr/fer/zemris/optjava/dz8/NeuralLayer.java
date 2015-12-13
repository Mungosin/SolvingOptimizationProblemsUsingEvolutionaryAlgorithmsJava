package hr.fer.zemris.optjava.dz8;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class implements methods required to work with a neural network layer
 *
 */
public class NeuralLayer {
	int numberOfInputs;
	LinkedList<Neuron> neurons;
	int numWeight;
	
	/** 
	 * Constructor for neural network layer 
	 * @param numberOfInputs number of inputs per neuron for current layer
	 * @param numberOfNeurons number of neurons in the layer 
	 * @param transferFunc transfer function used to calculate the output
	 */
	public NeuralLayer(int numberOfInputs, int numberOfNeurons, IFunction transferFunc){
		this.numberOfInputs = numberOfInputs;
		this.neurons = new LinkedList<Neuron>();
		for(int i=0;i<numberOfNeurons;i++){
			neurons.add(new Neuron(numberOfInputs, transferFunc));
		}
		this.numWeight = numberOfNeurons * numberOfInputs;
	}
	
	/**
	 * Method is used to get the number of weights in the layer
	 * @return returns the number of weights in the layer
	 */
	public int getLayerWeight(){
		return numWeight;
		
	}
	
	/**
	 * Method is used to calculate outputs of the current layer for the given inputs and weights
	 * @param input array of inputs
	 * @param weights array of weights for the current layer
	 * @return double array of calculated outputs per neuron in the layer
	 */
	public double[] calculateOutput(double input[], double weights[]){
		int index=0;
		double weightsOfNeuron[];
		double output[] = new double[neurons.size()];
		for(Neuron neuron : neurons){
			weightsOfNeuron = Arrays.copyOfRange(weights, index*this.numberOfInputs, index*this.numberOfInputs+this.numberOfInputs);
			output[index++] = neuron.calculateOutput(input, weightsOfNeuron);
		}
		return output;
	}
}
