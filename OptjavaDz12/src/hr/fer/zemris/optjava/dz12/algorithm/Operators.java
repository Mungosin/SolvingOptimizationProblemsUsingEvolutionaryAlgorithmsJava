package hr.fer.zemris.optjava.dz12.algorithm;

import hr.fer.zemris.optjava.dz12.rng.IRNG;
import hr.fer.zemris.optjava.dz12.rng.RNG;

import java.util.LinkedList;
import java.util.Random;





public class Operators {
	IRNG rand = RNG.getRNG();
	
	
	public Solution createChild(Solution p1, Solution p2){
		Solution child = crossover(p1, p2);
		mutate(child);
		child.evaluateSolution();
		return child;
	}
	
	public Solution crossover(Solution p1, Solution p2){
		int point = rand.nextInt(0,p1.kromosom.size());
		Solution child = p1.duplicate();
		int length = p2.kromosom.size();
		child.fitness = 0;
 
        for(int i = point; i<length; i++){
        	child.kromosom.set(i, p2.kromosom.get(i).duplicate());
        }
		        
        return child;
	}
		 
		 
		 
		public void mutate(Solution sol){
		        double mutationChance = Constants.mutationChance;

				int length = sol.kromosom.size();
				
		        for (int i = 0; i < length; i++) {
		            if(rand.nextDouble() < Constants.mutationChance){
		            	CLB temp = sol.kromosom.get(i);
		            	//test mutiranja i ulaznih varijabli
		            	
		            	int brojIzlaznih = temp.izlazneVrijednosti.length;
		            	int mutirajIndex = rand.nextInt(0, brojIzlaznih);
		            	temp.izlazneVrijednosti[mutirajIndex] = !temp.izlazneVrijednosti[mutirajIndex];
		            }
		        }
		}
		
		public Solution findParent(
				LinkedList<Solution> currentSolutions, int numberOfContestants) {
			int length =  currentSolutions.size();
			Solution currentContestant;
			Solution bestParent;
			bestParent = currentSolutions.get(rand.nextInt(0,length));
			for(int i=1;i<numberOfContestants;i++){
				currentContestant = currentSolutions.get(rand.nextInt(0,length));
				if(bestParent.fitness < currentContestant.fitness){
					bestParent = currentContestant;
				}
			}
			return bestParent;
		}
}
