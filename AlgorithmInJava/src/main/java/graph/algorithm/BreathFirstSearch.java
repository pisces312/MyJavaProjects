package graph.algorithm;

import graph.Graph;
import graph.IColoredVertex;
import graph.IPathVertex;
import graph.Vertex;
import graph.algorithm.GraphAlgorithm.TraverseFunc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;

import junit.framework.Assert;

public final class BreathFirstSearch {

	private BreathFirstSearch() {

	}

	private static <T extends Vertex> boolean isVisited(Collection<T> visited,
			T node) {
		return visited.contains(node);

	}

	private static <T extends Vertex> void setVisited(Collection<T> visited,
			T node) {
		visited.add(node);
	}

	/**
	 * it will travserse unreachable vertex
	 * 
	 */
	public static <T extends IColoredVertex & IPathVertex> void bfsForAllNodes(
			Graph<T> g, T s, final TraverseFunc<T> p) {
		final class TraverseFuncForAll implements TraverseFunc<T> {
			int c = 0;


			public void visit(T vertex) {
				if (p != null) {
					p.visit(vertex);
				}
				++c;
			}

			public int getCount() {
				return c;
			}
		}
		TraverseFuncForAll func = new TraverseFuncForAll();
		bfsReachableNodesBy2Color(g, s, func);
		int c = func.getCount();
		if (c == 0) {
			return;
		}
		if (c == g.getNodeNum()) {
			return;
		}
		Collection<T> nodes = g.getVertexes();
		for (T node : nodes) {
			if (node.getDistance() == IPathVertex.UNREACHABLE) {
				if (p != null) {
					p.visit(node);
				}
			}
		}
	}

	/**
	 * p602 !22.2-3
	 * 
	 * fastest implementation
	 * 
	 * only calc the reachable vertex
	 * 
	 * 
	 * 
	 * @param g
	 * @param s
	 * @param p
	 * @return if all nodes are reachable
	 */
	public static <T extends IPathVertex & IColoredVertex> void bfsReachableNodesBy2Color(
			Graph<T> g, T s, TraverseFunc<T> p) {
		// final String method = "breadthFirstSearchUsing2Color";
		if (g == null || !g.hasVertex(s)) {
			return;
		}
		Collection<T> nodes = g.getVertexes();
		for (T node : nodes) {
			node.setColor(IColoredVertex.COLOR_WHITE);
			node.setDistance(Integer.MAX_VALUE);
			node.setPrecedence(null);
		}
		s.setDistance(0);
		// ///////////////////////
		Queue<T> q = new LinkedList<T>();
		q.add(s);
		s.setColor(IColoredVertex.COLOR_BLACK);
		if (p != null)
			p.visit(s);
		// int countReachableNode = 1;
		while (!q.isEmpty()) {
			T u = q.poll();
			Collection<T> adjNodes = g.getAdjVertexes(u);
			for (T v : adjNodes) {
				if (v.getColor() == IColoredVertex.COLOR_WHITE) {
					v.setDistance(u.getDistance() + 1);
					v.setPrecedence(u);
					v.setColor(IColoredVertex.COLOR_BLACK);
					if (p != null)
						p.visit(v);
					// ++countReachableNode;
					q.add(v);
				}
			}
		}
		// System.out.println("queue");
		// System.out.println(q);
		// return countReachableNode == nodes.size();
	}

	private static <T extends Vertex> void breadthFirstSearch1(Graph<T> g, T s,
			TraverseFunc<T> func) {
		if (g == null || !g.hasVertex(s)) {
			return;
		}
		Set<T> visited = new HashSet<T>();
		setVisited(visited, s);
		Queue<T> q = new LinkedList<T>();
		q.add(s);
		Collection<T> allNodes = g.getVertexes();
		while (!q.isEmpty()) {
			T u = q.poll();
			func.visit(u);
			for (T v : allNodes) {
				if (g.isConnected(u, v) && !isVisited(visited, v)) {
					visited.add(v);
					q.add(v);
				}
			}
		}
	}

