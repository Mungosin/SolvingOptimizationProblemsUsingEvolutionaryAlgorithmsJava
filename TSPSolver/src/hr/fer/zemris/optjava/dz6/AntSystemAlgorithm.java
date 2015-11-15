package hr.fer.zemris.optjava.dz6;

import java.util.LinkedList;
import java.util.Random;
/**
 * Class implements methods used by the ant system algorithm to solve traveling salesman problem
 */
public class AntSystemAlgorithm implements IOptAlgorithm {
	protected Double[][] distanceBetweenTowns;
	protected Double[][] feromoneLevelsBetweenTowns;
	protected Double[][] heuristics;
	protected int[] reachable;
	protected int[] cityIndexes;
	protected double[] probabilityOfSelection;
	protected int maxIterations;
	protected int numberOfAnts;
	protected int numberOfTowns;
	protected double TMax;
	protected double TMin;
	protected double a;
	protected double alpha;
	protected double beta;
	protected Random rand;
	protected double sigma;
	protected LinkedList<City> cities;
	protected LinkedList<LinkedList<Integer>> candidateListForEachCity;
	protected boolean foundBest;
	protected int sizeOfCandidateList;
	protected TSPSolution bestSolution;
	protected TSPSolution[] ants;

	/**
	 * Constructor for ant system algorithm
	 * @param distanceBetweenTowns matrix of distances between nodes
	 * @param maxIterations max number of iterations
	 * @param numberOfAnts number of ants
	 * @param numberOfTowns number of nodes
	 * @param a parameter used in calculation of TMin = TMax/a
	 * @param beta parameter used in calculation of probabilities, power of heuristics in the formula
	 * @param alfa parameter used in calculation of probabilites, power of feromones in the formula
	 * @param cities list of nodes
	 * @param sizeOfCandidateList size of candidate list for every city
	 */
	public AntSystemAlgorithm(Double[][] distanceBetweenTowns, int maxIterations, int numberOfAnts, int numberOfTowns,
			double a, double beta, double alfa, LinkedList<City> cities, int sizeOfCandidateList) {
		this.distanceBetweenTowns = distanceBetweenTowns;
		this.maxIterations = maxIterations;
		this.numberOfAnts = numberOfAnts;
		this.a = a;
		this.sigma = Constants.sigma;
		this.beta = beta;
		this.alpha = alfa;
		this.numberOfTowns = numberOfTowns;
		this.rand = new Random(System.currentTimeMillis());
		this.feromoneLevelsBetweenTowns = new Double[numberOfTowns][numberOfTowns];
		this.heuristics = new Double[numberOfTowns][numberOfTowns];
		this.cities = cities;
		this.foundBest = false;
		this.bestSolution = new TSPSolution();
		this.bestSolution.cityIndexes = new int[this.numberOfTowns];
		this.cityIndexes = new int[this.numberOfTowns];
		this.reachable = new int[this.numberOfTowns];
		this.probabilityOfSelection = new double[this.numberOfTowns];
		this.sizeOfCandidateList = sizeOfCandidateList;
		this.candidateListForEachCity = new LinkedList<>();
		
		
		int index=0;
		for(City city : this.cities){
			cityIndexes[index++] = city.index -1;//moguci problem
		}
		
		System.out.println("Creating heuristics");
		for (int i = 0; i < numberOfTowns; i++) {
			heuristics[i][i] = 0.0;
			for (int j = i + 1; j < numberOfTowns; j++) {
				heuristics[i][j] = Math.pow(1 / distanceBetweenTowns[i][j], beta);
				heuristics[j][i] = Math.pow(1 / distanceBetweenTowns[j][i], beta);
			}
		}
		calculateTMax();
		calculateTMin();

		System.out.println("Setting feromones");
		for (int i = 0; i < numberOfTowns; i++) {
			feromoneLevelsBetweenTowns[i][i] = 0.0;
			for (int j = i + 1; j < numberOfTowns; j++) {
				feromoneLevelsBetweenTowns[i][j] = this.TMax;
				feromoneLevelsBetweenTowns[j][i] = this.TMax;
			}
		}
		
		System.out.println("Creating ants");
		ants = new TSPSolution[this.numberOfAnts];
		for(int i=0;i<ants.length;i++){
			ants[i] = new TSPSolution();
			ants[i].cityIndexes = new int[this.numberOfTowns];
		}
		
		System.out.println("Creating candidate lists");
		createCandidateList();
	}

