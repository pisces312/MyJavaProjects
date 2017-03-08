package graph.adjacencylist;

import graph.Edge;
import graph.Graph;
import graph.adjacencymatrix.AdjacencyMatrixGraph;
import graph.adjacencymatrix.MVertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * clone the node in "addVertex"
 * 
 * prefer an adjacency-matrix representation, however, when the graph is dense E
 * is close to V^2
 * 
 * 
 * the node must use the first created one, it contains the LinkedList
 * 
 * save only one edge for both directed/undirected graph
 * 
 * @author nili66china@163.com
 */
public class AdjacencyListGraph<T extends LVertex> extends Graph<T> {
	public static final Logger logger = Logger
			.getLogger(AdjacencyListGraph.class.getPackage().getName());
	public static final String CLASS_NAME = AdjacencyListGraph.class.getName();
	ArrayList<T> nodes = new ArrayList<T>();

	public AdjacencyListGraph(boolean isDirected, boolean isWeighted,
			boolean isAllowMultiEdge) {
		super(isDirected, isWeighted, isAllowMultiEdge);
	}

	public AdjacencyListGraph(boolean isDirected, boolean isWeighted) {
		super(isDirected, isWeighted, false);
	}

	public AdjacencyListGraph(boolean isDirectedGraph) {
		this(isDirectedGraph, true);
	}

	/**
	 * [Core]
	 */
	public T addVertex(T node, boolean isNeedCopy) {
		final String method = "addVertex";
		if (node == null) {
			return null;
		}
		T existedNode = getExistedVertex(node);
		if (existedNode == null) {
			if (isNeedCopy) {				
				@SuppressWarnings("unchecked")
				T newNode = (T) node.createEmptyVertex();
				logger.logp(Level.FINER, CLASS_NAME, method, "add new node"
						+ newNode);
				nodes.add(newNode);
				return newNode;
			} else {
				nodes.add(node);
				logger.logp(Level.FINER, CLASS_NAME, method,
						"add the node from parameter " + node);
				return node;
			}

		} else {
			return existedNode;
		}
	}

	@Override
	public boolean removeVertex(T node) {
		// check if this vertex is exsited
		if (!hasVertex(node)) {
			return false;
		}
		boolean flag = false;
		// remove all edges
		Iterator<T> itr = nodes.iterator();
		while (itr.hasNext()) {
			T v = itr.next();
			if (v.removeAllEdgeNode(node, isMultiGraph)) {
				flag = true;
			}
			// remove node itself
			if (v.equals(node)) {
				v.clear();
				itr.remove();
			}
		}
		return flag;
	}

	/**
	 * p590 for undirected edge u-v, save both u->v and v->u
	 */
	@Override
	public Edge<T> addEdge(final Edge<T> edge, boolean isNeedCopy) {
		final String method = "addEdge";
		if (edge == null || edge.isDirected != isDirectedGraph) {
			System.err.println("edge.isDirected != isDirectedGraph");
			return null;
		}
		if (isWeightedGraph && edge.cost == null) {
			logger.logp(Level.WARNING, CLASS_NAME, method,
					"need cost for weighted graph");
			return null;
		}
		T begin = addVertex(edge.src, isNeedCopy);
		T end = addVertex(edge.dest, isNeedCopy);
		if (begin == null || end == null) {
			return null;
		}
		Edge<T> copiedEdge = new Edge(begin, end, edge.cost, isDirectedGraph);
		Edge<T> e = (Edge<T>) begin.addEdgeNode(copiedEdge, isMultiGraph);
		logger.logp(Level.FINER, CLASS_NAME, method, "adding edge " + edge);
		if (!isDirectedGraph) {
			end.addEdgeNode(copiedEdge, isMultiGraph);
		}
		return e;
	}

	/**
	 * if there is no edge after removing this edge, keep the vertex, allow
	 * orphan vertex
	 * 
	 * return false if there is no such edge
	 */
	@Override
	public boolean removeEdge(T begin, T end) {
		final String method = "removeEdge";
		T existedBeginNode = getExistedVertex(begin);
		T existedEndNode = getExistedVertex(end);
		if (existedBeginNode == null || existedEndNode == null) {
			return false;
		}
		logger.logp(Level.FINEST, CLASS_NAME, method, "removing "
				+ existedBeginNode + "->" + existedEndNode);
		boolean flag = false;
		if (isMultiGraph) {
			flag = existedBeginNode.removeEdgeNode(existedEndNode, null, false,
					isMultiGraph, false);
		} else {
			flag = existedBeginNode.removeEdgeNode(existedEndNode, null, false,
					isMultiGraph, true);
		}
		if (isDirectedGraph) {
			return flag;
		}
		if (isMultiGraph) {
			return flag
					&& existedEndNode.removeEdgeNode(existedBeginNode, null,
							false, isMultiGraph, false);
		} else {
			return flag
					&& existedEndNode.removeEdgeNode(existedBeginNode, null,
							false, isMultiGraph, true);
		}
	}

