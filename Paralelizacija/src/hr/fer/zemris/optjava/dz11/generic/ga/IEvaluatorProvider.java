package hr.fer.zemris.optjava.dz11.generic.ga;

public interface IEvaluatorProvider {
	/**
	 * Metoda za dohvat generatora slu�ajnih brojeva koji pripada
	 * ovom objektu.
	 *
	 * @return generator slu�ajnih brojeva
	 */
	public Evaluator getEvaluator();
}