	/**
	 * Method is used to create candidate list for every city
	 */
	private void createCandidateList() {
		for(int i=0;i<this.numberOfTowns;i++){
			LinkedList<Integer> listForCity = createCandidateListForCity(i);
			this.candidateListForEachCity.add(listForCity);
 		}
	}
	
	/**
	 * Method creates a candidate list for the given city
	 * @param currentCity city for which the list is being created
	 * @return created list of candidates
	 */
	private LinkedList<Integer> createCandidateListForCity(int currentCity){
		LinkedList<Integer> candidateList = new LinkedList<>();
		LinkedList<Double> distanceToTown = new LinkedList<>();
		for(Integer integ : this.cityIndexes){
			if(integ==currentCity){
				continue;
			}
			candidateList.add(integ.intValue());
			distanceToTown.add(this.distanceBetweenTowns[currentCity][integ]);
		}

		while(true){
			boolean swapped = false;
			for(int i=1;i<candidateList.size();i++){
				if(distanceToTown.get(i)<distanceToTown.get(i-1)){
					swapped=true;
					double temp = distanceToTown.get(i).doubleValue();
					distanceToTown.set(i, distanceToTown.get(i-1).doubleValue());
					distanceToTown.set(i-1, temp);
					
					int p = candidateList.get(i).intValue();
					candidateList.set(i, candidateList.get(i-1).intValue());
					candidateList.set(i-1, p);
				}
			}
			
			if(swapped==false){
				break;
			}
		}
		
		while(candidateList.size()>this.sizeOfCandidateList){
			candidateList.removeLast();
		}
		return candidateList;
	}

	/**
	 * Method returns a refreshed candidate list with elements that are not yet visited
	 * @param currentCity current city where the ant is located
	 * @param ant whose candidate list is being checked
	 * @return refreshed candidate list
	 */
	public LinkedList<Integer> allElementsInListVisited(int currentCity, TSPSolution ant){
		LinkedList<Integer> elementsNotVisited = new LinkedList<Integer>();
		LinkedList<Integer> cityList = this.candidateListForEachCity.get(currentCity);
		int antCityLength = ant.cityIndexes.length;
		boolean visited = false;
		for(Integer integ : cityList){
			visited=false;
			for(int i=0;i<antCityLength;i++){
				if(integ.intValue() == ant.cityIndexes[i]){
					visited=true;
					break;
				}
			}
			if(visited==false){
				elementsNotVisited.add(integ.intValue());
			}
		}
		return elementsNotVisited;
		
	}
	
	/**
	 * Method calculates TMax using a greedy algorithm
	 */
	private void calculateTMax() {
		LinkedList<Integer> listOfVisited = new LinkedList<Integer>();
		int current = 0;
		double path = 0;
		listOfVisited.add(current);
		while (listOfVisited.size() != numberOfTowns) {
			double currentDistance = -1;
			int next = 0;
			for (int i = 0; i < numberOfTowns; i++) {
				if (currentDistance == -1 && !listOfVisited.contains(i) && i != current) {
					next = i;
					currentDistance = this.distanceBetweenTowns[current][next];
				} else if (currentDistance != -1 && currentDistance > distanceBetweenTowns[current][i]
						&& !listOfVisited.contains(i) && i != current) {
					next = i;
					currentDistance = this.distanceBetweenTowns[current][next];
				}
			}
			listOfVisited.add(next);
			path += this.distanceBetweenTowns[current][next];
			current = next;
		}
		path += this.distanceBetweenTowns[current][0];
		this.TMax = this.numberOfAnts / (path * sigma);
	}

	/**
	 * Method calculates TMin as TMax/a
	 */
	private void calculateTMin() {
		TMin = TMax / a;
	}

	/**
	 * Method is used to evaporate feromone trails after each iteration
	 */
	private void evaporateTrails() {
		Double evaporationConstant = 1 - sigma;
		for (int i = 0; i < this.numberOfTowns; i++) {
			for (int j = i + 1; j < this.numberOfTowns; j++) {
				if(feromoneLevelsBetweenTowns[i][j] * evaporationConstant > this.TMin){
					feromoneLevelsBetweenTowns[i][j] = feromoneLevelsBetweenTowns[i][j] * evaporationConstant;
					feromoneLevelsBetweenTowns[j][i] = feromoneLevelsBetweenTowns[i][j];
				}else {
					feromoneLevelsBetweenTowns[i][j] = TMin;
					feromoneLevelsBetweenTowns[j][i] = TMin;
				}
			}
		}
	}

