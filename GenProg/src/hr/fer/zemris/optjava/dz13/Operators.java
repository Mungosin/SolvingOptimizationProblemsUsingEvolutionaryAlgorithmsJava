package hr.fer.zemris.optjava.dz13;

import hr.fer.zemris.optjava.dz13.solution.Cvor;
import hr.fer.zemris.optjava.dz13.solution.Solution;
import hr.fer.zemris.optjava.dz13.solution.Vrijednost;

import java.util.LinkedList;
import java.util.Random;






public class Operators {
	Random rand = new Random();
	Tournament tournament = new Tournament(Constants.tournament, rand);
	

	public Cvor izgradiStablo(boolean full, int maxDubina){
		Cvor prviCvor;
		if(maxDubina == 1){
			prviCvor = CvorSTerminalnim(1);
		}else {
			prviCvor = CvorSFunkcijom(1);
		}	
		Cvor zavrsenoStablo;
		if(full){
			zavrsenoStablo = Full(prviCvor, maxDubina);
			zavrsenoStablo.azurirajBrojDjece();
			zavrsenoStablo.azurirajDubinu(1);
			return zavrsenoStablo;
		}else {
			zavrsenoStablo = Grow(prviCvor, maxDubina);
			zavrsenoStablo.azurirajBrojDjece();
			zavrsenoStablo.azurirajDubinu(1);
			return zavrsenoStablo;
		}
		
		
	}


	private Cvor Full(Cvor trenutni, int maxDubina) {
		if(trenutni.dubina == maxDubina){
			return trenutni;
		}
		
		if(trenutni.maxDjece >0){
			
			if(trenutni.dubina == maxDubina-1){
				for(int i=0;i<trenutni.maxDjece;i++){
					Cvor novi = CvorSTerminalnim(trenutni.dubina+1);
					trenutni.listaDjece.add(novi);
					Full(novi, maxDubina);
				}
			}else {
				for(int i=0;i<trenutni.maxDjece;i++){
					Cvor novi = CvorSFunkcijom(trenutni.dubina+1);
					trenutni.listaDjece.add(novi);
					Full(novi, maxDubina);
				}
			}
		}
		return trenutni;
	}


	private Cvor Grow(Cvor trenutni, int maxDubina) {
		if(trenutni.dubina == maxDubina){
			return trenutni;
		}
		if(trenutni.maxDjece >0){
			if(trenutni.dubina != maxDubina-1){
				for(int i=0;i<trenutni.maxDjece;i++){
					if(rand.nextBoolean()){
						Cvor novi = CvorSFunkcijom(trenutni.dubina+1);
						trenutni.listaDjece.add(novi);
						Grow(novi, maxDubina);
					}else {
						Cvor novi = CvorSTerminalnim(trenutni.dubina+1);
						trenutni.listaDjece.add(novi);
						Grow(novi, maxDubina);
					}
				}
			}else {
				for(int i=0;i<trenutni.maxDjece;i++){
					Cvor novi = CvorSTerminalnim(trenutni.dubina+1);
					trenutni.listaDjece.add(novi);
					Grow(novi, maxDubina);
				}
			}
		}
		
		return trenutni;
	}
	
	private Cvor CvorSFunkcijom(int dubina){
		Cvor prviCvor = new Cvor();
		prviCvor.djeceIspodMene = 0;
		prviCvor.dubina = dubina;
		prviCvor.listaDjece = new LinkedList<Cvor>();
		switch(rand.nextInt(3)){
		case 0:
			prviCvor.vrijednost = Vrijednost.IfFoodAhead;
			prviCvor.maxDjece = 2;
			break;
		case 1:
			prviCvor.vrijednost = Vrijednost.Prog2;
			prviCvor.maxDjece = 2;
			break;
		case 2:
			prviCvor.vrijednost = Vrijednost.Prog3;
			prviCvor.maxDjece = 3;
			break;
		}
		return prviCvor;
	}	
	
