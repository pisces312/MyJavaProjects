package graph.adjacencymatrix;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * special for sparse graph, E<<V^2
 * 
 * the id of node starts from 0
 * 
 * does not support multiple edge graph
 * 
 * weight can be negative or zero
 * 
 * 
 * *the node must use the first created one, it contains the id
 * 
 * @author Administrator
 */
public class AdjacencyMatrixGraph<T extends MVertex> extends Graph<T> {
	public static final Logger logger = Logger
			.getLogger(AdjacencyMatrixGraph.class.getPackage().getName());
	public static final String CLASS_NAME = AdjacencyMatrixGraph.class
			.getName();
	// 矩阵和当前节点数组的最大容量
	public final static int INITIAL_NODE_NUM = 5;
	// public final static Integer CONNECTED = 1;

	private int edgeNum;
	ArrayList<T> nodes;

	// if one id is blank, for remove
	boolean[] idFlags;
	int freeIdIdx = 0;
	// only save the id of node
	// 初始有一个默认大小，按4倍增大
	// int [][] costMatrix;
	// use Integer Object to support negative cost
	// Integer[][] costMatrix;
	Edge<T>[][] costMatrix;

	// public AdjacentGraph(AVertex [] nodes, boolean isDirected) {
	// // isMultiEdgeGraph = false;
	// initGraph(nodes, isDirected);
	// }
	public AdjacencyMatrixGraph(int initialSize, boolean isDirected,
			boolean isWeighted) {
		super(isDirected, isWeighted, false);
		init(initialSize);
	}

	public AdjacencyMatrixGraph(boolean isDirected, boolean isWeighted) {
		this(INITIAL_NODE_NUM, isDirected, isWeighted);
	}

	public AdjacencyMatrixGraph(boolean isDirected) {
		this(isDirected, true);
	}

	@Override
	public boolean removeEdge(T begin, T end) {
		final String method = "removeEdge";
		if (!isValidEdge(begin, end)) {
			logger.logp(Level.WARNING, CLASS_NAME, method, "remove edge "
					+ costMatrix[begin.id][end.id]);
			return false;
		}
		Edge<T> removedEdge = costMatrix[begin.id][end.id];
		if (removedEdge != null) {
			logger.logp(Level.FINER, CLASS_NAME, method, "remove edge "
					+ removedEdge);
			edgeNum--;
			costMatrix[begin.id][end.id] = null;
		}
		if (!isDirectedGraph) {
			removedEdge = costMatrix[end.id][begin.id];
			if (removedEdge != null) {
				logger.logp(Level.FINER, CLASS_NAME, method, "remove edge "
						+ removedEdge);
				costMatrix[end.id][begin.id] = null;
			}
		}
		return true;
	}

