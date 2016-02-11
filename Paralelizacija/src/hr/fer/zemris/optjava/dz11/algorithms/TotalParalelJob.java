package hr.fer.zemris.optjava.dz11.algorithms;

import hr.fer.zemris.optjava.dz11.generic.ga.Evaluator;
import hr.fer.zemris.optjava.dz11.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.generic.ga.IEvaluatorProvider;
import hr.fer.zemris.optjava.dz11.generic.ga.Solution;
import hr.fer.zemris.optjava.dz11.genetic.operators.Constants;
import hr.fer.zemris.optjava.dz11.genetic.operators.ISelection;
import hr.fer.zemris.optjava.dz11.genetic.operators.Operators;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TotalParalelJob implements Runnable{
	public ConcurrentLinkedQueue<Integer> ulazniRedTotalParalel;
	public ConcurrentLinkedQueue<Solution> izlazniRedTotalParalel;
	public LinkedList<Solution> populacija;
	public Operators operatori;
	public ISelection selection;
	
	
	public TotalParalelJob(ConcurrentLinkedQueue<Integer> ulazniRedTotalParalel,ConcurrentLinkedQueue<Solution> izlazniRedTotalParalel){
		this.ulazniRedTotalParalel = ulazniRedTotalParalel;
		this.izlazniRedTotalParalel = izlazniRedTotalParalel;
	}
	
	public void setPop(LinkedList<Solution> pop){
		populacija = pop;
	}
	
	@Override
	public void run() {
		if(operatori == null){

			operatori = new Operators();
		}
		Evaluator evaluator = ((IEvaluatorProvider)Thread.currentThread()).getEvaluator();
		Integer brojZaNapraviti = null;
		while(true){
			brojZaNapraviti = ulazniRedTotalParalel.poll();
			if(brojZaNapraviti != null){
				for(int i=0;i<brojZaNapraviti;i++){
					Solution p1 = operatori.findParent(populacija, Constants.tournament);

					Solution p2 = operatori.findParent(populacija, Constants.tournament);
					
					Solution child = operatori.createChild(p1, p2);

					evaluator.evaluate(child);
					izlazniRedTotalParalel.add(child);
				}
			}else{
				break;
			}
		}
	}

}
