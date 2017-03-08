/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.test;

import graph.Edge;
import graph.Graph;
import graph.IColoredVertex;
import graph.IPathVertex;
import graph.IVertex;
import graph.Vertex;
import graph.adjacencylist.AdjacencyListGraph;
import graph.adjacencylist.CPLVertex;
import graph.adjacencylist.LVertex;
import graph.adjacencylist.MultiGraph;
import graph.adjacencylist.TCPLVertex;
import graph.adjacencymatrix.AdjacencyMatrixGraph;
import graph.adjacencymatrix.CPMVertex;
import graph.adjacencymatrix.MVertex;
import graph.algorithm.BreathFirstSearch;
import graph.algorithm.GraphAlgorithm;
import graph.algorithm.GraphAlgorithm.TraverseFunc;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;
import config.Config;

/**
 * 
 * @author Administrator
 */
public class TestGraph {
	public static final Logger logger = Logger.getLogger(TestGraph.class
			.getPackage().getName());
	public static final String CLASS_NAME = TestGraph.class.getName();
	static {
		Config.initLogConfig();
	}
	public static final TraverseFunc<? extends IVertex> defaultTraverseFunc = new TraverseFunc<IVertex>() {


		public void visit(IVertex vertex) {
			System.out.println(vertex);
		}

	};

	private static <T extends Vertex & IVertex> Graph<T> topBipartiteGraph(
			Graph<T> g, Class<T> c) {
		if (g.isDirectedGraph()) {
			return null;
		}
		g.clear();
		g.addEdge(c, "0", "1", 1);
		g.addEdge(c, "0", "2", 1);
		g.addEdge(c, "1", "3", 1);
		g.addEdge(c, "1", "4", 1);
		g.addEdge(c, "2", "6", 1);
		g.addEdge(c, "2", "7", 1);
		g.addEdge(c, "4", "5", 1);
		return g;
	}

	/**
	 * in degree s 1 t 2 x 3 y 2 z 2
	 * 
	 * @param g
	 * @param c
	 */
	public static final <T extends Vertex & IVertex> void constructGraph1(
			Graph<T> g, Class<T> c) {
		g.clear();
		g.addEdge(c, "s", "t", 6);
		g.addEdge(c, "s", "y", 7);
		g.addEdge(c, "t", "x", 5);
		g.addEdge(c, "t", "y", 8);
		g.addEdge(c, "t", "z", -4);
		g.addEdge(c, "x", "t", -2);
		g.addEdge(c, "y", "x", -3);
		g.addEdge(c, "y", "z", 9);
		g.addEdge(c, "z", "s", 2);
		g.addEdge(c, "z", "x", 7);
	}

	/**
	 * p592 22.1-1
	 */
	private static void testGraphDegree() {
		AdjacencyListGraph<LVertex> alGraph = new AdjacencyListGraph<LVertex>(
				true);
		testOutDegree(alGraph, LVertex.class);
		AdjacencyMatrixGraph<MVertex> amGraph = new AdjacencyMatrixGraph<MVertex>(
				true);
		testOutDegree(amGraph, MVertex.class);
	}

	/**
	 * 22.1-1 Given an adjacency-list representation of a directed graph, how
	 * long does it take to compute the out-degree of every vertex? How long
	 * does it take to compute the in-degrees?
	 * 
	 * Answer:
	 * 
	 * The out-degree of a vertex is the number of edges leaving it. To compute
	 * the out-degree, we will require O(V+E) time. The in-degree, on the other
	 * hand, requires us going through the entire list of vertices, requiring us
	 * to to make O(V^2+E) operations. in degree s 1; t 2; x 3; y 2; z 2;
	 */
	private static <T extends Vertex & IVertex> void testOutDegree(Graph<T> g,
			Class<T> c) {
		constructGraph1(g, c);
		T node = null;
		int d = -1;

		// ////////////////////////////////////
		node = g.getNodeByName("s");
		d = g.getInDegree(node);
		System.out.println(node + ":" + d);
		Assert.assertTrue(d == 1);
		//
		node = g.getNodeByName("t");
		d = g.getInDegree(node);
		System.out.println(node + ":" + d);
		Assert.assertTrue(d == 2);
		//
		node = g.getNodeByName("x");
		d = g.getInDegree(node);
		System.out.println(node + ":" + d);
		Assert.assertTrue(d == 3);
		//
		node = g.getNodeByName("y");
		d = g.getInDegree(node);
		System.out.println(node + ":" + d);
		Assert.assertTrue(d == 2);
		//
		node = g.getNodeByName("z");
		d = g.getInDegree(node);
		System.out.println(node + ":" + d);
		Assert.assertTrue(d == 2);

		// ////////////////////////////////////
		node = g.getNodeByName("s");
		d = g.getOutDegree(node);
		System.out.println(node + ":" + d);
		Assert.assertTrue(d == 2);
		//
		node = g.getNodeByName("t");
		d = g.getOutDegree(node);
		System.out.println(node + ":" + d);
		Assert.assertTrue(d == 3);
		//
		node = g.getNodeByName("x");
		d = g.getOutDegree(node);
		System.out.println(node + ":" + d);
		Assert.assertTrue(d == 1);
		//
		node = g.getNodeByName("y");
		d = g.getOutDegree(node);
		System.out.println(node + ":" + d);
		Assert.assertTrue(d == 2);
		//
		node = g.getNodeByName("z");
		d = g.getOutDegree(node);
		System.out.println(node + ":" + d);
		Assert.assertTrue(d == 2);
		//

	}

