package hr.fer.zemris.optjava.dz4.part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import hr.fer.zemris.optjava.dz4.part1.ISelection;
import hr.fer.zemris.optjava.dz4.part1.RouletteWheel;
import hr.fer.zemris.optjava.dz4.part1.Tournament;
import hr.fer.zemris.optjava.dz4.part1.Zad4;
/**
 * Class implements the main method and calls optimization algorithm
 *
 */
public class BoxFilling {
	//working parameters PathToFile 10 4 4 true 100 50

	/**
	 * Main method
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		int sizePop;
		int maxBoxSize;
		int maxIterations;
		int n,m;
		boolean p;
		LinkedList<Stick> sticks;
		if (args.length != 7) {
			System.out.println(
					"Parameters are not correct, first parameter is the size of the population "
					+ "second parameter is the max wanted error value third parameter is the maximal number of "
					+ "generations fourth is the type of selection either rouletteWheel or tournament:n where n stands "
					+ "for selection of 1 best from n units and the final parameter is sigma which stands for "
					+ "the deviation used to mutate solutions");
			return;
		} else {
			
			try{
				File file = new File(args[0]);
				sticks = readSticks(file);
				if(sticks.isEmpty()) {
					System.out.println("No sticks were defined in the given file");

					return;
				}
			} catch(FileNotFoundException e){
				System.out.println("First parameter must be a path to a file containing the definition of boxfilling problem");
				return;
			}
			try{
				sizePop= Integer.parseInt(args[1]);
			} catch(NumberFormatException e){
				System.out.println("Second parameter must be a number indicating the size of population");
				return;
			}
			try{
				n = Integer.parseInt(args[1]);
			} catch(NumberFormatException e){
				System.out.println("Third parameter must be a number indicating the amount of contestants entering the tournament for best parent");
				return;
			}
			try{
				m = Integer.parseInt(args[2]);
			} catch(NumberFormatException e){
				System.out.println("Fourth parameter must be a number indicating the amount of contestants entering the tournament for worst parent to be replaced");
				return;
			}
			
			switch(args[4]){
			case "true":
				p=true;
				break;
			case "false":
				p=false;
				break;
				default:
					System.out.println("fifth parameter must be either true or false indicating if a child will always be accepted or discarded if its fitness is worse than the worst found parent");
					return;
			}
			try{
				maxBoxSize= Integer.parseInt(args[6]);
			} catch(NumberFormatException e){
				System.out.println("Second parameter must be a number indicating the size of population");
				return;
			}
			try{
				maxIterations= Integer.parseInt(args[5]);
			} catch(NumberFormatException e){
				System.out.println("Second parameter must be a number indicating the size of population");
				return;
			}
			
			IOptAlgorithm alg= new BoxSteadyStateAlgorithm(sizePop, maxBoxSize, maxIterations, n, m, p, sticks);
			alg.run();
		}
	}
	
	/**
	 * 
	 * @param file containing the definition of the problem
	 * @return sticks read from the file and their heights, every stick is numerated and no two sticks share the same number
	 * @throws FileNotFoundException
	 */
	public static LinkedList<Stick> readSticks(File file) throws FileNotFoundException{
		Scanner scan = new Scanner(file);
		LinkedList<Stick> sticks = new LinkedList<>();
		int stickNumber = 1;
		while(scan.hasNextLine()){
			String line = scan.nextLine();
			line = line.replace("[", "");
			line = line.replace("]", "");
			String intTokens[] = line.split(",");
			for(String token : intTokens){
				String tokenTrim = token.trim();
				try{
				sticks.add(new Stick(Integer.parseInt(tokenTrim), stickNumber));
				}
				catch (NumberFormatException e){
					System.out.println("Height of sticks needs to be defined in a file like [15, 10, 2, 3, 4]");
					System.exit(0);
				}
				stickNumber++;
			}
		}
		scan.close();
		return sticks;
	}
}
