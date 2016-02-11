package hr.fer.zemris.optjava.dz12.algorithm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Solution implements Comparable<Solution>{
	public int fitness;
	public int brojCLB;
	public LinkedList<CLB> kromosom;
	public int[] evaluationArray;
	public String expression;
	public String[] varijable;
	public int brojVarijabli;
	public ScriptEngine JavascriptEngine;
	public int[] ulazneVrijednosti;
	public int brojUlaznihVarijabli;
	public boolean shutdown;
	
	public Solution(Solution copied){
		this.expression = copied.expression;
		this.kromosom = new LinkedList<CLB>();
		for(CLB bla : copied.kromosom){
			kromosom.add(bla.duplicate());
		}
		this.brojCLB = copied.brojCLB;
		this.evaluationArray = new int[copied.brojCLB];
		this.varijable = Arrays.copyOf(copied.varijable, copied.varijable.length);
		this.brojVarijabli = copied.brojVarijabli;
		this.brojUlaznihVarijabli = copied.brojUlaznihVarijabli;
		this.JavascriptEngine = copied.JavascriptEngine;
		this.fitness = copied.fitness;
		this.shutdown = copied.shutdown;

		this.ulazneVrijednosti = Arrays.copyOf(copied.ulazneVrijednosti, copied.ulazneVrijednosti.length);
	}
	
	public Solution(int brojUlaznihVarijabli, int brojCLB, String expression, int brojVarijabli, String[] varijable, ScriptEngine JavascriptEngine){
		this.shutdown = false;
		this.expression = expression.toUpperCase();
		this.kromosom = new LinkedList<CLB>();
		this.evaluationArray = new int[brojCLB];
		this.brojCLB = brojCLB;
		this.varijable = varijable;
		this.brojVarijabli = brojVarijabli;
		this.brojUlaznihVarijabli = brojUlaznihVarijabli;
		this.JavascriptEngine = JavascriptEngine;
		this.fitness = 1;
		this.ulazneVrijednosti = new int[brojCLB+ brojVarijabli];
		
		int counter = brojVarijabli;
		for(int i=0;i<this.brojCLB;i++){
			kromosom.add(new CLB(brojUlaznihVarijabli, counter));
			counter++;
		}
	}
	
	public boolean evaluateExpression(int[] ulazi){
		String newExpression=new String(this.expression);
		for(int i=0;i<this.brojVarijabli;i++){
			Boolean temp = (ulazi[i] == 1);
			newExpression = newExpression.replace(varijable[i], temp.toString());
		}
		try {

			String rezultat = (this.JavascriptEngine.eval(newExpression).toString());
			return Boolean.parseBoolean(rezultat);
		} catch (ScriptException e) {
			System.out.println("greska u parsiranju sljedeceg izraza "+ newExpression);
			System.exit(0);
			return false;
		}
	}
	
	public void evaluateSolution(){
		//svaku kombu evaluirat i orginalni exprešn
		Arrays.fill(this.evaluationArray, 0);
		
		for(int i=0; i< Math.pow(2,this.brojVarijabli); i++){
			int[] ulazi = createIntArray(i);
			for(int k=0;k<this.brojVarijabli;k++){
				this.ulazneVrijednosti[k] = ulazi[k];
			}
			for(CLB temp : kromosom){
				int redniBroj = temp.redniBroj;
				int[] ulazniKod = new int[brojUlaznihVarijabli];
				int j=0;
				for(int tempKod : temp.ulaziKod){
					ulazniKod[j++] = ulazneVrijednosti[tempKod];
				}
				boolean rezultat = temp.evaluate(ulazniKod);
				boolean rezExpres = evaluateExpression(ulazi);
				
				
				if(rezultat != rezExpres){
					this.evaluationArray[temp.redniBroj-this.brojVarijabli]++;
				}
				
				if(rezultat){
					ulazneVrijednosti[redniBroj-1] = 1;
				}else{
					
					ulazneVrijednosti[redniBroj-1] = 0;
				}
			}
		}
		
		izracunajFitness();
	}
	
	public void IspisiKombinacijeUTxt(BufferedWriter outDec, int redniBrojCLB){
		//svaku kombu evaluirat i orginalni exprešn
				Arrays.fill(this.evaluationArray, 0);
				for(String varijabla : this.varijable){

					try {
						outDec.write(varijabla + "\t");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				

				try {
					outDec.write("Izlaz CLB-a broj:" + redniBrojCLB+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for(int i=0; i< Math.pow(2,this.brojVarijabli); i++){
					int[] ulazi = createIntArray(i);
					for(int k=0;k<this.brojVarijabli;k++){
						this.ulazneVrijednosti[k] = ulazi[k];
					}
					for(CLB temp : kromosom){
						int redniBroj = temp.redniBroj;
						int[] ulazniKod = new int[brojUlaznihVarijabli];
						int j=0;
						

						if(redniBroj ==redniBrojCLB){
							for(int kod : ulazi){
								try {
									outDec.write(kod+ "\t");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
						for(int tempKod : temp.ulaziKod){
							ulazniKod[j] = ulazneVrijednosti[tempKod];
							j++;
						}
						boolean rezultat = temp.evaluate(ulazniKod);

						if(redniBroj ==redniBrojCLB){
							try {
								if(rezultat){
									outDec.write("1\n");
								}else{
									outDec.write("0\n");
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						boolean rezExpres = evaluateExpression(ulazi);						
						
						if(rezultat){
							ulazneVrijednosti[redniBroj-1] = 1;
						}else{
							
							ulazneVrijednosti[redniBroj-1] = 0;
						}
					}
				}
				
	}
	
	public Solution duplicate(){
		Solution duplicate = new Solution(this);
		return duplicate;
	}
	
	private void izracunajFitness() {
		this.fitness = 1;
		for(int bla : evaluationArray){
			this.fitness *=bla;
		}
	}

	private int[] createIntArray(int num){
		int[] returnArray = new int[this.brojVarijabli];
		
		int brojVar = this.brojVarijabli;
		for(int i=0;i<this.brojVarijabli;i++){
			returnArray[brojVar-i-1] = (num & (1<<i))>>i;
		}
		return returnArray;
	}

	@Override
	public int compareTo(Solution o) {
			return Integer.compare(this.fitness, o.fitness);
	}
	

}