	/**
	 * 22.1-3 The transpose of a directed graph G = (V, E) is the graph GT = (V,
	 * ET), where ET = {(v, u) V �� V : (u, v) E}. Thus, GT is G with all its
	 * edges reversed. Describe efficient algorithms for computing GT from G,
	 * for both the adjacency-list and adjacency-matrix representations of G.
	 * Analyze the running times of your algorithms.
	 * 
	 * 
	 * We're looking at a directed graph, which means the adjacency matrix will
	 * not be symmetric as it was in the prior problem. Thus, we will have to
	 * swap every element (u,v) to (v,u). The resulting time to swap all
	 * elements of a matrix is O(V^2). As for an adjacency list, we'll have to
	 * create a new adjacency list while traversing the existing one, costing
	 * less time overall at O(V+E).
	 */
	private static <T extends Vertex & IVertex> void testTranspose() {
		final String method = "testTranspose";
		logger.entering(CLASS_NAME, method);
		AdjacencyListGraph<LVertex> g = new AdjacencyListGraph<LVertex>(true,
				false);
		testTranspose(g, LVertex.class);
		AdjacencyMatrixGraph<MVertex> g2 = new AdjacencyMatrixGraph<MVertex>(
				true, false);
		testTranspose(g2, MVertex.class);
	}

	private static <T extends Vertex & IVertex> void testTranspose(Graph<T> g,
			Class<T> c) {
		constructGraph1(g, c);
		g.print();
		g.transpose();
		g.print();

	}

	private static void testGraphCommon() {
		Graph g = null;
		g = new AdjacencyListGraph<LVertex>(true, true);
		testGraphCommon(g, LVertex.class);
		g = new AdjacencyListGraph<LVertex>(false, true);
		testGraphCommon(g, LVertex.class);
		g = new AdjacencyMatrixGraph<MVertex>(true, true);
		testGraphCommon(g, MVertex.class);
		g = new AdjacencyMatrixGraph<MVertex>(false, true);
		testGraphCommon(g, MVertex.class);
	}

