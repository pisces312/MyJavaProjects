package graph.algorithm;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import graph.Edge;
import graph.Edge.EdgeType;
import graph.Graph;
import graph.IColoredVertex;
import graph.ICounterVertex;
import graph.IPathVertex;
import graph.ITimeVertex;
import graph.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Administrator
 */
public class GraphAlgorithm {
	public static final Logger logger = Logger.getLogger(GraphAlgorithm.class
			.getPackage().getName());
	public static final String CLASS_NAME = GraphAlgorithm.class.getName();

	public interface TraverseFunc<T> {
		public void visit(T u);
	}

	private static <T extends Vertex> boolean isVisited(Collection<T> visited,
			T node) {
		return visited.contains(node);

	}

	private static <T extends Vertex> void setVisited(Collection<T> visited,
			T node) {
		visited.add(node);
	}

	public static <T extends IColoredVertex & ICounterVertex> int countSimplePathForDAG(
			Graph<T> g, T s, T t) {
		final String method = "countSimplePathForDAG";
		if (g == null || !g.hasVertex(s) || !g.hasVertex(t)) {
			return 0;
		}
		Collection<T> nodes = g.getVertexes();
		for (T node : nodes) {
			node.setColor(IColoredVertex.COLOR_WHITE);
			node.setCounter(0);
		}
		t.setCounter(1);
		// //////////////////////////////////////////////////

		Stack<T> stack = new Stack<T>();
		stack.push(s);
		logger.logp(Level.FINEST, CLASS_NAME, method, "push a new root " + s);
		while (!stack.isEmpty()) {
			T u = stack.peek();
			logger.logp(Level.FINEST, CLASS_NAME, method, "peek " + u);
			boolean isFound = false;
			for (T v : g.getAdjVertexes(u)) {
				if (v.getColor() == IColoredVertex.COLOR_WHITE) {
					if (v.equals(t)) {
						v.setColor(IColoredVertex.COLOR_BLACK);
						logger.logp(Level.FINEST, CLASS_NAME, method, "Find "
								+ v);
					} else {
						stack.push(v);
						v.setColor(IColoredVertex.COLOR_GRAY);
						logger.logp(Level.FINEST, CLASS_NAME, method, "push "
								+ v);
						isFound = true;
					}
					break;
				}
			}
			if (!isFound) {
				u = stack.pop();
				u.setColor(IColoredVertex.COLOR_BLACK);
				for (T v : g.getAdjVertexes(u)) {
					u.addCounter(v.getCounter());
				}
				logger.logp(Level.FINEST, CLASS_NAME, method, "pop " + u);
			}
		}

		return s.getCounter();
	}

	public static class TreeGenerator<T extends IPathVertex & ITimeVertex>
			implements TraverseFunc<T> {
		Graph<T> tree;
		Graph<T> graph;

		int finishedTimeOfTree = Integer.MIN_VALUE;
		int discoveredTimeofTree = Integer.MAX_VALUE;

		public TreeGenerator(Graph<T> originalGraph) {
			tree = originalGraph.createEmptyGraph();
			graph = originalGraph;
		}


		public void visit(T u) {
			if (discoveredTimeofTree > u.getDiscoveredTime()) {
				discoveredTimeofTree = u.getDiscoveredTime();
			}
			if (finishedTimeOfTree < u.getFinishedTime()) {
				finishedTimeOfTree = u.getFinishedTime();
			}
			// System.out.println("visit " + u);
			if (u.getPrecedence() == null) {
				tree.addVertex(u);
			} else {
				Integer cost = graph.getCost((T) u.getPrecedence(), u);
				tree.addEdge((T) u.getPrecedence(), u, cost);
			}
		}

		public Graph<T> getTree() {
			return tree;
		}

		public int getDiscoveredTimeOfTree() {
			return discoveredTimeofTree;
		}

		public int getFinishedTimeOfTree() {
			return finishedTimeOfTree;
		}
	}

	public static <T extends IColoredVertex & IPathVertex & ITimeVertex> boolean isStrongConnectedDirectedGraph(
			Graph<T> originalGraph) {
		List<List<T>> components = getAllStrongConnectedComponents(originalGraph);
		return components != null && components.size() == 1;
	}

