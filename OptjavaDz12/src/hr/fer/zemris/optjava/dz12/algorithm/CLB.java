package hr.fer.zemris.optjava.dz12.algorithm;

import hr.fer.zemris.optjava.dz12.rng.IRNG;
import hr.fer.zemris.optjava.dz12.rng.RNG;

public class CLB {
	public int brojUlaznihVarijabli;
	public int brojIzlaznihVrijednosti;
	public int[] ulaziKod;
	public boolean[] izlazneVrijednosti;
	public IRNG rand = RNG.getRNG();
	public int redniBroj;
	
	public CLB(int brojUlaznihVarijabli, int redniBroj){
		this.brojUlaznihVarijabli = brojUlaznihVarijabli;
		this.brojIzlaznihVrijednosti = (int) Math.pow(2, brojUlaznihVarijabli);
		this.izlazneVrijednosti = new boolean[brojIzlaznihVrijednosti];
		this.ulaziKod = new int[this.brojUlaznihVarijabli];
		this.redniBroj = redniBroj;
		randomIzlazi();
		randomUlazi();
	}

	public CLB duplicate(){
		CLB duplikat = new CLB(this.brojUlaznihVarijabli ,this.redniBroj);
		
		for(int i=0;i<this.brojUlaznihVarijabli;i++){
			duplikat.ulaziKod[i] = this.ulaziKod[i];
		}
		
		for(int i=0;i<this.brojIzlaznihVrijednosti;i++){
			duplikat.izlazneVrijednosti[i] = this.izlazneVrijednosti[i];
		}
		
		return duplikat;
	}
	
	public boolean evaluate(int[] ulazi){
		int length = ulazi.length-1;
		int pozicijaIzlaza = 0;
		for(int temp : ulazi){
			if(temp != 1){
				length--;
				continue;
			}else {
			
				pozicijaIzlaza += (int) Math.pow(2, length);
				length--;
		
			}
		}
		return this.izlazneVrijednosti[pozicijaIzlaza];
	}
	
	private void randomUlazi() {
		for(int i=0;i<this.brojUlaznihVarijabli;i++){
			this.ulaziKod[i] = rand.nextInt(0, redniBroj);
		}
	}

	private void randomIzlazi() {
		for(int i=0;i<this.brojIzlaznihVrijednosti;i++){
			this.izlazneVrijednosti[i] = rand.nextBoolean();
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Vrijednosti u CLB-ovoj lookup tablici su sljedeæe: ");
		for(Boolean temp : this.izlazneVrijednosti){
			sb.append("\n"+temp);
		}
		return sb.toString();
	}
	
	
}