	@Override
	public void print() {
		super.print();
		System.out.println("\nData structure(Edge Node List):");
		for (T node : nodes) {
			// System.out.println( node + " adj: "+getAdjacencyNodes(node));
			node.print();
		}

	}

	/**
	 * TODO not support multigraph
	 */
	@Override
	public Integer getCost(T begin, T end) {
		if (isMultiGraph) {
			throw new UnsupportedOperationException();
		}
		return begin.getCost(end);
	}

	/**
	 * 
	 * Only depends on directed/multiple
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Edge<T>> getEdges() {
		// final String method = "getEdges";
		if (!isMultiGraph) {
			Collection<Edge<T>> set = new HashSet<Edge<T>>();
			for (T v : nodes) {
				set.addAll((Collection<? extends Edge<T>>) v.getOutEdges());
			}
			return set;
		} else {
			if (isDirectedGraph) {
				LinkedList<Edge<T>> list = new LinkedList<Edge<T>>();
				for (T v : nodes) {
					list.addAll((Collection<? extends Edge<T>>) v.getOutEdges());
				}
				return list;
			} else {
				Map<Edge<T>, Integer> map = new HashMap<Edge<T>, Integer>();
				for (T v : nodes) {
					for (Edge e : v.getOutEdges()) {
						Integer num = map.get(e);
						if (num != null) {
							map.put(e, num + 1);
						} else {
							map.put(e, 1);
						}
					}
				}
				// /////////////////////////////////////////
				LinkedList<Edge<T>> list = new LinkedList<Edge<T>>();
				for (Entry<Edge<T>, Integer> entry : map.entrySet()) {
					int count = entry.getValue() / 2;
					Edge<T> edge = entry.getKey();
					for (int i = 0; i < count; ++i) {
						list.add(edge);
					}
				}
				return list;
			}
		}
	}

	@Override
	public List<T> getVertexes() {
		return new ArrayList<T>(nodes);
	}

	@Override
	public boolean isConnected(T begin, T end) {
		return begin.isConnectedTo(end);
	}

	@Override
	public int getEdgeNum() {
		int edgeNum = 0;
		Iterator<T> itr = nodes.iterator();
		while (itr.hasNext()) {
			T v = itr.next();
			edgeNum += v.getEdgeNum();
		}
		if (!isDirectedGraph) {
			edgeNum /= 2;
		}
		// for undirected graph, the actual num is half of it
		return edgeNum;
	}

	@Override
	public int getNodeNum() {
		return nodes.size();
	}

	@Override
	public boolean hasEdge(Edge<T> e) {
		if (e == null) {
			return false;
		}
		T srcNode = getExistedVertex(e.src);
		T desNode = getExistedVertex(e.dest);
		if (srcNode == null || desNode == null) {
			return false;
		}
		return srcNode.getExistedEdge(e, false) != null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Edge<T> getExistedEdge(T begin, T end, Integer cost) {
		final String method = "getEdge";
		T srcNode = getExistedVertex(begin);
		T desNode = getExistedVertex(end);
		if (srcNode == null || desNode == null) {
			return null;
		}
		if (isWeightedGraph && cost == null) {
			logger.logp(Level.WARNING, CLASS_NAME, method,
					"cost can't be null for weighted graph");
			return null;
		}
		List<Edge> list = srcNode.getExistedEdge(new Edge(srcNode, desNode,
				cost, isDirectedGraph), isMultiGraph);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void clear() {
		if (isEmpty()) {
			return;
		}
		if (nodes != null) {
			for (T v : nodes) {
				v.clear();
			}
			nodes.clear();
		}
	}

	@Override
	public int getOutDegree(T node) {
		T existedNode = getExistedVertex(node);
		if (existedNode != null) {
			return existedNode.getEdgeNum();
		}
		return -1;
	}

	@Override
	public int getInDegree(T node) {
		T existedNode = getExistedVertex(node);
		if (existedNode == null) {
			return -1;
		}
		int c = 0;
		for (T v : nodes) {
			List<Edge> list = v.getExistedEdge(existedNode, isMultiGraph);
			if (list != null) {
				c += list.size();
			}
		}
		return c;
	}

	public AdjacencyMatrixGraph<MVertex> convertToMatricGraph() {
		AdjacencyMatrixGraph<MVertex> g = new AdjacencyMatrixGraph<MVertex>(
				nodes.size(), isDirectedGraph, isWeightedGraph);

		Collection<Edge<T>> edges = getEdges();
		for (Edge<T> e : edges) {
			g.addEdge(new Edge<MVertex>(new MVertex(e.src),
					new MVertex(e.dest), e.cost, isDirectedGraph), true);
		}
		return g;
	}

	/**
	 * time O(V+E)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void transpose() {
		final String method = "transpose";
		if (!isDirectedGraph) {
			// since the undirected graph is symmetry
			logger.logp(Level.WARNING, CLASS_NAME, method,
					"Not a directed graph");
			return;
		}

		// space O(E)
		LinkedList<?>[] edgeNodesArray = new LinkedList[nodes.size()];
		for (int i = 0; i < nodes.size(); ++i) {
			T node = nodes.get(i);
			node.index = i;
			edgeNodesArray[i] = node.getOutEdges();
			node.setOutEdgeNodes(null);
		}
		for (int i = 0; i < edgeNodesArray.length; ++i) {
			for (Object obj : edgeNodesArray[i]) {
				Edge e = (Edge) obj;
				T tgtNode = (T) nodes.get(i).getTargetVertex(e);
				addEdge(new Edge(tgtNode, nodes.get(i), e.cost, isDirectedGraph));
			}
		}
	}

	/**
	 * XXX
	 * 
	 * Iterate through every vertex in G. For every vertex vi, if there exists
	 * an adjacent vertex vj that is greater, then add vi to the adjacency list
	 * of j in G'. From here, we do the same for G', but in reverse, putting vi
	 * in vj's list if vi is greater. This gives us the requested graph, and
	 * with each step taking O(V+E) since we only iterated through the lists in
	 * both cases, the overall time is also O(V+E).
	 * 
	 * @param c
	 * @return
	 */
	public AdjacencyListGraph<T> convertToSingleGraph() {
		AdjacencyListGraph<T> g2 = new AdjacencyListGraph<T>(false,
				isWeightedGraph, false);
		g2.addVertexes(nodes);
		List<T> nodes2 = g2.getVertexes();
		for (int i = 0; i < nodes2.size(); ++i) {
			T node = nodes.get(i);
			node.index = i;
			T node2 = nodes2.get(i);
			node2.index = i;
		}
		// /////////////////////////////////
		// alg
		for (T vi : nodes) {
			// System.out.println(vi.getOutEdges());
			for (Edge e : vi.getOutEdges()) {
				T vj = (T) vi.getTargetVertex(e);
				if (vi.compareTo(vj) != 0) {
					g2.addEdge(new Edge(vj, vi, e.cost, isDirectedGraph));
				}
			}
		}

		return g2;
	}