	private static <T extends Vertex & IVertex> void testGraphCommon(
			Graph<T> g, Class<T> c) {
		IVertex v = new TCPLVertex("a");
		IVertex u = v.createEmptyVertex();
		System.out.println(u);
		Assert.assertEquals("a", u.getName());
		Assert.assertEquals(TCPLVertex.class, u.getClass());
		//
		//
		Edge<T> e1 = g.addEdge(c, "0", "1", 1);
		g.addEdge(c, "0", "2", 1);
		g.addEdge(c, "1", "3", 1);
		g.addEdge(c, "1", "4", 1);
		g.addEdge(c, "2", "6", 1);
		g.addEdge(c, "2", "7", 1);
		g.addEdge(c, "4", "5", 1);
		g.print();
		//
		//
		Collection<Edge<T>> edges = g.getEdges();
		Collection<T> vertexes = g.getVertexes();

		// ///////////////////////////////////////////////////
		Edge<T> te1 = new Edge<T>(g.getNodeByName("0"), g.getNodeByName("1"),
				1, g.isDirectedGraph());
		Edge<T> te2 = new Edge<T>(g.getNodeByName("1"), g.getNodeByName("0"),
				1, g.isDirectedGraph());
		Edge<T> te3 = new Edge<T>(g.getNodeByName("0"), g.getNodeByName("1"),
				2, g.isDirectedGraph());
		Edge<T> te4 = new Edge<T>(g.getNodeByName("1"), g.getNodeByName("0"),
				3, g.isDirectedGraph());

		//
		boolean flag1 = te1.equals(e1);
		System.out.println(flag1);

		//
		boolean flag2 = te2.equals(e1);
		System.out.println(flag2);
		//
		boolean flag3 = te3.equals(e1);
		System.out.println(flag3);
		//
		boolean flag4 = te3.equals(te4);
		System.out.println(flag4);
		//
		boolean flag5 = te1.equals(te2);
		System.out.println(flag5);
		//
		T v1 = g.getNodeByName("1");
		T v2 = g.getNodeByName("1");
		T v3 = g.getNodeByName("3");
		// System.out.println(v1 + "," + v2);
		// System.out.println(v1.hashCode() + "," + v2.hashCode());
		System.out.println(v1.equals(v2));
		Edge<T> ee1 = new Edge<T>(v1, v3, 2, g.isDirectedGraph());
		Edge<T> ee2 = new Edge<T>(v3, v1, 2, g.isDirectedGraph());
		// System.out.println(ee1);
		// System.out.println(ee2);
		// System.out.println(ee1.hashCode());
		// System.out.println(ee2.hashCode());
		if (g.isDirectedGraph()) {
			// edge equal test
			Assert.assertTrue(flag1);
			Assert.assertTrue(!flag2);
			Assert.assertTrue(!flag3);
			Assert.assertTrue(!flag4);
			Assert.assertTrue(!flag5);
			Assert.assertTrue(ee1.hashCode() != ee2.hashCode());
			// common

			Assert.assertTrue(g.getEdgeNum() == 7);
			Assert.assertTrue(edges.size() == 7);
			Assert.assertTrue(g.getNodeNum() == 8);
			Assert.assertTrue(vertexes.size() == 8);
		} else {
			Assert.assertTrue(flag1);
			Assert.assertTrue(flag2);
			Assert.assertTrue(!flag3);
			Assert.assertTrue(!flag4);
			Assert.assertTrue(flag5);
			Assert.assertTrue(ee1.hashCode() == ee2.hashCode());
		}
		g.removeEdge(g.getNodeByName("4"), g.getNodeByName("5"));
		g.print();
		System.out.println();
		Assert.assertTrue(!g.hasEdge(new Edge(g.getNodeByName("4"), g
				.getNodeByName("5"), 1, g.isDirectedGraph())));
		//
		// g.removeEdge(nodes[4], nodes[5]);
		// g.print();
		// System.out.println();
		//
		g.removeVertex(g.getNodeByName("5"));
		g.print();
		System.out.println();
		// ///////////////////////////////////////////////////
		// Collection<Edge<LVertex>> edges = g.getEdges();
		// System.out.println(edges);
		// test duplicate edge
		// g.addEdge(nodes[0], nodes[0], 1);
		// Assert.assertTrue(g.isConnected(nodes[0], nodes[0]));
		// g.print();
	}

	private static void testMultiEdgeGraph() {
		final String method = "testMultiEdgeGraph";
		logger.entering(CLASS_NAME, method);
		MultiGraph g = new MultiGraph(false, true);
		g.addEdge(LVertex.class, "1", "2", 1);
		g.addEdge(LVertex.class, "1", "3", 2);
		g.addEdge(LVertex.class, "2", "4", 3);
		g.addEdge(LVertex.class, "2", "5", 4);
		g.addEdge(LVertex.class, "3", "6", 5);
		g.addEdge(LVertex.class, "3", "7", 6);
		g.addEdge(LVertex.class, "1", "2", 1);
		g.addEdge(LVertex.class, "1", "3", 2);
		g.addEdge(LVertex.class, "2", "4", 3);
		g.addEdge(LVertex.class, "2", "5", 4);
		g.addEdge(LVertex.class, "3", "6", 5);
		g.addEdge(LVertex.class, "3", "7", 6);
		g.print();
		int edgeNum = g.getEdgeNum();
		int edgeNum2 = g.getEdges().size();
		System.out.println(edgeNum);
		System.out.println(edgeNum2);
		Assert.assertEquals(edgeNum, 12);
		Assert.assertEquals(edgeNum, edgeNum2);
		// //////////////////////////////

		// System.out.println();
		//
		// // allow duplicate
		// nodes = g.getVertexes();
		// g.addEdge(nodes[0], nodes[1]);
		// g.print();
		// System.out.println();
	}

	private static void testRemoveAll() {
		AdjacencyListGraph<LVertex> g = new AdjacencyListGraph<LVertex>(false);
		topBipartiteGraph(g, LVertex.class);
		g.print();
		System.out.println();
		LVertex[] nodes = new LVertex[g.getNodeNum()];
		for (LVertex node : g.getVertexesArray(nodes)) {
			g.removeVertex(node);
		}
		g.print();
		assert (g.getEdgeNum() == 0 && g.getNodeNum() == 0);
	}

