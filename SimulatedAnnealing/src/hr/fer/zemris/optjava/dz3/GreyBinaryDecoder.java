package hr.fer.zemris.optjava.dz3;
/**
 * Class extends BitVectorDecoder and is used to decode natural grey code into double values
 *
 */
public class GreyBinaryDecoder extends BitVectorDecoder{
	/**
	 * Constructor for NaturalBinaryDecoder object
	 * @param mins minimal value of the binary code per variable
	 * @param maxs maximal value of the binary code per variable
	 * @param bits starting bits to be decoded
	 * @param n number of bits per variable
	 */
	public GreyBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super(mins, maxs, bits, n);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Constructor for NaturalBinaryDecoder object
	 * @param min minimal value of the binary code
	 * @param max maximal value of the binary code
	 * @param n number of bits per variable
	 * @param totalBits total bits
	 */
	public GreyBinaryDecoder(double min, double max, int n, int totalBits) {
		super(min, max, n, totalBits);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double[] decode(BitVectorSolution object) {
		double valueOfCode=0;
		double[] decodedValue = new double[this.n];
		int[] binaryCode;
		int[] subBytes;
		int numOfBitsPerVariable = this.totalBits/this.n;
		for(int i=0;i<this.n;i++){
			subBytes = copyArray(object.bits, i*numOfBitsPerVariable, (i+1)* numOfBitsPerVariable);
			binaryCode = grayToBinary(subBytes);
			valueOfCode = binaryToDouble(binaryCode);
			decodedValue[i] = this.mins[i] + ((valueOfCode)/(Math.pow(2, numOfBitsPerVariable) -1)) * (this.maxs[i] - this.mins[i]);
		}
		
		return decodedValue;
	}

	@Override
	public void decode(BitVectorSolution object, double[] result) {
		double valueOfCode=0;
		int[] binaryCode;
		int[] subBytes;
		int numOfBitsPerVariable = this.totalBits/this.n;
		if(result.length != this.n){
			throw new IndexOutOfBoundsException("Result length doesnt match the number of variables");
		}
		for(int i=0;i<this.n;i++){
			subBytes = copyArray(object.bits, i*numOfBitsPerVariable, (i+1)* numOfBitsPerVariable);
			binaryCode = grayToBinary(subBytes);
			valueOfCode = binaryToDouble(binaryCode);
			result[i] = this.mins[i] + ((valueOfCode)/(Math.pow(2, numOfBitsPerVariable) -1)) * (this.maxs[i] - this.mins[i]);
		}
		
	}
	/**
	 * Method is used to copy a part of the given byte array
	 * @param array that is being copied
	 * @param from index from which it is being copied
	 * @param to index to which it is being copied
	 * @return the newly created array
	 */
	private int[] copyArray(byte[] array, int from, int to){
		int[] copiedArray = new int[to-from];
		for(int i=from,j=0;i<to;i++,j++){
			copiedArray[j] = array[i];
		}
		return copiedArray;
	}
	/**
	 * Method is used to transform the grey code into binary code
	 * @param grayCode to be converted to binaryCode
	 * @return the binary code from the given grey code
	 */
	private int[] grayToBinary(int[] grayCode){
		int codeLength = grayCode.length;
		int[] binaryCode = new int[codeLength];
		for(int i=0;i<codeLength;i++){
			if(i==0){
				binaryCode[0] = grayCode[0];
			}else {
				if(binaryCode[i-1] != grayCode[i]){
					binaryCode[i] = 1;
				}else {
					binaryCode[i] = 0;
				}
			}
		}
		return binaryCode;
		
	}
	/**
	 * Method is used to transform value of a binary to double
	 * @param binaryCode binary code to be transformed to their double value
	 * @return double value of the given binary code
	 */
	private double binaryToDouble(int[] binaryCode){
		int codeLength = binaryCode.length;
		double valueOfCode=0;
		for(int i=codeLength-1, power=0;i>=0;i--, power++){
			if(binaryCode[i] == 0){
				continue;
			}
			valueOfCode += Math.pow(2, power);
		}
		
		return valueOfCode;
	}

}
