package hr.fer.zemris.optjava.dz12.provimpl;

import hr.fer.zemris.optjava.dz12.rng.IRNG;
import hr.fer.zemris.optjava.dz12.rng.IRNGProvider;
import hr.fer.zemris.optjava.dz12.rng.RNG;
import hr.fer.zemris.optjava.dz12.rngimpl.RNGRandomImpl;

public class ThreadBoundRNGProvider implements IRNGProvider{
	
	@Override
	public IRNG getRNG() {
			return ((IRNGProvider)Thread.currentThread()).getRNG();
	}

}
