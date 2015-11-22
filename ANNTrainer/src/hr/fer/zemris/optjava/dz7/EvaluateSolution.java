package hr.fer.zemris.optjava.dz7;
/**
 * Class is used to evaluate the quality of trained neural network
 *
 */
public class EvaluateSolution implements Evaluator{
	FFANN neuralNet;
	
	/**
	 * Constructor for the Evaluator class
	 * @param neuralNet neural network to be evaluated
	 */
	public EvaluateSolution(FFANN neuralNet){
		this.neuralNet = neuralNet;
	}

	/**
	 * Method evaluates the quadratic error of a trained neural network
	 */
	@Override
	public double evaluate(double[] coordinates){
		Dataset data = neuralNet.getData();
		double error =0;
		double outputs[] = new double[data.data.getFirst().output.size()];
		for(Data dataTemp : data.data){
			double input[] = new double[dataTemp.input.size()];
			for(int i=0;i<input.length;i++){
				input[i] = dataTemp.input.get(i);
			}
			neuralNet.calcOutputs(input, coordinates, outputs);
			
			for(int i=0;i<dataTemp.output.size();i++){
				error += Math.pow(outputs[i] - dataTemp.output.get(i).doubleValue(),2);
			}
			
		}
		return error/data.getSizeOfSample();
	}

	/**
	 * Method returns a 2 dimensional array where the first element is number of wrongly predicted
	 * and second element is the number of correctly predicted 
	 */
	@Override
	public double[] getStatistics(double[] coordinates) {
		Dataset data = neuralNet.getData();
		double error =0;
		double[] statistics = new double[2];
		double outputs[] = new double[data.data.getFirst().output.size()];
		for(Data dataTemp : data.data){
			double input[] = new double[dataTemp.input.size()];
			for(int i=0;i<input.length;i++){
				input[i] = dataTemp.input.get(i);
			}
			neuralNet.calcOutputs(input, coordinates, outputs);
			
			boolean wrong=false;
			for(int i=0;i<dataTemp.output.size();i++){
				double comparison;
				if(outputs[i]<0.5){
					comparison = 0.0;
				}else{
					comparison = 1;
				}

				if(dataTemp.output.get(i).doubleValue() != comparison){
					statistics[0] ++;
					wrong=true;
					break;
				}
			}
			if(!wrong){
				statistics[1]++;
			}
			
		}
		return statistics;
	}

}
