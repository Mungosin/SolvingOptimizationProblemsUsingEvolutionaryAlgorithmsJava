package hr.fer.zemris.optjava.dz8;

import java.util.Random;

public class VectorOperations {

	public static NeuralNetSolution mutateVector(NeuralNetSolution currentVector, NeuralNetSolution firstHelperVector, NeuralNetSolution secondHelperVector, NeuralNetSolution bestVector){
		int vectorLength = currentVector.coordinateLength;
		NeuralNetSolution mutantVector = new NeuralNetSolution(vectorLength, currentVector.evaluator);
		for(int i=0;i<vectorLength;i++){
			mutantVector.position[i] = currentVector.position[i] + Constants.mutationFactor * (bestVector.position[i] - currentVector.position[i]) + Constants.mutationFactor * (firstHelperVector.position[i] - secondHelperVector.position[i]);
		}
		return mutantVector;
		
	}
	
	public static NeuralNetSolution Crossover(NeuralNetSolution mutantVector, NeuralNetSolution goalVector, Random rand){
		NeuralNetSolution testVector = new NeuralNetSolution(mutantVector.coordinateLength, mutantVector.evaluator);
		int vectorLength = mutantVector.coordinateLength;
		
		for(int i =0; i<vectorLength;i++){
			if(rand.nextDouble() > Constants.percentageOfCrossover){
				testVector.position[i] = mutantVector.position[i];
			}else {
				testVector.position[i] = goalVector.position[i];
			}
		}
		return testVector;
	}
}
