package hr.fer.zemris.optjava.dz11.generic.ga;

public interface IEvaluatorProvider {
	/**
	 * Metoda za dohvat generatora slučajnih brojeva koji pripada
	 * ovom objektu.
	 *
	 * @return generator slučajnih brojeva
	 */
	public Evaluator getEvaluator();
}
