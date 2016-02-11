package hr.fer.zemris.optjava.dz11.genetic.operators;

import java.util.LinkedList;
import java.util.Random;




import hr.fer.zemris.optjava.dz11.generic.ga.IEvaluatorProvider;
import hr.fer.zemris.optjava.dz11.generic.ga.Evaluator;
import hr.fer.zemris.optjava.dz11.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.generic.ga.Solution;
import hr.fer.zemris.optjava.dz11.rng.IRNG;
import hr.fer.zemris.optjava.dz11.rng.IRNGProvider;
import hr.fer.zemris.optjava.dz11.rng.RNG;
import hr.fer.zemris.optjava.dz11.rngimpl.RNGRandomImpl;


public class Operators {
	IRNG rand = RNG.getRNG();
	
	
	public Solution createChild(Solution p1, Solution p2){
		Solution child = crossover(p1, p2);
		mutate(child);
		Evaluator evaluator = ((IEvaluatorProvider)Thread.currentThread()).getEvaluator();
		evaluator.evaluate(child);
		return child;
	}
	
	public Solution crossover(Solution p1, Solution p2){
		int point = rand.nextInt(0,p1.getNumberOfRectangles());
		GASolution<int[]> child = p1.duplicate();
		child.fitness = 0;
		 
		        for(int i = point; i<p2.getNumberOfRectangles(); i++){
		        	child.data[i*5+1] = p2.data[i*5+1];
		        	child.data[i*5+2] = p2.data[i*5+2];
		        	child.data[i*5+3] = p2.data[i*5+3];
		        	child.data[i*5+4] = p2.data[i*5+4];
		        	child.data[i*5+5] = p2.data[i*5+5];
		 
		        }
		        
		        return new Solution(child.data, child.fitness);
	}
		 
		 
		 
		public void mutate(Solution sol){
		        int offset = Constants.offset;
		        for (int i = 0; i < sol.getNumberOfRectangles(); i++) {
		            int randInt = rand.nextInt(0,10);
		            if(randInt==0){
		                sol.data[i*5+3]+=offset;
		                if(sol.data[i*5+3]>70){
		                    sol.data[i*5+3]=70;
		                }
		            }else if(randInt==1){
		                sol.data[i*5+3]-=offset;
		                if(sol.data[i*5+3]<0){
		                    sol.data[i*5+3]=0;
		                }
		            }else if(randInt==2){
		                sol.data[i*5+4]+=offset;
		                if(sol.data[i*5+4]>45){
		                    sol.data[i*5+4]=45;
		                }
		 
		            }else if(randInt==3){
		                sol.data[i*5+4]-=offset;
		                if(sol.data[i*5+4]<0){
		                    sol.data[i*5+4]=0;
		                }
		 
		            }else if(randInt==4){
		                sol.data[i*5+1]-=offset;
		                if(sol.data[i*5+1]<0){
		                    sol.data[i*5+1]=0;
		                }
		 
		            }
		            else if(randInt==5){
		                sol.data[i*5+1]+=offset;
		                if(sol.data[i*5+1]>200){
		                    sol.data[i*5+1]=200;
		                }
		 
		            }
		            else if(randInt==6){
		                sol.data[i*5+2]-=offset;
		                if(sol.data[i*5+2]<0){
		                    sol.data[i*5+2]=0;
		                }
		 
		            }
		            else if(randInt==7){
		                sol.data[i*5+2]+=offset;
		                if(sol.data[i*5+2]>133){
		                    sol.data[i*5+2]=133;
		                }
		 
		            } else if(randInt==8){
		                sol.data[i*5+5]+=offset;
		                if(sol.data[i*5+5]>256){
		                    sol.data[i*5+5]=256;
		                }
		 
		            }else if(randInt==9){
		                sol.data[i*5+5]-=offset;
		                if(sol.data[i*5+5]<0){
		                    sol.data[i*5+5]=0;
		                }
		 
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
