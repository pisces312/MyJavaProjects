package graph.test;

import graph.Edge;
import graph.adjacencylist.AdjacencyListGraph;
import graph.adjacencylist.CCPLVertex;
import graph.adjacencylist.CPLVertex;
import graph.adjacencylist.TCPLVertex;
import graph.algorithm.DepthFirstSearch;
import graph.algorithm.GraphAlgorithm;
import graph.algorithm.GraphAlgorithm.TraverseFunc;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;

public class TestDFS {
	// ///////////////////////////////////////////////////////////////////////////////////////

	public static <T extends Collection<V>, V> void test(T a) {

	}

	private static void testDFS() {
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		TestFigures.figure22_2a(g, TCPLVertex.class);
		g.print();
		System.out.println();

		DepthFirstSearch.dfsBy3Color(g,
				(TraverseFunc<TCPLVertex>) TestGraph.defaultTraverseFunc);
		//
		//
		TestFigures.figure22_5_directed(g, TCPLVertex.class);
		g.print();
		System.out.println();
		DepthFirstSearch.dfsBy3Color(g,
				(TraverseFunc<TCPLVertex>) TestGraph.defaultTraverseFunc);
		//
		//
		TestFigures.figure22_4_directed(g, TCPLVertex.class);
		g.print();
		System.out.println();
		DepthFirstSearch.dfsBy3Color(g,
				(TraverseFunc<TCPLVertex>) TestGraph.defaultTraverseFunc);
		boolean flag = g.getNodeByName("x")
				.isDescendantOf(g.getNodeByName("y"));
		System.out.println(flag);
		Assert.assertTrue(flag);
		//
		flag = g.getNodeByName("x").isDescendantOf(g.getNodeByName("z"));
		System.out.println(flag);
		Assert.assertTrue(!flag);
	}

	private static void testEdgeCount() {
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		TestFigures.figure22_2a(g, TCPLVertex.class);
		g.print();
		System.out.println();
		DepthFirstSearch.dfsBy3Color(g,
				(TraverseFunc<TCPLVertex>) TestGraph.defaultTraverseFunc);
		// for (Edge<TCPLVertex> e : list) {
		// System.out.println(e);
		// }
	}