	@Override
	public void run() {
		int iter =0;
		int stagnationDetector =0;
		double lastBestDistance = 0;
		while( iter < this.maxIterations ) {
			System.out.println((iter+1) + ". Iteration of algorithm" );
			iter++;
			for ( int antIndex = 0; antIndex < ants . length ; antIndex++) {
				TSPSolution ant = ants[antIndex];
				calculateRoute(ant);
			}
			updateTrails(); 
			evaporateTrails(); 
			checkBestSolution();
			
			
			if(lastBestDistance == 0 || bestSolution.tourLength<lastBestDistance){
				lastBestDistance = bestSolution.tourLength;
				stagnationDetector = 0;
			}else{
				stagnationDetector++;
				if(stagnationDetector >= Constants.resetTMax){ 
					reinitializeFeromones();
				}
				
			}
		}
		
		System.out.println(this.bestSolution);
	}

	
	/**
	 * Method is used to reinitialize all feromones to TMax if stagnation occurs
	 */
	private void reinitializeFeromones() {
		for (int i = 0; i < numberOfTowns; i++) {
			feromoneLevelsBetweenTowns[i][i] = 0.0;
			for (int j = i + 1; j < numberOfTowns; j++) {
				feromoneLevelsBetweenTowns[i][j] = this.TMax;
				feromoneLevelsBetweenTowns[j][i] = this.TMax;
			}
		}
	}

	/**
	 * Method is used to check if a better solution is found
	 */
	private void checkBestSolution() {
		if(!this.foundBest){
			this.foundBest = true;
			TSPSolution ant = this.ants[0];
			System.arraycopy(ant.cityIndexes, 0, bestSolution.cityIndexes, 0, ant.cityIndexes.length);
			this.bestSolution.tourLength = ant.tourLength;
		}
		double currentBest = this.bestSolution.tourLength;
		int bestIndex=-1;
		for(int i=0;i<ants.length;i++){
			TSPSolution ant = ants[i];
			if(ant.tourLength<currentBest){
				currentBest = ant.tourLength;
				bestIndex=i;
			}
		}
		if(bestIndex != -1){
			TSPSolution ant = ants[bestIndex];
			System.arraycopy(ant.cityIndexes, 0, bestSolution.cityIndexes, 0, ant.cityIndexes.length);
			this.bestSolution.tourLength = ant.tourLength;
		}
	}

	
	
	/**
	 * Method is used to calculate next route for the given ant
	 * @param ant ant whose route is being calculated
	 */
	private void calculateRoute(TSPSolution ant) {
		System.arraycopy(this.cityIndexes, 0, this.reachable, 0, this.cityIndexes.length);
		HelperFunctions.shuffleInts(this.reachable, this.rand);
		ant.cityIndexes[0] = reachable[0];
		
		for(int nextStep=1; nextStep<cities.size()-1;nextStep++){ // step with index city size -1 is the last visited node so there are no steps after that
			
			int previousVisited = ant.cityIndexes[nextStep-1];
			double probabilitySum = 0.0;
			

			LinkedList<Integer> reachableCandidateList = allElementsInListVisited(previousVisited, ant);
			
			if(reachableCandidateList.size()!=0){
				double probabilities[] = new double[reachableCandidateList.size()];
				int probabilityLength = probabilities.length;
				double probSum = 0.0;
				
				for(int i=0;i<probabilityLength;i++){
					int indexOfTownOnIthPlace = reachableCandidateList.get(i);
					probabilities[i] = Math.pow(this.feromoneLevelsBetweenTowns[previousVisited][indexOfTownOnIthPlace], this.alpha) * this.heuristics[previousVisited][indexOfTownOnIthPlace];
					probSum+=probabilities[i];
				}

				for(int i=0;i<probabilityLength;i++){
					probabilities[i] = probabilities[i]/probSum;
				}
				
				double num = rand.nextDouble();
				probSum=0.0;
				int candidate = -1;
				for(int cand = 0; cand<probabilityLength;cand++){
					probSum+=probabilities[cand];
					if(num<=probSum){
						candidate = reachableCandidateList.get(cand);
						break;
					}
				}
				if(candidate == -1){
					candidate = reachableCandidateList.get(probabilityLength-1);
				}

				int candidateIndex = -1;
				
				for(int i=nextStep;i<cities.size();i++){
					if(reachable[i] == candidate){
						candidateIndex = i;
						break;
					}
				}
				if(candidateIndex==-1){
					candidateIndex = reachableCandidateList.getFirst();
				}
				int temp = reachable[nextStep];
				reachable[nextStep] = reachable[candidateIndex];
				reachable[candidateIndex] = temp;
				ant.cityIndexes[nextStep] = reachable[nextStep];

			}else{
				
				for(int cand = nextStep; cand < cities.size();cand++){
					int index = reachable[cand];
					this.probabilityOfSelection[index] = Math.pow(this.feromoneLevelsBetweenTowns[previousVisited][index], this.alpha) * this.heuristics[previousVisited][index];
					probabilitySum+=this.probabilityOfSelection[index];
				}
				
				for(int cand = nextStep; cand<cities.size();cand++){
					int index = reachable[cand];
					this.probabilityOfSelection[index] = this.probabilityOfSelection[index] / probabilitySum;
				}
				
				double number = rand.nextDouble();
				probabilitySum=0.0;
				int selectedCandidate = -1;
				for(int cand = nextStep; cand<cities.size();cand++){
					int cityIndex = reachable[cand];
					probabilitySum+=probabilityOfSelection[cityIndex];
					if(number<=probabilitySum){
						selectedCandidate = cand;
						break;
					}
				}
				if(selectedCandidate == -1){
					selectedCandidate = cities.size()-1;
				}
				int tmp = reachable[nextStep];
				reachable[nextStep] = reachable[selectedCandidate];
				reachable[selectedCandidate] = tmp;
				ant.cityIndexes[nextStep] = reachable[nextStep];
				
			}
		
		} 
		ant.cityIndexes[ant.cityIndexes.length-1] = reachable[ant.cityIndexes.length -1];
		evaluateSolution(ant);
		
		
	}

