package hr.fer.zemris.optjava.dz11.rng;
/**
 * Su�elje koje predstavlja objekte koji sadr�e generator slu�ajnih
 * brojeva i koji ga stavljaju drugima na raspolaganje uporabom
 * metode {@link #getRNG()}. Objekti koji implementiraju ovo su�elje
 * ne smiju na svaki poziv metode {@link #getRNG()} stvarati i vra�ati
 * novi generator ve� moraju imati ili svoj vlastiti generator koji vra�aju,
 * ili pristup do kolekcije postoje�ih generatora iz koje dohva�aju i vra�aju
 * jedan takav generator (u skladu s pravilima konkretne implementacije ovog
 * su�elja) ili isti stvaraju na zahtjev i potom �uvaju u cache-u za istog
 * pozivatelja.
 *
 */
public interface IRNGProvider {
	/**
	 * Metoda za dohvat generatora slu�ajnih brojeva koji pripada
	 * ovom objektu.
	 *
	 * @return generator slu�ajnih brojeva
	 */
	public IRNG getRNG();
}