	private static void ex22_3_2() {
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		TestFigures.figure22_6_directed_sorted(g, TCPLVertex.class);

		System.out.println();
		// dfsUsingStackWithTime(g, list, (TraverseFunc<TCPLVertex>)
		// TestGraph.defaultTraverseFunc);
		DepthFirstSearch.dfsBy3Color(g,
				(TraverseFunc<TCPLVertex>) TestGraph.defaultTraverseFunc);
		g.print();
		// for (Edge<TCPLVertex> e : list) {
		// System.out.println(e);
		// }
		Collection<Edge<TCPLVertex>> list = g.getEdges();
		LinkedList<Edge<TCPLVertex>> treeEdges = new LinkedList<Edge<TCPLVertex>>();
		LinkedList<Edge<TCPLVertex>> backEdges = new LinkedList<Edge<TCPLVertex>>();
		LinkedList<Edge<TCPLVertex>> fwEdges = new LinkedList<Edge<TCPLVertex>>();
		LinkedList<Edge<TCPLVertex>> crossEdges = new LinkedList<Edge<TCPLVertex>>();
		for (Edge<TCPLVertex> e : list) {
			switch (e.type) {
			case TREE_EDGE:
				treeEdges.add(e);
				break;
			case BACK_EDGE:
				backEdges.add(e);
				break;
			case FORWARD_EDGE:
				fwEdges.add(e);
				break;
			case CROSS_EDGE:
				crossEdges.add(e);
				break;
			}
		}
		LinkedList<Edge<TCPLVertex>> expectedtreeEdges = new LinkedList<Edge<TCPLVertex>>();
		LinkedList<Edge<TCPLVertex>> expectedbackEdges = new LinkedList<Edge<TCPLVertex>>();
		LinkedList<Edge<TCPLVertex>> expectedfwEdges = new LinkedList<Edge<TCPLVertex>>();
		LinkedList<Edge<TCPLVertex>> expectedcrossEdges = new LinkedList<Edge<TCPLVertex>>();
		expectedtreeEdges.add(new Edge<TCPLVertex>(g.getNodeByName("q"), g
				.getNodeByName("s"), null, g.isDirectedGraph()));
		expectedtreeEdges.add(new Edge<TCPLVertex>(g.getNodeByName("s"), g
				.getNodeByName("v"), null, g.isDirectedGraph()));
		expectedtreeEdges.add(new Edge<TCPLVertex>(g.getNodeByName("v"), g
				.getNodeByName("w"), null, g.isDirectedGraph()));
		expectedtreeEdges.add(new Edge<TCPLVertex>(g.getNodeByName("q"), g
				.getNodeByName("t"), null, g.isDirectedGraph()));
		expectedtreeEdges.add(new Edge<TCPLVertex>(g.getNodeByName("t"), g
				.getNodeByName("x"), null, g.isDirectedGraph()));
		expectedtreeEdges.add(new Edge<TCPLVertex>(g.getNodeByName("x"), g
				.getNodeByName("z"), null, g.isDirectedGraph()));
		expectedtreeEdges.add(new Edge<TCPLVertex>(g.getNodeByName("t"), g
				.getNodeByName("y"), null, g.isDirectedGraph()));
		expectedtreeEdges.add(new Edge<TCPLVertex>(g.getNodeByName("r"), g
				.getNodeByName("u"), null, g.isDirectedGraph()));
		//
		expectedbackEdges.add(new Edge<TCPLVertex>(g.getNodeByName("w"), g
				.getNodeByName("s"), null, g.isDirectedGraph()));
		expectedbackEdges.add(new Edge<TCPLVertex>(g.getNodeByName("z"), g
				.getNodeByName("x"), null, g.isDirectedGraph()));
		expectedbackEdges.add(new Edge<TCPLVertex>(g.getNodeByName("y"), g
				.getNodeByName("q"), null, g.isDirectedGraph()));
		//
		expectedfwEdges.add(new Edge<TCPLVertex>(g.getNodeByName("q"), g
				.getNodeByName("w"), null, g.isDirectedGraph()));
		//
		expectedcrossEdges.add(new Edge<TCPLVertex>(g.getNodeByName("u"), g
				.getNodeByName("y"), null, g.isDirectedGraph()));
		expectedcrossEdges.add(new Edge<TCPLVertex>(g.getNodeByName("r"), g
				.getNodeByName("y"), null, g.isDirectedGraph()));
		TestGraph.assertGraphEdges(treeEdges, expectedtreeEdges);
		TestGraph.assertGraphEdges(crossEdges, expectedcrossEdges);
		TestGraph.assertGraphEdges(fwEdges, expectedfwEdges);
		TestGraph.assertGraphEdges(backEdges, expectedbackEdges);
	}

	private static void ex22_3_3() {
		// LinkedList<Edge<TCPLVertex>> list = new
		// LinkedList<Edge<TCPLVertex>>();
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		TestFigures.figure22_4_directed(g, TCPLVertex.class);

		String result = DepthFirstSearch.createParenthesisStructure2(g);
		// String result = createParenthesisStructure(g);
		System.out.println(result);
		g.print();
	}

	private static void ex22_3_4() {
		// LinkedList<Edge<TCPLVertex>> list = new
		// LinkedList<Edge<TCPLVertex>>();
		AdjacencyListGraph<CPLVertex> g = new AdjacencyListGraph<CPLVertex>(
				true, false);
		TestFigures.figure22_4_directed(g, CPLVertex.class);

		g.print();
		System.out.println();
		DepthFirstSearch.dfsBy2ColorWithoutTime(g,
				(TraverseFunc<CPLVertex>) TestGraph.defaultTraverseFunc);
	}

