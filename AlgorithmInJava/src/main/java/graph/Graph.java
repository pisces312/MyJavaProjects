/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * cost is integer type and support negative
 * 
 * support directed/undirected graph
 * 
 * support weighted/unweighted graph
 * 
 * every vertex should be different, the name is unique
 * 
 * allow the edge to itself
 * 
 * adjacency-list graph not fully supports multiple graph
 * 
 * TODO adjacency-matrix graph only supports unweighted multiple graph
 * 
 * TODO add func to support change directed graph to undirected graph
 * 
 * TODO MultiUnweightedGraph
 * 
 * @author Administrator
 */
public abstract class Graph<T extends IVertex> {
	public static final Logger logger = Logger.getLogger(Graph.class
			.getPackage().getName());
	public static final String CLASS_NAME = Graph.class.getName();
	protected final boolean isDirectedGraph;
	protected final boolean isWeightedGraph;
	protected final boolean isMultiGraph;
	// TODO
	protected boolean isImmutableGraph = false;

	public Graph(boolean isDirectedGraph, boolean isWeighted,
			boolean isAllowMultiEdge) {
		this.isDirectedGraph = isDirectedGraph;
		this.isWeightedGraph = isWeighted;
		this.isMultiGraph = isAllowMultiEdge;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Graph<T> clone() {
		Graph<T> g = createEmptyGraph();
		g.addVertexes(getVertexes());
		Collection<Edge<T>> edges = getEdges();
		for (Edge<T> e : edges) {
			g.addEdges(e);
		}
		return g;
	}

	public Graph<T> createEmptyGraph() {
		@SuppressWarnings("unchecked")
		Class<Graph<T>> gc = (Class<Graph<T>>) getClass();
		try {
			Constructor<Graph<T>> ctor = gc.getConstructor(Boolean.TYPE,
					Boolean.TYPE, Boolean.TYPE);
			return ctor.newInstance(isDirectedGraph, isWeightedGraph,
					isMultiGraph);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public final boolean isMultiGraph() {
		return isMultiGraph;
	}

	public final boolean isDirectedGraph() {
		return isDirectedGraph;
	}

	public final boolean isWeightedGraph() {
		return isWeightedGraph;
	}

	public abstract int getOutDegree(T node);

	public abstract int getInDegree(T node);

	// public void setDirected(boolean flag) {
	// isDirectedGraph = flag;
	// }

	public T getNodeByName(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		}
		T beginNode = null;
		for (T node : getVertexes()) {
			if (name.equals(node.getName())) {
				beginNode = node;
				break;
			}
		}
		return beginNode;
	}

	public boolean isEmpty() {
		Collection<T> nodes = getVertexes();
		if (nodes == null || nodes.isEmpty()) {
			return true;
		}
		return false;
	}

	private T createAnyVertex(Constructor<T> ctor, String name) {
		try {
			return ctor.newInstance(name);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public T createAnyVertex(Class<T> c, String name) {
		try {
			Constructor<T> ctor = c.getConstructor(String.class);
			return createAnyVertex(ctor, name);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;

	}

	// ///////////////////////////////////////////////////////////////
	/**
	 * Must return the one that first time created
	 * 
	 * @param vertex
	 * @return
	 */
	public abstract T addVertex(T node, boolean isNeedCopy);

	/**
	 * default is to copy the node
	 * 
	 * @param node
	 * @return
	 */
	public T addVertex(T node) {
		return addVertex(node, true);
	}

	public List<T> addVertexes(List<T> vertexes) {
		if (vertexes == null) {
			return null;
		}
		List<T> list = new ArrayList<T>(vertexes.size());
		for (T vertex : vertexes) {
			T node = addVertex(vertex);
			list.add(node);
		}
		return list;
	}

	public List<T> addVertexes(T[] vertexes) {
		if (vertexes == null) {
			return null;
		}
		List<T> list = new ArrayList<T>(vertexes.length);
		for (T vertex : vertexes) {
			T node = addVertex(vertex);
			list.add(node);
		}
		return list;
	}

	public List<T> addVertexes(Class<T> c, String... args) {
		Constructor<T> ctor = null;
		try {
			ctor = c.getConstructor(String.class);
			if (ctor != null) {
				List<T> vertexes = new ArrayList<T>(args.length);
				for (String vertex : args) {
					T node = addVertex(ctor.newInstance(vertex), false);
					vertexes.add(node);
				}
				return vertexes;
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	public abstract boolean removeVertex(final T vertex);

	// ////////////////////////////////////////////////////

	public abstract Edge<T> addEdge(final Edge<T> edge, boolean isNeedCopy);

	/**
	 * this is for adding existed edge
	 * 
	 * @param edge
	 * @return
	 */
	public Edge<T> addEdge(final Edge<T> edge) {
		return addEdge(edge, true);
	}

	public Edge<T> addEdge(String begin, String end) {
		return addEdge(begin, end, null);
	}

	public Edge<T> addEdge(String begin, String end, Integer cost) {
		T beginNode = getNodeByName(begin);
		if (beginNode == null) {
			return null;
		}
		T endNode = getNodeByName(end);
		if (endNode == null) {
			return null;
		}
		return addEdge(beginNode, endNode, cost);
	}

	public Edge<T> addEdge(T begin, T end) {
		return addEdge(begin, end, null);
	}

	// cost==null means non-weighted graph
	public Edge<T> addEdge(T begin, T end, Integer cost) {
		if (begin == null || end == null) {
			return null;
		}
		Edge<T> edge = new Edge<T>(begin, end, cost, isDirectedGraph);
		return addEdge(edge, true);
	}

	public List<Edge<T>> addEdges(final Edge<T>... edges) {
		if (edges == null) {
			return null;
		}
		List<Edge<T>> list = new ArrayList<Edge<T>>(edges.length);
		for (Edge<T> e : edges) {
			Edge<T> newEdge = addEdge(e, true);
			if (newEdge != null) {
				list.add(e);
			}
		}
		return list;
	}

	public Edge<T> addEdge(Class<T> c, String begin, String end) {
		return addEdge(c, begin, end, null);
	}

	public Edge<T> addEdge(Class<T> c, String begin, String end, Integer cost) {
		final String method = "addEdge";
		List<T> nodes = addVertexes(c, begin, end);
		if (nodes == null || nodes.size() < 2) {
			logger.logp(Level.WARNING, CLASS_NAME, method,
					"Failed to create new node");
			return null;
		}
		return addEdge(nodes.get(0), nodes.get(1), cost);
	}

	// //////////////////////////////////////////////////
	public abstract boolean removeEdge(T begin, T end);

	/**
	 * 
	 * @param node
	 * 
	 * @return
	 */
	public abstract void removeEdges(T node, boolean isRemoveIn,
			boolean isRemoveOut);

	public abstract void clear();

	// XXX incorrect for multigraph
	public abstract Integer getCost(final T begin, final T end);

	// //////////////////////////////////////////////////////////////
	/**
	 * get the existed edge from graph
	 * 
	 * @return
	 */
	public abstract Collection<Edge<T>> getEdges();

	/**
	 * the new edge won't be added to graph and may not be existed in graph
	 * 
	 * @param begin
	 * @param end
	 * @param cost
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Edge<T> createAnyEdge(T begin, T end, Integer cost) {
		final String method = "createAnyEdge";
		if (begin == null || end == null) {
			return null;
		}
		if (isWeightedGraph && cost == null) {
			logger.logp(Level.WARNING, CLASS_NAME, method,
					"cost can't be null for weighted graph");
			return null;
		}
		return new Edge(begin, end, cost, isDirectedGraph);
	}

	public abstract Edge<T> getExistedEdge(final T begin, final T end,
			final Integer cost);

	/**
	 * must return List, keep an order
	 * 
	 * @return a new list container but the element is from graph
	 */
	public List<T> getVertexes(Comparator<T> cmp) {
		List<T> list = getVertexes();
		if (cmp != null) {
			Collections.sort(list, cmp);
		}
		return list;
	}

	public abstract List<T> getVertexes();

	public T[] getVertexesArray(T[] cache) {
		if (cache == null) {
			return null;
		}
		Collection<T> vertexes = getVertexes();
		if (vertexes != null) {
			return vertexes.toArray(cache);
		}
		return null;
	}

	public abstract List<T> getAdjVertexes(final T node);

	public static final Comparator<? super IVertex> alphaComparator = new Comparator<IVertex>() {


		public int compare(IVertex o1, IVertex o2) {
			return o1.compareTo(o2);
		}
	};

	public List<T> getAdjVertexes(final T node, Comparator<? super IVertex> cmp) {
		List<T> nodes = getAdjVertexes(node);
		if (nodes != null && !nodes.isEmpty() && cmp != null) {
			Collections.sort(nodes, cmp);
		}
		return nodes;
	}

	public abstract boolean isConnected(final T begin, final T end);

	public abstract boolean hasVertex(T node);

	public abstract boolean hasEdge(Edge<T> e);

	public abstract int getEdgeNum();

	public abstract int getNodeNum();

	public abstract void transpose();

	/**
	 * not a directed graph
	 * 
	 * @return
	 */
	public boolean isTree() {
		if (isDirectedGraph) {
			return false;
		}
		Collection<T> nodes = getVertexes();
		if (nodes == null || nodes.isEmpty()) {
			return true;
		}

		return nodes.size() - 1 == getEdgeNum();
	}

	// TODO
	// equals
	// ////////////////////////////////
	public void print() {
		System.out.println("------------" + this + "--------------");
		System.out.println("isDirectedGraph:" + isDirectedGraph);
		System.out.println("isWeightedGraph:" + isWeightedGraph);
		System.out.println("isMultiGraph:" + isMultiGraph);
		// System.out.println("isMultiEdge:" + isMultiEdgeGraph);
		Collection<T> nodes = getVertexes();
		Collection<Edge<T>> edges = getEdges();
		System.out.println("node num:" + nodes.size() + "\nnodes:");
		for (T node : nodes) {
			System.out.println(node);
		}

		System.out.println("edge num:" + edges.size() + "\nedges:");

		for (Edge<T> edge : edges) {
			System.out.println(edge);
		}
	}

}
