package hr.fer.zemris.trisat;
/**
 * Clause class implementation
 *
 */
public class Clause {
	private int[] clause;
	
	/**
	 * Constructor that initializes Clause class with the given int array
	 * @param indexes int array with values  greater than 0 or less than 0 incrementing in absolute value
	 */
	public Clause(int[] indexes){
		clause = indexes.clone();
	}
	
	/**
	 * Getter method for the size of the Clause
	 * @return size of the clause array
	 */
	public int getSize(){
		return clause.length;		
	}
	
	/**
	 * Gets the value of the Literal at the given position
	 * @param index of the wanted element in the array
	 * @return iteral value at the wanted place
	 */
	public int getLiteral(int index ){
		if(clause.length> index && index>=0){
			return clause[index];
		}else {
			throw new IndexOutOfBoundsException();
		}
		
	}
	
	/**
	 * Checks if the Clause is satisfied with the given BitVector
	 * @param assignment BitVector to be checked
	 * @return true if the clause is satisfied, otherwise false
	 */
	public boolean isSatisfied(BitVector assignment){ 
			int size = clause.length;
			for(int i=0; i< size;i++){
				if(clause[i]>0 && assignment.get(clause[i]-1)  || !assignment.get(Math.abs(Math.abs(clause[i])-1)) && clause[i]<0){   //-1 jer mi array krece od 0, a literali od x1 itd... 
					return true;
				}else {
					continue;
				}
			}
			return false;
		
	}
	
	@Override
	public String toString(){
		int length = clause.length;
		StringBuilder returnString = new StringBuilder(length);
		for(int i=0; i<length;i++){
			if(i==0){
				returnString.append("(");
			}
			if(clause[i] > 0){
					returnString.append("X" + clause[i]);
			}else {
					returnString.append("!X"+ Math.abs(clause[i]));
			}		
			if(i != length-1){
				returnString.append(" + ");
			}else if(i== length-1){
				returnString.append(")");
			}
		}
		return returnString.toString();
		
	}
}
