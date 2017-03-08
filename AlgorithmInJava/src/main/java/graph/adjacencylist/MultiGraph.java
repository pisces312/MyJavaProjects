package graph.adjacencylist;

public class MultiGraph extends AdjacencyListGraph<LVertex> {

	public MultiGraph(boolean isDirected, boolean isWeighted) {
		super(isDirected, isWeighted, true);
	}

}
