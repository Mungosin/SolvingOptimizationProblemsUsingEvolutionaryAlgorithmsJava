package hr.fer.zemris.optjava.dz4.part2;
/**
 * Class implements all methods and attributes that describe the object that is being sorted in the boxSolution
 *
 */
public class Stick implements Comparable<Stick>{
	private int height;
	protected int stickNumber;
	/**
	 * Constructs the Stick object with given parameters
	 * @param height is the dimension of the stick
	 * @param stickNumber unique number of the stick
	 */
	public Stick(int height, int stickNumber){
		this.height = height;
		this.stickNumber = stickNumber;
	}
	/**
	 * Method is used to get the height dimension of the stick
	 * @return height of the stick
	 */
	public int getHeight(){
		return height;
	}
	
	@Override
	public int hashCode(){
		return Integer.hashCode(stickNumber);
	}
	@Override
	public boolean equals (Object obj){
		if(obj == null ||!(obj instanceof Stick)){
			return false;
		}else if(obj == this || ((Stick)obj).stickNumber == this.stickNumber) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public String toString(){
		return String.valueOf(height);
	}

	@Override
	public int compareTo(Stick o) {
		if(this.height<o.height){
			return 1;
		}else if(this.height>o.height){
			return -1;
		}else{
			return 0;
		}
	}
	
}
