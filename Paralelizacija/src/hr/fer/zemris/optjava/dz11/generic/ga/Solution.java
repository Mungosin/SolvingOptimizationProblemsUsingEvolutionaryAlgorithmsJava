package hr.fer.zemris.optjava.dz11.generic.ga;

import java.util.Random;

import hr.fer.zemris.optjava.dz11.rng.IRNG;
import hr.fer.zemris.optjava.dz11.rng.RNG;

public class Solution extends GASolution<int[]>{

	
	public Solution(int length){
		this.data = new int[length];
	}
	
	public Solution(int[] solution, double fitness) {
		this.data = solution;
		this.fitness = fitness;
	}

	@Override
	public GASolution<int[]> duplicate() {
		int[] copy = new int[this.data.length];
		for(int i=0; i<this.data.length;i++){
			copy[i] = this.data[i];
		}
		double fitness = this.fitness;
		return new Solution(copy, fitness);
	}

	public int getNumberOfRectangles() {
		return (this.data.length-1 )/5;
	}

	public void randomize(int pictureWidth, int pictureHeight) {
		Random rand = new Random();
		this.data[0] = rand.nextInt(256);
		for(int i = 0; i<this.getNumberOfRectangles(); i++){
        	this.data[i*5+1] = rand.nextInt(pictureWidth); //
        	this.data[i*5+2] = rand.nextInt(pictureHeight); //
        	this.data[i*5+3] = rand.nextInt(pictureWidth - this.data[i*5+1]); //
        	this.data[i*5+4] = rand.nextInt(pictureWidth - this.data[i*5+2]); //
        	this.data[i*5+5] = rand.nextInt(256); //
 
        }
	}

}
