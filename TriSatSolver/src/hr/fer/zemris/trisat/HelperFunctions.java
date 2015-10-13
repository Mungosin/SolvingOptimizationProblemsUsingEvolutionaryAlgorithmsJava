package hr.fer.zemris.trisat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HelperFunctions class implementation with methods required for Algorithm implementations
 *
 */
public class HelperFunctions {
	private BufferedReader reader;
	private boolean finished=false;
	
	public HelperFunctions(BufferedReader reader){
		this.reader = reader;
	}

	/**
	 * Initializes the SATFormula object
	 * @return SATFormula that has been created from the given file read with BufferedReader
	 */
	public SATFormula initializeSATForms(){
		String line;
		line = readUsefulRow();
		Clause[] clauses = null;
		int numberOfVariables=0;
		int numberOfClauses=0;
		boolean firstInitialization=false;
		while(line!=null){
			if(line.length()>0 && line.charAt(0) == 'p' && !firstInitialization){				
				Pattern p = Pattern.compile("(^p|)([0-9]+\\s+[0-9]+)");
				Matcher m = p.matcher(line);
				if(m.find()){
					String[] tokens = m.group(0).split("\\s+");
					numberOfVariables = Integer.parseInt(tokens[0]);
					numberOfClauses = Integer.parseInt(tokens[1]);
					firstInitialization = true;
				}else {
					return null;
				}
			}else {
				clauses = initializeClauses(numberOfVariables, numberOfClauses);
				if(clauses == null){
					return null;
				}
				line = readUsefulRow();
			}
		}
		return new SATFormula(numberOfVariables, clauses);
	}
	
	/**
	 * Initializes Clauses of a formula with the BufferedReader that reads from a file
	 * @param numberOfVariables in the formula
	 * @param numberOfClauses number of Clauses in the formula
	 * @return array of Clauses
	 */
	private Clause[] initializeClauses(int numberOfVariables, int numberOfClauses){
		Clause[] returnClauses = new Clause[numberOfClauses];
		int numberOfFoundClauses = 0;
		for(int i=0;i<numberOfClauses;i++){
			String clause = readUsefulRow();
			if(clause==null){ 
				break;
			}
			Pattern p = Pattern.compile("(((\\-[1-9][0-9]*|[1-9][0-9]*)\\s)+)");
			Matcher matcher = p.matcher(clause);
			if(matcher.find()){
				String[] intTokens = matcher.group(0).split("\\s+");
				int[] initializationField = new int[intTokens.length];
				int tokenLength = intTokens.length;
				for (int j=0; j<tokenLength;j++){
					initializationField[j] = Integer.parseInt(intTokens[j]);
				}
				returnClauses[numberOfFoundClauses++] = new Clause(initializationField);
			}
		}
		if(numberOfFoundClauses != numberOfClauses){
			return null;
		}
		return returnClauses;
	}
	
	/**
	 * Method is used to read rows that don't start with c and stops when it finds '%' symbol
	 * @return String object read from the file
	 */
	private String readUsefulRow(){
		String returnString = null;
		try {
			if(finished){
				return null;
			}
			while((returnString = reader.readLine())!= null){
				if(returnString.charAt(0) == 'c'){
					continue;
				}else if(returnString.charAt(0) == '%'){
					finished=true;
					return null;
				}else {
					break;
				}				
			}
		} catch (IOException e) {
			return null;
		}
		return returnString;
	}
	
	/**
	 * Method is used to create a boolean array from the given integer
	 * @param lengthOfArrayWanted wanted length of the converted integer
	 * @param numberToBeConverted integer to be converted into boolean array
	 * @return returns the boolean array of the integer
	 */
	public boolean[] intToBooleanArray(int lengthOfArrayWanted, int numberToBeConverted){
		String stringReprezentation = Integer.toString(numberToBeConverted);
		boolean[] initializer = new boolean[lengthOfArrayWanted];
		for(int i=0; i< lengthOfArrayWanted; i++){
			if((numberToBeConverted%10) %2 == 0){
				initializer[lengthOfArrayWanted-i -1] = false;
			}else {
				initializer[lengthOfArrayWanted -i -1] = true;
			}				
			numberToBeConverted /= 2;
		}
		return initializer;
	}
	
	/**
	 * Method that calculates the fitness function of the second algorithm
	 * @param satForm SATFormulaStats object used to calculate the fitness of the assignment in the given formula
	 * @return fitness of the current assignment
	 */
	public int fitnessFunctionSecondAlgorithm(SATFormulaStats satForm){
		return satForm.getNumberOfSatisfied();
	}
	
	/**
	 * Method that calculates the fitness function of the third algorithm
	 * @param satForm SATFormulaStats object used to calculate the fitness of the assignment in the given formula
	 * @return fitness of the current assignment
	 */
	public double fitnessFunctionThirdAlgorithm(SATFormulaStats satForm){
		return satForm.getNumberOfSatisfied()+satForm.getPercentageBonus();
	}

	/**
	 * Method is used to find if the current BitVector has a greater BitVector than him for example 100 has a greater and it is 101, but vector 111 doesn't have a greater representation using 3 bits
	 * @param current BitVector that is being checked
	 * @return true if it has a greater, false otherwise
	 */
	public boolean hasGreater(BitVector current) {
		int size = current.getSize();
		for(int i=0;i<size;i++){
			if(!current.get(i)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Method is used to get the next greater BitVector of the given BitVector, for example next greater BitVector of 101 is 110
	 * @param current BitVector whose next greater will be returned
	 * @return next greater BitVector from the given BitVector
	 */
	public BitVector nextGreater(BitVector current) {
		int size = current.getSize();
		boolean[] next = new boolean[size];
		boolean carry = false;
		boolean additionFinished = false;
		for(int i=size-1;i>=0;i--){
			if(additionFinished){
				next[i] = current.get(i);
			}else{
				if(i==size-1){
					if(!current.get(i)){
						next[i] = true;
					}else {
						next[i] = false;
						carry = true;
					}
				}else {
					if(current.get(i) && carry){
						next[i] = false;
						carry = true;
					}else if(current.get(i) && !carry){
						next[i] = true;
						carry = false;
						additionFinished=true;
					}else if(!current.get(i) && carry){
						next[i] = true;
						carry = false;
						additionFinished=true;
					}else if((!current.get(i) && !carry)){
						next[i] = false;
						carry = false;
						additionFinished=true;
					}
				}
			}
		}
		return new BitVector(next);

	}
}
