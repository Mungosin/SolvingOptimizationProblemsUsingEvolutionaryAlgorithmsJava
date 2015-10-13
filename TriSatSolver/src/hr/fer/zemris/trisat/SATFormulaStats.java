package hr.fer.zemris.trisat;
/**
 *Class that implements the information about formula and its statistics 
 *
 */
public class SATFormulaStats {
	private double post[];
	private BitVector assignment;
	private SATFormula formula;
	
	/**
	 * Constructor for SATFormulaStats that initializes the SATFormula
	 * @param formula whose statstics will be monitored
	 */
	public SATFormulaStats(SATFormula formula){
		this.post = new double[formula.getNumberOfClauses()];
		this.formula = formula;
	}

	/**
	 * Sets the BitVector for assessment and updates percentages if the boolean parameter is true
	 * @param assignment that will be evaluated
	 * @param updatePercentages if true updates percentages of the statistics
	 */
	public void setAssignment(BitVector assignment, boolean updatePercentages){
		this.assignment = assignment;
		if(updatePercentages){
			int numberOfClauses = formula.getNumberOfClauses();
			for(int i=0;i<numberOfClauses;i++){
				if(formula.getClause(i).isSatisfied(assignment)){
					post[i] += (1-post[i]) * Constants.percentageConstantUp;
				}else{
					post[i] += (0-post[i]) * Constants.percentageConstantDown;
				}
			}
		}
	}
	
	/**
	 * Gets number of satisfied clauses by the current assignment
	 * @return number of satisfied clauses
	 */
	public int getNumberOfSatisfied(){
		int numberOfClauses = formula.getNumberOfClauses();
		int numberOfSatisfied=0;
		for(int i=0;i<numberOfClauses;i++){
			if(formula.getClause(i).isSatisfied(assignment)){
				numberOfSatisfied++;
			}
		}
		return numberOfSatisfied;

	}

	/**
	 * Method checks if the formula is satisfied by the current assignment
	 * @return true if the formula is satisfied
	 */
	public boolean isSatisfied(){
		return formula.isSatisfied(assignment);
	}

	/**
	 * Gets percentage bonus of all clauses in the statistic
	 * @return percentage bonus of all clauses
	 */
	public double getPercentageBonus(){
		double bonus = 0;
		int numberOfClauses = formula.getNumberOfClauses();
		for(int i = 0; i< numberOfClauses;i++){
			if(formula.getClause(i).isSatisfied(assignment)){
				bonus += Constants.percentageUnitAmount * (1-post[i]);
			}else {
				bonus += -Constants.percentageUnitAmount * (1-post[i]);
			}
		}
		return bonus;
	}

	/**
	 * Returns the percentage of how much times the clause has been satisfied
	 * @param index of the element wanted
	 * @return the percentage of times the indexth clause has been satisfied
	 */
	public double getPercentage(int index){
		if(post.length>index && index>=0){
			return post[index];
		}else {
			throw new IndexOutOfBoundsException();
		}
	}
}
