package hr.fer.zemris.optjava.dz8;

import java.util.Random;
/**
 * 
 * Class implements methods used for vector operations
 */
public class VectorOperations {

	/**
	 * Method is used to create a base vector
	 * @param currentVector vector from current iteration
	 * @param firstHelperVector first helper vector used in creation
	 * @param secondHelperVector second helper vector used in creation
	 * @param bestVector best vector in the population 
	 * @return the newly created vector
	 */
	public static NeuralNetSolution mutateVector(NeuralNetSolution currentVector, NeuralNetSolution firstHelperVector, NeuralNetSolution secondHelperVector, NeuralNetSolution bestVector){
		int vectorLength = currentVector.coordinateLength;
		NeuralNetSolution mutantVector = new NeuralNetSolution(vectorLength, currentVector.evaluator);
		for(int i=0;i<vectorLength;i++){
			mutantVector.position[i] = currentVector.position[i] + Constants.mutationFactor * (bestVector.position[i] - currentVector.position[i]) + Constants.mutationFactor * (firstHelperVector.position[i] - secondHelperVector.position[i]);
		}
		return mutantVector;
		
	}
	
	/**
	 * Method is used to crossover the baser vector and the goal vector
	 * @param mutantVector base vector used in crossover
	 * @param goalVector goal vector used in crossover
	 * @param rand random number generator 
	 * @return the newly created vector
	 */
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
