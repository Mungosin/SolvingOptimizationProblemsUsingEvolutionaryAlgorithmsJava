package hr.fer.zemris.optjava.dz12;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.optjava.dz12.algorithm.Constants;
import hr.fer.zemris.optjava.dz12.algorithm.ParalelJob;
import hr.fer.zemris.optjava.dz12.algorithm.Solution;
import hr.fer.zemris.optjava.dz12.rng.EVOThread;
import hr.fer.zemris.optjava.dz12.rng.IRNG;
import hr.fer.zemris.optjava.dz12.rng.RNG;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Main {

	public static void main(String[] args) {
		int brojUlazaCLB = 0;
		int brojCLB = 0;
		int maxIterPoPoslu = 0;
		int velicinaPopulacije = 0;
		
		if(args.length!=5){
			System.out.println("potrebni su sljedeci parametri: broj ulaza clba, broj clbova, maximum iteracija, velicina populacije, i logicki izraz s varijablama i kljucnim rijecima NOT AND i OR");
		}
		
		try{
			brojUlazaCLB = Integer.parseInt(args[0]);
		}catch(Exception e){
			System.out.println("Prvi parametar mora biti cjelobrojna vrijednost koja definira broj ulaza u CLB");
		}
		
		try{
			brojCLB = Integer.parseInt(args[1]);
		}catch(Exception e){
			System.out.println("Drugi parametar mora biti cjelobrojna vrijednost koja definira broj CLB-ova");
		}
		
		try{
			maxIterPoPoslu = Integer.parseInt(args[2]);
		}catch(Exception e){
			System.out.println("Treci parametar mora biti cjelobrojna vrijednost koja definira maximalan broj iteracija po poslu");
		}
		
		try{
			velicinaPopulacije = Integer.parseInt(args[3]);
		}catch(Exception e){
			System.out.println("Cetvrti parametar mora biti cjelobrojna vrijednost koja definira maximalan broj riješenja u populaciji");
		}
		
		
		
		String[] varijable = izluèiVarijable(args[4]);
		
		ParalelJob[] poslovi = new ParalelJob[Constants.poslovi];
		EVOThread[] threads = new EVOThread[Constants.poslovi];
		LinkedList<ConcurrentLinkedQueue<Solution>> redovi = new LinkedList<>();
		for(int i=0;i<Constants.poslovi;i++){
			redovi.add(new ConcurrentLinkedQueue<Solution>());
		}
		for(int i=0;i<Constants.poslovi;i++){
			ParalelJob posao = new ParalelJob(redovi.get((Constants.poslovi+i-1)%Constants.poslovi), redovi.get(i), maxIterPoPoslu, brojUlazaCLB, brojCLB, args[4], varijable.length, varijable, velicinaPopulacije);
			poslovi[i] = posao;
			threads[i] = new EVOThread(posao);
		}

		for(int i=0;i<Constants.poslovi;i++){
			threads[i].start();
		}

		for(int i=0;i<Constants.poslovi;i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	
		//EVOThread thread = new EVOThread(job);
		//thread.start();
		//evaluateExpression("bla");
	}
	
	private static String[] izluèiVarijable(String string) {
		String izraz = string.toUpperCase();
		LinkedList<String> varijable = new LinkedList<String>();
		izraz = izraz.replace("("," ");
		izraz = izraz.replace(")", " ");
		izraz = izraz.replace("AND", " ");
		izraz = izraz.replace("OR",	" ");
		izraz = izraz.replace("NOT", " ");
		String token[] = izraz.split(" ");
		if(token.length == 0){
			System.out.println("Izraz nema varijabli");
			System.exit(0);
		}
		for(String var : token){
			if(!varijable.contains(var) && !var.equals(" ") && !var.isEmpty()){
				varijable.add(var);
			}
		}
		
		String[] varijableArray = new String[varijable.size()];
		for(int i=0;i<varijable.size();i++){
			varijableArray[i] = varijable.get(i);
		}
		
		return varijableArray;
		
	}

	public static String evaluateExpression(String expression){
		 try {

	            ScriptEngineManager sem = new ScriptEngineManager();
	            ScriptEngine se = sem.getEngineByName("JavaScript");
	            //String myExpression = "('abc' == 'xyz' && 'thy' == 'thy') || ('ujy' == 'ujy')";
	            String myExpression = "(!true)";
	            String rezultat = se.eval(myExpression).toString();
	            System.out.println(se.eval(myExpression));
	            return rezultat;
	        } catch (ScriptException e) {

	            System.out.println("Invalid Expression");
	            e.printStackTrace();
	            return null;
	        }
	}

}