	/**
	 * <u, d:0>
	 * 
	 * <v, d:0, pre:u>
	 * 
	 * <y, d:0, pre:v>
	 * 
	 * <x, d:0, pre:y>
	 * 
	 * <w, d:0>
	 * 
	 * <z, d:0, pre:w>
	 */
	private static void ex22_3_6() {
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		TestFigures.figure22_4_directed(g, TCPLVertex.class);

		g.print();
		System.out.println();
		TraverseFunc<TCPLVertex> p = (TraverseFunc<TCPLVertex>) TestGraph.defaultTraverseFunc;
		DepthFirstSearch.dfsUsingStackBy2Color(g, p);
		DepthFirstSearch.dfsUsingStackWithTime(g, p);
	}

	private static void ex22_3_8And9() {
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		g.addEdge(TCPLVertex.class, "s", "u");
		g.addEdge(TCPLVertex.class, "u", "s");
		g.addEdge(TCPLVertex.class, "s", "v");
		g.print();
		System.out.println();
		TraverseFunc<TCPLVertex> p = (TraverseFunc<TCPLVertex>) TestGraph.defaultTraverseFunc;
		DepthFirstSearch.dfsUsingStackWithTime(g, p);
		Assert.assertTrue(g.getNodeByName("u").getDiscoveredTime() < g
				.getNodeByName("v").getDiscoveredTime());

		Assert.assertTrue(g.getNodeByName("v").getDiscoveredTime() > g
				.getNodeByName("u").getFinishedTime());
		Assert.assertFalse(g.isConnected(g.getNodeByName("u"),
				g.getNodeByName("v")));

	}

	private static void ex22_3_10() {
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		TestFigures.figure22_6_directed_sorted(g, TCPLVertex.class);
		g.print();
		System.out.println();
		DepthFirstSearch.dfsUsingStackWithTime(g,
				(TraverseFunc<TCPLVertex>) TestGraph.defaultTraverseFunc);
	}

	private static void ex22_3_11() {
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		g.addEdge(TCPLVertex.class, "u", "u");

		System.out.println();
		TraverseFunc<TCPLVertex> p = (TraverseFunc<TCPLVertex>) TestGraph.defaultTraverseFunc;
		DepthFirstSearch.dfsUsingStackWithTime(g, p);
		g.print();
	}

	private static void ex22_3_13() {
		boolean flag = true;
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		g.addEdge(TCPLVertex.class, "u", "s");
		g.addEdge(TCPLVertex.class, "s", "t");
		g.addEdge(TCPLVertex.class, "u", "t");
		flag = DepthFirstSearch.isSinglyConnected(g);
		g.print();
		System.out.println(flag);
		Assert.assertFalse(flag);
		//
		//
		g.clear();
		g.addEdge(TCPLVertex.class, "u", "s");
		g.addEdge(TCPLVertex.class, "s", "t");
		g.addEdge(TCPLVertex.class, "s", "u");
		g.addEdge(TCPLVertex.class, "t", "u");
		flag = DepthFirstSearch.isSinglyConnected(g);
		g.print();
		System.out.println(flag);
		Assert.assertFalse(flag);
		//
		//
		g.clear();
		g.addEdge(TCPLVertex.class, "u", "s");
		g.addEdge(TCPLVertex.class, "s", "u");
		g.addEdge(TCPLVertex.class, "s", "t");
		g.addEdge(TCPLVertex.class, "t", "u");
		flag = DepthFirstSearch.isSinglyConnected(g);
		g.print();
		System.out.println(flag);
		Assert.assertFalse(flag);

	}

	/**
	 * p -> n -> o -> s -> m -> r -> y -> v -> x -> w -> z -> u -> q -> t
	 * 
	 * p->v
	 * 
	 * pov
	 * 
	 * poryv
	 * 
	 * posryv
	 * 
	 * psryv
	 */
	private static void ex22_4_1() {
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		TestFigures.figure22_8_dag_alphabetically(g, TCPLVertex.class);
		g.print();
		List<TCPLVertex> list = null;
		try {
			list = GraphAlgorithm.topologicalSortByDFS(g);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list);
		boolean flag = GraphAlgorithm.isTopologicalSorted(g, list);
		System.out.println(flag);
		Assert.assertTrue(flag);
		String[] array = new String[] { "p", "n", "o", "s", "m", "r", "y", "v",
				"x", "w", "z", "u", "q", "t" };
		if (list != null)
			for (int i = 0; i < list.size(); ++i) {
				Assert.assertEquals(array[i], list.get(i).getName());
			}
		g.print();

	}

