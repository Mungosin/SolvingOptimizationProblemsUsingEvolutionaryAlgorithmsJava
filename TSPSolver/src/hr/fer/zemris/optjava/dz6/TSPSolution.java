package hr.fer.zemris.optjava.dz6;

/**
 * Class defines attributes and methods required to represent a solution of traveling salesman problem
 *
 */
public class TSPSolution {
	protected int[] cityIndexes;
	protected double tourLength;
	
	@Override
	public String toString(){
		StringBuilder cities = new StringBuilder();
		int firstCityIndex = 0;
		int cityLength = cityIndexes.length;
		for(int i=0;i<cityLength;i++){
			if(cityIndexes[i] == 0){
				firstCityIndex = i;
				break;
			}
		}
		for(int i=firstCityIndex;i<cityLength;i++){
			cities.append((cityIndexes[i]+1) + "->");
		}
		for(int i=0;i<firstCityIndex;i++){
			if(i==firstCityIndex-1){

				cities.append((cityIndexes[i] +1));
				continue;
			}
			cities.append((cityIndexes[i] +1)+ "->");
		}
		return "Distance traveled: " + tourLength + "\n" + cities.toString();
	}
	
	
}
