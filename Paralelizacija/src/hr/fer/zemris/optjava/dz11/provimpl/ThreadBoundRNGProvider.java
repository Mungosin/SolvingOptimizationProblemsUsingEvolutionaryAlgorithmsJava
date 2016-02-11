package hr.fer.zemris.optjava.dz11.provimpl;

import hr.fer.zemris.optjava.dz11.rng.IRNG;
import hr.fer.zemris.optjava.dz11.rng.IRNGProvider;
import hr.fer.zemris.optjava.dz11.rng.RNG;
import hr.fer.zemris.optjava.dz11.rngimpl.RNGRandomImpl;

public class ThreadBoundRNGProvider implements IRNGProvider{
	
	@Override
	public IRNG getRNG() {
			return ((IRNGProvider)Thread.currentThread()).getRNG();
	}

}
