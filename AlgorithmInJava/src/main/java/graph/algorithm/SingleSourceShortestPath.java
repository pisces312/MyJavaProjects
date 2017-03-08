package graph.algorithm;

import graph.Edge;
import graph.Graph;
import graph.IPathVertex;
import graph.IVertex;
import graph.Vertex;
import graph.adjacencylist.AdjacencyListGraph;
import graph.adjacencylist.LVertex;
import graph.adjacencymatrix.AdjacencyMatrixGraph;
import graph.adjacencymatrix.MVertex;
import graph.adjacencymatrix.PMVertex;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Administrator
 */
public class SingleSourceShortestPath {

	// PriorityQueue<Integer> queue;

	// T [] nodes;

	// public Dijkstra(Graph<T> g2) {
	// this.g = g2;
	// nodes = g2.getVertexesArray();
	// for (T n : nodes) {
	// d.put(n, 0);
	// }
	// }

	// public interface WeightFunction<T extends Vertex & IShortestPathVertex> {
	// public int getWeight(T u, T v);
	// }

	public static <T extends Vertex & IPathVertex> void initializeSingleSouce(
			Graph<T> g, T start) {
		System.out.println("initializeSingleSouce");
		for (T node : g.getVertexes()) {
			node.setDistance(Integer.MAX_VALUE);
		}
		start.setDistance(0);
		// ////////////////
		// for (T node : g.getVertexes()) {
		// System.out.println(node);
		// }
	}

	public static <T extends Vertex & IPathVertex> void relax(
			Graph<T> g, T u, T v) {
		int newWeight = g.getCost(u, v);
		if (u.getDistance() == Integer.MAX_VALUE
				|| newWeight == Integer.MAX_VALUE) {
			return;
		}
		if (v.getDistance() > u.getDistance() + newWeight) {
			v.setDistance(u.getDistance() + newWeight);
			v.setPrecedence(u);
		}
	}

	public static <T extends Vertex & IPathVertex> void printSingleSourceShortestPath(
			Graph<T> g) {
		Collection<T> nodes = g.getVertexes();
		for (T node : nodes) {
			IPathVertex pre = node;
			while (pre != null) {
				System.out.print(pre.getName() + "<-");
				pre = pre.getPrecedence();
			}
			System.out.println();
		}
	}

	public static <T extends Vertex & IPathVertex> boolean bellmanFord(
			Graph<T> g, T s) {
		initializeSingleSouce(g, s);
		Collection<Edge<T>> edges = g.getEdges();
		for (int i = 0, num = g.getNodeNum(); i < num; ++i) {// the longest path
																// is nodeNum-1
			for (Edge<T> e : edges) {
				relax(g, e.src, e.dest);
			}
		}
		for (Edge<T> e : edges) {
			if (e.dest.getDistance() > e.src.getDistance()
					+ g.getCost(e.src, e.dest)) {
				return false;
			}
		}
		// printSingleSourceShortestPath(g);
		return true;
	}

	// TODO
	public static <T extends Vertex & IPathVertex> void dagShortestPath(
			Graph<T> g, T s) {
		List<T> topSortedNodes = GraphAlgorithm.topologicalSortByRemoval(g);
		System.out.println(topSortedNodes);
	}