	private Cvor CvorSTerminalnim(int dubina){
		Cvor prviCvor = new Cvor();
		prviCvor.djeceIspodMene = 0;
		prviCvor.dubina = dubina;
		prviCvor.listaDjece = new LinkedList<Cvor>();
		prviCvor.maxDjece = 0;
		switch(rand.nextInt(3)){
		case 0:
			prviCvor.vrijednost = Vrijednost.Left;
			break;
		case 1:
			prviCvor.vrijednost = Vrijednost.Right;
			break;
		case 2:
			prviCvor.vrijednost = Vrijednost.Move;
			break;
		}
		return prviCvor;
	}
	
	
	
	
	public Solution reprodukcija(Solution sol){
		return new Solution(sol);
	}
	
	
	
	public Solution[] crossover(Solution p1, Solution p2){
		Solution[] sol = new Solution[2];
		Solution copyP1 = new Solution(p1);
		Solution copyP2 = new Solution(p2);
		boolean uspjesno = true;
		
		int mijenjamP1 =  rand.nextInt(copyP1.root.djeceIspodMene);
		int mijenjamP2 =  rand.nextInt(copyP2.root.djeceIspodMene);
		
		Cvor cvorP1 = copyP1.vratiCvorSBrojem(copyP1.root, mijenjamP1, 0);
		Cvor cvorP2 = copyP2.vratiCvorSBrojem(copyP2.root, mijenjamP2, 0);
		
		copyP1.zamijeniCvor(mijenjamP1, cvorP2);
		copyP2.zamijeniCvor(mijenjamP2, cvorP1);
		
		if(copyP1.root.dubinaStabla()>copyP1.maxDubina || copyP2.root.dubinaStabla()>copyP2.maxDubina){
			uspjesno=false;
		}else{
			copyP1.root.azurirajBrojDjece();
			copyP2.root.azurirajBrojDjece();
		}

		if(uspjesno && copyP1.root.djeceIspodMene >= Constants.maxCvorova || copyP2.root.djeceIspodMene >= Constants.maxCvorova){ //vece il jednako jer gleda djecu ispod roota a ne broji root
			uspjesno=false;
		}
		
		if(uspjesno){
			copyP1.evaluate();
			copyP2.evaluate();
			copyP1.root.azurirajBrojDjece();
		    copyP1.root.azurirajDubinu(1);
			copyP2.root.azurirajBrojDjece();
		    copyP2.root.azurirajDubinu(1);
		    if(copyP2.fitness == p1.fitness){
		    	copyP2.fitness = Constants.scale * copyP1.fitness;
		    }
		    if(copyP2.fitness == p2.fitness){
		    	copyP2.fitness = Constants.scale * copyP2.fitness;
		    }
		    
			sol[0] = copyP1;
			sol[1] = copyP2;
		}else {

			Solution p1copy = new Solution(p1);
			Solution p2copy = new Solution(p2);
			p1copy.fitness = p1.fitness * Constants.scale;
			p2copy.fitness = p2.fitness * Constants.scale;
			sol[0] = p1copy;
			sol[1] = p2copy;
		}
		
		return sol; 
		
	}
		 
		 
		 
	public Solution mutate(Solution sol){
	       Solution copySol = new Solution(sol);
	       int zamijeni = rand.nextInt(copySol.root.djeceIspodMene);

	  
	       Cvor zamijenjeni = copySol.vratiCvorSBrojem(copySol.root, zamijeni, 0);
	       int dubina = copySol.maxDubina - zamijenjeni.dubinaStabla();
	       
	       if(dubina == 0 || dubina == 1){
	    	   Solution sol1 = new Solution(sol);
	    	   sol1.fitness = Constants.scale * sol1.fitness;
	    	   return sol1;
	       }
	       
	       Cvor growStablo = this.izgradiStablo(false, rand.nextInt(dubina)+1);
	       copySol.zamijeniCvor(zamijeni, growStablo, zamijenjeni);
	       
	       
	       copySol.root.azurirajBrojDjece();
	       copySol.root.azurirajDubinu(1);
	       
	       if(copySol.root.dubinaStabla()> copySol.maxDubina){
	    	   Solution sol1 = new Solution(sol);
	    	   sol1.fitness = Constants.scale * sol1.fitness;
	    	   return sol1;
	       }
	       copySol.evaluate();
	       
			if(copySol.fitness == sol.fitness){
				copySol.fitness = Constants.scale * copySol.fitness;
			}
	       return copySol;
	}

	public Solution findParent(
			LinkedList<Solution> currentSolutions, int numberOfContestants) {
		return this.tournament.findParent(currentSolutions);
	}
}
