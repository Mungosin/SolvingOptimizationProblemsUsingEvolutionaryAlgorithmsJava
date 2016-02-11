package hr.fer.zemris.optjava.dz12.algorithm;



import hr.fer.zemris.optjava.dz12.rng.RNG;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ParalelJob implements Runnable {
	public ConcurrentLinkedQueue<Solution> ulazniRedTotalParalel;
	public ConcurrentLinkedQueue<Solution> izlazniRedTotalParalel;
	public LinkedList<Solution> populacija;
	public Operators operatori;
	public int brojgeneracija;
	public int brojUlaznihVarijabli;
	public int brojCLB;
	public String expression;
	public int brojVarijabli;
	public String[] varijable;
	public int velicinaPopulacije;
	public ScriptEngine JavascriptEngine;
	public boolean exit;

	public ParalelJob(ConcurrentLinkedQueue<Solution> ulazniRedTotalParalel,
			ConcurrentLinkedQueue<Solution> izlazniRedTotalParalel,
			int brojgeneracija, int brojUlaznihVarijabli, int brojCLB,
			String expression, int brojVarijabli, String[] varijable, int velicinaPopulacije) {
		
		this.brojgeneracija = brojgeneracija;
		this.brojUlaznihVarijabli = brojUlaznihVarijabli;
		this.brojCLB = brojCLB;
		this.expression = expression.toUpperCase();
		this.brojVarijabli = brojVarijabli;
		this.varijable = varijable;
		this.ulazniRedTotalParalel = ulazniRedTotalParalel;
		this.izlazniRedTotalParalel = izlazniRedTotalParalel;
		this.velicinaPopulacije = velicinaPopulacije;
		ScriptEngineManager sem = new ScriptEngineManager();
		this.JavascriptEngine = sem.getEngineByName("JavaScript");
		this.exit = false;

		this.expression = this.expression.replace("NOT", "!");
		this.expression = this.expression.replace("OR", "||");
		this.expression = this.expression.replace("AND", "&&");
	}

	@Override
	public void run() {
		if (operatori == null) {

			operatori = new Operators();
		}
		initializePopulation();
		int iteration = 0;
		while (iteration<this.brojgeneracija && !exit){
			
			if(iteration %50 == 0){
				System.out.println("ID dretve: "+ Thread.currentThread().getId() + " Iteracija: " + iteration + " Trenutni fitness: " + this.populacija.getFirst().fitness);
			}
			Collections.sort(this.populacija);
			
			
			
			// elitizam za novuPop
			if(iteration!=0){

				this.populacija = createNextPop();
			}
						
			if(this.ulazniRedTotalParalel.peek() != null && this.ulazniRedTotalParalel.peek().shutdown==true){
				System.out.println("ID ugašene dretve "+ Thread.currentThread().getId());
				exit = true;
				Solution tempSolut = this.populacija.getFirst();
				tempSolut.shutdown = true;
				this.izlazniRedTotalParalel.add(tempSolut);
				break;
			}
			//dohvati rjesenja od susjeda i posalji svoje najbolje
			if(iteration % 10 == 0){
				Collections.sort(this.populacija);
				Solution bestSol = this.populacija.getFirst();
//				System.out.println("ID Dretve: "+Thread.currentThread().getId() + " Trenutni fitness: " + this.populacija.getFirst().fitness);
				int pozicija = this.populacija.size()-1;
				while (true) {
					Solution tempSol = ulazniRedTotalParalel.poll();

					if (tempSol != null) {
						if(tempSol.shutdown){
							this.izlazniRedTotalParalel.add(tempSol);
							exit = true;
							System.out.println("ID ugašene dretve "+ Thread.currentThread().getId());
							break;
						}
						
						if(tempSol.fitness < this.populacija.get(pozicija).fitness){
							this.populacija.set(pozicija, tempSol);
							pozicija--;
						}
					} else {
						break;
					}
				}
				if(bestSol.fitness != 0){

					this.izlazniRedTotalParalel.add(bestSol);
				}else {
					bestSol.shutdown = true;
					this.izlazniRedTotalParalel.add(new Solution(bestSol));
					break;
				}
			}
			iteration++;			
		}
		
		
		if(!exit){
			try {
				Thread.sleep(RNG.getRNG().nextInt(50,250));
				System.out.println("\n\n\nZavršena dretva bez prekidanja " + Thread.currentThread().getId());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Collections.sort(this.populacija);
			Solution prvi = this.populacija.getFirst();
			int redniBrojCLBa = -1;
			for(int i=0;i<this.brojCLB;i++){
				if(prvi.evaluationArray[i] == 0){
					redniBrojCLBa = i;
					break;
				}
			}
			System.out.println("Najbolji pronadeni fitness: " + prvi.fitness);
			if(prvi.fitness == 0){
				System.out.println("Detaljniji ispis nalazi se u textualnoj datoteci koja u sebi sadrzi ID dretve\n\n");
				ispisiRijesenje(prvi, redniBrojCLBa);
			}
		}
	}
	
	private void ispisiRijesenje(Solution prvi, int redniBrojCLBa){
		BufferedWriter outDec = null;

		try {
			FileWriter decStream = new FileWriter("izlaz-dretve-s-idjem-"+Thread.currentThread().getId()+".txt", false);

			outDec = new BufferedWriter(decStream);
			outDec.write("CLB koji riješava logicki izraz + "+ this.expression +"  je na " + (redniBrojCLBa +1) +" poziciji u lancu CLB-ova\n");
			outDec.write(dohvatiUlazeUCLB(prvi.kromosom.get(redniBrojCLBa).ulaziKod)+"\n");
			outDec.write(prvi.kromosom.get(redniBrojCLBa)+ "\n");
			prvi.IspisiKombinacijeUTxt(outDec, redniBrojCLBa+this.brojVarijabli);
			
		} catch (IOException ignorable) {
		} finally {
			if (outDec != null) {
				try {
					outDec.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	

	private String dohvatiUlazeUCLB(int[] ulazneVrijednosti) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<ulazneVrijednosti.length;i++){
			sb.append((i+1) +". ulaz u CLB ");
			if(ulazneVrijednosti[i] < this.brojVarijabli){
				sb.append("je varijabla " + this.varijable[ulazneVrijednosti[i]]+ "\n");
			}else {
				sb.append("je izlaz iz CLB-a s rednim brojem " + (ulazneVrijednosti[i] - this.brojVarijabli+1)+ "\n");
			}
		}
		return sb.toString();
	
		
	}

	private LinkedList<Solution> createNextPop() {
		Solution firstBest = null;
		Solution secondBest = null;
		LinkedList<Solution> newPop = new LinkedList<>();
		firstBest = this.populacija.getFirst();
		secondBest = this.populacija.get(1);
	
		newPop.add(firstBest);
		newPop.add(secondBest);
		while(newPop.size()!= this.velicinaPopulacije){
			Solution p1 = operatori.findParent(populacija,
					Constants.tournament);

			Solution p2 = operatori.findParent(populacija,
					Constants.tournament);

			Solution child = operatori.createChild(p1, p2);

			newPop.add(child);
		}
		return newPop;
		
	}

	private void initializePopulation() {
		this.populacija = new LinkedList<>();
		for(int i=0;i<this.velicinaPopulacije;i++){
			Solution sol = new Solution(this.brojUlaznihVarijabli, this.brojCLB, expression, this.brojVarijabli, varijable, JavascriptEngine);
			sol.evaluateSolution();
			this.populacija.add(sol);
		}
	}

}
