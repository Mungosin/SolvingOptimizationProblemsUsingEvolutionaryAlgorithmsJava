package hr.fer.zemris.optjava.dz13.solution;

import java.util.LinkedList;

public class Cvor {
	public int dubina;
	public int djeceIspodMene;
	public Vrijednost vrijednost;
	public int maxDjece;
	public LinkedList<Cvor> listaDjece;
	
	private int maxDubina(Cvor trenutni){
		if(trenutni.listaDjece == null || trenutni.listaDjece.isEmpty()){
			return 1;
		}
		int maxDubina = 0;
		for(Cvor temp : trenutni.listaDjece){
			int dubina = temp.maxDubina(temp);
			if(maxDubina < dubina){
				maxDubina = dubina;
			}
		}
		return maxDubina+1;
		
	}
	
	public int dubinaStabla(){
		return maxDubina(this);
	}
	
	public int azurirajBrojDjece() {

		djeceIspodMene = 0;
		if(listaDjece == null || listaDjece.isEmpty()){
			djeceIspodMene = 0;
			return 1;
		}else {
			for(Cvor temp : listaDjece){
				djeceIspodMene += temp.azurirajBrojDjece();
			}	
		}
		if(maxDjece != listaDjece.size()){
			throw new IllegalArgumentException("Doslo je do pogreske pri izgradnji stabla broj djece nije jednak potrebnom" + vrijednost + " mora imati " + maxDjece + 
					" a ima " + listaDjece.size() + " " + this.dubina + " " + this.vrijednost);
		}
		return djeceIspodMene;
	}
	
	public void azurirajDubinu(int novaDubina){
		this.dubina = novaDubina;
		for(Cvor temp : listaDjece){
			temp.azurirajDubinu(novaDubina+1);
		}
	}

	public Cvor copyOf() {
		Cvor novi = new Cvor();
		novi.maxDjece = this.maxDjece;
		novi.dubina = this.dubina;
		novi.vrijednost = this.vrijednost;
		novi.djeceIspodMene = this.djeceIspodMene;
		novi.listaDjece = new LinkedList<Cvor>();
		if(this.listaDjece == null || this.listaDjece.isEmpty()){
			return novi;
		}
		
		for(Cvor temp : this.listaDjece){
			novi.listaDjece.add(temp.copyOf());
		}
		
		return novi;
	}
	
	public String ispisi(){
		StringBuilder builder = new StringBuilder();
		builder.append(this.vrijednost+ "(");
		for(int i=0;i<this.listaDjece.size();i++){
			Cvor bla = this.listaDjece.get(i);
			if(i== this.listaDjece.size()-1){
				builder.append(bla.ispisi());
			}else {
				builder.append(bla.ispisi()+", ");
			}
			
		}
		builder.append(")");
		return builder.toString();
	}
	
	public String toString(){
		return this.ispisi();
	}
	
}