	/**
	 * must be directed graph, allow circle
	 * 
	 * @param g
	 * @param vc
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends IColoredVertex & IPathVertex & ITimeVertex> List<List<T>> getAllStrongConnectedComponents(
			Graph<T> originalGraph) {
		if (originalGraph == null || !originalGraph.isDirectedGraph()) {
			return null;
		}
		Graph<T> g = originalGraph.clone();
		final LinkedList<T> decByFinishedTimeNodes = new LinkedList<T>();
		// 1) DFS to G and top sort
		DepthFirstSearch.dfsUsingStackWithTime(g, new TraverseFunc<T>() {

			public void visit(T u) {
				// sort by descending finish time
				decByFinishedTimeNodes.addFirst(u);
			}
		});
		// 2) transpose G
		g.transpose();
		// 3) DFS to G^T
		// System.out.println(decByFinishedTimeNodes);
		List<List<T>> components = new LinkedList<List<T>>();
		for (T node : decByFinishedTimeNodes) {
			node.setColor(IColoredVertex.COLOR_WHITE);
			node.setPrecedence(null);
		}
		// traverse in topologically sorted order for G^T
		for (T node : decByFinishedTimeNodes) {
			if (node.getColor() == IColoredVertex.COLOR_WHITE) {
				TreeGenerator<T> generator = new TreeGenerator<T>(g);
				DepthFirstSearch.dfsUsingStackBy2Color(g, node, generator);
				components.add(generator.getTree().getVertexes());
			}
		}
		return components;
	}

	/**
	 * �ж��Ƿ�����ͨͼ
	 * 
	 * @param startNode
	 * @return
	 */
	public static <T extends Vertex> boolean isConnectedGraph(Graph<T> g,
			T startNode) {
		if (g == null || startNode == null || !g.hasVertex(startNode)) {
			return false;
		}
		HashSet<T> r = new HashSet<T>();
		r.add(startNode);
		//
		HashSet<T> left = new HashSet<T>();
		Collection<T> nodes = g.getVertexes();
		for (T i : nodes) {
			if (startNode != i) {
				left.add(i);
			}
		}
		Iterator<T> rItr = r.iterator();
		while (rItr.hasNext()) {
			T uNode = rItr.next();
			Iterator<T> leftItr = left.iterator();
			while (leftItr.hasNext()) {
				T vNode = leftItr.next();
				if (g.isConnected(uNode, vNode)) {
					System.out.println("��" + vNode + "�ӵ�R��");
					leftItr.remove();
					r.add(vNode);
					rItr = r.iterator();
				}
			}
		}
		System.out.println("�����ڵ�" + startNode + "����ͨ��֧");
		for (T i : r) {
			System.out.print(i + " ");
		}
		System.out.println();
		return r.size() == g.getNodeNum();
		// return true;
	}

	/**
	 * �ҳ�������ͨ��֧ XXX
	 */
	public static <T extends Vertex> void findAllConnectedComponents(Graph<T> g) {
		Set<T> visited = new HashSet<T>();
		Collection<T> nodes = g.getVertexes();
		boolean isChanged = false;
		for (T k : nodes) {
			if (isVisited(visited, k)) {
				continue;
			}
			setVisited(visited, k);
			System.out.println("-----connected component-------");
			System.out.print(k + " ");
			while (!isChanged) {
				// System.out.println(visited);
				isChanged = false;
				for (T i : nodes) {
					if (!isVisited(visited, i)) {
						continue;
					}
					for (T j : nodes) {
						if (!isVisited(visited, j) && g.isConnected(i, j)) {
							setVisited(visited, j);
							System.out.print(j + " ");
							isChanged = true;
						}
					}
				}
			}
			System.out.println();
		}
	}

	/**
	 * �������� ֻ�������ͼ
	 * 
	 * it will change the original graph
	 */
	// public static <T extends Vertex> void topSortMethod1(Graph<T> g) {
	// if (g == null || !g.isDirectedGraph()) {
	// System.out.println("��ͼ��������ͼ");
	// return;
	// }
	//
	// T [] nodes = g.getVertexesArray();
	// if (nodes == null || nodes.length == 0) {
	// return;
	// }
	// /////////////////////////////////////////////////////////////
	// int i = 0, j = 0, c1 = 0, c2 = 0;
	// Set<T> visited = new HashSet<T>();
	//
	// while (true) {
	// c2 = c1;
	// for (i = 0; i < nodes.length; i++) {
	// if (!isVisited(visited, nodes[i])) {
	// for (j = 0; j < nodes.length; j++) {
	// if (g.isConnected(nodes[j], nodes[i])) {
	// break;
	// }
	// }
	// if (j == nodes.length) {
	// setVisited(visited, nodes[i]);
	// System.out.println(nodes[i]);
	// for (j = 0; j < nodes.length; j++) {
	// //�Ƴ���ȥ�Ľڵ�ı�
	// g.removeEdge(nodes[i], nodes[j]);
	// }
	// c1++;
	// }
	// }
	//
	// }
	// //���һ������û���ҵ�����ȵĽڵ㣬��˵�����ܲ����������
	// if (c1 == c2) {
	// if (c1 == nodes.length) {
	// System.out.println("�ɹ������������");
	// } else {
	// // System.out.println(i + " " + j);
	// System.out.print("�޷���ɣ���ʣ");
	// for (j = 0; j < nodes.length; j++) {
	// if (!isVisited(visited, nodes[j])) {
	// System.out.print(nodes[i] + " ");
	// }
	// }
	// }
	// break;
	// }
	// }
	// }