	public boolean isValidVertex(T v) {
		if (v == null || v.id >= costMatrix.length || v.id < 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isConnected(T begin, T end) {
		if (!isValidEdge(begin, end)) {
			System.out.println("invalid edge " + begin + "->" + end);
			return false;
		}
		return costMatrix[begin.id][end.id] != null;
	}

	@Override
	public void clear() {
		if (isEmpty()) {
			return;
		}
		init();
	}

	private void init() {
		init(INITIAL_NODE_NUM);
	}

	private void init(int nodeSize) {
		if (nodes != null)
			nodes.clear();
		nodes = null;
		costMatrix = null;
		int initialSize = INITIAL_NODE_NUM;
		if (nodeSize > 0) {
			initialSize = nodeSize;
		}
		nodes = new ArrayList<T>(initialSize);
		costMatrix = new Edge[initialSize][initialSize];
		clearIDFlags(initialSize);

	}

	@SuppressWarnings("unchecked")
	@Override
	public T addVertex(T node, boolean isNeedCopy) {
		final String method = "addVertex";
		int idx = nodes.indexOf(node);
		if (idx >= 0) {
			// System.out.println("existed node");
			return nodes.get(idx);
		}
		if (isNeedCopy) {
			node = ((T) node.createEmptyVertex());
			logger.logp(Level.FINER, CLASS_NAME, method, "add new node"
					+ node);			
		} else {
			logger.logp(Level.FINER, CLASS_NAME, method,
					"add the node from parameter " + node);
		}

		nodes.add(node);
		if (nodes.size() > costMatrix.length) {
			System.out.println("resize");
			int matrixSize = costMatrix.length * 2;
			Edge<T>[][] matrix2 = new Edge[matrixSize][matrixSize];
			for (int i = 0; i < costMatrix.length; i++) {
				for (int j = 0; j < costMatrix[i].length; j++) {
					matrix2[i][j] = costMatrix[i][j];
					// System.out.print(matrix2[i][j] + " ");
				}
				// System.out.println();
			}
			// for (int i = costMatrix.length; i < matrixSize; i++) {
			// for (int j = costMatrix.length; j < matrixSize; j++) {
			// matrix2[i][j] = null;
			// }
			// }
			costMatrix = null;
			costMatrix = matrix2;

			// for (int i = 0; i < costMatrix.length; i++) {
			// for (int j = 0; j < costMatrix[i].length; j++) {
			// System.out.print(costMatrix[i][j] + " ");
			// }
			// System.out.println();
			// }
			// System.out.println();
			resizeIDFlags(matrixSize);
		}
		int id = acquireID();
		if (id >= 0) {
			node.setId(id);
		}
		// System.out.println("adding vertex " + node);
		return node;
	}

	private int acquireID() {
		for (; freeIdIdx < idFlags.length; ++freeIdIdx) {
			if (!idFlags[freeIdIdx]) {
				idFlags[freeIdIdx] = true;
				return freeIdIdx;
			}
		}
		return -1;
	}

	private void releaseID(int id) {
		if (id < freeIdIdx) {
			freeIdIdx = id;
		}
	}

	private void resizeIDFlags(int newSize) {
		if (newSize <= idFlags.length) {
			return;
		}
		boolean[] idFlags2 = new boolean[newSize];
		System.arraycopy(idFlags, 0, idFlags2, 0, idFlags.length);
		freeIdIdx = idFlags.length;
		idFlags = null;
		idFlags = idFlags2;
	}

	private void clearIDFlags(int initilSize) {
		freeIdIdx = 0;
		idFlags = null;
		idFlags = new boolean[initilSize];
	}

	/**
	 * 
	 * 
	 * @param node
	 * @return
	 */
	@Override
	public boolean removeVertex(T node) {
		releaseID(node.id);
		return nodes.remove(node);
		// int index = nodes.indexOf(node);
		// if (index == -1) {
		// return false;
		// }
		// nodes.remove(index);
		// for (int i = index; i < nodes.size(); i++) {
		// nodes.get(i).id--;
		// }
		// return true;
	}

	// TODO
	// 删除节点后，压缩多余的空间
	public void vacuum() {

	}

	// 返回一个新的数组
	// 必须保证数组中元素的引用还是以前的！！！

	@Override
	public List<T> getVertexes() {
		return nodes;
		// AVertex [] n = new AVertex[nodes.size()];
		// //这里返回的不是原来的引用
		// // return nodes.toArray(new VNode[nodes.size()]);
		// int i = 0;
		// for (AVertex u : nodes) {
		// n[i++] = u;
		// }
		// // nodes.
		// return n;
	}

	@Override
	public int getNodeNum() {
		return nodes.size();
	}

	@Override
	public int getEdgeNum() {
		return edgeNum;
	}

	public boolean isValidEdge(Edge<T> e) {
		if (e == null) {
			return false;
		}
		if (e.isDirected != isDirectedGraph) {
			return false;
		}
		return isValidVertex(e.src) && isValidVertex(e.dest);
	}

	public boolean isValidEdge(T begin, T end) {
		return isValidVertex(begin) && isValidVertex(end);
	}

	// public abstract boolean isValidVertex(T v);
	@Override
	public Integer getCost(T begin, T end) {
		if (!isValidEdge(begin, end)) {
			return null;
		}
		if (!isWeightedGraph) {
			return null;
		}
		if (costMatrix[begin.id][end.id] == null) {
			return null;
		}
		return costMatrix[begin.id][end.id].cost;
	}

	@Override
	public Collection<Edge<T>> getEdges() {
		LinkedList<Edge<T>> edges = new LinkedList<Edge<T>>();
		if (isDirectedGraph) {
			for (int u = 0; u < nodes.size(); ++u) {
				for (int v = 0; v < nodes.size(); ++v) {
					if (costMatrix[u][v] != null) {
						edges.add(costMatrix[u][v]);
					}
				}
			}
		} else {
			// System.out.println("getEdges");
			for (int i = 0; i < costMatrix.length; i++) {
				for (int j = 0; j < i; j++) {
					if (costMatrix[i][j] != null) {
						edges.add(costMatrix[i][j]);
					}
				}
			}
		}
		return edges;
	}

	@Override
	public boolean hasEdge(Edge<T> e) {

		if (e == null) {
			return false;
		}
		if (e.isDirected != isDirectedGraph) {
			System.err.println("e.isDirected!=isDirectedGraph");
			return false;
		}
		return getExistedEdge(e.src, e.dest, e.cost) != null;
	}

	@Override
	public int getOutDegree(T node) {
		if (!isValidVertex(node)) {
			return -1;
		}
		int src = node.id;
		int c = 0;
		for (int i = 0; i < nodes.size(); ++i) {
			if (costMatrix[src][i] != null) {
				++c;
			}
		}
		return c;
	}

	@Override
	public int getInDegree(T node) {
		if (!isValidVertex(node)) {
			return -1;
		}
		int dest = node.id;
		int c = 0;
		for (int i = 0; i < nodes.size(); ++i) {
			if (costMatrix[i][dest] != null) {
				++c;
			}
		}
		return c;
	}

	// 转置
	// GT
	public void transpose() {
		final String method = "transpose";
		if (!isDirectedGraph) {
			// since the undirected graph is symmetry
			logger.logp(Level.WARNING, CLASS_NAME, method,
					"Not a directed graph");
			return;
		}
		// space:O(V^2)
		// time:O(V^2)
		boolean[][] matrixVisitedFlags = new boolean[costMatrix.length][costMatrix.length];
		for (int i = 0; i < costMatrix.length; ++i) {
			for (int j = 0; j < costMatrix.length; ++j) {
				if (costMatrix[i][j] != null && !matrixVisitedFlags[i][j]) {
					// don't need to set the original position's flag
					// matrixVisitedFlags[i][j] = true;
					matrixVisitedFlags[j][i] = true;
					Edge<T> t = costMatrix[j][i];
					costMatrix[j][i] = costMatrix[i][j];
					costMatrix[i][j] = t;

				}
			}
		}
	}

	@Override
	public void print() {
		super.print();
		System.out.println("\nData structure(Matrix):");
		System.out.print("  ");
		for (int i = 0; i < nodes.size(); ++i) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < nodes.size(); ++i) {
			System.out.print(i + " ");
			for (int j = 0; j < nodes.size(); ++j) {
				if (costMatrix[i][j] == null) {
					System.out.print("X" + " ");
				} else {
					if (isWeightedGraph) {
						System.out.print(costMatrix[i][j].cost + " ");
					} else {
						System.out.print("O ");
					}
				}
			}
			System.out.println();
		}

	}

	// @Override
	// public Edge<T> addEdge(String begin, String end) {
	// return addEdgeWithoutCreate(begin, end, 1);
	// }

	/**
	 * 
	 */
	@Override
	public Edge<T> addEdge(Edge<T> edge, boolean isNeedCopy) {
		final String method = "addEdge";
		logger.entering(CLASS_NAME, method);
		if (edge == null) {
			logger.logp(Level.WARNING, CLASS_NAME, method, "edge is null");
			return null;
		}
		if (edge.isDirected != isDirectedGraph) {
			logger.logp(Level.WARNING, CLASS_NAME, method,
					"edge's directed property is not the same as graph's");
			return null;
		}
		if (isWeightedGraph) {
			if (edge.cost == null) {
				logger.logp(Level.WARNING, CLASS_NAME, method,
						"need cost for weighted graph");
				return null;
			}
		}

		T begin = addVertex(edge.src, isNeedCopy);
		T end = addVertex(edge.dest, isNeedCopy);
		if (begin == null || end == null) {
			return null;
		}
		Edge<T> existedEdge = getExistedEdgeFromExistedVertex(begin, end,
				edge.cost);
		// /////////////////////////////////////////////
		if (existedEdge == null) {
			Edge<T> e = new Edge<T>(begin, end, edge.cost, isDirectedGraph);
			costMatrix[begin.id][end.id] = e;
			logger.logp(Level.FINER, CLASS_NAME, method, "add edge " + e);
			edgeNum++;
			if (!isDirectedGraph) {
				costMatrix[end.id][begin.id] = e;
			}
		} else {
			if (costMatrix[begin.id][end.id].cost != edge.cost) {
				costMatrix[begin.id][end.id].cost = edge.cost;
				logger.logp(Level.FINER, CLASS_NAME, method, "update edge "
						+ costMatrix[begin.id][end.id]);
			}
		}
		logger.exiting(CLASS_NAME, method);
		return costMatrix[begin.id][end.id];
	}

	@Override
	public List<T> getAdjVertexes(T node) {
		if (!hasVertex(node)) {
			return Collections.emptyList();
		}
		List<T> list = new LinkedList<T>();
		for (int i = 0; i < nodes.size(); ++i) {
			if (costMatrix[node.id][i] != null) {
				list.add(nodes.get(i));
			}
		}
		return list;
	}

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

	private Edge<T> getExistedEdgeFromExistedVertex(T srcNode, T desNode,
			Integer cost) {
		if (srcNode == null || desNode == null) {
			return null;
		}
		boolean isExisted = (costMatrix[srcNode.id][desNode.id] != null && costMatrix[srcNode.id][desNode.id].cost == cost);
		if (isDirectedGraph) {
			if (isExisted) {
				return costMatrix[srcNode.id][desNode.id];
			}
		}
		if (costMatrix[desNode.id][srcNode.id] != null
				&& costMatrix[desNode.id][srcNode.id].cost == cost) {
			return costMatrix[desNode.id][srcNode.id];
		}
		return null;
	}

	@Override
	public Edge<T> getExistedEdge(T begin, T end, Integer cost) {
		final String method = "getExistedEdge";
		T srcNode = getExistedVertex(begin);
		T desNode = getExistedVertex(end);
		return getExistedEdgeFromExistedVertex(srcNode, desNode, cost);

	}

	@Override
	public void removeEdges(T node, boolean isRemoveIn, boolean isRemoveOut) {
		// TODO Auto-generated method stub

	}

}