	// ////////////////////////////////////////////
	@Override
	public boolean hasVertex(T node) {
		return nodes.contains(node);
	}

	private T getExistedVertex(T node) {
		int idx = nodes.indexOf(node);
		if (idx < 0) {
			return null;
		}
		return nodes.get(idx);
	}

	// //////////////////////////////////////////
	@SuppressWarnings("unchecked")
	@Override
	public final List<T> getAdjVertexes(T node) {
		T existedNode = getExistedVertex(node);
		if (existedNode == null) {
			return Collections.emptyList();
		}

		List<T> list = new ArrayList<T>(existedNode.getEdgeNum());
		for (Edge edgeNode : existedNode.getOutEdges()) {
			list.add((T) existedNode.getTargetVertex(edgeNode));
		}
		// System.out.println("----------" + existedNode + " adj-----------");
		// System.out.println(list);
		return list;
	}

	@Override
	public void removeEdges(T node, boolean isRemoveIn, boolean isRemoveOut) {
		T existedNode = getExistedVertex(node);
		if (existedNode == null) {
			return;
		}
		if (isDirectedGraph) {
			if (isRemoveOut) {
				// out
				existedNode.clear();
			}
			if (isRemoveIn) {
				// in
				for (T v : nodes) {
					if (!v.equals(existedNode)) {
						v.removeAllEdgeNode(existedNode, isMultiGraph);
					}
				}
			}
		} else {
			if (isRemoveIn || isRemoveOut) {
				existedNode.clear();
				for (T v : nodes) {
					if (!v.equals(existedNode)) {
						v.removeAllEdgeNode(existedNode, isMultiGraph);
					}
				}
			}
		}
	}

}
