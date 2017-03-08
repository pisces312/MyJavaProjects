package graph.test;

import graph.Graph;
import graph.IPathVertex;
import graph.adjacencylist.AdjacencyListGraph;
import graph.adjacencylist.CPLVertex;
import graph.algorithm.BreathFirstSearch;
import graph.algorithm.GraphAlgorithm;
import graph.algorithm.GraphAlgorithm.TraverseFunc;

import java.util.Collection;
import java.util.List;

public class TestBFS {
	// ////////////////////////////////////////////////////////////////////////
	private static void testUnreachableTravsverse() {
		Graph<CPLVertex> g = new AdjacencyListGraph<CPLVertex>(false, false);
		g.addVertexes(CPLVertex.class, "t");
		g.addVertexes(CPLVertex.class, "u");
		g.addEdge(CPLVertex.class, "r", "v");
		g.addEdge(CPLVertex.class, "r", "s");
		g.addEdge(CPLVertex.class, "w", "s");
		BreathFirstSearch.bfsForAllNodes(g, g.getNodeByName("r"),
				(TraverseFunc<CPLVertex>) TestGraph.defaultTraverseFunc);
	}

	private static void testTravserse() {
		AdjacencyListGraph<CPLVertex> g = new AdjacencyListGraph<CPLVertex>(
				false, false);
		TestFigures.figure22_3_undirected(g, CPLVertex.class);
		g.print();
		Collection<CPLVertex> nodes = g.getVertexes();
		// ///////////////////////////////////////////////////
		// bfsUsingTwoList(g, nodes[0]);
		// System.out.println(GraphAlgorithm.isBipartiteGraph(g));
		// bfsUsingArray(g, nodes[0]);
		// breadthFirstSearch(g, nodes[0], defaultTraverseFunc);
		// System.out.println();
		CPLVertex s = g.getNodeByName("s");
		BreathFirstSearch
				.bfsReachableNodesBy3ColorForLearning(g, s, null, null);
		System.out.println();
		for (CPLVertex n : nodes) {
			System.out.print(s.getName() + "->" + n.getName() + ": ");
			List<IPathVertex> path = GraphAlgorithm.constructPath(g, s, n);
			for (IPathVertex p : path) {
				System.out.print(p.getName() + " ");
			}
			System.out.println();
		}
		// breadthFirstSearchUsingColor(g, nodes[0], defaultTraverseFunc);
		// GraphAlgorithm.printAllShortestPath(g);

		// GraphAlgorithm.dfsRecursive(g, nodes[0]);
		// GraphAlgorithm.dfsUsingStack(g, nodes[0]);

		//
		// search.bfs(g.getVertexes()[0]);
		// System.out.println();
		// search.dfsDriver(g.getVertexes()[0]);
		// System.out.println();
		// search.dfsUsingStack(g.getVertexes()[0]);
		// System.out.println();

	}

	public static void main(String[] args) {
		testTravserse();
		testUnreachableTravsverse();
	}
}