	/**
	 * 22.1-2 Give an adjacency-list representation for a complete binary tree
	 * on 7 vertices. Give an equivalent adjacency-matrix representation. Assume
	 * that vertices are numbered from 1 to 7 as in a binary heap.
	 * 
	 * @return
	 */
	private static void testGraphConvertion() {
		final String method = "testGraphConvertion";
		logger.entering(CLASS_NAME, method);
		AdjacencyListGraph<LVertex> g = new AdjacencyListGraph<LVertex>(false,
				false);
		g.addEdge(LVertex.class, "1", "2");
		g.addEdge(LVertex.class, "1", "3");
		g.addEdge(LVertex.class, "2", "4");
		g.addEdge(LVertex.class, "2", "5");
		g.addEdge(LVertex.class, "3", "6");
		g.addEdge(LVertex.class, "3", "7");
		// //////////////////////////////////

		g.print();
		AdjacencyMatrixGraph<MVertex> g2 = g.convertToMatricGraph();
		g2.print();
		Assert.assertEquals(g.getEdgeNum(), g2.getEdgeNum());
	}

	/**
	 * !22.1-4 Given an adjacency-list representation of a multigraph G = (V,
	 * E), describe an O(V + E)-time algorithm to compute the adjacency-list
	 * representation of the "equivalent" undirected graph G�� = (V, E��), where
	 * E�� consists of the edges in E with all multiple edges between two
	 * vertices replaced by a single edge and with all self-loops removed.
	 */
	private static void testConvertToSingle() {
		final String method = "testConvertToSingle";
		logger.entering(CLASS_NAME, method);
		MultiGraph g = new MultiGraph(false, true);
		Edge<LVertex> e1 = g.addEdge(LVertex.class, "1", "2", 1);
		Edge<LVertex> e2 = g.addEdge(LVertex.class, "1", "3", 2);
		Edge<LVertex> e3 = g.addEdge(LVertex.class, "2", "4", 3);
		Edge<LVertex> e4 = g.addEdge(LVertex.class, "2", "5", 4);
		Edge<LVertex> e5 = g.addEdge(LVertex.class, "3", "6", 5);
		Edge<LVertex> e6 = g.addEdge(LVertex.class, "3", "7", 6);
		Edge<LVertex>[] e = new Edge[] { e1, e2, e3, e4, e5, e6 };
		g.addEdges(e);
		g.print();
		Assert.assertEquals(12, g.getEdgeNum());
		System.out.println();
		Graph<LVertex> g2 = g.convertToSingleGraph();
		g2.print();
		System.out.println(g2.getEdgeNum());
		Assert.assertEquals(g2.getEdgeNum(), 6);
		/**
		 * 1 -> 3 : 2 1 -> 2 : 1 2 -> 5 : 4 2 -> 4 : 3 3 -> 7 : 6 3 -> 6 : 5
		 */
		Collection<Edge<LVertex>> edges = g2.getEdges();

		assertGraphEdges(edges, e);

	}

	public static <T extends Vertex & IVertex> void assertGraphEdges(
			Collection<Edge<T>> edges, Edge<T>... expectedEdges) {
		assertGraphEdges(edges, Arrays.asList(expectedEdges));
	}

	public static <T extends Vertex & IVertex> Map<Edge<T>, Integer> createMap(
			Collection<Edge<T>> edges) {
		Map<Edge<T>, Integer> map = new HashMap<Edge<T>, Integer>();
		for (Edge<T> e : edges) {
			Integer num = map.get(e);
			if (num != null) {
				map.put(e, num + 1);
			} else {
				map.put(e, 1);
			}
		}
		return map;
	}

	public static <T extends Vertex & IVertex> void assertGraphEdges(
			Collection<Edge<T>> edges, Collection<Edge<T>> expectedEdges) {
		if (edges == null || expectedEdges == null) {
			return;
		}
		Assert.assertEquals(edges.size(), expectedEdges.size());
		// Collections.sort(edges);
		Map<Edge<T>, Integer> map = createMap(edges);
		Map<Edge<T>, Integer> map2 = createMap(expectedEdges);
		Assert.assertEquals(map.size(), map2.size());
		for (Entry<Edge<T>, Integer> e : map.entrySet()) {
			Edge<T> k = e.getKey();
			Integer v = e.getValue();
			Assert.assertEquals(v, map2.get(k));
		}
		// Assert.assertTrue(map.equals(map2));
		// Collections.sort(expectedEdges);
		// for (Edge<T> e : edges) {
		// Assert.assertTrue(expectedEdges.contains(e));
		// }

	}

