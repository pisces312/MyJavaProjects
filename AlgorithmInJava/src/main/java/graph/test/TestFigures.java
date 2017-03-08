package graph.test;

import graph.Graph;
import graph.IVertex;

public class TestFigures {

	/**
	 * the same graph, but different adjaceny list order
	 * 
	 * @param g
	 * @param c
	 */
	public static final <T extends IVertex> void figure22_3_2_undirected(
			Graph<T> g, Class<T> c) {
		if (g.isDirectedGraph()) {
			return;
		}
		g.clear();
		g.addEdge(c, "r", "v");
		g.addEdge(c, "r", "s");
		g.addEdge(c, "w", "s");
		// it will affect the w order
		g.addEdge(c, "w", "x");
		g.addEdge(c, "w", "t");
		//
		g.addEdge(c, "x", "t");
		g.addEdge(c, "t", "u");
		g.addEdge(c, "x", "y");
		g.addEdge(c, "y", "u");
		g.addEdge(c, "x", "u");
	}

	// p596
	public static final <T extends IVertex> void figure22_3_undirected(
			Graph<T> g, Class<T> c) {
		if (g.isDirectedGraph()) {
			return;
		}
		g.clear();
		g.addEdge(c, "r", "v");
		g.addEdge(c, "r", "s");
		g.addEdge(c, "w", "s");
		g.addEdge(c, "w", "t");
		g.addEdge(c, "w", "x");
		g.addEdge(c, "x", "t");
		g.addEdge(c, "t", "u");
		g.addEdge(c, "x", "y");
		g.addEdge(c, "y", "u");
		g.addEdge(c, "x", "u");
	}

	/**
	 * p590
	 * 
	 * @param g
	 * @param c
	 * @return
	 */
	public static <T extends IVertex> Graph<T> figure22_2a(Graph<T> g,
			Class<T> c) {
		g.clear();
		g.addEdge(c, "1", "2");
		g.addEdge(c, "1", "4");
		g.addEdge(c, "2", "5");
		g.addEdge(c, "3", "5");
		// g.print();
		g.addEdge(c, "3", "6");
		g.addEdge(c, "4", "2");

		g.addEdge(c, "5", "4");
		g.addEdge(c, "6", "6");
		return g;
	}

	/**
	 * p605
	 * 
	 * @param g
	 * @param c
	 * @return
	 */
	public static <T extends IVertex> Graph<T> figure22_4_directed(Graph<T> g,
			Class<T> c) {
		if (!g.isDirectedGraph()) {
			return null;
		}
		g.clear();
		g.addEdge(c, "u", "v");
		g.addEdge(c, "u", "x");
		g.addEdge(c, "v", "y");
		g.addEdge(c, "w", "y");
		g.addEdge(c, "w", "z");
		g.addEdge(c, "x", "v");
		g.addEdge(c, "y", "x");
		g.addEdge(c, "z", "z");
		return g;
	}

	public static <T extends IVertex> Graph<T> figure22_5_directed(Graph<T> g,
			Class<T> c) {
		if (!g.isDirectedGraph()) {
			return null;
		}
		g.clear();
		g.addEdge(c, "s", "w");
		g.addEdge(c, "s", "z");
		g.addEdge(c, "t", "u");
		g.addEdge(c, "t", "v");
		g.addEdge(c, "u", "t");
		g.addEdge(c, "u", "v");
		g.addEdge(c, "v", "s");
		g.addEdge(c, "v", "w");
		g.addEdge(c, "w", "x");
		g.addEdge(c, "x", "z");
		g.addEdge(c, "y", "x");
		g.addEdge(c, "z", "y");
		g.addEdge(c, "z", "w");
		return g;

	}

	public static <T extends IVertex> Graph<T> figure22_6_directed_sorted(
			Graph<T> g, Class<T> c) {
		if (!g.isDirectedGraph()) {
			return null;
		}
		g.clear();
		g.addEdge(c, "q", "s");
		g.addEdge(c, "q", "t");
		g.addEdge(c, "q", "w");
		g.addEdge(c, "r", "u");
		g.addEdge(c, "r", "y");
		g.addEdge(c, "s", "v");
		g.addEdge(c, "t", "x");
		g.addEdge(c, "t", "y");
		g.addEdge(c, "u", "y");
		g.addEdge(c, "v", "w");
		g.addEdge(c, "w", "s");
		g.addEdge(c, "x", "z");
		g.addEdge(c, "y", "q");
		g.addEdge(c, "z", "x");
		return g;

	}

	public static <T extends IVertex> Graph<T> figure22_7_dag(Graph<T> g,
			Class<T> c) {
		if (!g.isDirectedGraph()) {
			return null;
		}
		g.clear();
		g.addEdge(c, "undershorts", "shoes");
		g.addEdge(c, "undershorts", "pants");
		g.addEdge(c, "pants", "shoes");
		g.addEdge(c, "pants", "belt");
		g.addEdge(c, "belt", "jacket");
		g.addEdge(c, "shirt", "belt");
		g.addEdge(c, "shirt", "tie");
		g.addEdge(c, "tie", "jacket");
		g.addEdge(c, "socks", "shoes");
		g.addVertexes(c, "watch");
		return g;

	}

	public static <T extends IVertex> Graph<T> figure22_8_dag_alphabetically(
			Graph<T> g, Class<T> c) {
		if (g == null || !g.isDirectedGraph() || c == null) {
			return null;
		}
		g.clear();

		g.addEdge(c, "m", "q");
		g.addEdge(c, "m", "r");
		g.addEdge(c, "m", "x");

		g.addEdge(c, "n", "o");
		g.addEdge(c, "n", "q");
		g.addEdge(c, "n", "u");

		g.addEdge(c, "o", "r");
		g.addEdge(c, "o", "s");
		g.addEdge(c, "o", "v");

		g.addEdge(c, "p", "o");
		g.addEdge(c, "p", "s");
		g.addEdge(c, "p", "z");

		g.addEdge(c, "q", "t");

		g.addEdge(c, "r", "u");
		g.addEdge(c, "r", "y");

		g.addEdge(c, "s", "r");
		g.addEdge(c, "u", "t");
		g.addEdge(c, "v", "w");
		g.addEdge(c, "v", "x");
		g.addEdge(c, "w", "z");
		g.addEdge(c, "y", "v");
		return g;

	}

	public static <T extends IVertex> Graph<T> figure22_9_dag(Graph<T> g,
			Class<T> c) {
		if (g == null || !g.isDirectedGraph() || c == null) {
			return null;
		}
		g.clear();

		g.addEdge(c, "a", "b");
		g.addEdge(c, "b", "c");
		g.addEdge(c, "b", "e");

		g.addEdge(c, "b", "f");
		g.addEdge(c, "c", "d");
		g.addEdge(c, "c", "g");

		g.addEdge(c, "d", "c");
		g.addEdge(c, "d", "h");
		g.addEdge(c, "e", "a");

		g.addEdge(c, "e", "f");
		g.addEdge(c, "f", "g");
		g.addEdge(c, "g", "f");

		g.addEdge(c, "g", "h");

		g.addEdge(c, "h", "h");
		return g;
	}
}
