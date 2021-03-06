package hr.fer.zemris.optjava.dz11.provimpl;

import hr.fer.zemris.optjava.dz11.rng.IRNG;
import hr.fer.zemris.optjava.dz11.rng.IRNGProvider;
import hr.fer.zemris.optjava.dz11.rng.RNG;
import hr.fer.zemris.optjava.dz11.rngimpl.RNGRandomImpl;

public class ThreadLocalRNGProvider implements IRNGProvider{

	private ThreadLocal<IRNG> threadLocal = new ThreadLocal<IRNG>(){
	        @Override
	        protected IRNG initialValue()
	        {
	        	this.set(new RNGRandomImpl());
	            return this.get();
	        }};
	
	@Override
	public IRNG getRNG() {
		return threadLocal.get();
	}

}
