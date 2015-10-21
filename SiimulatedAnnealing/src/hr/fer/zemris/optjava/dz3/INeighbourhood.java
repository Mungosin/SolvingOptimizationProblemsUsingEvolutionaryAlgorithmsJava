package hr.fer.zemris.optjava.dz3;

public interface INeighbourhood<T extends SingleObjectiveSolution> {
	T randomNeighbour(T object);
}