	/**
	 * p600 Breadth-�0�3rst trees
	 */
	// public static void testBFSTree() {
	// Graph<CSLVertex> g = new AdjacencyListGraph<CSLVertex>(
	// false, false);
	// constructGraph2(g, CSLVertex.class);
	// g.print();
	// CSLVertex s = g.getNodeByName("s");
	// Graph<CSLVertex> bfsTree = GraphAlgorithm.constructBreadthFirstTree(g,
	// s, g.getClass());
	//
	// // Graph<CSLVertex> bfsTree =
	// // GraphAlgorithm.constructBreadthFirstTree(g,
	// // s, new AdjacencyListGraph<CSLVertex>(false, false),
	// // CSLVertex.class);
	// bfsTree.print();
	// //
	// GraphAlgorithm.printAllShortestPath(g);
	// Assert.assertTrue(bfsTree.isTree());
	// }
	private static void testBFSTree() {
		Graph<CPLVertex> g = new AdjacencyListGraph<CPLVertex>(false, false);
		TestFigures.figure22_3_undirected(g, CPLVertex.class);
		g.print();
		CPLVertex s = g.getNodeByName("s");
		// Graph<CSLVertex> bfsTree =
		// GraphAlgorithm.constructBreadthFirstTree(g,
		// s, g.getClass());

		Graph<CPLVertex> bfsTree = BreathFirstSearch.constructBreadthFirstTree(
				g, s, new AdjacencyListGraph<CPLVertex>(false, false),
				CPLVertex.class);
		bfsTree.print();
		//
		GraphAlgorithm.printAllShortestPath(g, s);
		Assert.assertTrue(bfsTree.isTree());
	}