	private static <T extends Vertex> void breadthFirstSearch2(Graph<T> g, T s,
			TraverseFunc<T> func) {
		if (g == null || !g.hasVertex(s)) {
			return;
		}
		Set<T> visited = new HashSet<T>();
		setVisited(visited, s);
		Queue<T> q = new LinkedList<T>();
		q.add(s);
		while (!q.isEmpty()) {
			T u = q.poll();
			func.visit(u);
			Collection<T> adjNodes = g.getAdjVertexes(u);
			for (T v : adjNodes) {
				if (!isVisited(visited, v)) {
					visited.add(v);
					q.add(v);
				}
			}
		}
	}

	/**
	 * 
	 * Breadth-�0�3rst search is one of the simplest algorithms for searching a
	 * graph and the archetype for many important graph algorithms. Prim��s
	 * minimum-spanning- tree algorithm (Section 23.2) and Dijkstra��s
	 * single-source shortest-paths algorithm (Section 24.3) use ideas similar
	 * to those in breadth-�0�3rst search.
	 * 
	 * The results of breadth-�0�3rst search may depend upon the order in which the
	 * neigh- bors of a given vertex are visited in line 12: the breadth-�0�3rst
	 * tree may vary, but the distances d computed by the algorithm will not.
	 * 
	 * Use Queue
	 * 
	 * the queue holds at most two distinct d values
	 * 
	 * O(V+E) for adjacency-list graph
	 * 
	 * @param g
	 * @param s
	 * @param p
	 */

	public static <T extends IPathVertex & IColoredVertex> void bfsReachableNodesBy3ColorForLearning(
			Graph<T> g, T s, T end, TraverseFunc<T> p) {
		final String method = "breadthFirstSearchUsingColor";
		if (g == null || !g.hasVertex(s)) {
			return;
		}
		Collection<T> nodes = g.getVertexes();
		for (T node : nodes) {
			node.setColor(IColoredVertex.COLOR_WHITE);
			node.setDistance(Integer.MAX_VALUE);
			node.setPrecedence(null);
		}
		s.setColor(IColoredVertex.COLOR_GRAY);
		s.setDistance(0);
		// ///////////////////////
		Queue<T> q = new LinkedList<T>();
		q.add(s);

		int c = 0;
		while (!q.isEmpty()) {
			++c;
			if (GraphAlgorithm.logger.isLoggable(Level.FINEST)) {
				GraphAlgorithm.logger.logp(Level.FINEST,
						GraphAlgorithm.CLASS_NAME, method,
						"Queue(at most two differnt distance value):\n" + q);
			}
			T u = q.poll();

			Collection<T> adjNodes = g.getAdjVertexes(u);
			for (T v : adjNodes) {
				// it may be 3 colors here
				if (GraphAlgorithm.logger.isLoggable(Level.FINEST)) {
					GraphAlgorithm.logger.logp(
							Level.FINEST,
							GraphAlgorithm.CLASS_NAME,
							method,
							"[" + c + "]Checking " + u.getName() + "'s adj "
									+ v.getName() + " with color "
									+ v.getColor());
				}
				if (v.getColor() == IColoredVertex.COLOR_WHITE) {
					v.setColor(IColoredVertex.COLOR_GRAY);
					v.setDistance(u.getDistance() + 1);
					v.setPrecedence(u);
					if (GraphAlgorithm.logger.isLoggable(Level.FINEST)) {
						GraphAlgorithm.logger.logp(Level.FINEST,
								GraphAlgorithm.CLASS_NAME, method, "[" + c
										+ "]For color " + v.getColor() + ": "
										+ v.getName() + ".d = " + u.getName()
										+ ".d + 1");

					}
					Assert.assertEquals(v.getDistance(), u.getDistance() + 1);
					q.add(v);
				} else if (v.getColor() == IColoredVertex.COLOR_GRAY) {
					if (GraphAlgorithm.logger.isLoggable(Level.FINEST)) {
						GraphAlgorithm.logger.logp(Level.FINEST,
								GraphAlgorithm.CLASS_NAME, method, "[" + c
										+ "]For color " + v.getColor() + ": "
										+ v.getName() + ".d <= " + u.getName()
										+ ".d + 1");

					}
					Assert.assertTrue(v.getDistance() <= u.getDistance() + 1);
				} else if (v.getColor() == IColoredVertex.COLOR_BLACK) {
					if (GraphAlgorithm.logger.isLoggable(Level.FINEST)) {
						GraphAlgorithm.logger.logp(Level.FINEST,
								GraphAlgorithm.CLASS_NAME, method, "[" + c
										+ "]For color " + v.getColor() + ": "
										+ v.getName() + ".d <= " + u.getName()
										+ ".d");
					}
					Assert.assertTrue(v.getDistance() <= u.getDistance());
				}
			}
			u.setColor(IColoredVertex.COLOR_BLACK);
			if (p != null)
				p.visit(u);
			if (end != null && u.equals(end)) {
				return;
			}
		}
	}

