package hr.fer.zemris.optjava.dz11.algorithms;

import hr.fer.zemris.optjava.dz11.generic.ga.Evaluator;
import hr.fer.zemris.optjava.dz11.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.generic.ga.IEvaluatorProvider;
import hr.fer.zemris.optjava.dz11.generic.ga.Solution;

import java.util.concurrent.ConcurrentLinkedQueue;

public class HalfParalelJob implements Runnable{
	public ConcurrentLinkedQueue<Solution> ulazniRedHalfParalel;
	public ConcurrentLinkedQueue<Solution> izlazniRedHalfParalel;
	
	
	public HalfParalelJob(ConcurrentLinkedQueue<Solution> ulazniRedHalfParalel,ConcurrentLinkedQueue<Solution> izlazniRedHalfParalel){
		this.ulazniRedHalfParalel = ulazniRedHalfParalel;
		this.izlazniRedHalfParalel = izlazniRedHalfParalel;
	}
	
	@Override
	public void run() {
		Evaluator evaluator = ((IEvaluatorProvider)Thread.currentThread()).getEvaluator();
		Solution trenutniSolution = null;
		boolean prekid = false;
		while(true){
			prekid = false;
			trenutniSolution = ulazniRedHalfParalel.poll();
			if(trenutniSolution != null){
				evaluator.evaluate(trenutniSolution);
				izlazniRedHalfParalel.add(trenutniSolution);
			}else{
				break;
			}
		}
	}

}
