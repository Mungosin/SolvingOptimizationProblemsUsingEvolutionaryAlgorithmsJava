package hr.fer.zemris.optjava.dz11.generic.ga;

public interface IEvaluatorProvider {
	/**
	 * Metoda za dohvat generatora sluèajnih brojeva koji pripada
	 * ovom objektu.
	 *
	 * @return generator sluèajnih brojeva
	 */
	public Evaluator getEvaluator();
}
