package hr.fer.zemris.optjava.dz11.algorithms;

import hr.fer.zemris.optjava.dz11.art.GrayScaleImage;
import hr.fer.zemris.optjava.dz11.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.generic.ga.Solution;
import hr.fer.zemris.optjava.dz11.genetic.operators.Constants;
import hr.fer.zemris.optjava.dz11.genetic.operators.ISelection;
import hr.fer.zemris.optjava.dz11.genetic.operators.Operators;
import hr.fer.zemris.optjava.dz11.rng.EVOThread;
import hr.fer.zemris.optjava.dz11.rng.IRNG;
import hr.fer.zemris.optjava.dz11.rngimpl.RNGRandomImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Zad1Alg {
	IRNG rand = new RNGRandomImpl();
	public int brojKvadrata;
	public int velicinaPopulacije;
	public int maxGen;
	public double minFitness;
	public String fileOutputPath;
	public String picturePath;
	public boolean totalParalel;
	public LinkedList<Solution> populacija;
	public ISelection selection;
	public LinkedList<HalfParalelJob> halfParalelJobs;
	public LinkedList<TotalParalelJob> totalParalelJobs;
	public String picOutputPath;
	
	public ConcurrentLinkedQueue<Integer> ulazniRedTotalParalel;
	public ConcurrentLinkedQueue<Solution> izlazniRedTotalParalel;
	
	public ConcurrentLinkedQueue<Solution> ulazniRedHalfParalel;
	public ConcurrentLinkedQueue<Solution> izlazniRedHalfParalel;
	
	public int pictureWidth;
	public int pictureHeight;
	
	public Zad1Alg(int brojKvadrata, int velicinaPopulacije, int maxGen, double minFitness, String picturePath, String fileOutputPath, String picOutputPath, ISelection selection, boolean totalParalel){
		this.brojKvadrata = brojKvadrata;
		this.velicinaPopulacije = velicinaPopulacije;
		this.maxGen = maxGen;
		this.minFitness = minFitness;
		this.fileOutputPath = fileOutputPath;
		this.picturePath = picturePath;
		this.totalParalel = totalParalel;
		this.selection = selection;
		this.picOutputPath = picOutputPath;

		this.halfParalelJobs = new LinkedList<HalfParalelJob>();
		this.totalParalelJobs = new LinkedList<TotalParalelJob>();
		
		this.ulazniRedTotalParalel = new ConcurrentLinkedQueue<Integer>();
		this.izlazniRedTotalParalel = new ConcurrentLinkedQueue<Solution>();
		
		this.ulazniRedHalfParalel = new ConcurrentLinkedQueue<Solution>() ;
		this.izlazniRedHalfParalel = new ConcurrentLinkedQueue<Solution>();
		
		File pic = new File(picturePath);
		
		GrayScaleImage img = null;
		try {
			img = GrayScaleImage.load(pic);
		} catch (IOException e) {
			System.out.println("Slika nije loadana");
			System.exit(0);
		}
		
		this.pictureHeight = img.getHeight();
		this.pictureWidth = img.getWidth();
		
		// stvaranje fajla tu treba di ce se ispisivat rjesenje s parametrima vamo tamo
		initializePopulation();
		
		if(totalParalel){
			runTotalParalel();
		}else {
			runHalfParalel();
		}
	}
	
	
	private void initializePopulation() {
		this.populacija = new LinkedList<>();
		for(int i=0;i<this.velicinaPopulacije;i++){
			Solution sol = new Solution(this.brojKvadrata+1);
			sol.randomize(this.pictureWidth, this.pictureHeight);
			this.populacija.add(sol);
		}
	}


	private void runTotalParalel() {
		int cores = Runtime.getRuntime().availableProcessors();
		EVOThread[] threads = new EVOThread[cores];
		GrayScaleImage[] slike = new GrayScaleImage[cores];
		TotalParalelJob[] poslovi = new TotalParalelJob[cores];
		for(int i=0;i<cores;i++){
			TotalParalelJob posao = new TotalParalelJob(this.ulazniRedTotalParalel, this.izlazniRedTotalParalel);
			GrayScaleImage novi = null;
			try {
				novi = GrayScaleImage.load(new File(picturePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			poslovi[i] = posao;
			slike[i] = novi;
			threads[i] = new EVOThread(posao, novi);
			this.totalParalelJobs.add(posao);
		}
		
		
		int iteration = 0;
		while(iteration<this.maxGen){
			
			
			if(iteration %50 == 0){
				System.out.println("Iteracija: " + iteration);
			}
			
			int brojUPop = this.velicinaPopulacije;
			Collections.sort(this.populacija);
			
			while(true){
				if(brojUPop-Constants.djecePoDretvi >=0){
					brojUPop -= Constants.djecePoDretvi;
					this.ulazniRedTotalParalel.add(new Integer(Constants.djecePoDretvi));
				}else {
					this.ulazniRedTotalParalel.add(new Integer(brojUPop));
					break;
				}
			}
			for(TotalParalelJob job : this.totalParalelJobs){
				job.setPop(this.populacija);
			}
			
			
			for(int i = 0; i < threads.length; i++){
					threads[i] = new EVOThread(poslovi[i], slike[i]);
					threads[i].start();
			}
			
			for(int i = 0; i < threads.length; i++){
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					System.out.println("wat");
					e.printStackTrace();
				}
			}
			Solution firstBest = null;
			Solution secondBest = null;
			if(iteration != 0){
				firstBest = this.populacija.getFirst();
				secondBest = this.populacija.get(1);
			}
			this.populacija.clear();
			for(Solution sol : this.izlazniRedTotalParalel){
				this.populacija.add(sol);
				if(this.populacija.size() == this.velicinaPopulacije -2 && iteration != 0){
					break;
				}
			}
			if(iteration != 0){
				this.populacija.add(firstBest);
				this.populacija.add(secondBest);
			}
			
			this.izlazniRedTotalParalel.clear();
			
						
			if(iteration % 500 == 0){
				Collections.sort(this.populacija);
				System.out.println("Trenutni fitness: " + this.populacija.getFirst().fitness);
				GrayScaleImage pic = new GrayScaleImage(this.pictureWidth, this.pictureHeight);
				Solution bestSol = this.populacija.getFirst();
				pic.clear((byte)bestSol.data[0]);
				for(int i=0; i<bestSol.getNumberOfRectangles();i++){
					pic.rectangle(bestSol.data[i*5+1], bestSol.data[i*5+2], bestSol.data[i*5+3], bestSol.data[i*5+4], (byte)bestSol.data[i*5+5]);
				}
				try {
					pic.save(new File(picOutputPath));
				} catch (IOException e) {
					
				}
			}
			
			if(this.populacija.getFirst().fitness > minFitness){
				System.out.println(this.populacija.getFirst().fitness);
				System.out.println("Zavrseno jer je doslo do zeljene pogreske");
				break;
			}
			iteration++;
		}
		
		Collections.sort(this.populacija);
		System.out.println("Najbolji pronadeni fitness: " + this.populacija.getFirst().fitness);
		GrayScaleImage pic = new GrayScaleImage(this.pictureWidth, this.pictureHeight);
		Solution bestSol = this.populacija.getFirst();
		pic.clear((byte)bestSol.data[0]);
		for(int i=0; i<bestSol.getNumberOfRectangles();i++){
			pic.rectangle(bestSol.data[i*5+1], bestSol.data[i*5+2], bestSol.data[i*5+3], bestSol.data[i*5+4], (byte)bestSol.data[i*5+5]);
		}
		try {
			pic.save(new File("C:\\Java\\Paralelizacija\\kucaAprox.png"));
		} catch (IOException e) {
			
		}
		
		spremiParametreUFile(this.fileOutputPath, bestSol);
	}


	public void runHalfParalel(){
		int cores = Runtime.getRuntime().availableProcessors();
		EVOThread[] threads = new EVOThread[cores];
		GrayScaleImage[] slike = new GrayScaleImage[cores];
		HalfParalelJob[] poslovi = new HalfParalelJob[cores];
		for(int i=0;i<cores;i++){
			HalfParalelJob posao = new HalfParalelJob(ulazniRedHalfParalel, izlazniRedHalfParalel);
			GrayScaleImage novi = null;
			try {
				novi = GrayScaleImage.load(new File(picturePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			poslovi[i] = posao;
			slike[i] = novi;
			threads[i] = new EVOThread(posao, novi);
			halfParalelJobs.add(posao);
		}
		
		
		int iteration = 0;
		while(iteration++<this.maxGen){
			
			

			this.populacija = this.nextPopulation(this.populacija);
			
			if(iteration %50 == 0){

				System.out.println("Iteracija: " + iteration);
			}

			
			for(Solution sol : this.populacija){
				ulazniRedHalfParalel.add(sol);
			}
			
			for(int i = 0; i < threads.length; i++){
					threads[i] = new EVOThread(poslovi[i], slike[i]);
					threads[i].start();
			}
			
			for(int i = 0; i < threads.length; i++){
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					System.out.println("wat");
					e.printStackTrace();
				}
			}
			this.populacija.clear();
			
			for(Solution sol : this.izlazniRedHalfParalel){
				this.populacija.add(sol);
			}
			this.izlazniRedHalfParalel.clear();
			
						
			if(iteration % 500 == 0){
				Collections.sort(this.populacija);
				System.out.println("Trenutni fitness: " + this.populacija.getFirst().fitness);
				GrayScaleImage pic = new GrayScaleImage(this.pictureWidth, this.pictureHeight);
				Solution bestSol = this.populacija.getFirst();
				pic.clear((byte)bestSol.data[0]);
				for(int i=0; i<bestSol.getNumberOfRectangles();i++){
					pic.rectangle(bestSol.data[i*5+1], bestSol.data[i*5+2], bestSol.data[i*5+3], bestSol.data[i*5+4], (byte)bestSol.data[i*5+5]);
				}
				try {
					pic.save(new File("C:\\Java\\Paralelizacija\\kucaAprox.png"));
				} catch (IOException e) {
					
				}
			}
			
			if(this.populacija.getFirst().fitness > minFitness){
				System.out.println(this.populacija.getFirst().fitness);
				System.out.println("Zavrseno jer je doslo do zeljene pogreske");
				break;
			}
			

			
		}
		
		Collections.sort(this.populacija);
		System.out.println("Najbolji pronadeni fitness: " + this.populacija.getFirst().fitness);
		GrayScaleImage pic = new GrayScaleImage(this.pictureWidth, this.pictureHeight);
		Solution bestSol = this.populacija.getFirst();
		pic.clear((byte)bestSol.data[0]);
		for(int i=0; i<bestSol.getNumberOfRectangles();i++){
			pic.rectangle(bestSol.data[i*5+1], bestSol.data[i*5+2], bestSol.data[i*5+3], bestSol.data[i*5+4], (byte)bestSol.data[i*5+5]);
		}
		try {
			pic.save(new File("C:\\Java\\Paralelizacija\\kucaAprox.png"));
		} catch (IOException e) {
			
		}
		
		spremiParametreUFile(this.fileOutputPath, bestSol);
	}	
	
	
	private void spremiParametreUFile(String fileOutputPath, Solution bestSol) {
		BufferedWriter fileOutputBuffered = null;
		
		try {
			FileWriter fileOutput = new FileWriter(fileOutputPath, false);

			fileOutputBuffered = new BufferedWriter(fileOutput);

			for (int front : bestSol.data) {
				fileOutputBuffered.write(front + "\n");
			}
		} catch (IOException ignorable) {
		} finally {
			if (fileOutputBuffered != null) {
				try {
					fileOutputBuffered.close();
				} catch (IOException e) {
				}
			}
		}
	}


	private LinkedList<Solution> nextPopulation(LinkedList<Solution> currentPop){
		LinkedList<Solution> nextPop = new LinkedList<Solution>();
		int popSize =0;
		
		Solution firstParent;
		Solution secondParent;
		Solution child;
		Collections.sort(this.populacija);
		
		nextPop.add(this.populacija.get(0));
		nextPop.add(this.populacija.get(1));
		popSize = 2;
		while(popSize<this.velicinaPopulacije){
			firstParent = selection.findParent(currentPop);
			secondParent = selection.findParent(currentPop);
			child = this.crossover(firstParent, secondParent);
			this.mutate(child);
			nextPop.add(child);
			popSize++;

		}
		return nextPop;
	}
	
	
	public Solution crossover(Solution p1, Solution p2){
		int point = rand.nextInt(0,p1.getNumberOfRectangles());
		GASolution<int[]> child = p1.duplicate();
		child.fitness = 0;
		 
		        for(int i = point; i<p2.getNumberOfRectangles(); i++){
		        	child.data[i*5+1] = p2.data[i*5+1];
		        	child.data[i*5+2] = p2.data[i*5+2];
		        	child.data[i*5+3] = p2.data[i*5+3];
		        	child.data[i*5+4] = p2.data[i*5+4];
		        	child.data[i*5+5] = p2.data[i*5+5];
		 
		        }
		        
		        return new Solution(child.data, child.fitness);
	}
		 
		 
		 
		public void mutate(Solution sol){
		        int offset = Constants.offset;
		        for (int i = 0; i < sol.getNumberOfRectangles(); i++) {
		            int randInt = rand.nextInt(0,10);
		            if(randInt==0){
		                sol.data[i*5+3]+=offset;
		                if(sol.data[i*5+3]>70){
		                    sol.data[i*5+3]=70;
		                }
		            }else if(randInt==1){
		                sol.data[i*5+3]-=offset;
		                if(sol.data[i*5+3]<0){
		                    sol.data[i*5+3]=0;
		                }
		            }else if(randInt==2){
		                sol.data[i*5+4]+=offset;
		                if(sol.data[i*5+4]>45){
		                    sol.data[i*5+4]=45;
		                }
		 
		            }else if(randInt==3){
		                sol.data[i*5+4]-=offset;
		                if(sol.data[i*5+4]<0){
		                    sol.data[i*5+4]=0;
		                }
		 
		            }else if(randInt==4){
		                sol.data[i*5+1]-=offset;
		                if(sol.data[i*5+1]<0){
		                    sol.data[i*5+1]=0;
		                }
		 
		            }
		            else if(randInt==5){
		                sol.data[i*5+1]+=offset;
		                if(sol.data[i*5+1]>200){
		                    sol.data[i*5+1]=200;
		                }
		 
		            }
		            else if(randInt==6){
		                sol.data[i*5+2]-=offset;
		                if(sol.data[i*5+2]<0){
		                    sol.data[i*5+2]=0;
		                }
		 
		            }
		            else if(randInt==7){
		                sol.data[i*5+2]+=offset;
		                if(sol.data[i*5+2]>133){
		                    sol.data[i*5+2]=133;
		                }
		 
		            } else if(randInt==8){
		                sol.data[i*5+5]+=offset;
		                if(sol.data[i*5+5]>256){
		                    sol.data[i*5+5]=256;
		                }
		 
		            }else if(randInt==9){
		                sol.data[i*5+5]-=offset;
		                if(sol.data[i*5+5]<0){
		                    sol.data[i*5+5]=0;
		                }
		 
		            }
		        }
		}
	
}
