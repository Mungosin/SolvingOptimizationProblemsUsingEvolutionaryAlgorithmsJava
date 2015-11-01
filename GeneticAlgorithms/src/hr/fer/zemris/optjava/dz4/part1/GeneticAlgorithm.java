package hr.fer.zemris.optjava.dz4.part1;

import java.io.File;
import java.util.Random;

/**
 * Class implements the main method and calls the appropriate algorithms based
 * on the inputs
 *
 */
public class GeneticAlgorithm {
	
	//Working input 
	//tournament  - 100 0.05 100000 tournament:10 0.01 
	//Roulette wheel - 100 0.05 100000 rouletteWheel 0.15
	// Constants class
	//alpha = 0.5;
	//saveBest = 2;
	//maxAbortions = 1000;

	/**
	 * Main method
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		File myFile = new File("");
		int sizePop;
		double maxError;
		int maxGen;
		int n;
		double sigma;
		ISelection selection;
		Random rand = new Random(System.currentTimeMillis());
		if (args.length != 5) {
			System.out.println(
					"Parameters are not correct, first parameter is the size of the population "
					+ "second parameter is the max wanted error value third parameter is the maximal number of "
					+ "generations fourth is the type of selection either rouletteWheel or tournament:n where n stands "
					+ "for selection of 1 best from n units and the final parameter is sigma which stands for "
					+ "the deviation used to mutate solutions");
			return;
		} else {

			Zad4 function = new Zad4(myFile.getAbsolutePath()+"/02-zad-prijenosna.txt");
			try{
				sizePop = Integer.parseInt(args[0]);
			} catch(NumberFormatException e){
				System.out.println("First parameter must be a number indicating size of population");
				return;
			}
			try{
				maxError = Double.parseDouble(args[1]);
			} catch(NumberFormatException e){
				System.out.println("Second parameter must be a number indicating the max wanted error value");
				return;
			}
			try{
				maxGen = Integer.parseInt(args[2]);
			} catch(NumberFormatException e){
				System.out.println("Third parameter must be a number indicating the max number of generations");
				return;
			}
			
			switch(args[3]){
			case "rouletteWheel":
				
				selection = new RouletteWheel(rand);
				break;
			default:
				if(args[3].contains("tournament:")){
					String tokens[] = args[3].split(":");
					try{
						if(tokens.length!= 2 ){
							throw new IllegalArgumentException();
						}
						n = Integer.parseInt(tokens[1]);
						selection = new Tournament(n, rand);
					}catch (NumberFormatException e){
						System.out.println("Tournament selection must be written as tournament:n where n is the number of contestants");
						return;
					}catch (IllegalArgumentException ex){
						System.out.println("Tournament selection must be written as tournament:n where n is the number of contestants");
						return;
					}
				}else{
					System.out.println("Fourth parameter must be the desired type of selection either tournament:n where n is the number of contestants or rouletteWheel");
					return;
				}
				break;
			}
			try{
				sigma = Double.parseDouble(args[4]);
			} catch(NumberFormatException e){
				System.out.println("Third parameter must be a number indicating the max number of generations");
				return;
			}
			IOptAlgorithm alg= new GeneticBLXAlgorithm(sizePop, maxError, maxGen, selection, sigma, function);
			alg.run();
		}
	}

}