	// TODO, only use start
	public static <T extends Vertex> void dijkstra(Graph<T> g, T start, T target) {
		HashSet<T> sSet = new HashSet<T>();
		HashSet<T> vSet = new HashSet<T>();
		sSet.add(start);
		Collection<T> nodes = g.getVertexes();
		for (T n : nodes) {
			if (n != start) {
				vSet.add(n);
			}
		}
		Hashtable<T, Integer> d = new Hashtable<T, Integer>();
		for (T n : nodes) {
			d.put(n, Integer.MAX_VALUE);
		}
		d.put(start, 0);
		// /////////////////////////////////////////////
		boolean isStop = false;
		while (!vSet.isEmpty() && !isStop) {
			isStop = true;
			Iterator<T> vItr = vSet.iterator();
			while (vItr.hasNext()) {
				T v = vItr.next();
				int min = Integer.MAX_VALUE;
				boolean flag = false;
				for (T u : sSet) {// 如果没有一条边与v联通则出错，说明不是联通图
					int uCost = d.get(u);
					if (g.isConnected(u, v)) {
						isStop = false;// 只要有一条联通即可
						int t = uCost + g.getCost(u, v);
						if (t < min) {
							min = t;
							flag = true;
						}

					}
				}
				if (flag) {
					d.put(v, min);
					sSet.add(v);
					vItr.remove();
				}
			}
			// }
		}
		if (isStop) {
			System.out.println("错误");
		} else {
			for (T n : nodes) {
				System.out.println(n + "  " + d.get(n));
			}
		}
		System.out.println();

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	private static Graph<MVertex> topUnDirectedConnectedAjacentGraph() {
		Graph<MVertex> g = new AdjacencyMatrixGraph<MVertex>(false);
		MVertex[] nodes = new MVertex[7];
		// String[] str = {
		// "d", "s", "g", "a", "o", "w", "m"
		// };
		for (int i = 0; i < 7; i++) {
			// nodes[i] = new VNode(str[i]);
			nodes[i] = new MVertex(String.valueOf(i));
			// nodes[i].setId(i);
		}
		g.addEdge(nodes[0], nodes[1], 2);
		g.addEdge(nodes[0], nodes[4], 5);
		g.addEdge(nodes[0], nodes[6], 1);
		g.addEdge(nodes[1], nodes[2], 7);
		g.addEdge(nodes[1], nodes[4], 3);
		g.addEdge(nodes[1], nodes[5], 7);

		g.addEdge(nodes[2], nodes[3], 1);

		g.addEdge(nodes[4], nodes[5], 4);
		g.addEdge(nodes[4], nodes[6], 6);
		g.addEdge(nodes[5], nodes[6], 2);
		return g;
	}

	private static AdjacencyMatrixGraph<MVertex> topUnConnectedGraph() {
		AdjacencyMatrixGraph<MVertex> g = new AdjacencyMatrixGraph<MVertex>(
				false);
		MVertex[] nodes = new MVertex[8];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new MVertex(String.valueOf(i));
			// nodes[i].setId(i);
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
		return g;
	}

	private static AdjacencyListGraph topUnDirectedConnectedLinkedListGraph() {
		AdjacencyListGraph g = new AdjacencyListGraph(false);
		LVertex[] nodes = new LVertex[7];
		// String[] str = {
		// "d", "s", "g", "a", "o", "w", "m"
		// };
		for (int i = 0; i < 7; i++) {
			// nodes[i] = new VNode(str[i]);
			nodes[i] = new LVertex(String.valueOf(i));
			// nodes[i].setId(i);
		}
		g.addEdge(nodes[0], nodes[1], 2);
		g.addEdge(nodes[0], nodes[4], 5);
		g.addEdge(nodes[0], nodes[6], 1);
		g.addEdge(nodes[1], nodes[2], 7);
		g.addEdge(nodes[1], nodes[4], 3);
		g.addEdge(nodes[1], nodes[5], 7);

		g.addEdge(nodes[2], nodes[3], 1);

		g.addEdge(nodes[4], nodes[5], 4);
		g.addEdge(nodes[4], nodes[6], 6);
		g.addEdge(nodes[5], nodes[6], 2);
		return g;
	}

	private static void testAdjacentGraph() {
		AdjacencyMatrixGraph<MVertex> g = topUnConnectedGraph();
		MVertex[] nodes = g.getVertexesArray(new MVertex[0]);
		SingleSourceShortestPath.dijkstra(g, nodes[0], nodes[1]);
	}

	private static void testLinkedListGraph() {
		AdjacencyListGraph<LVertex> g = topUnDirectedConnectedLinkedListGraph();
		LVertex[] nodes = g.getVertexesArray(new LVertex[0]);
		SingleSourceShortestPath.dijkstra(g, nodes[0], nodes[1]);
	}

	/**
	 * p652
	 * 
	 * @return
	 */
	private static <T extends Vertex & IVertex> Graph<T> createSingleSourceShortestPathGraph2(
			Graph<T> g, Class<T> c) {
		// if (!g.isEmpty()) {
		g.clear();
		// }
		g.addVertexes(c, "s", "t", "x", "y", "z");
		g.addEdge("s", "t", 6);
		g.addEdge("s", "y", 7);
		g.addEdge("t", "x", 5);
		g.addEdge("t", "y", 8);
		g.addEdge("t", "z", -4);
		g.addEdge("x", "t", -2);
		g.addEdge("y", "x", -3);
		g.addEdge("y", "z", 9);
		g.addEdge("z", "s", 2);
		g.addEdge("z", "x", 7);
		return g;

	}

	private static <T extends Vertex & IVertex> Graph<T> createSingleSourceShortestPathGraph3(
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
		return g;
	}

	private static <T extends Vertex & IPathVertex> void testBellmanFord(
			Graph<T> g) {
		g.print();

		// SLVertex s=g.getNodeByName("s");
		// System.out.println(s);
		boolean flag = bellmanFord(g, g.getNodeByName("s"));
		System.out.println(flag);
		printSingleSourceShortestPath(g);
		g.print();
	}

	public static void main(String[] args) {

		// Graph<SLVertex> g = createSingleSourceShortestPathGraph3(new
		// LinkedListGraph<SLVertex>(true), SLVertex.class);
		//
		Graph<PMVertex> g = createSingleSourceShortestPathGraph3(
				new AdjacencyMatrixGraph<PMVertex>(true), PMVertex.class);
		// Graph<SLVertex> g = createSingleSourceShortestPathGraph2(new
		// LinkedListGraph<SLVertex>(true), SLVertex.class);
		testBellmanFord(g);

		//
		//
		// dagShortestPath(g, g.getNodeByName("s"));

		// AdjacentGraph<SAVertex> g = new AdjacentGraph<SAVertex>();
		// SAVertex [] nodes = new SAVertex[8];
		// for (int i = 0; i < nodes.length; i++) {
		// nodes[i] = new SAVertex(String.valueOf(i));
		// // nodes[i].setId(i);
		// }
		// g.initGraph(nodes, false);
		// g.addEdge(nodes[0], nodes[1]);
		// g.addEdge(nodes[0], nodes[2]);
		// g.addEdge(nodes[1], nodes[2]);
		// g.addEdge(nodes[1], nodes[3]);
		// g.addEdge(nodes[1], nodes[4]);
		// g.addEdge(nodes[2], nodes[4]);
		// g.addEdge(nodes[2], nodes[7]);
		// g.addEdge(nodes[3], nodes[4]);
		// g.addEdge(nodes[4], nodes[5]);
		// // testLinkedListGraph();
		//
		// initializeSingleSouce(g, nodes[0]);

	}
}