	private static void ex22_4_2() {
		AdjacencyListGraph<CCPLVertex> g = new AdjacencyListGraph<CCPLVertex>(
				true, false);
		g.addEdge(CCPLVertex.class, "s", "u");
		g.addEdge(CCPLVertex.class, "u", "t");
		g.addEdge(CCPLVertex.class, "s", "t");
		int c = GraphAlgorithm.countSimplePathForDAG(g, g.getNodeByName("s"),
				g.getNodeByName("t"));
		g.print();
		System.out.println(c);
		Assert.assertEquals(2, c);
		//
		//
		//

		TestFigures.figure22_8_dag_alphabetically(g, CCPLVertex.class);
		c = GraphAlgorithm.countSimplePathForDAG(g, g.getNodeByName("p"),
				g.getNodeByName("v"));
		g.print();
		System.out.println(c);
		Assert.assertEquals(4, c);
	}

	private static void testTopSort() {
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		TestFigures.figure22_7_dag(g, TCPLVertex.class);
		List<TCPLVertex> list = null;
		try {
			list = GraphAlgorithm.topologicalSortByDFS(g);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(list);
	}

	private static void testTopSortByRemove() {
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		TestFigures.figure22_7_dag(g, TCPLVertex.class);
		List<TCPLVertex> list = null;
		list = GraphAlgorithm.topologicalSortByRemoval(g);
		boolean flag = GraphAlgorithm.isTopologicalSorted(g, list);
		System.out.println(flag);
		Assert.assertTrue(flag);
		System.out.println(list);
		//
		//
		g.clear();
		TestFigures.figure22_7_dag(g, TCPLVertex.class);
		list.clear();
		list.add(g.getNodeByName("undershorts"));
		list.add(g.getNodeByName("pants"));
		list.add(g.getNodeByName("shirt"));
		list.add(g.getNodeByName("tie"));
		list.add(g.getNodeByName("watch"));
		list.add(g.getNodeByName("shoes"));
		list.add(g.getNodeByName("belt"));
		list.add(g.getNodeByName("jacket"));
		list.add(g.getNodeByName("socks"));

		// System.out.println(g.isConnected(g.getNodeByName("socks"),
		// g.getNodeByName("shoes")));
		flag = GraphAlgorithm.isTopologicalSorted(g, list);
		System.out.println(flag);
		System.out.println(list);
		Assert.assertFalse(flag);
	}

	private static void testDAG() {
		AdjacencyListGraph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(
				true, false);
		TestFigures.figure22_7_dag(g, TCPLVertex.class);
		boolean flag = GraphAlgorithm.isDAG(g);
		System.out.println(flag);
		Assert.assertTrue(flag);
		//
		//
		g.clear();
		g.addEdge(TCPLVertex.class, "s", "u");
		g.addEdge(TCPLVertex.class, "u", "s");
		flag = GraphAlgorithm.isDAG(g);
		System.out.println(flag);
		Assert.assertFalse(flag);
	}

	public static void main(String[] args) {
		testTopSort();
		testDAG();
		testTopSortByRemove();

		ex22_3_2();
		ex22_3_3();
		ex22_3_4();
		ex22_3_6();
		ex22_3_8And9();
		ex22_3_10();
		ex22_3_11();
		ex22_3_13();
		ex22_4_1();
		ex22_4_2();
		//
		//
		// g = new AdjacencyListGraph<TCPLVertex>(false, false);
		// TestFigures.figure22_2a(g, TCPLVertex.class);
		// g.print();
		// System.out.println();
		// dfsByColor(g, (TraverseFunc<TCPLVertex>)
		// TestGraph.defaultTraverseFunc);
	}
}