	private static void testTopSort() {
		try {
			// Graph g = new AdjacentGraph();
			AdjacencyMatrixGraph<MVertex> g = topDirectedConnectedGraph();
			g.print();
			// TODO
			// GraphAlgorithm.topSortMethod1(g);
			// System.out.println();
			//
			g = topDirectedConnectedGraph();
			GraphAlgorithm.topologicalSortByRemoval(g);
			// search(top1(g));
			// search(top2(g));
			//
			// g = new LinkedListGraph();
			// search(top2(g));
			// search(top1(g));
		} catch (Exception ex) {
			Logger.getLogger(GraphAlgorithm.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public static AdjacencyMatrixGraph<MVertex> topDirectedConnectedGraph() {
		AdjacencyMatrixGraph<MVertex> g = new AdjacencyMatrixGraph<MVertex>(
				true);
		MVertex[] nodes = new MVertex[7];
		String[] str = { "d", "s", "g", "a", "o", "w", "m" };
		for (int i = 0; i < 7; i++) {
			nodes[i] = new MVertex(str[i]);
			g.addVertex(nodes[i]);
		}
		g.addEdge(nodes[0], nodes[3]);
		g.addEdge(nodes[0], nodes[4]);
		g.addEdge(nodes[0], nodes[6]);
		g.addEdge(nodes[1], nodes[2]);
		g.addEdge(nodes[1], nodes[4]);
		g.addEdge(nodes[1], nodes[5]);
		g.addEdge(nodes[2], nodes[4]);
		g.addEdge(nodes[2], nodes[3]);
		g.addEdge(nodes[3], nodes[4]);
		g.addEdge(nodes[4], nodes[5]);
		g.addEdge(nodes[4], nodes[6]);
		g.addEdge(nodes[5], nodes[6]);
		return g;
	}

	// һ������ͼ����

	public static AdjacencyMatrixGraph<MVertex> topBipartiteGraph() {
		AdjacencyMatrixGraph<MVertex> g = new AdjacencyMatrixGraph<MVertex>(
				false);
		MVertex[] nodes = new MVertex[8];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new MVertex(String.valueOf(i));
			g.addVertex(nodes[i]);
		}
		g.addEdge(nodes[0], nodes[1]);
		g.addEdge(nodes[0], nodes[2]);
		g.addEdge(nodes[1], nodes[3]);
		g.addEdge(nodes[1], nodes[4]);
		g.addEdge(nodes[2], nodes[6]);
		g.addEdge(nodes[2], nodes[7]);
		g.addEdge(nodes[4], nodes[5]);
		return g;
	}

	// /////////////////////////////////////////////////////////////////////////////
	// һ���������ͨͼ
	// 6Ϊ�����Ľڵ�
	// XXX
	private static void testConnectedComponent() {
		boolean isDirected = false;
		// boolean isDirected=true;
		// AdjacencyMatrixGraph<MVertex> g = new
		// AdjacencyMatrixGraph<MVertex>(isDirected, false);
		// MVertex [] nodes = new MVertex[8];
		// for (int i = 0; i < nodes.length; i++) {
		// nodes[i] = new MVertex(String.valueOf(i));
		// }
		AdjacencyListGraph<LVertex> g = new AdjacencyListGraph<LVertex>(
				isDirected, false);
		LVertex[] nodes = new LVertex[8];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new LVertex(String.valueOf(i));
		}
		g.addEdge(nodes[0], nodes[1]);
		g.addEdge(nodes[0], nodes[2]);
		g.addEdge(nodes[1], nodes[2]);
		g.addEdge(nodes[1], nodes[3]);
		g.addEdge(nodes[1], nodes[4]);
		g.addEdge(nodes[2], nodes[4]);
		g.addEdge(nodes[2], nodes[7]);
		g.addEdge(nodes[3], nodes[4]);
		g.addEdge(nodes[4], nodes[5]);
		g.print();
		System.out.println(g.getEdgeNum());

		boolean isConnected = GraphAlgorithm.isConnectedGraph(g, nodes[6]);
		System.out.println(isConnected);
		Assert.assertFalse(isConnected);
		// alg.isConnectedComponent(1);
		g.print();
		GraphAlgorithm.findAllConnectedComponents(g);

	}

	/**
	 * p601 22.2-1 3 5 4 2; 3 5 4; 3 5; 3; 3 6
	 */
	private static <T extends IColoredVertex & IPathVertex> void ex22_2_1(
			Graph<T> g, Class<T> c) {
		TestFigures.figure22_2a(g, c);
		g.print();
		Collection<T> nodes = g.getVertexes();
		T s = g.getNodeByName("3");
		BreathFirstSearch.bfsReachableNodesBy2Color(g, s, null);
		System.out.println();
		GraphAlgorithm.printAllShortestPath(g, s);
		for (T n : nodes) {
			System.out.print(n.getDistance() + " " + n.getPrecedence() + ":");
			List<IPathVertex> list = GraphAlgorithm.constructPath(g, s, n);
			if (list.isEmpty()) {

			}
			System.out.println();
		}
		System.out.println();
		for (T n : nodes) {
			System.out.println(GraphAlgorithm.printPath2(g, s, n));
		}
		Assert.assertEquals(g.getNodeByName("1").getDistance(),
				Integer.MAX_VALUE);
		Assert.assertEquals(g.getNodeByName("2").getDistance(), 3);
		Assert.assertEquals(g.getNodeByName("3").getDistance(), 0);
		Assert.assertEquals(g.getNodeByName("4").getDistance(), 2);
		Assert.assertEquals(g.getNodeByName("5").getDistance(), 1);
		Assert.assertEquals(g.getNodeByName("6").getDistance(), 1);

		Assert.assertEquals(g.getNodeByName("1").getPrecedence(), null);
		Assert.assertEquals(g.getNodeByName("2").getPrecedence().getName(),
				("4"));
		Assert.assertEquals(g.getNodeByName("3").getPrecedence(), null);
		Assert.assertEquals(g.getNodeByName("4").getPrecedence().getName(),
				("5"));
		Assert.assertEquals(g.getNodeByName("5").getPrecedence().getName(),
				("3"));
		Assert.assertEquals(g.getNodeByName("6").getPrecedence().getName(),
				("3"));
	}

	/**
	 * p602 22.2-2
	 */
	private static <T extends IPathVertex & IColoredVertex> void ex22_2_2(
			Graph<T> g, Class<T> c) {
		TestFigures.figure22_3_undirected(g, c);
		g.print();
		Collection<T> nodes = g.getVertexes();
		T s = g.getNodeByName("u");
		BreathFirstSearch.bfsReachableNodesBy2Color(g, s, null);
		// GraphAlgorithm.breadthFirstSearchUsingColor(g, s, null);
		System.out.println();
		GraphAlgorithm.printAllShortestPath(g, s);
		for (T n : nodes) {
			System.out.println(GraphAlgorithm.constructPath(g, s, n));
		}
		System.out.println();
		for (T n : nodes) {
			System.out.println(GraphAlgorithm.printPath2(g, s, n));
		}
		Assert.assertEquals(g.getNodeByName("r").getDistance(), 4);
		Assert.assertEquals(g.getNodeByName("s").getDistance(), 3);
		Assert.assertEquals(g.getNodeByName("t").getDistance(), 1);
		Assert.assertEquals(g.getNodeByName("u").getDistance(), 0);
		Assert.assertEquals(g.getNodeByName("v").getDistance(), 5);
		Assert.assertEquals(g.getNodeByName("w").getDistance(), 2);
		Assert.assertEquals(g.getNodeByName("x").getDistance(), 1);
		Assert.assertEquals(g.getNodeByName("y").getDistance(), 1);
	}

	/**
	 * 22.2-4 O(V^2)
	 */
	private static void ex22_2_4() {
		ex22_2_1(new AdjacencyMatrixGraph<CPMVertex>(true, false),
				CPMVertex.class);
		ex22_2_2(new AdjacencyMatrixGraph<CPMVertex>(false, false),
				CPMVertex.class);
	}

	/**
	 * The correctness proof for the BFS algorithm shows that u:d D �0�3.s; u/, and
	 * the algorithm doesn��t assume that the adjacency lists are in any
	 * particular order. In Figure 22.3, if t precedes x in Adj�0�3w�0�3, we can get
	 * the breadth-�0�3rst tree shown in the �0�3gure.
	 * 
	 * s r ; s r v ; s ; s w ; s w t ; s w x ; s w t u ; s w x y ;
	 * 
	 * But if x precedes t in Adj�0�3w�0�3 and u precedes y in Adj�0�3x�0�3, we can get edge
	 * .x; u/ in the breadth-�0�3rst tree.
	 * 
	 * s r ; s r v ; s r s ; s w ; s w x ; s w t ; s w x u ; s w x y ;
	 */
	private static void ex22_2_5() {
		Graph<CPLVertex> g = new AdjacencyListGraph<CPLVertex>(false, false);
		TestFigures.figure22_3_undirected(g, CPLVertex.class);
		CPLVertex s = g.getNodeByName("s");
		g.print();
		Graph<CPLVertex> bfsTree = BreathFirstSearch.constructBreadthFirstTree(
				g, s, new AdjacencyListGraph<CPLVertex>(false, false),
				CPLVertex.class);
		bfsTree.print();
		GraphAlgorithm.printAllShortestPath(g, s);
		Assert.assertTrue(bfsTree.isTree());
		//
		//
		//
		// g = new AdjacencyListGraph<CSLVertex>(false, false);
		TestFigures.figure22_3_2_undirected(g, CPLVertex.class);
		g.print();
		bfsTree = BreathFirstSearch.constructBreadthFirstTree(g, s,
				new AdjacencyListGraph<CPLVertex>(false, false),
				CPLVertex.class);
		bfsTree.print();
		GraphAlgorithm.printAllShortestPath(g, s);
		Assert.assertTrue(bfsTree.isTree());

	}

	private static void ex22_2_8() {
		Graph<CPLVertex> g = new AdjacencyListGraph<CPLVertex>(false, false);
		TestFigures.figure22_3_undirected(g, CPLVertex.class);
		CPLVertex s = g.getNodeByName("s");
		// g.print();
		Graph<CPLVertex> bfsTree = BreathFirstSearch.constructBreadthFirstTree(
				g, s, new AdjacencyListGraph<CPLVertex>(false, false),
				CPLVertex.class);
		// bfsTree.print();
		GraphAlgorithm.printAllShortestPath(g, s);
		Assert.assertTrue(bfsTree.isTree());
		//
		int d = GraphAlgorithm.getDiameterOfTree(bfsTree, s);
		System.out.println(d);
		Assert.assertEquals(6, d);

	}

	private static void testTopSort1() {
		Graph<CPLVertex> g = new AdjacencyListGraph<CPLVertex>(true, false);
		TestFigures.figure22_7_dag(g, CPLVertex.class);
		g.print();
	}

	private static void testOrderedAdjNodes() {
		Graph<CPLVertex> g = new AdjacencyListGraph<CPLVertex>(true, false);
		g.addEdge(CPLVertex.class, "a", "c");
		g.addEdge(CPLVertex.class, "a", "a");
		g.addEdge(CPLVertex.class, "a", "k");
		g.addEdge(CPLVertex.class, "a", "b");
		g.addEdge(CPLVertex.class, "a", "z");
		g.addEdge(CPLVertex.class, "a", "p");
		g.print();
		List<CPLVertex> adj = g.getAdjVertexes(g.getNodeByName("a"),
				Graph.alphaComparator);
		System.out.println(adj);
	}

	/**
	 * 4
	 * 
	 * [{a <d0,f0>}, {e <d0,f0>}, {b <d0,f0>}]
	 * 
	 * [{c <d0,f0>}, {d <d0,f0>}]
	 * 
	 * [{g <d0,f0>}, {f <d0,f0>}]
	 * 
	 * [{h <d0,f0>}]
	 */
	private static void ex22_5_1_SCC() {
		List<List<TCPLVertex>> components = null;
		Graph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(true, false);

		TestFigures.figure22_9_dag(g, TCPLVertex.class);
		System.out.println();
		//
		//
		components = GraphAlgorithm.getAllStrongConnectedComponents(g);
		g.print();
		System.out.println(components.size());
		Assert.assertEquals(4, components.size());
		for (List<TCPLVertex> cmp : components) {
			System.out.println(cmp);
		}
		Assert.assertTrue(components.get(0).containsAll(
				Arrays.asList(g.getNodeByName("a"), g.getNodeByName("e"),
						g.getNodeByName("b"))));
		Assert.assertTrue(components.get(1).containsAll(
				Arrays.asList(g.getNodeByName("c"), g.getNodeByName("d"))));
		Assert.assertTrue(components.get(2).containsAll(
				Arrays.asList(g.getNodeByName("g"), g.getNodeByName("f"))));
		Assert.assertTrue(components.get(3).containsAll(
				Arrays.asList(g.getNodeByName("h"))));
		//
		//
		//

		// 1)
		g.addEdge("b", "a");
		g.print();
		components = GraphAlgorithm.getAllStrongConnectedComponents(g);
		System.out.println(components.size());
		Assert.assertEquals(4, components.size());
		for (List<TCPLVertex> cmp : components) {
			System.out.println(cmp);
		}

		//
		// 2)
		g.addEdge("c", "b");
		g.print();
		components = GraphAlgorithm.getAllStrongConnectedComponents(g);
		System.out.println(components.size());
		Assert.assertEquals(3, components.size());
		for (List<TCPLVertex> cmp : components) {
			System.out.println(cmp);
		}
	}

	/**
	 * q r s t u v w x y z
	 * 
	 * d 5 1 15 7 3 17 16 11 6 12
	 * 
	 * f 10 2 20 8 4 18 19 14 9 13
	 * 
	 * Giving us the following components:
	 * 
	 * {r} -> {u} -> {q, y, t} -> {x, z} -> {s, w, v}
	 */
	private static void ex22_5_2() {
		List<List<TCPLVertex>> components = null;
		Graph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(true, false);

		TestFigures.figure22_6_directed_sorted(g, TCPLVertex.class);
		System.out.println();
		//
		//
		components = GraphAlgorithm.getAllStrongConnectedComponents(g);
		g.print();
		Assert.assertEquals(5, components.size());
		System.out.println(components.size());
		for (List<TCPLVertex> cmp : components) {
			System.out.println(cmp);
		}
		Assert.assertTrue(components.get(0).containsAll(
				Arrays.asList(g.getNodeByName("r"))));
		Assert.assertTrue(components.get(1).containsAll(
				Arrays.asList(g.getNodeByName("u"))));
		Assert.assertTrue(components.get(2).containsAll(
				Arrays.asList(g.getNodeByName("q"), g.getNodeByName("y"),
						g.getNodeByName("t"))));
		Assert.assertTrue(components.get(3).containsAll(
				Arrays.asList(g.getNodeByName("x"), g.getNodeByName("z"))));
		Assert.assertTrue(components.get(4).containsAll(
				Arrays.asList(g.getNodeByName("s"), g.getNodeByName("w"),
						g.getNodeByName("v"))));
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		ex22_5_2();

		// ex22_5_1_SCC();

		// Graph<TCPLVertex> g = new AdjacencyListGraph<TCPLVertex>(true,
		// false);
		// TestFigures.figure22_9_dag(g, TCPLVertex.class);
		// Graph<?> g2 = g.createEmptyGraph();
		// g2 = g.clone(TCPLVertex.class);
		// g2.print();

		// g.print();Class<Graph> gc,
		// GraphAlgorithm.findAllConnectedComponents(g);AdjacencyListGraph.class
		//
		//
		//

		// testOrderedAdjNodes();
		// testGraphCommon();
		// testRemoveAll();
		// testGraphDegree();
		// testGraphConvertion();
		// testTranspose();
		// testMultiEdgeGraph();
		// testConvertToSingle();
		// testBFSTree();
		// ex22_2_1(new AdjacencyListGraph<CPLVertex>(true, false),
		// CPLVertex.class);
		// ex22_2_1(new AdjacencyMatrixGraph<CPMVertex>(true, false),
		// CPMVertex.class);
		// ex22_2_2(new AdjacencyListGraph<CPLVertex>(false, false),
		// CPLVertex.class);
		// ex22_2_4();
		// ex22_2_5();
		// ex22_2_8();
		// testTopSort1();
		System.out.println("all has passed");
	}

}
