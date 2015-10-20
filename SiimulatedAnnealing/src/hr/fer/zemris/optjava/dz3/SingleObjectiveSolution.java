package hr.fer.zemris.optjava.dz3;

public class SingleObjectiveSolution {
	double fitness;
	double value;
	
	public SingleObjectiveSolution(){
		
	}
	
	int compareTo(SingleObjectiveSolution object){
		if(object.fitness<this.fitness){
			return 1;
		}else if(object.fitness>this.fitness){
			return -1;
		}
		return 0;
	}
}
