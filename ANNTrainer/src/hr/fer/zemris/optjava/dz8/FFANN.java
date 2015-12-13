package hr.fer.zemris.optjava.dz8;

import java.util.Arrays;
import java.util.LinkedList;
/**
 * Class implements methods and attributes used to represent a neural network
 *
 */
public class FFANN {
	public int numberOfElementsPerLayer[];
	public IFunction transferFunction[];
	public Dataset data;
	public LinkedList<NeuralLayer> neuralLayers;
	public int weightCount;
	public double[] outputOfFirstHiddenLayer;
	public boolean elman;
	
	/**
	 * Constructor for neural FFANN class
	 * @param numberOfElementsPerLayer number of neurons in the neural layer
	 * @param transferFunction tranfer functions used per layer
	 * @param data dataset used to train the neural network
	 */
	public FFANN(int numberOfElementsPerLayer[], IFunction transferFunction[], Dataset data, boolean elman){
		this.numberOfElementsPerLayer = numberOfElementsPerLayer;
		this.transferFunction = transferFunction;
		this.data = data;
		this.elman = elman;
		if(numberOfElementsPerLayer.length<3){
			throw new IllegalArgumentException("There must be atleast 3 layers");
		}
		if(transferFunction.length!= numberOfElementsPerLayer.length-1){
			throw new IllegalArgumentException("There must be a function defined for each layer");
		}
		
		this.outputOfFirstHiddenLayer = new double[numberOfElementsPerLayer[1]];
		this.neuralLayers = new LinkedList<>();
		if(elman){

			neuralLayers.add(new NeuralLayer(1, 1, new PassTroughFunction()));
			neuralLayers.add(new NeuralLayer(neuralLayers.get(0).neurons.size()+ numberOfElementsPerLayer[1], numberOfElementsPerLayer[1], transferFunction[0]));
		}else {

			neuralLayers.add(new NeuralLayer(numberOfElementsPerLayer[0], numberOfElementsPerLayer[0], new PassTroughFunction()));
			neuralLayers.add(new NeuralLayer(neuralLayers.get(0).neurons.size(), numberOfElementsPerLayer[1], transferFunction[0]));
		}
		for(int i=2;i<numberOfElementsPerLayer.length;i++){
			neuralLayers.add(new NeuralLayer(neuralLayers.get(i-1).neurons.size(), numberOfElementsPerLayer[i], transferFunction[i-1]));
		}
		
		this.weightCount=0;
		for(int i=1;i<neuralLayers.size();i++){
			this.weightCount += neuralLayers.get(i).numberOfInputs * neuralLayers.get(i).neurons.size();
		}
		
	}
	
	/**
	 * Method returns the number of weights of the neural network
	 * @return number of weights
	 */
	public int getWeightsCount(){
		return weightCount;
	}
	
	/**
	 * Method is used to set the output of the first hidden layer used as a context of the elman network
	 */
	public void setOutputOfFirstHiddenLayer(double inputs[], double weights[]){
		double[] currentInput = Arrays.copyOfRange(inputs, 0, inputs.length+this.outputOfFirstHiddenLayer.length);
		this.outputOfFirstHiddenLayer = this.neuralLayers.get(1).calculateOutput(currentInput, weights);
		
	}
	
	/**
	 * Method calculates the outputs of the neural network based on the given inputs and weights
	 * @param inputs inputs for the network
	 * @param weights weights of the network
	 * @param outputs array where the outputs will be written
	 * @param elman is a boolean that decides which neural net is being used, if it is true it will calculate outputs like elman neural network does
	 */
	public void calcOutputs(double inputs[], double weights[], double[] outputs){
//		int weightsFrom = 0;
//		int weightsTo = 0;
		double[] currentOutput;
		double[] currentInput;
//		double[] currentWeights;
		
		if(elman){
			currentInput = Arrays.copyOfRange(inputs, 0, inputs.length+this.outputOfFirstHiddenLayer.length);
			int start = 0;
			for(int i=inputs.length;i<currentInput.length;i++){
				currentInput[i] = this.outputOfFirstHiddenLayer[start++];
			}
		}else {
			currentInput = inputs;
		}

		
		for(int i=1;i<neuralLayers.size();i++){
//			weightsTo += neuralLayers.get(i).getLayerWeight();
//			currentWeights = Arrays.copyOfRange(weights, weightsFrom, weightsTo);
			currentOutput = neuralLayers.get(i).calculateOutput(currentInput, weights);
//			weightsFrom=weightsTo;
			currentInput = currentOutput;
			if(i==neuralLayers.size()-1){
				if(currentOutput.length != outputs.length){
					throw new IllegalArgumentException("arrays dont match in length");
				}
				for(int j=0;j<outputs.length;j++){
					outputs[j] = currentOutput[j];
				}
			}
		}
		if(elman){
			setOutputOfFirstHiddenLayer(inputs,weights);
		}
	}
	
	/**
	 * Method is used to get the dataset of the neural network
	 * @return training dataset of the network
	 */
	public Dataset getData(){
		return this.data;
	}
}