	//
	// ʹ�ö�������¼ÿ��ڵ�

	// TODO ???
	public static <T extends Vertex> boolean isBipartiteGraph(Graph<T> g) {
		if (g == null || g.getNodeNum() == 0) {
			return false;
		}
		Collection<T> nodes = g.getVertexes();
		T startNode = nodes.iterator().next();
		Map<T, Boolean> colors = new HashMap<T, Boolean>();
		// boolean [] colors = new boolean[nodes.length];
		// ����Ϊ��ż��Ϊ��
		ArrayList<LinkedList<T>> lists = new ArrayList<LinkedList<T>>(
				nodes.size());
		lists.add(new LinkedList<T>());
		lists.get(0).add(startNode);
		Set<T> visited = new HashSet<T>();
		int c = 0;
		setVisited(visited, startNode);
		while (c < nodes.size() && !lists.get(c).isEmpty()) {
			lists.add(new LinkedList<T>());
			boolean color = (c % 2 != 0);
			for (T u : lists.get(c)) {
				colors.put(u, color);
				for (T v : nodes) {
					if (!isVisited(visited, v) && g.isConnected(u, v)) {
						setVisited(visited, v);
						lists.get(c + 1).add(v);
						// System.out.print(v + " ");
					}
				}
			}
			c++;
		}
		// System.out.println();

		for (T i : nodes) {
			for (T j : nodes) {
				if (g.isConnected(i, j)) {
					if (colors.get(i) == colors.get(j)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	// //////////////////////////////////////////////////////////////////////////////////
	/**
	 * p601
	 * 
	 * @param g
	 * @param s
	 * @param v
	 */
	public static <T extends IPathVertex> List<IPathVertex> constructPath(
			Graph<T> g, T s, T v) {
		List<IPathVertex> list = new LinkedList<IPathVertex>();
		if (g != null && g.hasVertex(s) && g.hasVertex(v)) {
			constructPath(list, s, v);
		}
		return list;
	}

	private static void constructPath(List<IPathVertex> list, IPathVertex s,
			IPathVertex v) {
		if (v == s) {
			list.add(v);
			// System.out.println(s.getName());
		} else if (v.getPrecedence() == null) {
			// System.out.println("no path from " + s + " to " + v + " exists");
		} else {
			constructPath(list, s, v.getPrecedence());
			list.add(v);
			// System.out.println(v.getName());
		}
	}

	public static <T extends IPathVertex> List<IPathVertex> printPath2(
			Graph<T> g, T s, T v) {
		if (g != null && g.hasVertex(s) && g.hasVertex(v)) {
			return getTravseOrder(s, v);
		}
		return Collections.emptyList();
	}

	public static List<IPathVertex> getTravseOrder(IPathVertex s,
			IPathVertex node) {
		LinkedList<IPathVertex> list = new LinkedList<IPathVertex>();
		IPathVertex pre = node;
		while (pre != null) {
			list.addFirst(pre);
			pre = pre.getPrecedence();
		}
		if (!list.isEmpty() && !list.getFirst().equals(s)) {
			// not found path
			list.clear();
		}
		return list;
	}

	public static <T extends IPathVertex & IColoredVertex> void printAllShortestPath(
			Graph<T> g, T s) {
		List<T> nodes = g.getVertexes();
		for (T node : nodes) {
			List<IPathVertex> order = getTravseOrder(s, node);
			for (IPathVertex vertex : order) {
				System.out.print(vertex.getName() + " ");
			}
			System.out.println();
		}

	}

	// //////////////////////////////////////////////////////////////////////////////////
	public static <T extends IColoredVertex & IPathVertex> int getDiameterOfTree(
			final Graph<T> g, T root) {
		if (g == null || !g.isTree() || root == null) {
			return -1;
		}

		if (g.getNodeNum() == 0) {
			return 0;
		}
		if (g.getNodeNum() == 1) {
			return 1;
		}

		final int[] max = new int[2];

		// final List<T> sortedNodes = new ArrayList<T>(g.getNodeNum());
		BreathFirstSearch.bfsReachableNodesBy2Color(g, root,
				new TraverseFunc<T>() {
					int c = 0;

	
					public void visit(T vertex) {
						max[c % 2] = vertex.getDistance();
						++c;
						// sortedNodes.add(vertex);
					}
				});

		int d = 0;
		for (int m : max) {
			d += m;
		}
		return d;
	}

	public static <T extends Vertex> boolean isTopologicalSorted(Graph<T> g,
			List<T> sortedNode) {
		if (g == null || !g.isDirectedGraph() || sortedNode == null) {
			return false;
		}
		if (g.getNodeNum() != sortedNode.size()) {
			return false;
		}
		List<T> nodes = g.getVertexes();
		if (!nodes.containsAll(sortedNode)) {
			return false;
		}
		for (int i = 0; i < sortedNode.size(); ++i) {
			for (int j = i + 1; j < sortedNode.size(); ++j) {
				// if exists reversed edge, return false;
				if (g.isConnected(sortedNode.get(j), sortedNode.get(i))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * only for dag
	 * 
	 * it will change the original graph
	 */
	public static <T extends Vertex> List<T> topologicalSortByRemoval(Graph<T> g) {
		if (g == null || !g.isDirectedGraph()) {
			return null;
		}

		final LinkedList<T> list = new LinkedList<T>();
		Collection<T> nodes = g.getVertexes();
		boolean isChanged = true;
		while (isChanged) {
			isChanged = false;
			Iterator<T> itr = nodes.iterator();
			while (itr.hasNext()) {
				T node = itr.next();
				if (g.getInDegree(node) == 0) {
					isChanged = true;
					list.add(node);
					g.removeEdges(node, false, true);
					itr.remove();
				}
			}
		}
		return list;

		// boolean isConnected = true;
		// List<T> result = new LinkedList<T>();
		// Collection<T> nodes = g.getVertexes();
		// HashSet<T> set = new HashSet<T>(nodes);
		// Iterator<T> endNodeItr = set.iterator();
		// boolean isChanged = false;// ����ʹ�ü��ϣ�����ѭ������Ҫ���¿�ʼ
		// while (!set.isEmpty()) {
		// T endNode = endNodeItr.next();
		// boolean flag = false;
		// for (T beginNode : set) {
		// if (g.isConnected(beginNode, endNode)) {
		// flag = true;
		// break;
		// }
		// }
		// // ����ýڵ�û�������ɾ
		// if (!flag) {
		// isChanged = true;// ֻҪ�ı�һ�μ�Ϊ�ı�
		// endNodeItr.remove();
		// result.add(endNode);
		// // System.out.print(endNode + " ");
		// for (T node : set) {
		// // �Ƴ���ȥ�Ľڵ�ı�
		// // System.out.println("remove");
		// g.removeEdge(endNode, node);
		// }
		// }
		// if (!set.isEmpty()) {
		// if (!endNodeItr.hasNext()) {// ���������ף���ʱҪ�ж��Ƿ��ͷ��������
		// // System.out.println("end");
		// if (isChanged) {
		// endNodeItr = set.iterator();
		// isChanged = false;
		// } else {
		// // ����ͨͼ
		// System.out.print("�޷���ɣ���ʣ");
		// for (T node : set) {
		// System.out.print(node + " ");
		// }
		// isConnected = false;
		// break;
		//
		// }
		// }
		// }
		// }
		// if (isConnected) {
		// return result;
		// }
		// return null;
		// System.out.println();
	}

	/**
	 * TOPOLOGICAL-SORT(G)
	 * 
	 * The graph must be DAG
	 * 
	 * O(V+E)
	 * 
	 * 1 call DFS(G) to compute finishing times v.f for each vertex v
	 * 
	 * 2 as each vertex is finished, insert it onto the front of a linked list
	 * 
	 * 3 return the linked list of vertices
	 * 
	 * @param g
	 * @return
	 */
	public static <T extends IPathVertex & ITimeVertex & IColoredVertex> LinkedList<T> topologicalSortByDFS(
			Graph<T> g) throws Exception {
		if (g == null || !g.isDirectedGraph()) {
			return null;
		}
		final LinkedList<T> list = new LinkedList<T>();
		DepthFirstSearch.dfsUsingStackWithTime(g, new TraverseFunc<T>() {

			public void visit(T u) {
				// sort according to finish time
				list.addFirst(u);
			}
		});
		Collection<Edge<T>> edges = g.getEdges();
		for (Edge<T> e : edges) {
			if (e.type.equals(EdgeType.BACK_EDGE)) {
				throw new Exception("The Graph is not a DAG");
			}
		}
		return list;
	}

	public static <T extends IPathVertex & ITimeVertex & IColoredVertex> boolean isDAG(
			Graph<T> g) {
		if (g == null || !g.isDirectedGraph()) {
			return false;
		}
		DepthFirstSearch.dfsUsingStackWithTime(g, null);
		Collection<Edge<T>> edges = g.getEdges();
		for (Edge<T> e : edges) {
			if (e.type.equals(EdgeType.BACK_EDGE)) {
				return false;
			}
		}
		return true;
	}
}
