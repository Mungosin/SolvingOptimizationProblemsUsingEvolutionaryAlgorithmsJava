package hr.fer.zemris.optjava.dz6;

import java.util.Random;
/**
 * Class implements some utility functions needed for the algorithm
 *
 */
public class HelperFunctions {
	
	/**
	 * Method is used to shuffle elements in the array
	 * @param array array being shuffled
	 * @param rand random number generator
	 */
	public static void shuffleInts(int[] array, Random rand){
		for(int i=array.length;i>1;i--){
			int temp = rand.nextInt(i);
			if(temp != i-1){
				int p = array[i-1];
				array[i-1] = array[temp];
				array[temp] = p;
			}
		}
	}
}
