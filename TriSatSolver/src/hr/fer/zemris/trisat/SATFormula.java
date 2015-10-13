package hr.fer.zemris.trisat;

/**
 * SATFormula class that implements the methods and attributes required to evaluate the given formula
 *
 */
public class SATFormula {
	
	private int numberOfVariables;
	private Clause[] clauses;
	
	/**
	 * Constructor for SATFormula class that sets the number of variables and the array of all Clauses
	 * @param numberOfVariables in the formula
	 * @param clauses in the formula
	 */
	public SATFormula(int numberOfVariables, Clause[] clauses){
		this.numberOfVariables = numberOfVariables;
		this.clauses = clauses;		
	}
	
	/**
	 * Gets the number of variables in the formula
	 * @return the number of variables in the formula
	 */
	public int getNumberOfVariables(){
		return numberOfVariables;
		
	}
	
	/**
	 * Gets the number of clauses in the formula
	 * @return the number of clauses in the formula
	 */
	public int getNumberOfClauses(){
		return clauses.length;
	
	}
	
	/**
	 * Gets the Clause at the indexth position
	 * @param index position of the wanted clause
	 * @return the wanted clause at position index
	 */
	public Clause getClause(int index){
		if(clauses.length> index && index>=0){
			return clauses[index];
		}else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	/**
	 * Method is used to check if the formula is satisfied by the given BitVector object
	 * @param assignment assessed BitVector object
	 * @return true if the formula is satisfied
	 */
	public boolean isSatisfied(BitVector assignment){
		for(Clause tempClause : clauses){
			if(!tempClause.isSatisfied(assignment)){
				return false;
			}else {
				continue;
			}
		}
		return true;
		
	}
	
	@Override
	public String toString(){
		StringBuilder returnString = new StringBuilder();
		for(Clause tempClause : clauses){
			returnString.append(tempClause);
		}
		return returnString.toString();
	}
}