	public static <T extends IPathVertex & IColoredVertex> Graph<T> constructBreadthFirstTree(
			final Graph<T> g, final T s, Graph<T> bfsTree, Class<T> c) {
		if (g == null || s == null || bfsTree == null || c == null) {
			return null;
		}
		bfsReachableNodesBy2Color(g, s, null);
		// printAllShortestPath(g);
		//
		//
		bfsTree.clear();
		Collection<T> nodes = g.getVertexes();
		for (T node : nodes) {
			if (!node.equals(s) && node.getPrecedence() != null) {
				@SuppressWarnings("unchecked")
				T preNode = (T) node.getPrecedence();
				Integer cost = g.getCost(preNode, node);
				bfsTree.addEdge(preNode, node, cost);
			}
		}
		return bfsTree;
	}

	private static <T extends Vertex> void bfsUsingArray(Graph<T> g, T startNode) {
		Collection<T> nodes = g.getVertexes();
		ArrayList<LinkedList<T>> lists = new ArrayList<LinkedList<T>>(
				nodes.size());
		// û��add֮ǰ�����ܳ�ʼ��ʱ������������С���Բ���ʹ��set
		// lists.set(0, new LinkedList<Integer>());
		lists.add(new LinkedList<T>());
		lists.get(0).add(startNode);
		int c = 0;
		Set<T> visited = new HashSet<T>();
		System.out.print("bfsUsingArray " + startNode + " ");
		setVisited(visited, startNode);
		while (c < nodes.size() && !lists.get(c).isEmpty()) {
			lists.add(new LinkedList<T>());
			for (T u : lists.get(c)) {
				for (T v : nodes) {
					if (!isVisited(visited, v) && g.isConnected(u, v)) {
						setVisited(visited, v);
						lists.get(c + 1).add(v);
						System.out.print(v + " ");
					}
				}
			}
			c++;
		}
		System.out.println();

	}

	//
	private static <T extends Vertex> void bfsUsingTwoList(Graph<T> g,
			T startNode) {
		LinkedList<T> lastLayer = new LinkedList<T>();
		LinkedList<T> curLayer = new LinkedList<T>();
		// ���ڽ����������������
		LinkedList<T> t = null;
		lastLayer.add(startNode);
		// int c = 0;
		System.out.print("bfsUsingTwoList " + startNode + " ");
		Set<T> visited = new HashSet<T>();
		setVisited(visited, startNode);
		Collection<T> nodes = g.getVertexes();
		while (!lastLayer.isEmpty()) {
			// System.out.println("c="+c);
			curLayer.clear();
			for (T u : lastLayer) {
				// System.out.println("lastLayer:"+u);
				for (T v : nodes) {
					// System.out.println("next="+v);
					// System.out.println(g.isConnected(u, v));
					if (!isVisited(visited, v) && g.isConnected(u, v)) {
						setVisited(visited, v);
						curLayer.add(v);
						System.out.print(v + " ");
					}
				}
			}
			t = lastLayer;
			lastLayer = curLayer;
			curLayer = t;
			// c++;
		}
		System.out.println();

	}

}
