package hr.fer.zemris.optjava.dz7;

import java.util.LinkedList;

/**
 * Class is used to save datasets used for training/testing a trained neural network, and implements method used to work with it
 *
 */
public class Dataset {
	public LinkedList<Data> data;
	
	/**
	 * Constructor for Dataset class
	 * @param data collection of data in a dataset
	 */
	public Dataset(LinkedList<Data> data){
		if(!(data.size()>=1)){
			System.out.println("data list is empty");
			System.exit(0);
		}
		this.data = data;
	}
	/**
	 * Method is used to get the number of inputs in the dataset
	 * @return number of inputs in the dataset
	 */
	public int getSizeOfInput(){
		return data.getFirst().input.size();
	}
	
	/**
	 * Method is used to get the number of outputs in the dataset
	 * @return number of outputs in the dataset
	 */
	public int getSizeOfOutput(){
		return data.getFirst().output.size();
	}
	
	/**
	 * Method is used to get the number of data elements in the dataset
	 * @return number of data in the dataset
	 */
	public int getSizeOfSample(){
		return data.size();
	}
	
	/**
	 * Method is used to get the indexth element in the dataset
	 * @param index index of the wanted element
	 * @return returns the indexth element in the dataset
	 */
	public Data getIthSample(int index){
		if(index>= data.size()){
           throw new IllegalArgumentException("Index out of bounds");
		}
		return data.get(index);
	}
}