	/**
	 * Method is used to evaluate the solution of an ant
	 * @param ant ant whose solution is being evaluated
	 */
	private void evaluateSolution(TSPSolution ant) {
		ant.tourLength = 0;
		for(int i=1;i<ant.cityIndexes.length;i++){
			int lastVisited = ant.cityIndexes[i-1];
			int current = ant.cityIndexes[i];
			ant.tourLength += this.distanceBetweenTowns[lastVisited][current];
		}
		ant.tourLength+=this.distanceBetweenTowns[ant.cityIndexes[cityIndexes.length-1]][ant.cityIndexes[0]];
	}

	/**
	 * Method is used to update the trails of ants, updates the solution from the best solution found and a number of best ants configured in the constants class
	 */
	private void updateTrails() {
			int updates; 
			if(ants.length>Constants.bestAntsThatUpdateFeromones){
				updates = Constants.bestAntsThatUpdateFeromones;
			}else {
				updates = ants.length;
			}
			partialSort(ants, updates);
			
			for ( int antIndex = 0; antIndex < updates ; antIndex++) {
				TSPSolution ant = ants [antIndex] ; 
				double delta = 1.0 / ant.tourLength ;
				for(int i = 0; i < ant.cityIndexes.length-1; i++) { 
					int a = ant.cityIndexes[i];
					int b = ant.cityIndexes [ i +1];
					if(feromoneLevelsBetweenTowns[a][b] + delta < this.TMax){
						feromoneLevelsBetweenTowns[a][b] += delta;
						feromoneLevelsBetweenTowns[b][a] = feromoneLevelsBetweenTowns[a][b];
					}else {
						feromoneLevelsBetweenTowns[a][b] = TMax;
						feromoneLevelsBetweenTowns[b][a] = TMax;
					}
				}
			}
			
			TSPSolution ant = this.bestSolution;
			double delta = 1.0 / ant.tourLength ;
			for(int i = 0; i < ant.cityIndexes.length-1; i++) { 
				int a = ant.cityIndexes[i];
				int b = ant.cityIndexes [ i +1];
				if(feromoneLevelsBetweenTowns[a][b] + delta < this.TMax){
					feromoneLevelsBetweenTowns[a][b] += delta;
					feromoneLevelsBetweenTowns[b][a] = feromoneLevelsBetweenTowns[a][b];
				}else {
					feromoneLevelsBetweenTowns[a][b] = TMax;
					feromoneLevelsBetweenTowns[b][a] = TMax;
				}
			}
		
	}

	/**
	 * Sort the solutions partially and put number of best in front
	 * @param solutions solutions to be sorted
	 * @param updates number of best solutions wanted
	 */
	private void partialSort(TSPSolution[] solutions, int updates) {
		for(int i=0;i<updates;i++){
			int best = i;
			for(int j=i+1;j<solutions.length;j++){
				if(solutions[best].tourLength>solutions[j].tourLength){
					best = j;
				}
			}
			if(best != i){
				TSPSolution temp = solutions[i];
				solutions[i] = solutions[best];
				solutions[best] = temp;
			}
		}
		
		
	}

}
