package hr.fer.zemris.optjava.dz11.rngimpl;

import java.util.Random;

import hr.fer.zemris.optjava.dz11.rng.IRNG;

public class RNGRandomImpl implements IRNG{
	
	private Random rnd = new Random();
	
	
	@Override
	public double nextDouble() {
		return rnd.nextDouble();
	}

	@Override
	public double nextDouble(double min, double max) {
		// TODO Auto-generated method stub
		return rnd.nextDouble() * (max - min) + min;
	}

	@Override
	public float nextFloat() {
		// TODO Auto-generated method stub
		return rnd.nextFloat();
	}

	@Override
	public float nextFloat(float min, float max) {
		// TODO Auto-generated method stub
		return rnd.nextFloat() * (max - min) + min;
	}

	@Override
	public int nextInt() {
		// TODO Auto-generated method stub
		return rnd.nextInt();
	}

	@Override
	public int nextInt(int min, int max) {
		// TODO Auto-generated method stub
		return rnd.nextInt(max - min) + min;
	}

	@Override
	public boolean nextBoolean() {
		// TODO Auto-generated method stub
		return rnd.nextBoolean();
	}

	@Override
	public double nextGaussian() {
		// TODO Auto-generated method stub
		return rnd.nextGaussian();
	}

}
