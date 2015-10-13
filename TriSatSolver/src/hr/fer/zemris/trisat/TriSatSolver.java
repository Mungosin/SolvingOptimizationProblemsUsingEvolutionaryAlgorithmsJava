package hr.fer.zemris.trisat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Class that implements the main method 
 *
 */
public class TriSatSolver {

	/**
	 * Main mehtod used to open a file and call the desired algorithm
	 * @param args console arguments, first one is number of the desired algorithm and second one is a path to the .cnf file
	 */
	public static void main(String[] args) {
		if(args.length != 2){
			System.out.println("Wrong parameters, first parameter is the number of the desired algorithm (1-3) and the second is the path to .cnf file");
			return;
		}else {
			File myFile = new File(Paths.get(args[1]).toAbsolutePath().toString());
			Algorithms implementedAlgorithms = new Algorithms(myFile);
			switch(Integer.parseInt(args[0])){
				case 1:
					implementedAlgorithms.FirstAlgorithm();
					break;
				case 2:
					implementedAlgorithms.SecondAlgorithm();
					break;
				case 3:
					implementedAlgorithms.ThirdAlgorithm();
					break;
				default: System.out.println("No algorithms selected, choose between 1-3");
					break;
			}
		}
	}	
}
